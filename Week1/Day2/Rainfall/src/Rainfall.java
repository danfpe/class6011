import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
public class Rainfall {
    private String filename;
    private final Scanner fileReader;

    // constructor
    public Rainfall(String filename) throws FileNotFoundException {
        fileReader = new Scanner(new File(filename));
    }
    public void readLine() throws IOException {
        int lineNumber = 1;
        HashMap<String, ArrayList<Double>> dataMap = new HashMap<>();
        while (fileReader.hasNextLine()) {
            String line = fileReader.nextLine();
            String[] arrOfStr = line.split("\\s+");

            // put value to hashmap
            if (lineNumber > 1) {
                String key = arrOfStr[0];
                double value = Double.parseDouble(arrOfStr[2]);
                dataMap.computeIfAbsent(key, k -> new ArrayList<Double>());
                dataMap.get(key).add(value);
            }
            lineNumber++;
        }

        writeResult(dataMap);
    }

    public double calcAverage(ArrayList<Double> rainArray) {
        double sum = 0.0;
        for(double rain: rainArray) {
            sum += rain;
        }
        double average = sum / rainArray.size();
        return Math.round(average * 100.00) / 100.00;
    }

    public void writeResult(HashMap<String, ArrayList<Double>> dataMap) throws IOException {
        FileWriter writer = new FileWriter("result.txt");
        for(String key : dataMap.keySet()) {
            ArrayList<Double> rainArray = dataMap.get(key);
            String row = "The average rainfall amount for " + key + " is " + calcAverage(rainArray) + " inches";
            writer.write(row + "\n");
            System.out.println(row);
        }
        writer.close();
    }
}

