import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Server {


    public void go() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            ClientHandler clientRunable = new ClientHandler(clientSocket);
            Thread thread1 = new Thread(clientRunable);
            thread1.start();
        }
    }
}

