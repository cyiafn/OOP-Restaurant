package UI;
import ControlClasses.OrderManager;
import ControlClasses.PromotionManager;
import EntityClasses.Order;
import StaticClasses.Database;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.util.*;

public class PromotionUI {
    Scanner sc = new Scanner(System.in);
    private static PromotionUI instance = null;
    public static PromotionUI getInstance() {
        if (instance == null) {
            instance = new PromotionUI();
        }
        return instance;
    }
    public void displayOptions() throws IOException, CsvException {
        int choice;
        do{
            System.out.println("\n==================================================");
            System.out.println(" Welcome To Promotion Management: ");
            System.out.println("==================================================");
            System.out.println("(1) Create Promotion\t(2) Update Promotion");
            System.out.println("(3) Remove Promotion\t(4) View Promotion");
            System.out.println("(5) Back");
            choice = sc.nextInt();
            switch (choice){
                case 1:
                    PromotionManager.getInstance().createPromotion();
                    break;
                case 2:
                    PromotionManager.getInstance().updatePromotion();
                    /*Scanner sc = new Scanner(System.in);
                    int id = -1;
                    do {
                        try {
                            System.out.println("(1) to update with Alacart\t (2) to update with setItem");
                            id = sc.nextInt();
                            if(id <= 0) System.out.printf("Invalid input! ");
                            switch(id) {
                                case 1:
                                    System.out.println("Enter Alacart ID to be updated into promotion");
                                    id = sc.nextInt();
                                    if (id <= 0) System.out.println("Invalid Input!");
                                    break;
                                case 2:
                                    System.out.println("Enter SetMenu ID to be updated into promotion");
                                    id = sc.nextInt();
                                    if (id <= 0) System.out.println("Invalid Input!");
                                    break;
                            }
                        } catch (InputMismatchException e) {
                            System.out.printf("Invalid input! ");
                        }
                        sc.nextLine();
                    } while (id <= 0);
                    // Promotion promo = PromotionManager.getInstance().retrieveOrder(id);
                    //if (order != null) updateOrder(order);
                    //else System.out.println("Order does not exist!");*/
                    break;
                case 3:
                    PromotionManager.getInstance().deletePromotion();
                    break;
                case 4:
                    //Map data = Database.LoadFromJsonFile(filename.trim());
                    PromotionManager.getInstance().ViewPromotion();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Please enter a valid option!");
                    choice = 0;
            }
        } while(choice<5);
    }
}
