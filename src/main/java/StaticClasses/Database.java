/**
 Database static object to CRUD CSV
 @author Chen Yifan
 @version 1.0
 @since 2021-10-18
*/
package StaticClasses;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

/**
 * This static class implements helper methods.
 */
public final class Database{
    /**
     * The dir path of the CSV files.
     */
    private static String directory = "csv/";

    /**
     * WriteLine simply writes a line to the CSV.
     * It assumes that you know what you are doing and doesn't check for PK violation.
     *
     * @param csvName "Reservation.csv" or wtv
     * @param line line format =  {"2", "2021-10-13","2000","5", "Ryan", "995", "8"}; <- String[] type
     */
	public static void writeLine(String csvName, String[] line) throws IOException {
        try (ICSVWriter writer = new CSVWriterBuilder(
                new FileWriter(directory + csvName, true))
                .withQuoteChar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(';')
                .build()) {
            writer.writeNext(line);
        }
	}
    /**
     *
     * @param csvName The name of the CSV.
     * Pulls all data from a CSV.
     * @return An arraylist of hashmaps for all of the values in the CSV
     * @throws CsvException if csv exception
     * @throws IOException if fileio exception
     */
	public static ArrayList<HashMap<String,String>> readAll(String csvName) throws CsvException, IOException {
        try (CSVReader reader = new CSVReader(new FileReader(directory + csvName))) {
            //reading everything
            List<String[]> r = reader.readAll();
            //return obj
            ArrayList<HashMap<String,String>> dat = new ArrayList<>();
            //splitting headers

            String[] headers = r.get(0)[0].split(";");
            //parsing each line and assigning it to a hashmap
            for (int i = 1; i < r.size(); i++){
                HashMap<String, String> tempHM = new HashMap<>();
                String[] tempAr = r.get(i)[0].split(";");
                for (int j = 0; j < tempAr.length; j++){
                    tempHM.put(headers[j], tempAr[j]);
                }
                dat.add(tempHM);
            }
            return dat;
        }
	}

    /**
     * Assumes you know what you are doing and PKs are unique.
     * Finds PK, removes it and rewrites the whole CSV. (inefficient i know)
     * @param csvName name of csv file
     * @param primaryKey pk
     * @param line line to write format =  {"2", "2021-10-13","2000","5", "Ryan", "995", "8"}; <- String[] type
     * @return whether updated or not
     * @throws IOException File IO Exception
     * @throws CsvException CSV Exception
     */
	public static boolean updateLine(String csvName, String primaryKey, String[] line) throws IOException, CsvException {
        try (var fr = new FileReader(directory + csvName, StandardCharsets.UTF_8);
             var reader = new CSVReader(fr)) {
            //reads everything
            String[] nextLine;
            List<String[]> writeDat = new ArrayList<>();
            nextLine = reader.readNext();
            //parse header
            nextLine = nextLine[0].split(";");
            writeDat.add(nextLine);
            boolean updated = false;
            //parse each row, skip adding to write arraylist if pk same, reformat to writable format
            while ((nextLine = reader.readNext()) != null) {
                nextLine = nextLine[0].split(";");
                if (!nextLine[0].equals(primaryKey)){
                    writeDat.add(nextLine);
                }
                else{
                    updated = true;
                }
            }
            //if skipped, perform update
            writeDat.add(line);
            if (updated){
                try (ICSVWriter writer = new CSVWriterBuilder(
                    new FileWriter(directory + csvName))
                    .withQuoteChar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withSeparator(';')
                    .build()) {
                writer.writeAll(writeDat);
                }
                return true;
            }
            return false;

        }

	}

    /**
     * Essentially the same code as update, however, it doesn't "modify" anything
     * @param csvName csvName
     * @param primaryKey pk
     * @return whether or not successfully removed
     * @throws IOException file io exception
     * @throws CsvValidationException csv exception
     */
	public static Boolean removeLine(String csvName, String primaryKey) throws IOException, CsvValidationException {
		try (var fr = new FileReader(directory + csvName, StandardCharsets.UTF_8);
             var reader = new CSVReader(fr)) {
            //reads everything
            String[] nextLine;
            List<String[]> writeDat = new ArrayList<>();
            nextLine = reader.readNext();
            //parse header
            nextLine = nextLine[0].split(";");
            writeDat.add(nextLine);
            boolean updated = false;
            //parse each row, skip adding to write arraylist if pk same, reformat to writable format
            while ((nextLine = reader.readNext()) != null) {
                nextLine = nextLine[0].split(";");
                if (!nextLine[0].equals(primaryKey)){
                    writeDat.add(nextLine);
                }
                else{
                    updated = true;
                }
            }
            //if skipped, perform update
            if (updated){
                try (ICSVWriter writer = new CSVWriterBuilder(
                    new FileWriter(directory + csvName))
                    .withQuoteChar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withSeparator(';')
                    .build()) {
                writer.writeAll(writeDat);
                }
                return true;
            }
            return false;

        }
	}
}