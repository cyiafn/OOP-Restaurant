package ControlClasses;

import EntityClasses.*;
import EntityClasses.MenuItem;
import EntityClasses.Order;
import EntityClasses.Reservation;
import EntityClasses.Staff;
import Enumerations.FoodCategory;
import Enumerations.ReservationStatus;
import StaticClasses.Database;
import StaticClasses.InputHandler;
import com.opencsv.exceptions.CsvException;
import org.mockito.internal.matchers.Or;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderManager {
    //private HashMap<Integer, ArrayList<Order>> orderlist;
    ArrayList<Order> orderList = new ArrayList<Order>();

    private ArrayList<Staff> staff;
    private ArrayList<Reservation> reservations;
    private int quantity;
    public static final String filename = "order.csv";

    private static OrderManager instance = null;
    /*String filename =  "csv/order.json";
    private Order _order;

    public Order get_order() {
        return _order;
    }

    public void set_order(Order _order) {
        this._order = _order;
    }*/
    public OrderManager() {
        orderList = new ArrayList<Order>();
    }


    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    public void OrderManager(){
        ReservationManager reservationM = null;
        try {
            reservationM = new ReservationManager();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    public void savetoDB2() throws IOException {
        //OrderDB orderdb = new OrderDB();
        Database.saveOrder(filename, orderList);
    }



    public void savetoDB() throws IOException {
        Database.WriteToJsonFile(orderList, "csv/order.json");

    }
    public void loadinDB() throws IOException {
        //OrderDB orderdb = new OrderDB();
        this.orderList = Database.readOrder(filename);
    }


//    public void savetoDB1(Order order) throws IOException {
//        Database.writeLine(orderFile, order.getLineCSVFormat());
//
//    }

//    public void loadinDB() throws IOException, CsvException {
//        //loads everything
//        Map data = Database.loadFromJsonFile(filename.trim());
//        //Order new_order = formatDatabaseMapIntoMenu(data);
//        //_order = new_order;
//    }

    public void updateOrder(Order order) throws IOException {
        orderList.remove(order);
        //checkID();
        /*if(orderList.add(order)){
            Database.writeLine(orderFile, order.getLineCSVFormat());
        }*/
        orderList.add(order);

    }
    /*public void createOrderItem(Order order, String itemId) {
        Menu item = MenuManager.retrieveInstance().FindById(itemId);
        if (item != null) order.addItem(item);
        else System.out.println("This item does not exist");
    }*/
    public Order retrieveOrder(int orderID) {
        for (Order order : orderList) {
            if (order.getOrderID() == orderID)
                return order;
        }
        return null;
    }
    /*public ArrayList<Order> retrieveOrderList(String ReservationID) {
        ArrayList<Order> ol = new ArrayList<Order>();
        for (Order order : orderList) {
            if (order.getReservationID().equals(ReservationID))
                ol.add(order);
        }
        if(ol.size() > 0) return ol;
        else return null;
    }*/
    public void displayOrder(int orderID) {
        Order order;
        order = retrieveOrder(orderID);
        order.viewOrder();
    }
    public final int displayOrder() {
        Set<Integer> s = new HashSet<>();
        for (Order order : orderList) {
            int i = order.getOrderID();
            if (!s.contains(i)) {
                s.add(i);
            }
        }
        return orderList.size();
    }



    public void deleteOrder(Order order) {
        int id = order.getOrderID();
        if(id == (Order.getIdCount()-1))
            Order.setIdCount(id);
        orderList.remove(order);
        //checkID();
    }

    public void deleteOrderItem(Order order, String itemid) throws IOException {
        MenuItem it = MenuManager.getInstance().FindByIdForMenuItem(itemid);
        //System.out.println(it.get_menuItemID());

        if (it == null)
            System.out.println("Item does not exist!");
        else if (!order.removeItem(it));
            //System.out.println("Item not in order!");
        //MenuItem item = MenuManager.retrieveInstance().FindByIdForMenuItem(itemid);
        //System.out.println(item.get_menuItemID());
        //if (item != null) order.removeItem(item);
        //System.out.println(item.get_menuItemID());
        //else System.out.println("This item does not exist");
    }

    public void createOrderItem(Order order, String itemid) throws IOException {
        MenuItem item = MenuManager.getInstance().FindByIdForMenuItem(itemid);
        //System.out.println(item.get_menuItemID());
        if (item != null) order.addItem(item);
        else System.out.println("This item does not exist");
        //order.addtoOrder(item);
        //MenuItem item = MenuManager.retrieveInstance().FindById(menuid);
        //int res_find=MenuManager.retrieveInstance().FindById(item);
        /*if(res_find == 1)
        {*/
            //ArrayList<MenuItem> mi_arr = Or
            //order.addtoOrder(item);
            //Menu m = MenuManager.retrieveInstance().get_menu();
            //MenuItem item = MenuManager.retrieveInstance().FindByIdForMenuItem(item);
            //int res = m.Update(id)
            /*if (res== 1) {
                //order.addItem(m);
                order.addtoOrder(menuid);
                //System.out.println(m);
            }*/

        //}

    }





    /*public OrderManager() throws IOException, CsvException {
        staff = new ArrayList();
        orderlist = new HashMap<>();
        reservations = new ArrayList();
        quantity = 0;
        loadReservations();
        //loadOrder();


    }*/


   /* public void createOrder(Order order, String itemId) throws IOException, CsvException {
        ReservationManager reservationM = null;
        try {
            reservationM = new ReservationManager();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        *//*Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the TableNo: ");
        Integer tableNo = Integer.parseInt(sc.nextLine());

        if(reservations.get(tableNo).equals(tableNo)){
            MenuItem item = MenuManager.retrieveInstance().FindById(itemId);
            if (item != null) order.addtoOrder(item);
            else System.out.println("This item does not exist");
        }*//*

    }*/
     /*public boolean createOrder() throws IOException, CsvException {
         Scanner sc = new Scanner(System.in);
         String rid =
     }*/




}
