package UI;

import ControlClasses.MenuManager;
import Interfaces.UI;

import java.io.IOException;
import java.util.Scanner;

/**
 * MenuUI is a boudary class
 * Which responsible to display options for Menu Management and use Menu Manager to access the menu item
 * @author Daniel Chu Jia Hao
 * @version 1.0
 * @since 2021-11-07
 */
public class MenuUI implements UI {

    private static MenuUI instance = null;
    Scanner sc = new Scanner(System.in);

    public MenuUI() {
        sc = new Scanner(System.in);
    }

    /**
     * Singleton design for Menu Management
     * @return return one and only one menu UI
     */
    public static MenuUI getInstance() throws IOException {
        if (instance == null) instance = new MenuUI();
        return instance;
    }

    /**
     * Entry point for Menu Management
     * @throws IOException for read / write to json text file
     */
    public void displayOptions() throws IOException {
        int choice;
        do {

            System.out.println("\n==================================================");
            System.out.println(" Menu item Management: ");
            System.out.println("==================================================");
            System.out.println("(1) Create Menu item\t(2) Update Menu item");
            System.out.println("(3) Remove Menu item\t(4) View Menu\t(5) Back");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    MenuManager.getInstance().createMenuItem();
                    break;
                case 2:
                    MenuManager.getInstance().updateMenuItem();
                    break;
                case 3:
                    MenuManager.getInstance().deleteMenuItem();
                    break;
                case 4:
                    MenuManager.getInstance().viewMenu();
                case 5:
                    break;
                default:
                    System.out.println("Please enter a valid option!");
                    choice = 0;
            }
        } while (choice < 5);
    }
}
