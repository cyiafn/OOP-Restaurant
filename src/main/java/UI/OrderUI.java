package UI;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import EntityClasses.Menu;
import EntityClasses.MenuItem;
import EntityClasses.Order;
import EntityClasses.Reservation;
import ControlClasses.MenuManager;
import ControlClasses.OrderManager;
import ControlClasses.ReservationManager;
import Enumerations.PrintColor;
import StaticClasses.Database;
import StaticClasses.InputHandler;
import com.opencsv.exceptions.CsvException;
import org.apache.groovy.json.internal.IO;

public class OrderUI{
    public static OrderUI instance = null;
    Scanner sc = new Scanner(System.in);


    private OrderUI() {
        sc = new Scanner(System.in);
    }

    public static OrderUI getInstance() {
        if (instance == null) instance = new OrderUI();
        return instance;
    }

    public void displayOptions() throws IOException, CsvException {
        int choice;
        do {
            System.out.println("\n==================================================");
            System.out.println(" Welcome To Order Management: ");
            System.out.println("==================================================");
            System.out.println("(1) Create Order\t(2) Update Order");
            System.out.println("(3) Remove Order\t(4) View Order");
            System.out.println("(5) Back");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    createOrder();
                    break;
                case 2:
                    Scanner sc = new Scanner(System.in);
                    int id = -1;
                    do {
                        try {
                            System.out.println("Enter order ID to be updated: ");
                            id = sc.nextInt();
                            if(id <= 0) System.out.printf("Invalid input! ");
                        } catch (InputMismatchException e) {
                            System.out.printf("Invalid input! ");
                        }
                        sc.nextLine();
                    } while (id <= 0);
                    Order order = OrderManager.getInstance().retrieveOrder(id);
                    if (order != null) updateOrder(order);
                    else System.out.println("Order does not exist!");
                    break;
                case 3:
                    if (OrderManager.getInstance().displayOrder() > 0) {
                        OrderUI.getInstance().runRemoveOrder();
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

    public void createOrder() throws IOException, CsvException {
        System.out.println("\n==================================================");
        System.out.println(" Choose A Reservation: ");
        System.out.println("==================================================");
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
        String ReservationID;
        System.out.println("Enter Reservation ID");
        ReservationID = sc.nextLine();
        System.out.println("");
        OrderManager.getInstance().checkID();
        Order order = new Order(ReservationID);
//        try {
//            var resi = ReservationManager.getInstance().getTodaysCreatedReservations();
//            if(resi.contains(ReservationID)) {
//                order.setReservationID(ReservationID);
//            }
//            //else order.setReservationNum(r.getReservationNum());
//            else {System.out.println("There is no such reservation.");
//                    return;}
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        order.setReservationID(ReservationID);
        updateOrder(order);
        if(order.getOrderedItems().size() == 0) {
            System.out.println("You did not add any items! Order deleted.");
            OrderManager.getInstance().deleteOrder(order);
        } else {
            sc.nextLine();
            System.out.println("Enter Staff: ");
            staff = sc.nextLine();
            order.setStaff(staff);
            order.setStatus("Confirmed");
            System.out.println("Order created for " + ReservationID);
        }
        //OrderManager.getInstance().savetoDB();
        OrderManager.getInstance().savetoDB2();
    }
    public void updateOrder(Order order) throws IOException {
        sc = new Scanner(System.in);
        int input;
        //int idd;
        String id = "";
        order.viewOrder();
        do {
            System.out.println("Please Choose a option to Continue:");
            System.out.println("(1) Add item");
            if(order.getStatus().equals("Ordering")) {
                System.out.println("(2) Remove item");
                System.out.println("(3) Finish");
            }else {
                System.out.println("(2) Update Status");
                System.out.println("(3) Change Remarks\n(4) Remove item");
                System.out.println("(5) Finish");
            }
            input = sc.nextInt();
            switch (input) {
                case 1:
                    MenuManager.getInstance().viewMenu();
                    //OrderManager.getInstance().createOrderItem(order, id);
                    //MenuController.retrieveInstance().displayMenu();
                    System.out.println("");
                    //idd = -1;
                    //String id;
                    //int i = 0;
                    //do {
                        try {
                            System.out.print("Enter MenuItem Name: ");
                            Scanner sc = new Scanner(System.in);
                            //idd = sc.nextInt();
                            id = sc.nextLine();
                            System.out.println(id);
                            if(id == null) System.out.printf("Invalid input! ");
                            //if(idd <= 0) System.out.printf("Invalid input! ");
                        } catch (InputMismatchException e) {
                            System.out.printf("Invalid input! ");
                        }
                        sc.nextLine();
                    //} while (idd <= 0 );
                    OrderManager.getInstance().createOrderItem(order, id);

                    //System.out.println(id);
                    input = 0;
                    break;
                case 2:
                    if(order.getStatus().equals("Ordering")) {
                        if (!(order.getOrderedItems().isEmpty())) {
                             //String idd = "";
                            //idd =-1;
                            //do {
                                try {
                                    System.out.print("Enter MenuItem Name: ");
                                    Scanner sc = new Scanner(System.in);
                                    id = sc.nextLine();
                                    //idd = sc.nextInt();
                                    if(id == null) System.out.printf("Invalid input! ");
                                    //if(idd <= 0) System.out.printf("Invalid input! ");
                                } catch (InputMismatchException e) {
                                    System.out.printf("Invalid input! ");
                                }
                                sc.nextLine();
                           // } while (idd <= 0);
//                            MenuItem it = MenuManager.getInstance().findByIdForMenuItem(id);
//                            MenuItem it2 =  MenuManager.getInstance().findByIdForMenuItem(id);
//                            System.out.println(it);
//                            System.out.println(it2);
                            //MenuItem it = MenuManager.getInstance().findByNameForMenuItem(idd);
                            //System.out.println(it);
                            //System.out.println(it.get_menuItemID());
                            OrderManager.getInstance().deleteOrderItem(order, id);
                            /*if (it == null)
                                System.out.println("Item does not exist!");
                            else if (!order.removeItem(it))
                                System.out.println("Item not in order!");*/
                            input = 0;
                        }
                        else System.out.println("Order has no item!");
                    } else {
                        sc.nextLine();
                        System.out.println("Enter new status: ");
                        System.out.println("(1) Preparing");
                        System.out.println("(2) Prepared");
                        System.out.println("(3) Delivered");
                        String input1 = sc.nextLine();
                        switch (input1) {
                            case "1":
                                order.setStatus("Preparing");
                                break;
                            case "2":
                                order.setStatus("Prepared");
                                break;
                            case "3":
                                order.setStatus("Delivered");
                                break;
                        }
                    }
                    break;
                case 3:
                    if(!order.getStatus().equals("Ordering")) {
                        sc.nextLine();
                        System.out.print("Enter new remarks: ");
                        order.setStaff(sc.nextLine());
                    } else input = 5;
                    break;
                case 4:
                    if (!(order.getOrderedItems().isEmpty())) {
                        //idd = -1
                        //id = -1;
                        //do {
                            try {
                                System.out.print("Enter MenuItem Name: ");
                                Scanner sc = new Scanner(System.in);
                                id = sc.nextLine();
                                if(id == null) System.out.printf("Invalid input! ");
                            } catch (InputMismatchException e) {
                                System.out.printf("Invalid input! ");
                            }
                            sc.nextLine();
                            //OrderManager.getInstance().deleteOrderItem(order, id);

                        //} //while (id <= 0);
                        MenuItem it = MenuManager.getInstance().findByNameForMenuItem(id);
                        if (it == null)
                            System.out.println("Item does not exist!");
                        else if (!order.removeItem(it))
                            System.out.println("Item not in order!");
                        input = 0;
                    }
//                    else{
//                        if(order.getOrderedItems().size() == 0) {
//                            System.out.println("You did not add any items! Order deleted.");
//                            OrderManager.getInstance().deleteOrder(order);
//                        }
//                    }
                    break;
                case 5:

//                    if (OrderManager.getInstance().displayOrder() > 0) {
//                        OrderUI.getInstance().runRemoveOrder();
//                    } else {
//                        System.out.println("No order made yet!");
//                    }

                    break;
                default:
                    System.out.println("Please enter a valid option.");
                    input = 0;
            }
            OrderManager.getInstance().updateOrder(order);
            order.viewOrder();
        } while (input < 5);
        System.out.println("Done");
        if(order.getOrderedItems().size() == 0) {
            System.out.println("You did not add any items! Order deleted.");
            OrderManager.getInstance().deleteOrder(order);
        }
        OrderManager.getInstance().savetoDB2();
    }

    public void runRemoveOrder() throws IOException {
        sc = new Scanner(System.in);
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
        if (order != null) {
            OrderManager.getInstance().deleteOrder(order);
            System.out.printf("Order %d has been removed.\n", orderID);
            OrderManager.getInstance().savetoDB2();
        }
        else System.out.println("Order does not exist!");
    }

    public void viewOrder() {
        sc = new Scanner(System.in);
        if (OrderManager.getInstance().displayOrder() > 0) {
            int choice = -1;
            do {
                try {
                    System.out.println("View order by (0 - Order ID)");
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
