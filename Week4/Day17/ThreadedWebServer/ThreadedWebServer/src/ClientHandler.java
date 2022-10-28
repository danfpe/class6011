import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class ClientHandler implements Runnable{
    private HashMap<String, String> keyValueMaps = new HashMap<String, String>();
    private final Socket clientSocket;
    public ClientHandler (Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run()
    {
        System.out.println("the running thread id: " + Thread.currentThread().getId());
        InputStream inputstream = null;
        try {
            inputstream = clientSocket.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scanner sc = new Scanner(inputstream);
        // read the first line
        String line = sc.nextLine();
        System.out.println("Line: " + line);
        String[] firstLine = line.split(" ");
        String path = firstLine[1];
        String protocol = firstLine[2];

        while (sc.hasNextLine()) {
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
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(homepage);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String result = "";
        if (homepage.exists()) {
            result = "200 successful";
        }
        else{
            result = "404 not found";
        }

        OutputStream outputStream = null;
        try {
            outputStream = clientSocket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PrintWriter pw = new PrintWriter(outputStream);
        // send the response header
        pw.println(protocol + result + "\n");
        pw.flush();
        try {
            inputStream.transferTo(outputStream);
            inputStream.close();
            pw.close();
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
