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
    /**
     * input stream for order manager test case
     */
    private static final InputStream systemIn = System.in;
    /**
     * InputHandler
     */
    private static InputHandler inputHandler;
    /**
     * ByteArrayInputStream
     */
    private ByteArrayInputStream testIn;
    private String miid;

    /**
     * Init function for order manager test case
     */
    @BeforeAll
    static void Init(){
        inputHandler = new InputHandler();
        OrderManager.getInstance();
    }

    /**
     * Teardown function for order manager
     * Will delete the file
     */
    @AfterAll
    static void teardown() {
        Database.DeleteFile("csv/order.csv");
    }

    /**
     * To provide input for test case
     * @param data any test data
     * @throws IOException to ensure the input is read
     */
    private void provideInput(String data) throws IOException {
        testIn = new ByteArrayInputStream(data.getBytes());
        InputHandler.sc = new Scanner(testIn);
        System.setIn(testIn);
    }

    /**
     * Create Order Success
     */
    @Order(1)
    @Test
    void CreateOrderSuccess() {
        try {
            provideInput( "6e962206-ff71-4b8f-8bfc-76cb8da7faf0" + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + "Pizza" + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + "Kebab" + System.getProperty("line.separator")
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
            MenuItem mi = MenuManager.getInstance().findByNameForMenuItem("Pizza");
            if (mi instanceof  Alacarte
                    && mi.getName().equals("Pizza"))
            {
                flag = true;
            }
            assertTrue(flag);

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Create Order fail
     */
    @Order(2)
    @Test
    void CreateOrderFail() {
        try {
            provideInput( "6e962206-ff71-4b8f-8bfc-76cb8da7faf0" + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + "vodka" + System.getProperty("line.separator")
                    + 3 + System.getProperty("line.separator")
            );
            boolean flag = true;
            try {
                OrderUI.getInstance().createOrder();
            } catch (CsvException e) {
                e.printStackTrace();
            }
            MenuItem mi = MenuManager.getInstance().findByNameForMenuItem("vodka");
            if (mi instanceof  Alacarte
                    && mi.getName().equals("vodka"))
            {
                flag = false;
            }
            assertTrue(flag);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * update add order item success
     * @throws CsvException csvexception
     */
    @Order(3)
    @Test
    void UpdateAddOrderItemsSuccess() throws CsvException {
        try {
            provideInput( "2" + System.getProperty("line.separator")
                    + 2 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + "7up Lemon & Lime" + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + 3 + System.getProperty("line.separator")
                    + 5 + System.getProperty("line.separator")

            );
            boolean flag = false;
            OrderUI.getInstance().displayOptions();
            MenuItem mi = MenuManager.getInstance().findByNameForMenuItem("7up Lemon & Lime");
            if (mi instanceof  Alacarte
                    && mi.getName().equals("7up Lemon & Lime"))
            {
                flag = true;
            }
            assertTrue(flag);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * update add order item fail
     * @throws CsvException csvexception
     */
    @Order(4)
    @Test
    void UpdateAddOrderItemsFail() throws CsvException {
        try {
            provideInput( "2" + System.getProperty("line.separator")
                    + 2 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + "pizza" + System.getProperty("line.separator")
                    + 3 + System.getProperty("line.separator")
                    + 5 + System.getProperty("line.separator")

            );
            boolean flag = true;
            OrderUI.getInstance().displayOptions();
            MenuItem mi = MenuManager.getInstance().findByNameForMenuItem("pizza");
            if (mi instanceof  Alacarte
                    && mi.getName().equals("pizza"))
            {
                flag = false;
            }
            assertTrue(flag);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * update remove order item success
     * @throws CsvException csvexception
     */
    @Order(5)
    @Test
    void UpdateRemoveOrderItemsSuccess() throws CsvException {
        try {
            provideInput( "2" + System.getProperty("line.separator")
                    + 2 + System.getProperty("line.separator")
                    + 2 + System.getProperty("line.separator")
                    + "Kebab" + System.getProperty("line.separator")
                    + 3 + System.getProperty("line.separator")
                    + 5 + System.getProperty("line.separator")

            );
            boolean flag = false;
            OrderUI.getInstance().displayOptions();
            MenuItem mi = MenuManager.getInstance().findByNameForMenuItem("Kebab");
            if (mi instanceof  Alacarte
                    && mi.getName().equals("Kebab"))
            {
                flag = true;
            }
            assertTrue(flag);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * update remove order item fail
     * @throws CsvException csvexception
     */
    @Order(6)
    @Test
    void UpdateRemoveOrderItemsFail() throws CsvException {
        try {
            provideInput( "2" + System.getProperty("line.separator")
                    + 2 + System.getProperty("line.separator")
                    + 2 + System.getProperty("line.separator")
                    + "pizza" + System.getProperty("line.separator")
                    + 3 + System.getProperty("line.separator")
                    + 5 + System.getProperty("line.separator")

            );
            boolean flag = true;
            OrderUI.getInstance().displayOptions();
            MenuItem mi = MenuManager.getInstance().findByNameForMenuItem("pizza");
            if (mi instanceof  Alacarte
                    && mi.getName().equals("pizza"))
            {
                flag = false;
            }
            assertTrue(flag);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
