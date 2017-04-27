package UI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// used by DBloader. Reads files, and converts them into arraylist of array of strings
public class CSVReader {

    public static ArrayList<String[]> readFile(String csvFile) {

        String line = "";
        final String delim = ",";
        ArrayList<String[]> records = new ArrayList<String[]>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
                records.add(line.split(delim));//reads line and chops it off to create a string
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

}
