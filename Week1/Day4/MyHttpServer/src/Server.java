import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Server {
    private HashMap<String, String> keyValueMaps = new HashMap<String, String>();


    public void go() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            Socket socketToClient = serverSocket.accept();
            InputStream inputstream = socketToClient.getInputStream();
            Scanner sc = new Scanner(inputstream);
            // read the first line
            String line = sc.nextLine();
            System.out.println("Line: " + line);
            String[] firstLine = line.split(" ");
            String path = firstLine[1];
            String protocol = firstLine[2];
            while (!line.isEmpty()) {
                // split the line and store in hashmap
                line = sc.nextLine();
                if (line.isEmpty()) {
                    break;
                }
                String[] parseString = line.split(":",2);
                keyValueMaps.put(parseString[0], parseString[1]);
            }

            // open the request file ('filename')
            File homepage = new File("FrontEnd/index.html");
            FileInputStream inputStream = new FileInputStream(homepage);

            String result = "";
            if (homepage.exists()) {
                result = "200 successful";
            }
            else{
                result = "404 not found";
            }

            OutputStream outputStream = socketToClient.getOutputStream();
            PrintWriter pw = new PrintWriter(outputStream);
            // send the response header
            pw.println(protocol + result + "\n");
            pw.flush();
            inputStream.transferTo(outputStream);
            inputStream.close();
            pw.close();
            socketToClient.close();
        }
    }
}

