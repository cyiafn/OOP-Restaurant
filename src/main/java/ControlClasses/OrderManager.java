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

import java.io.File;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
/**
 Order Manager that handles Orders.
 @author Tan Ge Wen, Gotwin
 @version 1.0
 @since 2021-10-30
 */

public class OrderManager {
    /**
     * Constant for file name order.
     */
    ArrayList<Order> orderList = new ArrayList<Order>();
    public static final String filename = "order.csv";
    private static OrderManager instance = null;

    /**
     * Original method
     */
    public OrderManager() {
        orderList = new ArrayList<Order>();
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    public void init() throws IOException {
        loadinDB();
    }

    public void savetoDB() throws IOException {
        Database.saveOrder(filename, orderList);
    }

    public void loadinDB() throws IOException {
        this.orderList = Database.readOrder(filename);
    }

    public void updateOrder(Order order) throws IOException {
        orderList.remove(order);
        checkID();
        orderList.add(order);

    }

    public Order retrieveOrder(int orderID) {
        for (Order order : orderList) {
            if (order.getOrderID() == orderID)
                return order;
        }
        return null;
    }

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

    public void checkID() {
        int id = 1;
        if(orderList!=null) {
            for(Order order : orderList){
                if(order.getOrderID() > id) id = order.getOrderID();
            }
        }
        Order.setIdCount(id+1);
    }

    public void deleteOrder(Order order) {
        int id = order.getOrderID();
        if(id == (Order.getIdCount()-1))
            Order.setIdCount(id);
        orderList.remove(order);
        //checkID();
        try {
            savetoDB();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void deleteOrderItem(Order order, String itemid) throws IOException {
        MenuItem it = MenuManager.getInstance().findByNameForMenuItem(itemid);

        if (it == null)
            System.out.println("Item does not exist!");
        else if (order.removeItem(it));

    }

    public void createOrderItem(Order order, String itemid) throws IOException {
        MenuItem item = MenuManager.getInstance().findByNameForMenuItem(itemid);

        if (item.getName() != null) {
            if(item instanceof  SetMeal) {
                double newprice = ((SetMeal) item).getPromotionPrice();
                if (newprice > 0.0){
                    item.setPrice(newprice);
                }
            }
            if (item.getName().equals(item.getName())){
                order.removeItem(item);
            }
            order.addItem(item);
            int quantity;
//            System.out.println("Enter Quantity:");
//            Scanner sc = new Scanner(System.in);
//            quantity = sc.nextInt();
            quantity = InputHandler.getInt(1,30,"Please enter your quantity:","Invalid quantity,Please try again");
            item.setQuantity(quantity);
        } else System.out.println("This item does not exist");
    }
}
