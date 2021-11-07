package EntityClasses;

import ControlClasses.MenuManager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

import java.util.Calendar;

public class Order {
    private static int idCount = 1;
    private int orderID;
    //private LocalDateTime creationDate;
    private String reservationID;
    //private Reservation reservationID;
    //private ArrayList<Staff> staff1 = new ArrayList<Staff>();;
    private ArrayList<MenuItem> orderedItems = new ArrayList<MenuItem>();;
    //private ArrayList<String> orderedItems;
    private int quantity;
    //private OrderStatus status;
    private String status = "Ordering";
    private String Staff = "";
    //private String CustName;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM");
    private String date;

    public Order(int oid, String reservationID, ArrayList<MenuItem> orderedItems, String status, int quantity,String Staff){
        this.orderID = oid;
        //this.creationDate = creationDate;
        Calendar c = Calendar.getInstance();
        String d = sdf.format(c.getTime());
        this.date = d;
        this.reservationID = reservationID;
        this.orderedItems = orderedItems;
        this.quantity = quantity;
        this.status = status;
        this.Staff = Staff;
        //this.staff1 = staff1;
        idCount++;

    }

    public Order(int oid,String reservationID,String date,String status, String Staff,ArrayList<MenuItem> orderedItems){
        this.orderID = oid;
        this.Staff = Staff;
        this.date = date;
        this.reservationID = reservationID;
        this.orderedItems = orderedItems;
        this.status = status;
        //this.quantity = quantity;
        //idCount = orderID+1;
    }

    public Order(int oid,String reservationID,String date,String status, String Staff,ArrayList<MenuItem> orderedItems,int quantity){
        this.orderID = oid;
        this.Staff = Staff;
        this.date = date;
        this.reservationID = reservationID;
        this.orderedItems = orderedItems;
        this.status = status;
        this.quantity = quantity;
        //idCount = orderID+1;
    }

    /*public Order(int oid,String Staff, String date, String reservationID,ArrayList<MenuItem> orderedItems, String status){
        this.orderID = oid;
        this.Staff = Staff;
        this.date = date;
        this.reservationID = reservationID;
        this.orderedItems = orderedItems;
        this.status = status;
    }*/

    public Order(int oid, String date, String reservationID, ArrayList<MenuItem> orderedItems,String status, String Staff){
        this.orderID = oid;
        //this.creationDate = creationDate;
        this.date = date;
        this.reservationID = reservationID;
        this.orderedItems = orderedItems;
        //this.quantity = quantity;
        this.status = status;
        this.Staff = Staff;
        //this.staff1 = staff1;
        idCount = orderID+1;
    }

    public Order(String reservationID){
        this.orderID = idCount;
        Calendar c = Calendar.getInstance();
        String d = sdf.format(c.getTime());
        this.date = d;
        this.reservationID = reservationID;
        idCount++;
    }

    /*public void print() {
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println("Reservation ID: " + this.reservationID + "\nDate: " + this.creationDate.format(myFormatObj) +
        "\nOrders: " + this.orderedItems + "\nQuantity: " + Integer.toString(this.quantity) + "\nStatus: " + status.name()
        );
    }*/

    public static int getIdCount() {
        return idCount;
    }

    public static void setIdCount(int ID) {
        idCount = ID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Integer getOrderID() {
        return orderID;
    }

//    public LocalDateTime getCreationDate() {
//        return creationDate;
//    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }


    public String getStaff() {
        return Staff;
    }

    public void setStaff(String staff) {
        Staff = staff;
    }

//    public ArrayList<Staff> getStaff1() {
//        return staff1;
//    }

    public ArrayList<MenuItem> getOrderedItems() {
        return orderedItems;
    }

//    public OrderStatus getStatus() {
//        return status;
//    }
//
//    public void setStatus(OrderStatus status) {
//        this.status = status;
//    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /*public Menu createOrder() {
        ArrayList<MenuItem> orderedItems = new ArrayList<>()
    }*/

    /*public void addtoOrder(MenuItem item) {
        this.orderedItems.add(item);
    }*/

    /*public String[] getLineCSVFormat(){
        //String str = String.join(",", orderedItems);
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (MenuItem item : orderedItems) {
            String[] row = {orderID,reservationID, this.creationDate.format(myFormatObj), item.toString(), Integer.toString(quantity), status.name()};
            return row;
        }
        return null;
    }*/

    /*public boolean cleanup(){
        if (this.status == OrderStatus.CREATED && Duration.between(this.creationDate, LocalDateTime.now()).toMinutes() >= 15){
            this.status = OrderStatus.EXPIRED;
            return true;
        }
        return false;
    }*/
//    public void addStaff(Staff staff){
//        this.staff1.add(staff);
//    }

    public void addItem(MenuItem item){

        this.orderedItems.add(item);
    }

    public boolean removeItem(MenuItem item) throws IOException {
        for (MenuItem it : orderedItems) {
//            System.out.println(it.toString());
            //System.out.println(it.get_menuItemID());
            //this.orderedItems.remove(it);
            //return true;

            if (it.get_menuItemID() == item.get_menuItemID()) {
                this.orderedItems.remove(it);
                return true;
            }
//                if (it.get_menuItemID() == it.get_menuItemID()) {
//                    System.out.println(it.get_menuItemID());
//                    System.out.println(item);
//                    //this.orderedItems.remove(it);
//                    return true;
//                }
//            if (orderedItems.contains(it.get_menuItemID())) {
//                System.out.println(it);
//                this.orderedItems.remove(it);
//                return true;
//            }

        }
        return false;
    }

    /*public String[] getLineCSVFormat(){
        //DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (MenuItem item : orderedItems) {
            String[] row = {Integer.toString(orderID), Staff, date, reservationID, item.getmenuItemID(), item.getName(), String.valueOf(item.getPrice())};
            return row;
        }
        return new String[0];
    }*/
//    public String[] getLineCSVFormat(){
//        //DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        String[] row = {Integer.toString(orderID), Staff, date, reservationID, String.valueOf(orderedItems)};
//        return row;
//
//
//    }

    public void viewOrder() {
        System.out.println("ID    Staff      Date                       ReservationID                       Status   ");
        System.out.println(toString());
        System.out.println("=================================================================================");
        System.out.println("ID                                                Name           Price(S$)    Quantity  ");
        System.out.println("=================================================================================");
        for (MenuItem item : orderedItems) {
            System.out.println(item.get_menuItemID() +"    \t" + item.get_name() +"    \t" + item.get_price() +"    \t" + item.get_quantity());
            //System.out.println(item.get_quantity() +"    \t" + item.get_name() +"    \t" + item.get_price());
        }
        System.out.println("=================================================================================");
    }

    public String toString() {

        return (String.format(orderID +  "\t"+ Staff+ "    \t" +    date + "\t" +  reservationID + "       \t" + status ));
    }








}
