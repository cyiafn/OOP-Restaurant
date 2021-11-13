package Tests;


import ControlClasses.MenuManager;
import EntityClasses.*;
import StaticClasses.Database;
import StaticClasses.InputHandler;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 Black box Tests for menu manager
 @author Daniel Chu Jia Hao
 @version 1.0
 @since 2021-11-03
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MenuManager_test {

    private static final InputStream systemIn = System.in;
    private static InputHandler inputHandler;
    private ByteArrayInputStream testIn;
    private String miid;


    @BeforeAll
    static void Init(){
        try {
            inputHandler = new InputHandler();
            MenuManager.getInstance();
        }
        catch (IOException e)
        {
            fail("IO EXCEPTION");
        }
    }

    @AfterAll
    static void teardown() throws IOException {
        Database.DeleteFile("csv/menu.json");
        MenuManager.getInstance().init();
    }


    private void provideInput(String data) throws IOException {
        testIn = new ByteArrayInputStream(data.getBytes());
        InputHandler.sc = new Scanner(testIn);
        System.setIn(testIn);
    }


    @Order(1)
    @Test
    void CreateAlacarteFail() {
        try {
            provideInput(+ 2 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + "111" + System.getProperty("line.separator")
                    + "111" + System.getProperty("line.separator")
                    + 10 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + 0 + System.getProperty("line.separator")
            );
            boolean flag = true;
            MenuManager.getInstance().createMenuItem();
            MenuItem mi_1 = MenuManager.getInstance().findByNameForMenuItem("111");
            if(mi_1.getName()=="111" && mi_1.getDescription()=="111" && mi_1 instanceof  Alacarte)
            {
                flag=false;
            }
            assertTrue(flag);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Order(2)
    @Test
    void CreateAlacarteSuccessful() {
        try {
            provideInput(+ 3 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + "Steamed Truffle & Pork Xiao Long Bao" + System.getProperty("line.separator")
                    + "Made in Truffle & Pork" + System.getProperty("line.separator")
                    + 10 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
            );
            boolean flag = false;
            MenuManager.getInstance().createMenuItem();
            MenuItem mi = MenuManager.getInstance().findByNameForMenuItem("Steamed Truffle & Pork Xiao Long Bao");
                if (mi instanceof  Alacarte
                        && mi.getName().equals("Steamed Truffle & Pork Xiao Long Bao") &&
                        mi.getDescription().equals("Made in Truffle & Pork"))
                {
                    flag = true;
            }
            assertTrue(flag);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Order(3)
    @Test
    void UpdateAlacarteSuccessful() {
        try {
            MenuItem mi_1 = MenuManager.getInstance().findByNameForMenuItem("Steamed Truffle & Pork Xiao Long Bao");
            miid = mi_1.getMenuItemID();
            provideInput( miid + System.getProperty("line.separator")
                    + "999" + System.getProperty("line.separator")
                    + "999" + System.getProperty("line.separator")
                    + 10 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
            );
            boolean flag = false;
            MenuManager.getInstance().updateMenuItem();
            MenuItem mi = MenuManager.getInstance().findByNameForMenuItem("999");
            if (mi instanceof  Alacarte
                    && mi.getMenuItemID() == miid
                    && mi.getName().equals("999") &&
                    mi.getDescription().equals("999"))
            {
                flag = true;
            }
            assertTrue(flag);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Order(4)
    @Test
    void UpdateAlacarteFail() {
        try {
            MenuItem mi_1 = MenuManager.getInstance().findByNameForMenuItem("Steamed Truffle & Pork Xiao Long Bao");
            miid = mi_1.getMenuItemID();
            provideInput( miid + System.getProperty("line.separator")
                    + "999" + System.getProperty("line.separator")
                    + "999" + System.getProperty("line.separator")
                    + 10 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + 0 + System.getProperty("line.separator")
            );
            boolean flag = true;
            MenuManager.getInstance().updateMenuItem();
            MenuItem mi = MenuManager.getInstance().findByNameForMenuItem("999");
            if (mi instanceof  Alacarte
                    && mi.getMenuItemID() == miid
                    && mi.getName().equals("999") &&
                    mi.getDescription().equals("999"))
            {
                flag = false;
            }
            assertTrue(flag);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Order(5)
    @Test
    void DeleteAlacarteSuccessful() {
        try {
            MenuItem mi_1 = MenuManager.getInstance().findByNameForMenuItem("999");
            miid = mi_1.getMenuItemID();
            provideInput( miid + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
            );
            boolean flag = true;
            MenuManager.getInstance().deleteMenuItem();
            MenuItem mi = MenuManager.getInstance().findByNameForMenuItem("999");
            if (mi instanceof  Alacarte
                    && mi.getMenuItemID() == miid
                    && mi.getName().equals("999") &&
                    mi.getDescription().equals("999"))
            {
                flag = false;
            }
            assertTrue(flag);

        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Order(6)
    @Test
    void DeleteAlacarteFail() {
        try {
            MenuItem mi_1 = MenuManager.getInstance().findByNameForMenuItem("999");
            miid = mi_1.getMenuItemID();
            provideInput( miid + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
            );
            boolean flag = true;
            MenuManager.getInstance().deleteMenuItem();
            MenuItem mi = MenuManager.getInstance().findByNameForMenuItem("999");
            if (mi instanceof  Alacarte
                    && mi.getMenuItemID() == miid
                    && mi.getName().equals("999") &&
                    mi.getDescription().equals("999"))
            {
                flag = false;
            }
            assertTrue(flag);

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Order(7)
    @Test
    void CreateSetMealFail() {
        try {
            provideInput(+ 1 + System.getProperty("line.separator")
                    + 2 + System.getProperty("line.separator")
                    + "111" + System.getProperty("line.separator")
                    + "111" + System.getProperty("line.separator")
                    + 10 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + "any key" + System.getProperty("line.separator")
                    + "111" + System.getProperty("line.separator")
                    + "111" + System.getProperty("line.separator")
                    + 10 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + "any key" + System.getProperty("line.separator")
                    + "222" + System.getProperty("line.separator")
                    + "222" + System.getProperty("line.separator")
                    + 10 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + "any key" + System.getProperty("line.separator")
                    + "333" + System.getProperty("line.separator")
                    + "333" + System.getProperty("line.separator")
                    + 10 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + 0 + System.getProperty("line.separator")
            );
            boolean flag = true;
            MenuManager.getInstance().createMenuItem();
            MenuItem mi_1 = MenuManager.getInstance().findByNameForMenuItem("111");
            if(mi_1.getName()=="111" && mi_1.getDescription()=="111"
                    && mi_1 instanceof  SetMeal)
            {
                flag=false;
            }
            assertTrue(flag);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Order(8)
    @Test
    void CreateSetMealSuccessful() {
        try {
            provideInput(+ 3 + System.getProperty("line.separator")
                    + 2 + System.getProperty("line.separator")
                    + "Steamed Truffle & Pork Xiao Long Bao" + System.getProperty("line.separator")
                    + "Made in Truffle & Pork" + System.getProperty("line.separator")
                    + 10 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + "any key" + System.getProperty("line.separator")
                    + "111" + System.getProperty("line.separator")
                    + "111" + System.getProperty("line.separator")
                    + 10 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + "any key" + System.getProperty("line.separator")
                    + "222" + System.getProperty("line.separator")
                    + "222" + System.getProperty("line.separator")
                    + 10 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + "any key" + System.getProperty("line.separator")
                    + "333" + System.getProperty("line.separator")
                    + "333" + System.getProperty("line.separator")
                    + 10 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
            );
            boolean flag = false;
            MenuManager.getInstance().createMenuItem();
            MenuItem mi = MenuManager.getInstance().findByNameForMenuItem("Steamed Truffle & Pork Xiao Long Bao");
            if (mi instanceof SetMeal
                    && mi.getName().equals("Steamed Truffle & Pork Xiao Long Bao") &&
                    mi.getDescription().equals("Made in Truffle & Pork"))
            {
                flag = true;
            }
            assertTrue(flag);

        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Order(9)
    @Test
    void UpdateSetMealSuccessful() {
        try {
            MenuItem mi_1 = MenuManager.getInstance().findByNameForMenuItem("Steamed Truffle & Pork Xiao Long Bao");
            miid = mi_1.getMenuItemID();
            provideInput( miid + System.getProperty("line.separator")
                    + "999" + System.getProperty("line.separator")
                    + "999" + System.getProperty("line.separator")
                    + 10 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + "any key" + System.getProperty("line.separator")
                    + "111" + System.getProperty("line.separator")
                    + "111" + System.getProperty("line.separator")
                    + 10 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + "any key" + System.getProperty("line.separator")
                    + "222" + System.getProperty("line.separator")
                    + "222" + System.getProperty("line.separator")
                    + 10 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + "any key" + System.getProperty("line.separator")
                    + "333" + System.getProperty("line.separator")
                    + "333" + System.getProperty("line.separator")
                    + 10 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
            );
            boolean flag = false;
            MenuManager.getInstance().updateMenuItem();
            MenuItem mi = MenuManager.getInstance().findByNameForMenuItem("999");
            if (mi instanceof  SetMeal
                    && mi.getMenuItemID() == miid
                    && mi.getName().equals("999") &&
                    mi.getDescription().equals("999"))
            {
                flag = true;
            }
            assertTrue(flag);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Order(10)
    @Test
    void UpdateSetMealFail() {
        try {
            MenuItem mi_1 = MenuManager.getInstance().findByNameForMenuItem("Steamed Truffle & Pork Xiao Long Bao");
            miid = mi_1.getMenuItemID();
            provideInput( miid + System.getProperty("line.separator")
                    + "999" + System.getProperty("line.separator")
                    + "999" + System.getProperty("line.separator")
                    + 10 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + "any key" + System.getProperty("line.separator")
                    + "111" + System.getProperty("line.separator")
                    + "111" + System.getProperty("line.separator")
                    + 10 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + "any key" + System.getProperty("line.separator")
                    + "222" + System.getProperty("line.separator")
                    + "222" + System.getProperty("line.separator")
                    + 10 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + "any key" + System.getProperty("line.separator")
                    + "333" + System.getProperty("line.separator")
                    + "333" + System.getProperty("line.separator")
                    + 10 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + 0 + System.getProperty("line.separator")
            );
            boolean flag = true;
            MenuManager.getInstance().updateMenuItem();
            MenuItem mi = MenuManager.getInstance().findByNameForMenuItem("999");
            if (mi instanceof  SetMeal
                    && mi.getMenuItemID() == miid
                    && mi.getName().equals("999") &&
                    mi.getDescription().equals("999"))
            {
                flag = false;
            }
            assertTrue(flag);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Order(11)
    @Test
    void DeleteSetMealSuccessful() {
        try {
            MenuItem mi_1 = MenuManager.getInstance().findByNameForMenuItem("999");
            miid = mi_1.getMenuItemID();
            provideInput( miid + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
            );
            boolean flag = true;
            MenuManager.getInstance().deleteMenuItem();
            MenuItem mi = MenuManager.getInstance().findByNameForMenuItem("999");
            if (mi instanceof  SetMeal
                    && mi.getMenuItemID() == miid
                    && mi.getName().equals("999") &&
                    mi.getDescription().equals("999"))
            {
                flag = false;
            }
            assertTrue(flag);

        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Order(12)
    @Test
    void DeleteSetMealFail() {
        try {
            MenuItem mi_1 = MenuManager.getInstance().findByNameForMenuItem("999");
            miid = mi_1.getMenuItemID();
            provideInput( miid + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
            );
            boolean flag = true;
            MenuManager.getInstance().deleteMenuItem();
            MenuItem mi = MenuManager.getInstance().findByNameForMenuItem("999");
            if (mi instanceof  SetMeal
                    && mi.getMenuItemID() == miid
                    && mi.getName().equals("999") &&
                    mi.getDescription().equals("999"))
            {
                flag = false;
            }
            assertTrue(flag);

        }catch (IOException e) {
            e.printStackTrace();
        }
    }






}
