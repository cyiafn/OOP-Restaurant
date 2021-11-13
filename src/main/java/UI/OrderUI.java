package UI;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import Interfaces.UI;
import StaticClasses.InputHandler;
import EntityClasses.MenuItem;
import EntityClasses.Order;
import ControlClasses.MenuManager;
import ControlClasses.OrderManager;
import ControlClasses.ReservationManager;
import com.opencsv.exceptions.CsvException;
import Enumerations.PrintColor;

/**
 * OrderUI is a boundary class
 * Which responsible to display options for Order Management and use OrderManager to create order items
  @author Tan Ge Wen, Gotwin
  @version 1.0
  @since 2021-10-30
 */
public class OrderUI implements UI {
    public static OrderUI instance = null;
    Scanner sc = new Scanner(System.in);


    private OrderUI() {
        sc = new Scanner(System.in);
    }

    /**
     * Singleton design for Order Management
     * @return return one and only one Order UI
     */
    public static OrderUI getInstance() {
        if (instance == null) instance = new OrderUI();
        return instance;
    }

    /**
     * Entry point for Order Management
     */
    public void displayOptions() {
        int choice;
        do {
            System.out.println(PrintColor.YELLOW_BOLD);
            System.out.println("\n==================================================");
            System.out.println(" Welcome To Order Management: ");
            System.out.println("==================================================");
            System.out.print(PrintColor.RESET);
            System.out.println("(1) Create Order\t(2) Update Order");
            System.out.println("(3) Remove Order\t(4) View Order");
            System.out.println("(5) Back");
            choice = InputHandler.getInt(1, 5, "Please Choose a option to Continue: ", "Please enter an integer from 0-5!");
            switch (choice) {
                case 1:
                    try {
                        createOrder();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (CsvException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    int id = -1;
                    do {
                        try {
                            id = InputHandler.getInt(2, 100, "Enter order ID to be updated: ", "Please enter an integer from 0-5!");
                            if(id <= 0) System.out.printf("Invalid input! ");
                        } catch (InputMismatchException e) {
                            System.out.printf("Invalid input! ");
                        }
                    } while (id <= 0);
                    Order order = OrderManager.getInstance().retrieveOrder(id);
                    if (order != null) {
                        try {
                            updateOrder(order);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else System.out.println("Order does not exist!");
                    break;
                case 3:
                    if (OrderManager.getInstance().displayOrder() > 0) {
                        try {
                            OrderUI.getInstance().runRemoveOrder();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("No order made yet!");
                    }
                    break;
                case 4:
                    viewOrder();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Please enter a valid option!");
                    choice = 0;
            }
        } while (choice < 5);
    }

    /**
     * Create Order
     * create Order using reservation id
     * @throws IOException will throw io exception
     * @throws CsvException will throw io exception
     *
     */
    public void createOrder() throws IOException, CsvException {
        System.out.println(PrintColor.YELLOW_BOLD);
        System.out.println("\n==================================================");
        System.out.println(" Choose A Reservation: ");
        System.out.println("==================================================");
        System.out.print(PrintColor.RESET);
        try {
            var resi = ReservationManager.getInstance().getTodaysCreatedReservations();
            for ( int i = 0; i < resi.size(); i++ ){
                String str2 = Integer.toString(i);
                System.out.println("(" + str2+ ")" + " " + resi.get(i));
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        sc = new Scanner(System.in);
        String staff;
        String ReservationID = InputHandler.getString("Enter Reservation ID: ");
        System.out.println();
        OrderManager.getInstance().checkID();
        Order order = new Order(ReservationID);
        try {
            var resi = ReservationManager.getInstance().getTodaysCreatedReservations();
            if(resi.contains(ReservationID)) {
                order.setReservationID(ReservationID);
            }
            //else order.setReservationNum(r.getReservationNum());
            else {System.out.println("There is no such reservation.");
                    return;}


        } catch (IOException e) {
            e.printStackTrace();
        }
        order.setReservationID(ReservationID);
        ReservationManager.getInstance().checkin(ReservationID);
        updateOrder(order);
        if(order.getOrderedItems().size() == 0) {
            System.out.println("You did not add any items! Order deleted.");
            OrderManager.getInstance().deleteOrder(order);
        } else {
            staff = InputHandler.getString("Served By: ");
            order.setStaff(staff);
            order.setStatus("Confirmed");
            System.out.println("Order created for " + ReservationID);
        }
        OrderManager.getInstance().savetoDB();
    }
    /**
     * Update Order
     * update Order able to add/remove items
     * @throws IOException will throw io exception
     * @param order order
     *
     */
    public void updateOrder(Order order) throws IOException {
        sc = new Scanner(System.in);
        int input;
        String id = "";
        order.viewOrder();
        do {
            System.out.println("(1) Add item");
            if(order.getStatus().equals("Ordering")) {
                System.out.println("(2) Remove item");
                System.out.println("(3) Finish");
            }else {
                System.out.println("(2) Remove item");
                System.out.println("(3) Finish");
            }
            input = InputHandler.getInt(1, 3, "Please Choose a option to Continue: ", "Please enter an integer from 0-3!");
            switch (input) {
                case 1:
                    MenuManager.getInstance().viewMenu();
                    System.out.println();
                        try {
                             id = InputHandler.getString("Enter MenuItem Name: ");
                            if(id == null) System.out.printf("Invalid input! ");
                        } catch (InputMismatchException e) {
                            System.out.printf("Invalid input! ");
                        }
                    OrderManager.getInstance().createOrderItem(order, id);
                    input = 0;
                    break;
                case 2:
                    if(order.getStatus().equals("Ordering")) {
                        if (!(order.getOrderedItems().isEmpty())) {
                                try {
                                    id = InputHandler.getString("Enter MenuItem Name: ");
                                    if(id == null) System.out.printf("Invalid input! ");
                                } catch (InputMismatchException e) {
                                    System.out.printf("Invalid input! ");
                                }
                            OrderManager.getInstance().deleteOrderItem(order, id);
                            input = 0;
                        }
                        else System.out.println("Order has no item!");
                    } else {
                        if (!(order.getOrderedItems().isEmpty())) {
                            try {
                                id = InputHandler.getString("Enter MenuItem Name: ");
                                if(id == null) System.out.printf("Invalid input! ");
                            } catch (InputMismatchException e) {
                                System.out.printf("Invalid input! ");
                            }
                            MenuItem it = MenuManager.getInstance().findByNameForMenuItem(id);
                            if (it == null)
                                System.out.println("Item does not exist!");
                            else if (!order.removeItem(it))
                                System.out.println("Item not in order!");
                            input = 0;
                        }
                    }
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Please enter a valid option.");
                    input = 0;
            }
            OrderManager.getInstance().updateOrder(order);
            order.viewOrder();
        } while (input < 3);
        System.out.println("Done");
        if(order.getOrderedItems().size() == 0) {
            System.out.println("You did not add any items! Order deleted.");
            OrderManager.getInstance().deleteOrder(order);
        }
        OrderManager.getInstance().savetoDB();
    }

    /**
     * remove Order
     * remove Order using orderid
     * @throws IOException will throw io exception
     *
     */
    public void runRemoveOrder() throws IOException {
        sc = new Scanner(System.in);
        int orderID = -1;
        do {
            try {
                orderID = InputHandler.getInt(1,100,"Enter OrderID:","Invalid OrderID Please Try Again");
                if(orderID <= 0) System.out.printf("Invalid input! ");
            } catch (InputMismatchException e) {
                System.out.printf("Invalid input! ");
            }
        } while (orderID <= 0);
        Order order = OrderManager.getInstance().retrieveOrder(orderID);
        if (order != null) {
            OrderManager.getInstance().deleteOrder(order);
            System.out.printf("Order %d has been removed.\n", orderID);
            OrderManager.getInstance().savetoDB();
        }
        else System.out.println("Order does not exist!");
    }

    /**
     * view Order
     * view Order using orderid
     *
     */
    public void viewOrder() {
        sc = new Scanner(System.in);
        if (OrderManager.getInstance().displayOrder() > 0) {
            int choice = -1;
            do {
                try {
                    choice = InputHandler.getInt(0,0,"View order by (0 - Order ID)","Invalid, Please Try Again");
                    if(choice != 0 && choice != 1) System.out.printf("Invalid input! ");
                } catch (InputMismatchException e) {
                    System.out.printf("Invalid input! ");
                }
            } while (choice != 0 && choice != 1);
            if(choice == 0) {
                int orderID = -1;
                do {
                    try {
                        System.out.print("Enter OrderID:");
                        orderID = sc.nextInt();
                        if(orderID <= 0) System.out.printf("Invalid input! ");
                    } catch (InputMismatchException e) {
                        System.out.printf("Invalid input! ");
                    }
                    sc.nextLine();
                } while (orderID <= 0);
                Order order = OrderManager.getInstance().retrieveOrder(orderID);
                if (order != null) order.viewOrder();
                else System.out.println("Order does not exist!");
            }
        } else System.out.println("No order made yet!");
    }
}
