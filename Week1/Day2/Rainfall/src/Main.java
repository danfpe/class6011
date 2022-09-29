import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        Rainfall rainData = new Rainfall("Atlanta.txt");
        rainData.readLine();
    }
}