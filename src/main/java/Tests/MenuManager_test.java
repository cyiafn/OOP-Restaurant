package Tests;


import ControlClasses.MenuManager;
import EntityClasses.*;
import StaticClasses.Database;
import StaticClasses.InputHandler;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 Black box Tests for menu manager
 @author Daniel Chu
 @version 1.0
 @since 2021-11-03
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MenuManager_test {

    private static final InputStream systemIn = System.in;
    private static InputHandler inputHandler;
    private ByteArrayInputStream testIn;
    private String mi_ID;


    @BeforeAll
    static void Init(){
        try {
            inputHandler = new InputHandler();
            MenuManager.retrieveInstance();
        }
        catch (IOException e)
        {
            fail("IO EXCEPTION");
        }
    }

    @AfterAll
    static void Teardown() {
        Database.DeleteFile("csv/menu.json");
    }


    private void provideInput(String data) throws IOException {
        testIn = new ByteArrayInputStream(data.getBytes());
        inputHandler.sc = new Scanner(testIn);
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
            MenuManager.retrieveInstance().CreateMenuItem();
            MenuItem mi_1 = MenuManager.retrieveInstance().FindByNameForMenuItem("111");
            if(mi_1.get_name()=="111" && mi_1.get_description()=="111" && mi_1 instanceof  Alacarte)
            {
                flag=false;
            }
            assertTrue(flag);
            //MenuManager.retrieveInstance().Teardown(mi.get_menuItemID());
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
            MenuManager.retrieveInstance().CreateMenuItem();
            MenuItem mi = MenuManager.retrieveInstance().FindByNameForMenuItem("Steamed Truffle & Pork Xiao Long Bao");
                if (mi instanceof  Alacarte
                        && mi.get_name().equals("Steamed Truffle & Pork Xiao Long Bao") &&
                        mi.get_description().equals("Made in Truffle & Pork"))
                {
                    flag = true;
            }
            assertTrue(flag);
            //MenuManager.retrieveInstance().Teardown(mi.get_menuItemID());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Order(3)
    @Test
    void UpdateAlacarteSuccessful() {
        try {
            MenuItem mi_1 = MenuManager.retrieveInstance().FindByNameForMenuItem("Steamed Truffle & Pork Xiao Long Bao");
            mi_ID = mi_1.get_menuItemID();
            provideInput( mi_ID + System.getProperty("line.separator")
                    + "999" + System.getProperty("line.separator")
                    + "999" + System.getProperty("line.separator")
                    + 10 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
            );
            boolean flag = false;
            MenuManager.retrieveInstance().UpdateMenuItem();
            MenuItem mi = MenuManager.retrieveInstance().FindByNameForMenuItem("999");
            if (mi instanceof  Alacarte
                    && mi.get_menuItemID() == mi_ID
                    && mi.get_name().equals("999") &&
                    mi.get_description().equals("999"))
            {
                flag = true;
            }
            assertTrue(flag);
            //MenuManager.retrieveInstance().Teardown(mi.get_menuItemID());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Order(4)
    @Test
    void UpdateAlacarteFail() {
        try {
            MenuItem mi_1 = MenuManager.retrieveInstance().FindByNameForMenuItem("Steamed Truffle & Pork Xiao Long Bao");
            mi_ID = mi_1.get_menuItemID();
            provideInput( mi_ID + System.getProperty("line.separator")
                    + "999" + System.getProperty("line.separator")
                    + "999" + System.getProperty("line.separator")
                    + 10 + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
                    + 0 + System.getProperty("line.separator")
            );
            boolean flag = true;
            MenuManager.retrieveInstance().UpdateMenuItem();
            MenuItem mi = MenuManager.retrieveInstance().FindByNameForMenuItem("999");
            if (mi instanceof  Alacarte
                    && mi.get_menuItemID() == mi_ID
                    && mi.get_name().equals("999") &&
                    mi.get_description().equals("999"))
            {
                flag = false;
            }
            assertTrue(flag);
            //MenuManager.retrieveInstance().Teardown(mi.get_menuItemID());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Order(5)
    @Test
    void DeleteAlacarteSuccessful() {
        try {
            MenuItem mi_1 = MenuManager.retrieveInstance().FindByNameForMenuItem("999");
            mi_ID = mi_1.get_menuItemID();
            provideInput( mi_ID + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
            );
            boolean flag = true;
            MenuManager.retrieveInstance().DeleteMenuItem();
            MenuItem mi = MenuManager.retrieveInstance().FindByNameForMenuItem("999");
            if (mi instanceof  Alacarte
                    && mi.get_menuItemID() == mi_ID
                    && mi.get_name().equals("999") &&
                    mi.get_description().equals("999"))
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
            MenuItem mi_1 = MenuManager.retrieveInstance().FindByNameForMenuItem("999");
            mi_ID = mi_1.get_menuItemID();
            provideInput( mi_ID + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
            );
            boolean flag = true;
            MenuManager.retrieveInstance().DeleteMenuItem();
            MenuItem mi = MenuManager.retrieveInstance().FindByNameForMenuItem("999");
            if (mi instanceof  Alacarte
                    && mi.get_menuItemID() == mi_ID
                    && mi.get_name().equals("999") &&
                    mi.get_description().equals("999"))
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
            MenuManager.retrieveInstance().CreateMenuItem();
            MenuItem mi_1 = MenuManager.retrieveInstance().FindByNameForMenuItem("111");
            if(mi_1.get_name()=="111" && mi_1.get_description()=="111"
                    && mi_1 instanceof  SetMeal)
            {
                flag=false;
            }
            assertTrue(flag);
            //MenuManager.retrieveInstance().Teardown(mi.get_menuItemID());
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
            MenuManager.retrieveInstance().CreateMenuItem();
            MenuItem mi = MenuManager.retrieveInstance().FindByNameForMenuItem("Steamed Truffle & Pork Xiao Long Bao");
            if (mi instanceof SetMeal
                    && mi.get_name().equals("Steamed Truffle & Pork Xiao Long Bao") &&
                    mi.get_description().equals("Made in Truffle & Pork"))
            {
                flag = true;
            }
            assertTrue(flag);
            //MenuManager.retrieveInstance().Teardown(mi.get_menuItemID());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Order(9)
    @Test
    void UpdateSetMealSuccessful() {
        try {
            MenuItem mi_1 = MenuManager.retrieveInstance().FindByNameForMenuItem("Steamed Truffle & Pork Xiao Long Bao");
            mi_ID = mi_1.get_menuItemID();
            provideInput( mi_ID + System.getProperty("line.separator")
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
            MenuManager.retrieveInstance().UpdateMenuItem();
            MenuItem mi = MenuManager.retrieveInstance().FindByNameForMenuItem("999");
            if (mi instanceof  SetMeal
                    && mi.get_menuItemID() == mi_ID
                    && mi.get_name().equals("999") &&
                    mi.get_description().equals("999"))
            {
                flag = true;
            }
            assertTrue(flag);
            //MenuManager.retrieveInstance().Teardown(mi.get_menuItemID());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Order(10)
    @Test
    void UpdateSetMealFail() {
        try {
            MenuItem mi_1 = MenuManager.retrieveInstance().FindByNameForMenuItem("Steamed Truffle & Pork Xiao Long Bao");
            mi_ID = mi_1.get_menuItemID();
            provideInput( mi_ID + System.getProperty("line.separator")
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
            MenuManager.retrieveInstance().UpdateMenuItem();
            MenuItem mi = MenuManager.retrieveInstance().FindByNameForMenuItem("999");
            if (mi instanceof  SetMeal
                    && mi.get_menuItemID() == mi_ID
                    && mi.get_name().equals("999") &&
                    mi.get_description().equals("999"))
            {
                flag = false;
            }
            assertTrue(flag);
            //MenuManager.retrieveInstance().Teardown(mi.get_menuItemID());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Order(11)
    @Test
    void DeleteSetMealSuccessful() {
        try {
            MenuItem mi_1 = MenuManager.retrieveInstance().FindByNameForMenuItem("999");
            mi_ID = mi_1.get_menuItemID();
            provideInput( mi_ID + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
            );
            boolean flag = true;
            MenuManager.retrieveInstance().DeleteMenuItem();
            MenuItem mi = MenuManager.retrieveInstance().FindByNameForMenuItem("999");
            if (mi instanceof  SetMeal
                    && mi.get_menuItemID() == mi_ID
                    && mi.get_name().equals("999") &&
                    mi.get_description().equals("999"))
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
            MenuItem mi_1 = MenuManager.retrieveInstance().FindByNameForMenuItem("999");
            mi_ID = mi_1.get_menuItemID();
            provideInput( mi_ID + System.getProperty("line.separator")
                    + 1 + System.getProperty("line.separator")
            );
            boolean flag = true;
            MenuManager.retrieveInstance().DeleteMenuItem();
            MenuItem mi = MenuManager.retrieveInstance().FindByNameForMenuItem("999");
            if (mi instanceof  SetMeal
                    && mi.get_menuItemID() == mi_ID
                    && mi.get_name().equals("999") &&
                    mi.get_description().equals("999"))
            {
                flag = false;
            }
            assertTrue(flag);

        }catch (IOException e) {
            e.printStackTrace();
        }
    }






}
