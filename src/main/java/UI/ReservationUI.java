package UI;

import ControlClasses.ReservationManager;
import EntityClasses.Reservation;
import Enumerations.PrintColor;
import Interfaces.UI;
import StaticClasses.InputHandler;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;

/**
 Reservation UI that redirects reservations traffic.
 @author Chen Yifan
 @version 1.0
 @since 2021-10-30
*/

/**
 * UI Handler for all things related to reservation.
 */
public class ReservationUI implements UI {
    /**
     * Stores the single instance of reservationui
     */
    private static ReservationUI instance = null;
    /**
	 * Static method to create a new reservationui if there isn't already one, else return
	 * @return singleton reservationui
	 */
    public static ReservationUI getInstance() {
        if (instance == null) instance = new ReservationUI();
        return instance;
    }

    /**
     * Displays interface for reservation manager
     */

    public void displayOptions(){
        System.out.println(PrintColor.YELLOW_BOLD);
        System.out.println(
                "==============================\n" + "\tReservation Management\n" + "=============================="
        );
        System.out.print(PrintColor.RESET);
        System.out.println("(1) Create reservation booking.\t(2) Check reservation booking.");
        System.out.println("(3) Remove reservation booking\t(4) Check table availability.");
        int opt = InputHandler.getInt(0, 4, "Please enter an option (0 to exit): ", "Please enter an integer from 0-4!");
        switch (opt){
            case 0:
                break;
            case 1:
                try {
                    if (ReservationManager.getInstance().createReservation()){
                        System.out.print(PrintColor.GREEN);
                        System.out.println("Reservation was made!");
                        System.out.print(PrintColor.RESET);
                    }else{
                        System.out.print(PrintColor.RED);
                        System.out.println("No tables available with the given parameters.");
                        System.out.print(PrintColor.RESET);
                    }

                } catch (IOException | CsvException e) {
                    e.printStackTrace();
                }
                System.out.print(PrintColor.RESET);
                break;
            case 2:
                try {
                    ReservationManager.getInstance().checkReservation();
                } catch (IOException | CsvException e) {
                    e.printStackTrace();
                }
                System.out.print(PrintColor.RESET);
                break;
            case 3:
                try {
                    if (ReservationManager.getInstance().deleteReservation()){
                        System.out.print(PrintColor.GREEN);
                        System.out.println("Deleted reservation!");
                        System.out.print(PrintColor.RESET);
                    }
                    else{
                        System.out.print(PrintColor.RED);
                        System.out.println("Reservation not deleted");
                        System.out.print(PrintColor.RESET);
                    }
                } catch (IOException | CsvException e) {
                    e.printStackTrace();
                }
                System.out.print(PrintColor.RESET);
                break;
            case 4:
                try {
                    ReservationManager.getInstance().checkAvailability();
                } catch (IOException | CsvException e) {
                    e.printStackTrace();
                }
                System.out.print(PrintColor.RESET);
                break;

        }
    }
}
