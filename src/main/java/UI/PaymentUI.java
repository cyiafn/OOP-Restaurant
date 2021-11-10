package UI;

import ControlClasses.OrderManager;
import ControlClasses.PaymentManager;
import EntityClasses.Invoice;
import EntityClasses.Order;
import Enumerations.PrintColor;
import Interfaces.UI;
import StaticClasses.InputHandler;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.*;

/**
 * PaymentUI the boundary class that will be responsible for pass User's inputs to the PaymentManager(Controller Class)
 * PaymentUI will also be responsible for the display of options for user to pass inputs to the PaymentManager Class
 * @author Ong Yew Han
 * @version 1.7
 * @since 2021-11-09
 */
public class PaymentUI implements UI {
    /**
     * Stores the Singleton instance of the PaymentUI
     */
    public static PaymentUI instance = null;
    int c;
    Scanner sc = new Scanner(System.in);

    /**
     * Static method to create a new PaymentUI if there isn't already one, else return
     * @return a Singleton PaymentUI
     */
    public static PaymentUI getInstance(){
        if(instance == null) instance = new PaymentUI();
        return instance;
    }

    public static boolean isNumeric(String str){return false;};

    /**
     * Helper function to display the UI if the View Invoice option is selected by User
     * in displayOptions. The inputs will be validated here before parsing to PaymentManger
     */
    public void generateInvoice() {
        Scanner sc = new Scanner(System.in);
          if (true){
            int choice = -1;
            do {
                try {
                    System.out.println("Create invoice by\n(0 -> Order ID)");
                    System.out.println("(1 -> Back)");
                    //System.out.println("Create invoice by (1 -> Table No.)");
                    choice = sc.nextInt();
                    if(choice != 0 && choice != 1) System.out.printf("Invalid input! ");
                } catch (InputMismatchException e) {
                    System.out.printf("Invalid input! ");
                }
                sc.nextLine();
            } while (choice != 0 && choice != 1);

            if(choice == 0) {
                int orderID = -1;
                do {
                    try {
                        System.out.print("Enter Order ID:");
                        orderID = sc.nextInt();
                        if(orderID <= 0) System.out.printf("Invalid input! ");
                    } catch (InputMismatchException e) {
                        System.out.printf("Invalid input! ");
                    }
                    sc.nextLine();
                } while (orderID <= 0);
                Order order = OrderManager.getInstance().retrieveOrder(orderID);
                if (order != null){
                    System.out.println("Are you a Member of the Restaurant?");
                    System.out.println("0 -> No");
                    System.out.println("1 -> Yes");
                    choice = sc.nextInt();
                    PaymentManager.getInstance().createInvoice(order,choice);
                }
                else System.out.println("Order does not exist!");
            }
        } else System.out.println("No order made yet!");
    }

    /**
     * Helper function to display the UI if the View Invoice option is selected by User
     * in displayOptions. The inputs will be validated here before parsing to PaymentManger
     */
    public void viewInvoice(){
        Scanner sc = new Scanner(System.in);
        String invoiceId = InputHandler.getString("Enter the Invoice you would like to view");
        Invoice invoice = PaymentManager.getInstance().retrieveInvoice(invoiceId);
        if( invoice != null) {
            PaymentManager.getInstance().displayPayment(invoice);
        }
        else
            System.out.println("Invoice not Found");
    }
    /**
     * Helper function to display the UI if the View Invoice option is selected by User
     * in displayOptions. The inputs will be validated here before parsing to PaymentManger
     */
    public void viewRevenueReport(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Period");
        System.out.println("(1)By day\t(2)By Month\t(3)By Year\t(0)Back");
        int c = InputHandler.getInt(0,3,"", "Please enter a valid input");
        switch (c){
            case 1:
                String date;
//                    System.out.println("Enter the date in 'yyyyMMdd' format");
//                    date = sc.nextInt();
                    date = InputHandler.stringDate2("Enter the Date in 'dd/MM/yyyy' format");
                    PaymentManager.getInstance().generateRevenueReport(date, c);
                break;
            case 2:
                String month;
//                    System.out.println("Enter the date in 'yyyyMM' format");
//                    month = sc.nextInt();
                    month = InputHandler.stringDate3("Enter the Month in 'MM/yyyy' format");
                    PaymentManager.getInstance().generateRevenueReport(month, c);
                break;
            case 3:
                int year;
                date = InputHandler.stringDate4("Enter the Year in 'yyyy' format");
                PaymentManager.getInstance().generateRevenueReport(date, c);
//                do{
//                    System.out.println("Enter the date in 'yyyy' format");
//                    year = sc.nextInt();
//                    if( String.valueOf(year).length()==4){
//                        PaymentManager.getInstance().generateRevenueReport(String.valueOf(year), c);
//                        break;
//                    }
//                    else{
//                        if(year!=0)System.out.println("Invalid Year!");
//                        else System.out.println("Exited");
//                    }
//                }while (year!=0);
                break;
            default:

        }
    }

    /**
     * displayOptions() is the entry point called by the RRPSS class when functions related to Payment is
     * required
     * @throws IOException to catch any exception related to User Inputs
     */
    public void displayOptions() throws IOException {
        int choice;
        do{
            System.out.println(PrintColor.YELLOW_BOLD);
            System.out.println("\n==================================================");
            System.out.println(" Welcome To Payment Management: ");
            System.out.println("==================================================");
            System.out.print(PrintColor.RESET);
            System.out.println("(1) Create Invoice and Make Payment.\t(2) View Invoice.");
            System.out.println("(3) Revenue Report.\t(4) Back.");
            String s;
            choice = InputHandler.getInt(0, 4, "Please enter an option", "Please enter an integer from 0-4!");
            switch (choice) {
                case 1:
                    generateInvoice();
                    InputHandler.getString("Press Any Key To Continue");
                    break;
                case 2:
                    viewInvoice();
                    InputHandler.getString("Press Any Key To Continue");
                    break;
                case 3:
                    viewRevenueReport();
                    InputHandler.getString("Press Any Key To Continue");
                    break;
            }
        } while (choice != 4);
    }
}
