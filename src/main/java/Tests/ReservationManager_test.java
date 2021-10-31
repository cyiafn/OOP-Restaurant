package Tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import ControlClasses.ReservationManager;
import EntityClasses.Reservation;
import Enumerations.ReservationStatus;
import StaticClasses.Database;
import StaticClasses.InputHandler;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.*;
import org.mockito.internal.util.reflection.Fields;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 Tests for reservation manager
 @author Chen Yifan
 @version 1.0
 @since 2021-10-31
*/
public class ReservationManager_test {
    private static final LocalDateTime now = LocalDateTime.now();
    private static final InputStream systemIn = System.in;
    private static InputHandler inputHandler;
    private ByteArrayInputStream testIn;
    @BeforeAll
    static void init(){
        try{
            inputHandler = new InputHandler();
            ArrayList<Reservation> sampleDat = new ArrayList<>();
            sampleDat.add(new Reservation("testid1", now.minusMinutes(16), 10, "Test name 1", "999999999999999999", 10, ReservationStatus.CREATED));
            sampleDat.add(new Reservation("testid2", now, 10, "Test name 2", "999999999999999998", 9, ReservationStatus.CREATED));
            sampleDat.add(new Reservation("testid18", now, 8, "Test name 18", "999999999999999998", 7, ReservationStatus.CREATED));
            sampleDat.add(new Reservation("testid3", now.plusDays(10), 10, "Test name 3", "999999999999999998", 9, ReservationStatus.CREATED));
            sampleDat.add(new Reservation("testid4", now.plusDays(1).withHour(6).withMinute(0), 10, "Test name 4", "999999999999999998", 9, ReservationStatus.CREATED));
            sampleDat.add(new Reservation("testid5", now.plusDays(2).withHour(21).withMinute(0), 10, "Test name 5", "999999999999999998", 9, ReservationStatus.CREATED));
            sampleDat.add(new Reservation("testid6", now.plusDays(3).withHour(7), 10, "Test name 6", "999999999999999998", 9, ReservationStatus.CREATED));
            sampleDat.add(new Reservation("testid7", now.plusDays(3).withHour(10), 10, "Test name 7", "999999999999999998", 9, ReservationStatus.CREATED));
            sampleDat.add(new Reservation("testid8", now.plusDays(3).withHour(13), 10, "Test name 8", "999999999999999998", 9, ReservationStatus.CREATED));
            sampleDat.add( new Reservation("testid9", now.plusDays(3).withHour(16), 10, "Test name 9", "999999999999999998", 9, ReservationStatus.CREATED));
            sampleDat.add(new Reservation("testid10", now.plusDays(3).withHour(19), 10, "Test name 10", "999999999999999998", 9, ReservationStatus.CREATED));
            sampleDat.add(new Reservation("testid11", now.plusDays(3).withHour(21), 10, "Test name 11", "999999999999999998", 9, ReservationStatus.CREATED));
            sampleDat.add(new Reservation("testid12", now.plusDays(3).withHour(7), 10, "Test name 12", "999999999999999998", 10, ReservationStatus.CREATED));
            sampleDat.add(new Reservation("testid13", now.plusDays(3).withHour(10), 10, "Test name 13", "999999999999999998", 10, ReservationStatus.CREATED));
            sampleDat.add(new Reservation("testid14", now.plusDays(3).withHour(13), 10, "Test name 14", "999999999999999998", 10, ReservationStatus.CREATED));
            sampleDat.add(new Reservation("testid15", now.plusDays(3).withHour(16), 10, "Test name 15", "999999999999999998", 10, ReservationStatus.CREATED));
            sampleDat.add(new Reservation("testid16", now.plusDays(3).withHour(19), 10, "Test name 16", "999999999999999998", 10, ReservationStatus.CREATED));
            sampleDat.add(new Reservation("testid17", now.plusDays(3).withHour(21), 10, "Test name 17", "999999999999999998", 10, ReservationStatus.CREATED));
            sampleDat.add(new Reservation("testid20", now.plusDays(5).withHour(12).withMinute(0), 10, "Test name 20", "999999999999999998", 9, ReservationStatus.COMPLETED));
            for (Reservation i: sampleDat) {
                Database.writeLine("Reservation.csv", i.getLineCSVFormat());
            }
            ReservationManager.getInstance();
            //FieldUtils.writeField(ReservationManager.getInstance(), "reservations", mockedReservations, true);

        } catch (IOException | CsvException e) {
            fail("IO/CSV Exception");
        }
    }

    @AfterAll
    static void teardown(){
        try {

            for (int i = 0; i < 21; i ++){
                if (i != 19){
                    Database.removeLine("Reservation.csv", "testid" + i);
                }
            }
            System.setIn(systemIn);
        } catch (IOException | CsvValidationException e) {
            fail("IO/CSV Exception");
        }

    }


    private void provideInput(String data) throws IOException {
        testIn = new ByteArrayInputStream(data.getBytes());
        inputHandler.sc = new Scanner(testIn);
        System.setIn(testIn);
    }

    private String formatDtToStr(LocalDateTime dt){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dt.format(formatter);
    }
    @Order(2)
    @Test
    void get_todays_created_reservations(){
        try {
            ArrayList<Reservation> tod = ReservationManager.getInstance().getTodaysCreatedReservations();
            for (Reservation r: tod){
                r.print();
            }
            assertTrue(tod.size() == 1 && tod.get(0).getName().equals("Test name 2") && tod.get(0).getContactNo().equals("999999999999999998") && tod.get(0).getNoOfPax() == 10 && tod.get(0).getTableNo() == 9 && tod.get(0).getStatus() == ReservationStatus.CREATED);
        } catch (IOException | CsvException e) {
            fail("IO/CSV Exception.");
        }
    }
    @Order(3)
    @Test
    void delete_reservation_test_success(){
        try {
            provideInput("Test name 3" + System.getProperty("line.separator") + "999999999999999998"  + System.getProperty("line.separator") + formatDtToStr(now.plusDays(10)) + System.getProperty("line.separator") + "1" + System.getProperty("line.separator"));
            ReservationManager.getInstance().deleteReservation();

            ArrayList<HashMap<String, String>> data = Database.readAll("Reservation.csv");
            boolean flag = false;
            for (HashMap<String, String> r: data){
                if (r.get("status").equals("REMOVED") && r.get("reservationID").equals("testid3")){
                    flag = true;
                    break;
                }
            }
            assertTrue(flag);
        } catch (IOException | CsvException e) {
            fail("IO/CSV Exception");
        }
    }

    @Order(3)
    @Test
    void delete_reservation_test_failure(){
        try {
            provideInput("Test name 2" + System.getProperty("line.separator") + "999999999999999998" + System.getProperty("line.separator") + formatDtToStr(now.plusMinutes(1)) + System.getProperty("line.separator"));
            ReservationManager.getInstance().deleteReservation();
            ArrayList<HashMap<String, String>> data = Database.readAll("Reservation.csv");
            boolean flag = false;
            for (HashMap<String, String> r: data){
                if (r.get("status").equals("CREATED") && r.get("reservationID").equals("testid2")){
                    flag = true;
                    break;
                }
            }
            assertTrue(flag);


        } catch (IOException | CsvException e) {
            fail("IO/CSV Exception");
        }
    }
    @Order(1)
    @Test
    void cleanup_test(){
        try {
            ReservationManager.getInstance().cleanup();
            ArrayList<HashMap<String, String>> data = Database.readAll("Reservation.csv");
            int success = 0;
            for (HashMap<String, String> r: data){
                if (r.get("status").equals("EXPIRED") && r.get("reservationID").equals("testid1")){
                    success += 1;
                    if (success == 2){
                        break;
                    }
                }
                if (r.get("status").equals("CREATED") && r.get("reservationID").equals("testid2")){
                    success += 1;
                    if (success == 2){
                        break;
                    }
                }
            }
            assertTrue(success == 2);
        } catch (IOException |CsvException e) {
            fail("IO/CSV Exception");
        }
    }

    @Order(4)
    @Test
    void checkin_test_success(){
        try {
            provideInput("Test name 18" + System.getProperty("line.separator") + "999999999999999998" + System.getProperty("line.separator") + formatDtToStr(now) + System.getProperty("line.separator") + "1" + System.getProperty("line.separator"));
            ReservationManager.getInstance().checkin();
            ArrayList<HashMap<String, String>> data = Database.readAll("Reservation.csv");
            boolean flag = false;
            for (HashMap<String, String> r: data){
                if (r.get("status").equals("ACTIVE") && r.get("reservationID").equals("testid18")){
                    flag = true;
                    break;
                }
            }
            assertTrue(flag);


        } catch (IOException | CsvException e) {
            fail("IO/CSV Exception");
        }
    }

    @Order(5)
    @Test
    void checkin_test_failure(){
        try {
            provideInput("Test name 18" + System.getProperty("line.separator") + "999999999999999998" + System.getProperty("line.separator") + formatDtToStr(now) + System.getProperty("line.separator") );
            ReservationManager.getInstance().checkin();
            ArrayList<HashMap<String, String>> data = Database.readAll("Reservation.csv");
            boolean flag = false;
            for (HashMap<String, String> r: data){
                if (r.get("status").equals("ACTIVE") && r.get("reservationID").equals("testid18")){
                    flag = true;
                    break;
                }
            }
            assertTrue(flag);


        } catch (IOException | CsvException e) {
            fail("IO/CSV Exception");
        }
    }
    @Test
    void close_reservation(){
        try {
            provideInput("Test name 18" + System.getProperty("line.separator") + "999999999999999998" + System.getProperty("line.separator") + formatDtToStr(now) + System.getProperty("line.separator") + "1" + System.getProperty("line.separator"));
            ReservationManager.getInstance().closeReservation();
            ArrayList<HashMap<String, String>> data = Database.readAll("Reservation.csv");
            boolean flag = false;
            for (HashMap<String, String> r: data){
                if (r.get("status").equals("COMPLETED") && r.get("reservationID").equals("testid18")){
                    flag = true;
                    break;
                }
            }
            assertTrue(flag);


        } catch (IOException | CsvException e) {
            fail("IO/CSV Exception");
        }
    }

    @Test
    void create_reservation_fail_noavail_slots() {
        try {
            provideInput("testRes123" + System.getProperty("line.separator") + "999999999999999998" + System.getProperty("line.separator") + formatDtToStr(now.plusDays(3).withMinute(0).withHour(12)) + System.getProperty("line.separator")  + System.getProperty("line.separator") + "10" + System.getProperty("line.separator"));
            ReservationManager.getInstance().createReservation();
            ArrayList<HashMap<String, String>> data = Database.readAll("Reservation.csv");
            boolean flag = true;
            for (HashMap<String, String> r : data) {
                if (r.get("status").equals("CREATED") && r.get("name").equals("testRes123") && r.get("contactNo").equals("999999999999999998") && r.get("dt").equals(formatDtToStr(now.withMinute(0).withHour(12).plusDays(3))) && r.get("noOfPax").equals("10") && r.get("tableNo").equals("9")) {
                    flag = false;
                    break;
                }
            }
            assertTrue(flag);


        } catch (IOException | CsvException e) {
            fail("IO/CSV Exception");
        }
    }
    @Test
    void create_reservation_success() {
        try {
            provideInput("testRes123" + System.getProperty("line.separator") + "999999999999999998" + System.getProperty("line.separator") + formatDtToStr(now.plusDays(5).withHour(12).withMinute(0))  + System.getProperty("line.separator") + "10" + System.getProperty("line.separator"));
            ReservationManager.getInstance().createReservation();
            ArrayList<HashMap<String, String>> data = Database.readAll("Reservation.csv");
            boolean flag = false;
            for (HashMap<String, String> r : data) {
                if (r.get("status").equals("CREATED") && r.get("name").equals("testRes123") && r.get("contactNo").equals("999999999999999998") && r.get("dt").equals(formatDtToStr(now.plusDays(5).withHour(12).withMinute(0))) && r.get("noOfPax").equals("10") && r.get("tableNo").equals("9")) {
                    flag = true;
                    Database.removeLine("Reservation.csv", r.get("reservationID"));
                    break;
                }
            }
            assertTrue(flag);


        } catch (IOException | CsvException e) {
            fail("IO/CSV Exception");
        }
    }
}
