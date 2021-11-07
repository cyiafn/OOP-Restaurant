package UI;

import ControlClasses.OrderManager;
import ControlClasses.PaymentManager;
import EntityClasses.Order;
import Enumerations.PrintColor;
import Interfaces.UI;
import StaticClasses.InputHandler;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
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

    public void createInvoice() throws IOException, CsvException {
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
                    PaymentManager.getInstance().createInvoice(order, choice);
                }
                else System.out.println("Order does not exist!");
            }
        } else System.out.println("No order made yet!");
    }

    public void viewInvoice(){
//        double in
    }

    public void revenueReport(){

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
            int opt = InputHandler.getInt(0, 2, "Please enter an option (0 to exit): ", "Please enter an integer from 0-2!");
            switch (opt) {
                case 1:
                    //createInvoice();
                    break;
                case 2:
                    viewInvoice();
                    break;
                case 3:
                    revenueReport();
            }
    }
}
