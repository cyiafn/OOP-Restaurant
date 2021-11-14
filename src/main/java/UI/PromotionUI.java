package UI;

import ControlClasses.PromotionManager;
import Enumerations.PrintColor;
import Interfaces.UI;
import StaticClasses.InputHandler;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.util.*;


/**
 * PromotionUI is a boudary class
 * Which responsible for displaying options for Promotion Management
 * @author Chia Songcheng
 * @version 1.0
 * @since 2021-11-07
 */

public class PromotionUI implements UI {

    /**
     * Stores the single instance of Promotion UI
     */
    private static PromotionUI instance = null;

    /**
     * Static method to create a new PromotionUI if there isn't already one, else return
     * @return singleton PromotionUI
     */
    public static PromotionUI getInstance() {
        if (instance == null) {
            instance = new PromotionUI();
        }
        return instance;
    }

    /**
     * Entry point for Menu Management
     */

    public void displayOptions() {
        int choice;
        do{
            System.out.println(PrintColor.YELLOW_BOLD);
            System.out.println("\n==================================================");
            System.out.println(" Welcome To Promotion Management: ");
            System.out.println("==================================================");
            System.out.print(PrintColor.RESET);
            System.out.println("(1) Create Promotion\t(2) Update Promotion");
            System.out.println("(3) Remove Promotion\t(4) View Promotion");
            System.out.println("(5) Back");
            choice = InputHandler.getInt(1,5,"Please enter your choice","Invalid choice please try again");
            switch (choice){
                case 1:
                    try{
                        PromotionManager.getInstance().createPromotion();
                    } catch (IOException | CsvException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try{
                        PromotionManager.getInstance().updatePromotion();
                    } catch (IOException | CsvException f) {
                        f.printStackTrace();
                    }
                    break;
                case 3:
                    try{
                        PromotionManager.getInstance().deletePromotion();
                    } catch (IOException | CsvException g) {
                        g.printStackTrace();
                    }
                    break;
                case 4:
                    try{
                        PromotionManager.getInstance().viewPromotion();
                    } catch (IOException | CsvException h) {
                        h.printStackTrace();
                    }
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
