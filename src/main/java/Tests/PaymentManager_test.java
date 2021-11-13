package Tests;

import ControlClasses.PaymentManager;
import ControlClasses.ReservationManager;
import EntityClasses.Invoice;
import StaticClasses.Database;
import StaticClasses.InputHandler;
import UI.PaymentUI;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.*;

import java.awt.desktop.SystemEventListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 Black box Tests for Payment Manager
 @author Ong Yew Han
 @version 1.0
 @since 2021-11-03
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PaymentManager_test {

    private static final InputStream systemIn = System.in;
    private static final PrintStream systemOut = System.out;
    private static InputHandler inputHandler;
    private static final LocalDateTime now = LocalDateTime.now();
    private ByteArrayInputStream testIn;

    private String rTestId = "9999";
    private String oTestId = "999";
    private String iTestId = "1990101399";
    private static String primaryKey;

    String[] rTestData = {rTestId, "2021-12-13 06:00", "10", "testName","1234", "10", "ACTIVE"};
    String[] oTestData = { oTestId,"9999", "2021-12-13 06:00","Confirmed", "testStaffName","578007eb-9c86-4b72-a197-61a76eed463c","Pizza","78.92","3","false","alacarte"};
    String[] iTestData = { "1990101399","999","9999", "2021-10-13 06:00","10", "testStaffName","0","0","0","0","0","0","578007eb-9c86-4b72-a197-61a76eed463c","Pizza","78.92","3","false","alacarte"};

    @BeforeAll
    static void Init(){
        inputHandler = new InputHandler();
        PaymentManager.getInstance();
    }

    @AfterAll
    static void teardown(){
        try {
            SimpleDateFormat Id = new SimpleDateFormat("yyyyMMddss");


            for (int i = 0; i < 22; i ++){
                if (i != 19){
                    Database.removeLine("Reservation.csv", "9999");
//                    Database.removeLine("order.csv", "99" );
                    Calendar c = Calendar.getInstance();

                    Database.removeLine("Invoice.csv", primaryKey);
                    Database.removeLine("Invoice.csv", "1990101399");
                }
            }
            System.setIn(systemIn);
        } catch (IOException | CsvValidationException e) {
            fail("IO/CSV Exception");
        }

    }

    private String formatDtToStr(LocalDateTime dt){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dt.format(formatter);
    }

    private void provideInput(String data) throws IOException{
        testIn = new ByteArrayInputStream(data.getBytes());
        inputHandler.sc = new Scanner(testIn);
        System.setIn(testIn);
    }

    @Order(1)
    @Test
    void printPayment_success(){
//      reservationID;dt;noOfPax;name;contactNo;tableNo;status
        try {
            Database.writeLine("Reservation.csv", rTestData);
            Database.writeLine("order.csv", oTestData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            SimpleDateFormat Id = new SimpleDateFormat("yyyyMMddss");
            Calendar c = Calendar.getInstance();
            String id = Id.format(c.getTime()) + "999";
            primaryKey = id;
            provideInput(
                     "0" + System.getProperty("line.separator")
                    + oTestId + System.getProperty("line.separator")
                    + "1" + System.getProperty("line.separator"));
            PaymentUI.getInstance().generateInvoice();
        } catch (IOException e) {
            System.out.printf("Creating Test Invoice Failure");
            e.printStackTrace();
        }
        try{
            ArrayList<HashMap<String, String>> data = Database.readAll("Reservation.csv");
            boolean flag = false;
            for (HashMap<String, String> r: data){
                if (r.get("status").equals("COMPLETED") && r.get("reservationID").equals("9999")){
                    flag = true;
                    break;
                }
            }
            assertTrue(flag);
        } catch (IOException | CsvException e) {
            fail("IO/CSV Exception");
        }
    }


    @Order(2)
    @Test
    void printPayment_failure(){
        SimpleDateFormat Id = new SimpleDateFormat("yyyyMMddss");
        Calendar c = Calendar.getInstance();
        ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        try {
            provideInput(
                    "0" + System.getProperty("line.separator")
                            + "998" + System.getProperty("line.separator")
                            + "1" + System.getProperty("line.separator"));
            System.setOut(new PrintStream(myOut));
            PaymentUI.getInstance().generateInvoice();
        } catch (IOException e) {
            System.out.printf("Creating Test Invoice Failure");
            e.printStackTrace();
        }
        boolean flag = false;
        if(PaymentManager.getInstance().retrieveInvoice(Id.format(c.getTime()) + "998")==null){
            flag = true;
        }
        assert(flag);
//        System.setOut(systemOut);
//        assertEquals(myOut.toString().replaceAll("\u001B\\[[;\\d]*m", "").replace("\n", "").replace(" ", ""), "CreateInvoiceby(0)OrderID(1)BackEnterOrderID0Order(s)Loaded.Orderdoesnotexist!");
    }

    @Order(3)
    @Test
    void viewInvoice_success(){
        boolean flag = false;
        ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        try {
            Database.writeLine("Invoice.csv", iTestData);
            provideInput(iTestId + System.getProperty("line.separator"));
            System.setOut(new PrintStream(myOut));
            PaymentUI.getInstance().viewInvoice();

        } catch (IOException e) {
            System.out.printf("View Test Invoice Failure");
            e.printStackTrace();
        }
        System.setOut(systemOut);
        assertNotEquals(myOut.toString().replaceAll("\u001B\\[[;\\d]*m", "").replace("\n", "").replace(" ", ""), "EntertheInvoiceyouwouldliketoviewInvoicenotFound");
    }

    @Order(4)
    @Test
    void viewInvoice_failure(){
        ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        try {
            Database.writeLine("Invoice.csv", iTestData);
            provideInput(oTestId + System.getProperty("line.separator"));
            System.setOut(new PrintStream(myOut));
            PaymentUI.getInstance().viewInvoice();

        } catch (IOException e) {
            System.out.printf("View Test Invoice Failure");
            e.printStackTrace();
        }
        System.setOut(systemOut);
        assertEquals(myOut.toString().replaceAll("\u001B\\[[;\\d]*m", "").replace("\n", "").replace(" ", ""), "EntertheInvoiceyouwouldliketoviewInvoicenotFound");
    }

    @Order(5)
    @Test
    void revenueReportDay_success(){
        boolean flag = false;
        ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        try {
            Database.writeLine("Invoice.csv", iTestData);
            provideInput(
                    "1" + System.getProperty("line.separator")
                            + "13/10/1990" + System.getProperty("line.separator"));
            System.setOut(new PrintStream(myOut));
            PaymentUI.getInstance().viewRevenueReport();
        } catch (IOException e) {
            System.out.printf("Revenue Report Test Invoice Failure");
            e.printStackTrace();
        }
        System.setOut(systemOut);
        assertNotEquals(myOut.toString().replaceAll("\u001B\\[[;\\d]*m", "").replace("\n", ""), "Enter the Period(1)By day\t(2)By Month\t(3)By Year\t(0)BackEnter the Date in 'dd/MM/yyyy' formatNo records of Selected Day");
    }

    @Order(6)
    @Test
    void revenueReportDay_failure(){
        ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        try {
            Database.writeLine("Invoice.csv", iTestData);
            provideInput(
                    "1" + System.getProperty("line.separator")
                            + "13/10/1991" + System.getProperty("line.separator"));
            System.setOut(new PrintStream(myOut));
            PaymentUI.getInstance().viewRevenueReport();
        } catch (IOException e) {
            System.out.printf("Revenue Report Test Invoice Failure");
            e.printStackTrace();
        }
        System.setOut(systemOut);
        assertEquals(myOut.toString().replaceAll("\u001B\\[[;\\d]*m", "").replace("\n", ""), "Enter the Period(1)By day\t(2)By Month\t(3)By Year\t(0)BackEnter the Date in 'dd/MM/yyyy' formatNo records of Selected Day");
    }

    @Order(7)
    @Test
    void revenueReportMonth_success(){
        ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        try {
            Database.writeLine("Invoice.csv", iTestData);
            provideInput(
                    "2" + System.getProperty("line.separator")
                            + "10/1990" + System.getProperty("line.separator"));
            System.setOut(new PrintStream(myOut));
            PaymentUI.getInstance().viewRevenueReport();
        } catch (IOException e) {
            System.out.printf("Revenue Report Test Invoice Failure");
            e.printStackTrace();
        }
        System.setOut(systemOut);
        assertNotEquals(myOut.toString().replaceAll("\u001B\\[[;\\d]*m", "").replace("\n", ""), "Enter the Period(1)By day\t(2)By Month\t(3)By Year\t(0)BackEnter the Month in 'MM/yyyy' formatNo records of Selected Month");
    }

    @Order(8)
    @Test
    void revenueReportMonth_failure(){
        boolean flag = false;
        ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        try {
            Database.writeLine("Invoice.csv", iTestData);
            provideInput(
                    "2" + System.getProperty("line.separator")
                            + "10/1991" + System.getProperty("line.separator"));
            System.setOut(new PrintStream(myOut));
            PaymentUI.getInstance().viewRevenueReport();
        } catch (IOException e) {
            System.out.printf("Revenue Report Test Invoice Failure");
            e.printStackTrace();
        }
        System.setOut(systemOut);
        assertEquals(myOut.toString().replaceAll("\u001B\\[[;\\d]*m", "").replace("\n", ""), "Enter the Period(1)By day\t(2)By Month\t(3)By Year\t(0)BackEnter the Month in 'MM/yyyy' formatNo records of Selected Month");
    }

    @Order(9)
    @Test
    void revenueReportYear_success(){
        ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        try {
            Database.writeLine("Invoice.csv", iTestData);
            provideInput(
                    "2" + System.getProperty("line.separator")
                            + "10/1990" + System.getProperty("line.separator"));
            System.setOut(new PrintStream(myOut));
            PaymentUI.getInstance().viewRevenueReport();
        } catch (IOException e) {
            System.out.printf("Revenue Report Test Invoice Failure");
            e.printStackTrace();
        }
        System.setOut(systemOut);
        assertNotEquals(myOut.toString().replaceAll("\u001B\\[[;\\d]*m", "").replace("\n", ""), "Enter the Period(1)By day\t(2)By Month\t(3)By Year\t(0)BackEnter the Year in 'yyyy' formatNo records of Selected Year");
    }

    @Order(10)
    @Test
    void revenueReportYear_failure(){
        boolean flag = false;
        ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        try {
            Database.writeLine("Invoice.csv", iTestData);
            provideInput(
                    "3" + System.getProperty("line.separator")
                            + "1991" + System.getProperty("line.separator"));
            System.setOut(new PrintStream(myOut));
            PaymentUI.getInstance().viewRevenueReport();
        } catch (IOException e) {
            System.out.printf("Revenue Report Test Invoice Failure");
            e.printStackTrace();
        }
        System.setOut(systemOut);
        assertEquals(myOut.toString().replaceAll("\u001B\\[[;\\d]*m", "").replace("\n", ""), "Enter the Period(1)By day\t(2)By Month\t(3)By Year\t(0)BackEnter the Year in 'yyyy' formatNo records of Selected Year");
    }
}
