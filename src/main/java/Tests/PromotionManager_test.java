package Tests;

import ControlClasses.MenuManager;
import ControlClasses.PromotionManager;
import EntityClasses.*;
import Enumerations.FoodCategory;
import StaticClasses.Database;
import StaticClasses.InputHandler;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PromotionManager_test {
    private static final InputStream systemIn = System.in;
    private static InputHandler inputHandler;
    private ByteArrayInputStream testIn;

    @BeforeAll
    static void Init() {

        inputHandler = new InputHandler();
        ArrayList<Promotion> sampleDat = new ArrayList<>();
        try {
            //sampleDat.add(new Promotion("test","testingNOEDIT","testNOEDIT","02/11/2021","20/11/2021",10.0,"4fbfdb52-09c1-4c11-96b0-852420616862"));
            PromotionManager.getInstance();
            for (Promotion i: sampleDat) {
                Database.writeLine("Promotion.csv", i.getLineCSVFormat());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }
    }


    @AfterAll
    static void teardown() {
        try {
            for (int i = 0; i < 22; i++) {
                if (i != 19) {
                    Database.removeLine("Promotion.csv", "test");
                }
            }
            System.setIn(systemIn);
        } catch (IOException | CsvValidationException e) {
            fail("IO/CSV Exception");
        }

    }

    private void provideInput(String data) throws IOException {
        testIn = new ByteArrayInputStream(data.getBytes());
        InputHandler.sc = new Scanner(testIn);
        System.setIn(testIn);
    }

    @Order(1)
    @Test
    void createPromotionPass() {

        try {
            provideInput("testing" + System.getProperty("line.separator")
                    + "testing" + System.getProperty("line.separator")
                    + 10.00 + System.getProperty("line.separator")
                    + "2/11/2021" + System.getProperty("line.separator")
                    + "20/11/2021" + System.getProperty("line.separator")
                    + "000d3c0d-54e7-46fe-9df5-554e271cbcc0" + System.getProperty("line.separator")
            );
           boolean flag = false;
           PromotionManager.getInstance().createPromotion();
            ArrayList<Promotion> promotions = PromotionManager.getInstance().allPromotion();
            for(Promotion promo : promotions){
                if(promo.getPromotionName().equals("testing") && promo.getDescription().equals("testing") && promo.getPromotionPrice() ==10.0 && promo.getStartDate().equals("02/11/2021")
                        && promo.getEndDate().equals("20/11/2021") && promo.getMenuItemID().equals("000d3c0d-54e7-46fe-9df5-554e271cbcc0")){
                    flag = true;
                }
            }
            assertTrue(flag);
             } catch (IOException | CsvException e) {
                 e.printStackTrace();
              }
        }

    @Order(2)
    @Test
    void createPromotionFail() {
        try {
            provideInput("testing2" + System.getProperty("line.separator")
                    + "testing2" + System.getProperty("line.separator")
                    + 10.00 + System.getProperty("line.separator")
                    + "10/11/2021" + System.getProperty("line.separator")
                    + "13/11/2021" + System.getProperty("line.separator")
                    + "dead" + System.getProperty("line.separator")
                    +  "0"
            );
            boolean flag = true;
            PromotionManager.getInstance().createPromotion();
            ArrayList<Promotion> promotions = PromotionManager.getInstance().allPromotion();
            for(Promotion promo : promotions){
                if(promo.getPromotionName().equals("testing2") && promo.getDescription().equals("testing2") && promo.getPromotionPrice()==10.00
                        && promo.getStartDate().equals("10/11/2021") && promo.getEndDate().equals("13/11/2021") && promo.getMenuItemID().equals("dead")){
                    flag = false;
                }
            }
            assertTrue(flag);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    @Order(3)
    @Test
    void updateDescriptionPromotionPass() {
        try {
            provideInput("testing" + System.getProperty("line.separator")
                   + 2 + System.getProperty("line.separator")
                    + "new description"
            );
            boolean flag = false;
            PromotionManager.getInstance().updatePromotion();
            ArrayList<Promotion> promotions = PromotionManager.getInstance().allPromotion();
            for(Promotion promo : promotions){
                if( promo.getPromotionName().equals("testing") && promo.getDescription().equals("new description")){
                    flag = true;
                }
            }
            assertTrue(flag);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    @Order(4)
    @Test
    void updateStartDatePromotionPass() {
        try {
            provideInput("testing" + System.getProperty("line.separator")
                    + 3 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + "01/11/2021"
            );
            boolean flag = false;
            PromotionManager.getInstance().updatePromotion();
            ArrayList<Promotion> promotions = PromotionManager.getInstance().allPromotion();
            for(Promotion promo : promotions){
                if(promo.getPromotionName().equals("testing") && promo.getStartDate().equals("01/11/2021")){
                    flag = true;
                }
            }
            assertTrue(flag);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    @Order(5)
    @Test
    void updateStartDatePromotionFail() {
        try {
            provideInput("testing" + System.getProperty("line.separator")
                    + 3 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + "21/11/2021" + System.getProperty("line.separator")
                    + "21/11/2021" + System.getProperty("line.separator")
                    + "21/11/2021"
            );
            boolean flag = true;
            PromotionManager.getInstance().updatePromotion();
            ArrayList<Promotion> promotions = PromotionManager.getInstance().allPromotion();
            for(Promotion promo : promotions){
                if(promo.getPromotionName().equals("testing") && promo.getStartDate().equals("21/11/2021") ){
                    flag = false;
                }
            }
            assertTrue(flag);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    @Order(6)
    @Test
    void updateEndDatePromotionPass() {
        try {
            provideInput("testing" + System.getProperty("line.separator")
                    + 3 + System.getProperty("line.separator")
                    + 2 + System.getProperty("line.separator")
                    + "30/11/2021"
            );
            boolean flag = false;
            PromotionManager.getInstance().updatePromotion();
            ArrayList<Promotion> promotions = PromotionManager.getInstance().allPromotion();
            for(Promotion promo : promotions){
                if(promo.getPromotionName().equals("testing") && promo.getEndDate().equals("30/11/2021") ){
                    flag = true;
                }
            }
            assertTrue(flag);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    @Order(7)
    @Test
    void updateEndDatePromotionFail() {
        try {
            provideInput("testing" + System.getProperty("line.separator")
                    + 3 + System.getProperty("line.separator")
                    + 2 + System.getProperty("line.separator")
                    + "10/10/2021" + System.getProperty("line.separator")
                    + "10/10/2021" + System.getProperty("line.separator")
                    + "10/10/2021"
            );
            boolean flag = true;
            PromotionManager.getInstance().updatePromotion();
            ArrayList<Promotion> promotions = PromotionManager.getInstance().allPromotion();
            for(Promotion promo : promotions){
                //System.out.println("Comparing" + promo.getPromotionName());
                if(promo.getPromotionName().equals("testing") && promo.getEndDate().equals("10/10/2021") ){
                    flag = false;
                }
            }
            assertTrue(flag);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }




    @Order(12)
    @Test
    void updateNamePromotionPass() {
        try {
            provideInput("testing" + System.getProperty("line.separator")
                    + 1 +System.getProperty("line.separator")
                    + "testingChanged"
            );
            boolean flag = false;
            PromotionManager.getInstance().updatePromotion();
            ArrayList<Promotion> promotions = PromotionManager.getInstance().allPromotion();
            for(Promotion promo : promotions){
                System.out.println(promo.getPromotionName());
                if(promo.getPromotionName().equals("testingChanged")){
                    flag = true;
                }
            }
            assertTrue(flag);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    @Order(11)
    @Test
    void updateNamePromotionFail() {
        try {
            provideInput("testing" + System.getProperty("line.separator")
                    + 1 +System.getProperty("line.separator")
                    + "testingNOEDIT" + System.getProperty("line.separator")
                    + "0" + System.getProperty("line.separator")
            );
            boolean flag = false;
            PromotionManager.getInstance().updatePromotion();
            ArrayList<Promotion> promotions = PromotionManager.getInstance().allPromotion();
            for(Promotion promo : promotions){
                System.out.println("Comparing" + promo.getPromotionName());
                if(promo.getPromotionName().equals("testingNOEDIT")){
                    flag = true;
                }
            }
            assertTrue(flag);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    @Order(8)
    @Test
    void updatePricePromotionPass() {
        try {
            provideInput("testing" + System.getProperty("line.separator")
                    + 4 + System.getProperty("line.separator")
                    + 20 + System.getProperty("line.separator")
            );
            boolean flag = false;
            PromotionManager.getInstance().updatePromotion();
            ArrayList<Promotion> promotions = PromotionManager.getInstance().allPromotion();
            for(Promotion promo : promotions){
                System.out.println(promo.getPromotionName());
                if(promo.getPromotionName().equals("testing") && promo.getPromotionPrice() == 20.0){
                    flag = true;
                }
            }
            assertTrue(flag);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    @Order(9)
    @Test
    void updateMenuIDPromotionPass() {
        try {
            provideInput("testing" + System.getProperty("line.separator")
                    + 5 + System.getProperty("line.separator")
                    + "000d3c0d-54e7-46fe-9df5-554e271cbcc0"
            );
            boolean flag = false;
            PromotionManager.getInstance().updatePromotion();
            ArrayList<Promotion> promotions = PromotionManager.getInstance().allPromotion();
            for(Promotion promo : promotions){
             if(promo.getPromotionName().equals("testing") && promo.getMenuItemID().equals("000d3c0d-54e7-46fe-9df5-554e271cbcc0")){
                    flag = true;
                }
            }
            assertTrue(flag);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    @Order(10)
    @Test
    void updateMenuIDPromotionFail() {
        try {
            provideInput("testing" + System.getProperty("line.separator")
                    + 5 + System.getProperty("line.separator")
                    + "dead" + System.getProperty("line.separator")
                    + "dead" +  System.getProperty("line.separator")
                    + "dead" +  System.getProperty("line.separator")
            );
            boolean flag = false;
            PromotionManager.getInstance().updatePromotion();
            ArrayList<Promotion> promotions = PromotionManager.getInstance().allPromotion();
            for(Promotion promo : promotions){
                //System.out.println(promo.getPromotionName());
                if(promo.getPromotionName().equals("testing") && promo.getMenuItemID().equals("000d3c0d-54e7-46fe-9df5-554e271cbcc0")){
                    flag = true;
                }
            }
            assertTrue(flag);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }


    @Order(13)
    @Test
    void deletePromotionFail() {
        try {
            provideInput("testingChanged" + System.getProperty("line.separator")
                    + 0 + System.getProperty("line.separator")
            );
            boolean flag = false;
            PromotionManager.getInstance().deletePromotion();
            ArrayList<Promotion> promotions = PromotionManager.getInstance().allPromotion();
            for(Promotion promo : promotions){
                if(promo.getPromotionName().equals("testingChanged")){
                    flag = true;
                }
            }
            assertTrue(flag);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    @Order(14)
    @Test
    void deletePromotionPass() {
        try {
            provideInput("testingChanged" + System.getProperty("line.separator")
                    + 1 +System.getProperty("line.separator")
            );
            boolean flag = true;
            PromotionManager.getInstance().deletePromotion();
            ArrayList<Promotion> promotions = PromotionManager.getInstance().allPromotion();
            for(Promotion promo : promotions){
                if(promo.getPromotionName().equals("testingChanged")){
                    flag = false;
                }
            }
            assertTrue(flag);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }


    }

