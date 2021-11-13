package UI;

import ControlClasses.MenuManager;
import Enumerations.PrintColor;
import Interfaces.UI;
import StaticClasses.InputHandler;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Handler;

/**
 * MenuUI is a boundary class
 * Which responsible to display options for Menu Management and use Menu Manager to access the menu item
 *
 * @author Daniel Chu Jia Hao
 * @version 1.0
 * @since 2021-11-07
 */
public class MenuUI implements UI {

    /**
     * Menu UI instance
     */
    private static MenuUI instance = null;
    /**
     * The scanner to read user input
     */
    Scanner sc = new Scanner(System.in);

    /**
     * MenuUI constructor
     */
    public MenuUI() {
        sc = new Scanner(System.in);
    }

    /**
     * Singleton design for Menu Management
     *
     * @return return one and only one menu UI
     */
    public static MenuUI getInstance(){
        if (instance == null) instance = new MenuUI();
        return instance;
    }

    /**
     * Entry point for Menu Management
     *
     *  for read / write to json text file
     */
    public void displayOptions() {
        int choice;
        do {
            System.out.println(PrintColor.YELLOW_BOLD);
            System.out.println("==================================================");
            System.out.println(" Menu item Management: ");
            System.out.println("==================================================");
            System.out.print(PrintColor.RESET);
            System.out.println("(1) Create Menu item\t(2) Update Menu item");
            System.out.println("(3) Remove Menu item\t(4) View Menu\t(5) Back");

            choice = InputHandler.getInt(1, 5, "Please Choose a option to Continue: ", "Please enter an integer from 0-5!");
            switch (choice) {
                case 1:
                    try {
                        MenuManager.getInstance().createMenuItem();
                        break;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                case 2:
                    try {
                        MenuManager.getInstance().updateMenuItem();
                        break;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                case 3:
                    try {
                        MenuManager.getInstance().deleteMenuItem();
                        break;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                case 4:
                    try {
                        MenuManager.getInstance().viewMenu();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                case 5:
                    break;
                default:
                    System.out.println("Please enter a valid option!");
                    choice = 0;
            }
        } while (choice < 5);
    }
}
