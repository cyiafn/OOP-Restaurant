package UI;

import ControlClasses.MenuManager;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.Scanner;

public class MenuUI {

    private static MenuUI instance = null;
    Scanner sc = new Scanner(System.in);


    public MenuUI() {
        sc = new Scanner(System.in);
    }

    public static MenuUI getInstance() {
        if (instance == null) instance = new MenuUI();
        return instance;
    }

    public void displayOptions() throws IOException, CsvException {
        int choice;
        do {
            MenuManager.retrieveInstance().displayMenu();
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
        } while (choice < 4);
    }

    // CRUD
    public void CreateMenuItem() throws IOException {
        MenuManager.retrieveInstance().CreateMenuItem();
    }
    public void DeleteMenuItem() throws IOException {
        MenuManager.retrieveInstance().DeleteMenuItem();
    }
    public void UpdateMenuItem() throws IOException {
        MenuManager.retrieveInstance().UpdateMenuItem();
    }

    public void ViewMenu() {
        MenuManager.retrieveInstance().ViewMenu();
    }



}
