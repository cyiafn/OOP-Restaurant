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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 Black box Tests for Payment Manager
 @author Ong Yew Han
 @version 1.0
 @since 2021-11-03
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PaymentManager_test {

    private static final InputStream systemIn = System.in;
    private static InputHandler inputHandler;
    private static final LocalDateTime now = LocalDateTime.now();
    private ByteArrayInputStream testIn;
    private String testId = "9999";

    @BeforeAll
    static void Init(){
        inputHandler = new InputHandler();
        PaymentManager.getInstance();
    }

    @AfterAll
    static void teardown(){
        try {
            for (int i = 0; i < 22; i ++){
                if (i != 19){
                    Database.removeLine("Reservation.csv", "12345" );
                    Database.removeLine("order.csv", "99" );
                    Database.removeLine("Invoice.csv", "")
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
    void testPrintPayment_success(){
//      reservationID;dt;noOfPax;name;contactNo;tableNo;status
        String[] row = {"12345", "2021-12-13 06:00", "10", "testName","1234", "10", "ACTIVE"};
        String[] row1 = { "99","12345", "2021-12-13 06:00","Confirmed", "testStaffName","578007eb-9c86-4b72-a197-61a76eed463c","Pizza","78.92","3","false","alacarte"};
        try {
            Database.writeLine("Reservation.csv", row);
            Database.writeLine("order.csv", row1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            provideInput(
                     "0" + System.getProperty("line.separator")
                    + "99" + System.getProperty("line.separator")
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
                if (r.get("status").equals("COMPLETED") && r.get("reservationID").equals("12345")){
                    flag = true;
                    break;
                }
            }
            assertTrue(flag);
        } catch (IOException | CsvException e) {
            fail("IO/CSV Exception");
        }




//        //Create an Order
//        try {
//            ArrayList<String> reservationList = ReservationManager.getInstance().getTodaysCreatedReservations();
//            for(reservation : reservationList){
//
//            }
//            String orderId
//            provideInput("1" + System.getProperty("line.separator")
//                    + orderId + System.getProperty("line.separator")
//                    + "Vodka" + System.getProperty("line.separator")
//                    + 1 + System.getProperty("line.separator")
//                    + 3 + System.getProperty("line.separator")
//                    + "Tommy Lee" + System.getProperty("line.separator")
//            );
//        } catch (IOException | CsvException e){
//            System.out.printf("Creating Test Order Failure");
//            e.printStackTrace();
//        }
//        try {
//            provideInput(
//                     "0" + System.getProperty("line.separator")
//                    + testId + System.getProperty("line.separator")
//                    + "1" + System.getProperty("line.separator"));
//            PaymentUI.getInstance().generateInvoice();
//        } catch (IOException e) {
//            System.out.printf("Creating Test Invoice Failure");
//            e.printStackTrace();
//        }
//        boolean flag = false;

    }

    @Order(2)
    @Test
    void testPrintPayment_failure(){
        SimpleDateFormat Id = new SimpleDateFormat("yyyyMMddss");
        try {
            provideInput(
                    "0" + System.getProperty("line.separator")
                            + testId + System.getProperty("line.separator")
                            + "1" + System.getProperty("line.separator"));
            PaymentUI.getInstance().generateInvoice();
        } catch (IOException e) {
            System.out.printf("Creating Test Invoice Failure");
            e.printStackTrace();
        }
        boolean flag = false;
        Calendar c = Calendar.getInstance();
        if(PaymentManager.getInstance().retrieveInvoice(Id.format(c.getTime()) + testId)==null){
            flag = true;
        }
        assertTrue(flag);
    }


}
