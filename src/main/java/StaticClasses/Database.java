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

import EntityClasses.*;
import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import groovy.json.JsonBuilder;
import groovy.json.JsonOutput;
import groovy.json.JsonSlurper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Scanner;

import javax.crypto.spec.IvParameterSpec;


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


    /**
     * For json
     */

    private static FileWriter file;

    /**
     * The only method to write to JSON format text file
     * You can pass any type of class in this method,
     * With your own desired file path, and this method will save nice json format for you
     * Usage:: Database.WriteToJsonFile(menu,"csv/menu.json")
     * @param obj
     * @param filepath
     * @throws IOException
     */
    public static void WriteToJsonFile(Object obj, String filepath) throws IOException {
        try {
            // Make sure the file already exist beiore you write to them
            File f = new File(filepath.trim());
            if (f.createNewFile()) {
                System.out.println("File created: " + f.getName());
            } else {
                //  System.out.println("File already exists.");
            }
            JsonBuilder builder = new JsonBuilder(obj);
            String json_str= builder.toString();
            String json_beauty = JsonOutput.prettyPrint(json_str);
            file = new FileWriter(filepath);
            file.write(json_beauty);
        } catch (IOException e) {
            e.printStackTrace();

        }
        finally {

            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     *  pass in  file name and the file must be exist before you read
     *  You can choose to write empty object to the file by calling
     *  WriteToJsonFile method above
     *  Usage:: Database.readFromJsonFile("csv/menu.json")
     *
     * @param filepath
     * @throws IOException
     */
    public static Map LoadFromJsonFile(String filepath) throws IOException {
        JsonSlurper jsonSlurper= new JsonSlurper();
        FileReader fileReader = new FileReader(filepath);

        Map data = (Map) jsonSlurper.parse(fileReader);
        return data;
    }

    /**
     * DELETE FILE FUNCTION
     * @param filepath
     */
    public static void DeleteFile(String filepath) {
        File myObj = new File(filepath.trim());
        if (myObj.delete()) {
            System.out.println("Deleted the file: " + myObj.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
    }

    public static final String SEPARATOR = ";";
    public static void saveOrder(String filename, List orderList) throws IOException {
        ArrayList<String> ordersw = new ArrayList<String>();
        for (int i = 0; i < orderList.size(); i++) {
            Order order = (Order) orderList.get(i);
            StringBuilder st = new StringBuilder();
            st.append(order.getOrderID());
            st.append(SEPARATOR);
            st.append(order.getReservationID());
            st.append(SEPARATOR);
            st.append(order.getDate());
            st.append(SEPARATOR);
            st.append(order.getStatus());
            st.append(SEPARATOR);
            st.append(order.getStaff());
            st.append(SEPARATOR);


            ArrayList<MenuItem> items = order.getOrderedItems();
            for(MenuItem item : items) {
                st.append(item.getMenuItemID());
                st.append(SEPARATOR);
                st.append(item.getName());
                st.append(SEPARATOR);
                st.append(item.getPrice());
                st.append(SEPARATOR);
                st.append(item.getQuantity());
                st.append(SEPARATOR);
                if(item instanceof SetMeal){
                    if(((SetMeal) item).isPromotionStatus()){
                        st.append("true");
                        st.append(SEPARATOR);
                        st.append("setmeal");
                        st.append(SEPARATOR);
                    }
                    else {
                        st.append("false");
                        st.append(SEPARATOR);
                        st.append("setmeal");
                        st.append(SEPARATOR);
                    }
                }
                else if(item instanceof Alacarte) {
                    st.append("false");
                    st.append(SEPARATOR);
                    st.append("alacarte");
                    st.append(SEPARATOR);
                }
            }
            ordersw.add(st.toString());
        }

        Database.writeOrder(filename, ordersw);
    }

    public static void savePayment(String filename, ArrayList<Invoice> invoiceList) throws IOException {
        ArrayList<String> invoicesw = new ArrayList<String>();
        for (int i = 0; i < invoiceList.size(); i++) {
            Invoice invoice = invoiceList.get(i);
            Order order = invoice.getOrders();
            StringBuilder st = new StringBuilder();
            st.append(invoice.getInvoiceId());
            st.append(SEPARATOR);
            st.append(order.getOrderID());
            st.append(SEPARATOR);
            st.append(order.getReservationID());
            st.append(SEPARATOR);
            st.append(invoice.getDate());
            st.append(SEPARATOR);
            st.append(invoice.getTableNo());
            st.append(SEPARATOR);
            st.append(order.getStaff());
            st.append(SEPARATOR);
            st.append(invoice.getSubTotal());
            st.append(SEPARATOR);
            st.append(invoice.getMemberDiscAmt());
            st.append(SEPARATOR);
            st.append(invoice.getSubTotalAD());
            st.append(SEPARATOR);
            st.append(invoice.getGstAmt());
            st.append(SEPARATOR);
            st.append(invoice.getSvcChargeAmt());
            st.append(SEPARATOR);
            st.append(invoice.getTotal());
            st.append(SEPARATOR);

            ArrayList<MenuItem> items = order.getOrderedItems();
            for (MenuItem item : items) {
                st.append(item.getMenuItemID());
                st.append(SEPARATOR);
                st.append(item.getName());
                st.append(SEPARATOR);
                st.append(item.getPrice());
                st.append(SEPARATOR);
                st.append(item.getQuantity());
                st.append(SEPARATOR);
                //Check Promo
                if(item instanceof SetMeal){
                    if(((SetMeal) item).isPromotionStatus()){
                        st.append("true");
                        st.append(SEPARATOR);
                        st.append("setmeal");
                        st.append(SEPARATOR);
                    }
                    else {
                        st.append("false");
                        st.append(SEPARATOR);
                        st.append("setmeal");
                        st.append(SEPARATOR);
                    }
                }
                else if(item instanceof Alacarte) {
                    st.append("false");
                    st.append(SEPARATOR);
                    st.append("alacarte");
                    st.append(SEPARATOR);
                }
            }
            invoicesw.add(st.toString());
        }
        Database.writeOrder(filename, invoicesw);
    }

   // @Override
    public static ArrayList<Order> readOrder(String fileName) throws IOException {
        ArrayList<String> stringArray = (ArrayList<String>) Database.read(fileName);
        ArrayList<Order> orderList = new ArrayList<Order>();


        for (int i = 0; i < stringArray.size(); i++) {
            String st = stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st, SEPARATOR);
            ArrayList<MenuItem> items = new ArrayList<MenuItem>();
            ArrayList<MenuItem> item2 = new ArrayList<MenuItem>();

            int orderId = Integer.valueOf(star.nextToken().trim());
            String reservationID = star.nextToken().trim();
            String date = star.nextToken().trim();
            String status = star.nextToken().trim();
            String staff = star.nextToken().trim();
            //int quantity = Integer.valueOf(star.nextToken().trim());

            while(star.hasMoreTokens()) {
               // int itemID = Integer.valueOf(star.nextToken().trim());
                String itemID = star.nextToken().trim();
                String name = star.nextToken().trim();
                String desc = "";
                double price = Double.valueOf(star.nextToken().trim());
                int qty = Integer.valueOf(star.nextToken().trim());
                String promostatus = star.nextToken().trim();
                String mealtype = star.nextToken().trim();
                //MenuItem item = new MenuItem(itemID, name,desc, price, qty);
                //MenuItem item = new MenuItem(itemID, name, price, type);
                //items.add(item);
                if(mealtype.equals("setmeal")){
                    SetMeal setitem = new SetMeal(itemID, name,desc, price, qty, item2);
                    if(promostatus.equals("true")){
                        setitem.setPromotionStatus(true);

                    }
                    items.add(setitem);
                }
                if(mealtype.equals("alacarte")){
                    Alacarte alaitem = new Alacarte(itemID, name,desc, price, qty);
                    items.add(alaitem);
                }

            }

            Order order = new Order(orderId,reservationID,date,status,staff,items);
            orderList.add(order);
        }
        System.out.println(stringArray.size() + " Order(s) Loaded.");
        return orderList;
    }
    public static ArrayList<Invoice> readInvoice(String fileName) throws IOException {
        ArrayList<String> stringArray = (ArrayList<String>) Database.read(fileName);
        ArrayList<Invoice> invoiceList = new ArrayList<Invoice>();


        for (int i = 0; i < stringArray.size(); i++) {
            String st = stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st, SEPARATOR);
            ArrayList<MenuItem> items = new ArrayList<MenuItem>();
            ArrayList<MenuItem> item2 = new ArrayList<MenuItem>();

            String invoiceId = star.nextToken().trim();
            int orderId = Integer.valueOf(star.nextToken().trim());
            String reservationID = star.nextToken().trim();
            String date = star.nextToken().trim();
            int tableNo = Integer.valueOf(star.nextToken().trim());
            String staffName = star.nextToken().trim();
            double subTotal = Double.valueOf(star.nextToken().trim());
            double memberDiscAmt = Double.valueOf(star.nextToken().trim());
            double subTotalAD = Double.valueOf(star.nextToken().trim());
            double gstAmt = Double.valueOf(star.nextToken().trim());
            double svcChargeAmt = Double.valueOf(star.nextToken().trim());
            double  total = Double.valueOf(star.nextToken().trim());

            while(star.hasMoreTokens()) {
                // int itemID = Integer.valueOf(star.nextToken().trim());
                String itemID = star.nextToken().trim();
                String name = star.nextToken().trim();
                String desc = "";
                double price = Double.valueOf(star.nextToken().trim());
                int qty = Integer.valueOf(star.nextToken().trim());
                String promostatus = star.nextToken().trim();
                String mealtype = star.nextToken().trim();
                //MenuItem item = new MenuItem(itemID, name,desc, price, qty);
                //MenuItem item = new MenuItem(itemID, name, price, type);
                //items.add(item);
                if(mealtype.equals("setmeal")){
                    SetMeal setitem = new SetMeal(itemID, name,desc, price, qty, item2);
                    if(promostatus.equals("true")){
                        setitem.setPromotionStatus(true);
                        //setitem.isPromo
                    }
                    items.add(setitem);
                }
                if(mealtype.equals("alacarte")){
                    Alacarte alaitem = new Alacarte(itemID, name,desc, price, qty);
                    items.add(alaitem);
                }
            }
            Order order = new Order(orderId,reservationID,date,"confirmed",staffName,items);
            Invoice invoice = new Invoice(invoiceId, date, tableNo, subTotal, memberDiscAmt, subTotalAD
                    ,gstAmt, svcChargeAmt, total, order);
            invoiceList.add(invoice);
        }
        //System.out.println(stringArray.size() + " Invoice(s) Loaded.");
        return invoiceList;
    }
    /**
     * Old Legacy Code for readInvoice without checking promo (To be removed from production)
     * @param fileName
     * @param data
     * @throws IOException
     */
//    public static ArrayList<Invoice> readInvoice(String fileName) throws IOException {
//        ArrayList<String> stringArray = (ArrayList<String>) Database.read(fileName);
//        ArrayList<Invoice> invoiceList = new ArrayList<Invoice>();
//
//
//        for (int i = 0; i < stringArray.size(); i++) {
//            String st = stringArray.get(i);
//            StringTokenizer star = new StringTokenizer(st, SEPARATOR);
//            ArrayList<MenuItem> items = new ArrayList<MenuItem>();
//
//            String invoiceId = star.nextToken().trim();
//            int orderId = Integer.valueOf(star.nextToken().trim());
//            String reservationID = star.nextToken().trim();
//            String date = star.nextToken().trim();
//            int tableNo = Integer.valueOf(star.nextToken().trim());
//            String staffName = star.nextToken().trim();
//            double subTotal = Double.valueOf(star.nextToken().trim());
//            double memberDiscAmt = Double.valueOf(star.nextToken().trim());
//            double subTotalAD = Double.valueOf(star.nextToken().trim());
//            double gstAmt = Double.valueOf(star.nextToken().trim());
//            double svcChargeAmt = Double.valueOf(star.nextToken().trim());
//            double  total = Double.valueOf(star.nextToken().trim());
//
//            while(star.hasMoreTokens()) {
//                // int itemID = Integer.valueOf(star.nextToken().trim());
//                String itemID = star.nextToken().trim();
//                String name = star.nextToken().trim();
//                String desc = "";
//                double price = Double.valueOf(star.nextToken().trim());
//                int qty = Integer.valueOf(star.nextToken().trim());
//                MenuItem item = new MenuItem(itemID, name, desc, price, qty);
//                //MenuItem item = new MenuItem(itemID, name, price, type);
//                items.add(item);
//            }
//            Order order = new Order(orderId,reservationID,date,"confirmed",staffName,items);
//            Invoice invoice = new Invoice(invoiceId, date, tableNo, subTotal, memberDiscAmt, subTotalAD
//                                         ,gstAmt, svcChargeAmt, total, order);
//            invoiceList.add(invoice);
//        }
//        System.out.println(stringArray.size() + " Invoice(s) Loaded.");
//        return invoiceList;
//    }

    public static void writeOrder(String fileName, List data) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(directory+fileName));


        try {
            for (int i =0; i < data.size() ; i++) {
                out.println((String)data.get(i));
            }
        }
        finally {
            out.close();
        }
    }

    /**
     * For DB classes to read data from text file.
     *
     * @param fileName
     *            To determine the file to read from.
     * @return the data to read from the text file.
     */
    public static List read(String fileName) throws IOException {
        List data = new ArrayList();

        Scanner scanner = new Scanner(new FileInputStream(directory+fileName));
        try {
            while (scanner.hasNextLine()){
                data.add(scanner.nextLine());
            }
        }
        finally{
            scanner.close();
        }
        return data;
    }

}
