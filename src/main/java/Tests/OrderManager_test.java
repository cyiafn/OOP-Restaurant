package Tests;

import ControlClasses.MenuManager;
import ControlClasses.OrderManager;
import EntityClasses.*;
import StaticClasses.Database;
import StaticClasses.InputHandler;
import UI.OrderUI;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


/**
 Black box Tests for Order
 @author Tan Ge Wen Gotwin
 @version 1.0
 @since 2021-11-03
 */
public class OrderManager_test {

    private static final InputStream systemIn = System.in;
    private static InputHandler inputHandler;
    private ByteArrayInputStream testIn;
    private String miid;

    @BeforeAll
    static void Init(){
        inputHandler = new InputHandler();
        OrderManager.getInstance();
    }

    @AfterAll
    static void teardown() {
        Database.DeleteFile("csv/order.csv");
    }


    private void provideInput(String data) throws IOException {
        testIn = new ByteArrayInputStream(data.getBytes());
        inputHandler.sc = new Scanner(testIn);
        System.setIn(testIn);
    }

    @Order(1)
    @Test
    void CreateOrderSuccess() {
        try {
            provideInput( "1" + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + "Vodka" + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + 3 + System.getProperty("line.separator")
                    + "Tom" + System.getProperty("line.separator")
            );
            boolean flag = false;
            try {
                OrderUI.getInstance().createOrder();
            } catch (CsvException e) {
                e.printStackTrace();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Order(2)
    @Test
    void CreateOrderFail() {
        try {
            provideInput( "1" + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + "vodka" + System.getProperty("line.separator")
                    + 3 + System.getProperty("line.separator")
            );
            boolean flag = false;
            try {
                OrderUI.getInstance().createOrder();
            } catch (CsvException e) {
                e.printStackTrace();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Order(3)
    @Test
    void UpdateAddOrderItemsSuccess() {
        try {
            provideInput( "2" + System.getProperty("line.separator")
                    + 19 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + "Pizza" + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + "Katsu Curry" + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + 3 + System.getProperty("line.separator")
                    + 5 + System.getProperty("line.separator")

            );
            boolean flag = false;
            try {
                OrderUI.getInstance().displayOptions();
            } catch (CsvException e) {
                e.printStackTrace();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Order(4)
    @Test
    void UpdateAddOrderItemsFail() {
        try {
            provideInput( "2" + System.getProperty("line.separator")
                    + 7 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + "pizza" + System.getProperty("line.separator")
                    + 3 + System.getProperty("line.separator")
                    + 5 + System.getProperty("line.separator")

            );
            boolean flag = false;
            try {
                OrderUI.getInstance().displayOptions();
            } catch (CsvException e) {
                e.printStackTrace();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Order(5)
    @Test
    void UpdateRemoveOrderItemsSuccess() {
        try {
            provideInput( "2" + System.getProperty("line.separator")
                    + 16 + System.getProperty("line.separator")
                    + 2 + System.getProperty("line.separator")
                    + "Vodka" + System.getProperty("line.separator")
                    + 3 + System.getProperty("line.separator")
                    + 5 + System.getProperty("line.separator")

            );
            boolean flag = false;
            try {
                OrderUI.getInstance().displayOptions();
            } catch (CsvException e) {
                e.printStackTrace();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Order(6)
    @Test
    void UpdateRemoveOrderItemsFail() {
        try {
            provideInput( "2" + System.getProperty("line.separator")
                    + 17 + System.getProperty("line.separator")
                    + 2 + System.getProperty("line.separator")
                    + "vodka" + System.getProperty("line.separator")
                    + 3 + System.getProperty("line.separator")
                    + 5 + System.getProperty("line.separator")

            );
            boolean flag = false;
            try {
                OrderUI.getInstance().displayOptions();
            } catch (CsvException e) {
                e.printStackTrace();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Order(7)
    @Test
    void UpdateRemoveOrderSuccess() {
        try {
            provideInput( "3" + System.getProperty("line.separator")
                    + 14 + System.getProperty("line.separator")
                    + 5 + System.getProperty("line.separator")
            );
            boolean flag = false;
            try {
                OrderUI.getInstance().displayOptions();
            } catch (CsvException e) {
                e.printStackTrace();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Order(7)
    @Test
    void UpdateRemoveOrderFail() {
        try {
            provideInput( "3" + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + 5 + System.getProperty("line.separator")
            );
            boolean flag = false;
            try {
                OrderUI.getInstance().displayOptions();
            } catch (CsvException e) {
                e.printStackTrace();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Order(8)
//    @Test
//    void ViewOrderSuccess() {
//        try {
//            provideInput( "4" + System.getProperty("line.separator")
//                    + 0 + System.getProperty("line.separator")
//                    + 2 + System.getProperty("line.separator")
//            );
//            boolean flag = false;
//            try {
//                OrderUI.getInstance().displayOptions();
//            } catch (CsvException e) {
//                e.printStackTrace();
//            }
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    @Order(9)
//    @Test
//    void ViewOrderFail() {
//        try {
//            provideInput( "4" + System.getProperty("line.separator")
//                    + 0 + System.getProperty("line.separator")
//                    + 1 + System.getProperty("line.separator")
//            );
//            boolean flag = false;
//            try {
//                OrderUI.getInstance().displayOptions();
//            } catch (CsvException e) {
//                e.printStackTrace();
//            }
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//    }




}
