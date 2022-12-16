import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ClientHandlerRunnable implements Runnable {
    private final Socket clientSocket_;
    private String fileName_;
    private Room room_ = null;
    private String userName_ = null;
    private final HashMap<String, String> headers_ = new HashMap<>();
    static final String rootDir_ = "resources";

    public ClientHandlerRunnable(Socket socket) {
        this.clientSocket_ = socket;
    }

    public String getUserName() {
        return this.userName_;
    }

    public void run() {
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(this.clientSocket_.getInputStream()));
            this.parseHeader(bufferedReader);
            if (this.headers_.containsKey("sec-websocket-key")) {
                if (MyWebServer.verbose_) {
                    System.out.println("Web socket was requested...");
                }

                this.handleWebSocket();
                return;
            }

            this.handleRequestedFile(this.clientSocket_.getOutputStream());
            this.clientSocket_.close();
        } catch (NoSuchAlgorithmException | IOException exception) {
            if (MyWebServer.verbose_) {
                System.out.println("Client Handler Error: " + exception.getMessage());
            }

            exception.printStackTrace();
        }

    }

    private void handleWebSocket() throws NoSuchAlgorithmException, IOException {
        OutputStream outputStream = this.clientSocket_.getOutputStream();
        String webSocketKey = (String)this.headers_.get("sec-websocket-key");
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        String encodeToString = Base64.getEncoder().encodeToString(messageDigest.digest((webSocketKey + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").getBytes()));
        outputStream.write(("HTTP/1.1 101 Switching Protocols\r\nUpgrade: websocket\r\nConnection: Upgrade\r\nSec-WebSocket-Accept: " + encodeToString + "\r\n\r\n").getBytes());
        outputStream.flush();

        try {
            while(true) {
                this.readWebSocketMessage();
            }
        } catch (Exception exception) {
            if (MyWebServer.verbose_) {
                System.out.println("Exception happened when handling WS request for client: " + this.userName_);
            }

            if (this.room_ != null) {
                this.room_.removeClient(this);
                String leaveMessage = MyJsonCreator.createLeaveMessage(this.userName_, this.room_.getName());
                this.room_.sendMessage(leaveMessage, false);
            }

        }
    }

    private void readWebSocketMessage() throws Exception {
        try {
            DataInputStream inputStream = new DataInputStream(this.clientSocket_.getInputStream());
            byte[] first2Bytes = inputStream.readNBytes(2);
//            boolean var3 = (first2Bytes[0] & 128) > 0;
            int lower8bits = first2Bytes[0] & 15;
            if (lower8bits == 8) {
                throw new Exception("Connection Closed");
            }

//            boolean var5 = (first2Bytes[1] & 128) != 0;
            long higher8bits = (long)(first2Bytes[1] & 127);
            if (higher8bits == 126L) {
                higher8bits = (long)inputStream.readUnsignedShort();
            } else if (higher8bits == 127L) {
                higher8bits = inputStream.readLong();
            }

            byte[] maskingKey = inputStream.readNBytes(4);
            byte[] payload = inputStream.readNBytes((int)higher8bits);

            for(int i = 0; (long)i < higher8bits; ++i) {
                payload[i] ^= maskingKey[i % 4];
            }

            String decoded = new String(payload);
            String[] stringHeader = decoded.split(" ", 2);
            String msgType = stringHeader[0];
            String roomName;
            if (msgType.equals("join")) {
                if (this.userName_ != null) {
                    System.out.println("WARNING: client is re-joining...");
                }

                String[] userInfo = stringHeader[1].split(" ", 2);
                this.userName_ = userInfo[0];
                roomName = userInfo[1];
                Room room = Room.getRoom(roomName);
                this.room_ = room;
                room.addClient(this);
                if (this.userName_.equals("Server")) {
                    System.out.println("WARNING: Client tried to name themself 'Server'");
                }
            } else {
                String userName = stringHeader[0];
                String msg;
                if (!userName.equals(this.userName_)) {
                    msg = MyJsonCreator.createMessage("Server:", this.room_.getName(), "User '" + this.userName_ + "' tried to impersonate '" + userName + "'");
                    this.room_.sendMessage(msg, false);
                } else {
                    String content = stringHeader[1];
                    msg = MyJsonCreator.createMessage(this.userName_, this.room_.getName(), content);
                    this.room_.sendMessage(msg, true);
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    public synchronized void sendWebSocketMessage(String msg) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(this.clientSocket_.getOutputStream());
        outputStream.writeByte(129);
        if (msg.length() < 126) {
            outputStream.write(msg.length());
        } else if (msg.length() < 65535) {
            outputStream.writeByte(126);
            outputStream.writeShort(msg.length());
        } else {
            outputStream.writeByte(127);
            outputStream.writeLong((long)msg.length());
        }

        outputStream.write(msg.getBytes());
        outputStream.flush();
    }

    private void handleRequestedFile(OutputStream outputStream) throws IOException {
        PrintWriter printWriter = new PrintWriter(outputStream, true);
        File file = new File(this.fileName_);
        String statusCode = "200 OK";
        String contentType = "unknown";
        String htmlContent = null;
        FileInputStream fileInputStream = null;
        if (MyWebServer.verbose_) {
            System.out.println("Handle request for: " + this.fileName_);
        }

        long contentLength;
        if (!file.exists()) {
            statusCode = "404 Not Found";
            htmlContent = "<html><head></head><body>404: File Not Found</body></html>";
            contentLength = (long)htmlContent.length();
            contentType = "text/html";
        } else {
            boolean isSucceed = false;
            contentLength = file.length();
            fileInputStream = new FileInputStream(file);
            int dotIndex = this.fileName_.lastIndexOf(".");
            if (dotIndex != -1) {
                String fileSuffix = this.fileName_.substring(dotIndex + 1);
                if (!fileSuffix.equals("html") && !fileSuffix.equals("css")) {
                    if (!fileSuffix.equals("png") && !fileSuffix.equals("jpg")) {
                        if (fileSuffix.equals("js")) {
                            contentType = "text/javascript";
                            isSucceed = true;
                        }
                    } else {
                        contentType = "image/" + fileSuffix;
                        isSucceed = true;
                    }
                } else {
                    contentType = "text/" + fileSuffix;
                    isSucceed = true;
                }
            }

            if (!isSucceed) {
                statusCode = "400 Bad Request";
                htmlContent = "<html><head></head><body>400: Bad Request for file " + this.fileName_ + "</body></html>";
                contentLength = (long)htmlContent.length();
                contentType = "text/html";
            }
        }

        if (!statusCode.equals("200 OK") && MyWebServer.verbose_) {
            System.out.println("Sending error of: " + statusCode);
        }

        printWriter.println("HTTP/1.1 " + statusCode);
        printWriter.println("Server: Java HTTP Server by Davison");
        Date date = new Date();
        printWriter.println("Date: " + date);
        printWriter.println("Content-type: " + contentType);
        printWriter.println("Content-length: " + contentLength);
        printWriter.println("");
        if (htmlContent == null) {
            fileInputStream.transferTo(outputStream);
        } else {
            printWriter.println(htmlContent);
        }

        printWriter.flush();
    }

    private void parseHeader(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        this.fileName_ = line.split(" ")[1];
        if (this.fileName_.equals("/")) {
            this.fileName_ = "/index.html";
        }

        this.fileName_ = "resources" + this.fileName_;
        boolean isEnded = false;

        while(!isEnded) {
            line = bufferedReader.readLine();
            if (line.equals("")) {
                isEnded = true;
            } else {
                String[] headerList = line.split(": ");
                this.headers_.put(headerList[0].toLowerCase(), headerList[1]);
            }
        }

//        boolean var7 = false;
//        if (var7) {
//            System.out.println("Header fields:");
//            Iterator entryIterator = this.headers_.entrySet().iterator();
//
//            while(entryIterator.hasNext()) {
//                Map.Entry nextEntry = (Map.Entry)entryIterator.next();
//                PrintStream out = System.out;
//                String key = (String)nextEntry.getKey();
//                out.println("\t" + key + ": " + (String)nextEntry.getValue());
//            }
//        }

    }
}
