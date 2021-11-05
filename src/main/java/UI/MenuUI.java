package UI;

import ControlClasses.MenuManager;
import java.io.IOException;
import java.util.Scanner;

public class MenuUI {

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
                    MenuManager.getInstance().CreateMenuItem();
                    break;
                case 2:
                    MenuManager.getInstance().UpdateMenuItem();
                    break;
                case 3:
                    MenuManager.getInstance().DeleteMenuItem();
                    break;
                case 4:
                    MenuManager.getInstance().ViewMenu();
                case 5:
                    break;
                default:
                    System.out.println("Please enter a valid option!");
                    choice = 0;
            }
        } while (choice < 5);
    }
}
