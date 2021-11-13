/**
 Main orchestrating function. THIS IS NOT A GOD CLASS.
 @authors Gotwin, Ryan, Yifan, Daniel, Christopher
 @version 1.0
 @since 2021-10-18
*/

import ControlClasses.OrderManager;
import ControlClasses.PaymentManager;
import ControlClasses.ReservationManager;
import UI.*;

import java.io.IOException;
import java.util.Scanner;

/**
 * Main Class
 */
public class RRPSS {
    /**
     * Main ochestrating class.
     * @param args default
     */
    public static void main(String[] args) throws IOException {
        //init your managers + other inits here
        Scanner sc = new Scanner(System.in);
        //main orchestrating loop
        int opt;
        do {
            printEntryOptions();
            //handle unsafe inputs
            String unsafeInput = sc.next();
            if (unsafeInput.matches("\\d+")){
                opt = Integer.parseInt(unsafeInput);
            }
            else{
                opt = -1;
            }
            //Selection of option
            switch(opt) {
                case 0:
                    System.out.println("Goodbye!");
                    break;
                case 1:
                    MenuUI.getInstance().displayOptions();
                    break;
                case 2:
                    ReservationUI.getInstance().displayOptions();
                    break;
                case 3:
                    OrderUI.getInstance().displayOptions();
                    break;
                case 4:
                    PaymentUI.getInstance().displayOptions();
                    break;
                case 5:
                    PromotionUI.getInstance().displayOptions();
                default:
                    System.out.println("Please enter an integer from 0 - 16!");
                    break;
            }
        } while(opt != 0);

    }

    /**
     * Printing menu.
     */
    public static void printEntryOptions(){
        System.out.println("\n=========================\n" +
                "1. Menu Management.\n\n" +
                "2. Reservation Management.\n\n" +
                "3. Order Management.\n\n" +
                "4. Payment Management.\n\n" +
                "5. Promotion Management.\n\n" +
                "0. Exit.");
    }


}
