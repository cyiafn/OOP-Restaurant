package ControlClasses;

import EntityClasses.*;
import EntityClasses.MenuItem;
import EntityClasses.Order;
import StaticClasses.Database;
import StaticClasses.InputHandler;
import java.io.IOException;
import java.util.*;

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


    /**
     * Static method
     * Order Manager check exist only one at a time
     *
     * @return MenuManger
     */
    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    /**
     * This load in the order csv to be able to view orders
     * Do not remove this method because this function load the order from database
     *
     * @throws IOException, cause this read from csv file
     */
    public void init() throws IOException {
        loadinDB();
    }

    /**
     * This save order into the order csv
     * Do not remove this method because this function write the order from database
     *
     * @throws IOException, cause this write to csv file
     */
    public void savetoDB() throws IOException {
        Database.saveOrder(filename, orderList);
    }
    /**
     * This read the order csv
     * Do not remove this method because this function load the order from database
     *
     * @throws IOException, cause this read csv file
     */
    public void loadinDB() throws IOException {
        this.orderList = Database.readOrder(filename);
    }

    /**
     * remove order from orderlist
     * check order id
     * add order into orderlist
     * @throws IOException
     */
    public void updateOrder(Order order) throws IOException {
        orderList.remove(order);
        checkID();
        orderList.add(order);

    }
    /**
     * Retrieve order
     * if order id is the same
     * return order
     */
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
    /**
     * Helper function to view order
     */
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

    /**
     * Helper function to check order id
     */
    public void checkID() {
        int id = 1;
        if(orderList!=null) {
            for(Order order : orderList){
                if(order.getOrderID() > id) id = order.getOrderID();
            }
        }
        Order.setIdCount(id+1);
    }

    /**
     * Delete Order
     * Delete Order using orderID
     * @throws IOException
     */
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

    /**
     * Delete Order item
     * Delete Orderitem using menu item id
     * @throws IOException
     */
    public void deleteOrderItem(Order order, String itemid) throws IOException {
        MenuItem it = MenuManager.getInstance().findByNameForMenuItem(itemid);

        if (it == null)
            System.out.println("Item does not exist!");
        else if (order.removeItem(it));

    }
    /**
     * Create Order item
     * create Orderitem using menu item id
     * @throws IOException
     */
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
            quantity = InputHandler.getInt(1,30,"Please enter your quantity:","Invalid quantity,Please try again");
            item.setQuantity(quantity);
        } else System.out.println("This item does not exist");
    }
}
