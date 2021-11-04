/**
 Main orchestrating function. THIS IS NOT A GOD CLASS.
 @authors Gotwin, Ryan, Yifan, Daniel, Christopher
 @version 1.0
 @since 2021-10-18
*/

import ControlClasses.ReservationManager;
import Enumerations.ReservationStatus;
import UI.MenuUI;
import UI.ReservationUI;
import com.opencsv.exceptions.CsvException;

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
    public static void main(String[] args) throws IOException, CsvException {
        //init your managers + other inits here
        Scanner sc = new Scanner(System.in);
        ReservationManager reservationM = null;
        try {
            reservationM = new ReservationManager();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
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
                    ReservationUI.getInstance().display();
                    break;
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
                "4. Create promotion.\n" +
                "5. Update promotion.\n" +
                "6. Remove promotion.\n\n" +
                "7. Create order.\n" +
                "8. View order.\n" +
                "9. Add order items to order.\n" +
                "10. Remove order items from order.\n\n" +
                "11. Create reservation booking.\n" +
                "12. Check reservation booking.\n" +
                "13. Remove reservation booking.\n\n" +
                "14. Check table availability.\n\n" +
                "15. Print order invoice.\n" +
                "16. Print sale revenue report by period.\n\n" +
                "0. Exit.");
    }


}
