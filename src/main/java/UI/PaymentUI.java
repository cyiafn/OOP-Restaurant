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

public class PaymentUI implements UI {
    public static PaymentUI instance = null;
    int c;
    Scanner sc = new Scanner(System.in);

    private PaymentUI(){
        sc = new Scanner(System.in);
    }

    public static PaymentUI getInstance(){
        if(instance == null) instance = new PaymentUI();
        return instance;
    }

    public static boolean isNumeric(String str){return false;};

    public void generateInvoice() {
        Scanner sc = new Scanner(System.in);

//        if (PaymentManager.getInstance().displayOrder() > 0)
          if (true){
            int choice = -1;
            do {
                try {
                    System.out.println("Create invoice by (0 -> Order ID)");
                    System.out.println("Create invoice by (1 -> Table No.)");
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

    public void viewRevenueReport(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Period");
        System.out.println("(1)By day\t(2)By Month\t(3)By Year\t(0)Back");
        int c = InputHandler.getInt(0,3,"", "Please enter a valid input");
        switch (c){
            case 1:
                int date;
                do{
                    System.out.println("Enter the date in 'yyyyMMdd' format");
                    date = sc.nextInt();
                    if( String.valueOf(date).length()==8){
                        PaymentManager.getInstance().generateRevenueReport(String.valueOf(date), c);
                        break;
                    }
                    else{
                        if(date!=0)System.out.println("Invalid Date!");
                        else System.out.println("Exited");
                    }
                }while (date!=0);
                break;
            case 2:
                int month;
                do{
                    System.out.println("Enter the date in 'yyyyMM' format");
                    month = sc.nextInt();
                    if( String.valueOf(month).length()==6){
                        PaymentManager.getInstance().generateRevenueReport(String.valueOf(month), c);
                        break;
                    }
                    else{
                        if(month!=0)System.out.println("Invalid Month!");
                        else System.out.println("Exited");
                    }
                }while (month!=0);
                break;
            case 3:
                int year;
                do{
                    System.out.println("Enter the date in 'yyyy' format");
                    year = sc.nextInt();
                    if( String.valueOf(year).length()==4){
                        PaymentManager.getInstance().generateRevenueReport(String.valueOf(year), c);
                        break;
                    }
                    else{
                        if(year!=0)System.out.println("Invalid Year!");
                        else System.out.println("Exited");
                    }
                }while (year!=0);
                break;
            default:

        }
    }

    public void displayOptions() throws IOException {
            System.out.println(PrintColor.YELLOW_BOLD);
            System.out.println(
                    "==============================\n" + "\tPayment Management\n" + "=============================="
            );
            System.out.print(PrintColor.RESET);
            System.out.println("(1) Create Invoice and Make Payment.\t(2) View Invoice.");
            System.out.println("(3) Revenue Report.\t(4) NULL.");
            //System.out.println("(3) Remove reservation booking\t(4) Check table availability.");
            String s;
            int opt = InputHandler.getInt(0, 3, "Please enter an option (0 to exit): ", "Please enter an integer from 0-3!");
            switch (opt) {
                case 1:
                    //createInvoice();
                    generateInvoice();
                    s = InputHandler.getString("Press Any Key To Continue");
                    break;
                case 2:
                    viewInvoice();
                    s = InputHandler.getString("Press Any Key To Continue");
                    break;
                case 3:
                    viewRevenueReport();
                    s = InputHandler.getString("Press Any Key To Continue");
                    break;
            }
    }
}
