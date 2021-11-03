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
                    CreateMenuItem();
                    break;
                case 2:
                    UpdateMenuItem();
                    break;
                case 3:
                    DeleteMenuItem();
                    break;
                case 4:
                    ViewMenu();
                case 5:
                    break;
                default:
                    System.out.println("Please enter a valid option!");
                    choice = 0;
            }
        } while (choice < 5);
    }

    /**
     * Create operation from Menu Manager
     * @throws IOException
     */
    public void CreateMenuItem() throws IOException {
        MenuManager.retrieveInstance().CreateMenuItem();
    }

    /**
     * Delete Operation from Menu Manager
     * @throws IOException
     */
    public void DeleteMenuItem() throws IOException {
        MenuManager.retrieveInstance().DeleteMenuItem();
    }

    /**
     * Update operation from Menu Manager
     * @throws IOException
     */
    public void UpdateMenuItem() throws IOException {
        MenuManager.retrieveInstance().UpdateMenuItem();
    }

    /**
     * Retrieve operation from Menu Manager
     */
    public void ViewMenu() throws IOException {
        MenuManager.retrieveInstance().ViewMenu();
    }



}
