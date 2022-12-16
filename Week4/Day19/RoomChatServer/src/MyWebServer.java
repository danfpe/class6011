import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyWebServer {
    public static boolean verbose_ = false;

    public MyWebServer() {
    }

    public static void main(String[] var0) {
        if (var0.length > 0) {
            verbose_ = true;
        } else {
            System.out.println("Run with -v for verbose output...");
        }

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(8080);
        } catch (IOException ioException) {
            System.out.println("Failed to open server socket: " + ioException.getMessage());
            System.exit(-1);
        }

        if (verbose_) {
            System.out.println("Server running in directory: " + System.getProperty("user.dir"));
        }

        while(true) {
            if (verbose_) {
                System.out.println("Waiting for client to connect...");
            }

            Socket socket = null;

            try {
                socket = serverSocket.accept();
                ClientHandlerRunnable clientHandlerRunnable = new ClientHandlerRunnable(socket);
                (new Thread(clientHandlerRunnable)).start();
            } catch (IOException ioException) {
                System.out.println("Server socket failed accept: " + ioException.getMessage());
                ioException.printStackTrace();
            }
        }
    }
}
