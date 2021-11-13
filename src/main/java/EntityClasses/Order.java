package EntityClasses;


import java.io.IOException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Formatter;
import Enumerations.PrintColor;

/**
 * Order entity class
 @author Tan Ge Wen, Gotwin
 @version 1.0
 @since 2021-10-30
 */

/**
 Attributes of Order
 */
public class Order {
    private static int idCount = 0;
    private int orderID;
    private String reservationID;
    private ArrayList<MenuItem> orderedItems = new ArrayList<MenuItem>();
    private String status = "Ordering";
    private String Staff = "";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM");
    private String date;


    /**
     * Constructor of Order
     * @param oid OrderID
     * @param Staff Staff Name
     * @param date datetime
     * @param reservationID reservationID
     * @param orderedItems Array list of menu items
     * @param status Order status
     */
    public Order(int oid,String reservationID,String date,String status, String Staff,ArrayList<MenuItem> orderedItems){
        this.orderID = oid;
        this.Staff = Staff;
        this.date = date;
        this.reservationID = reservationID;
        this.orderedItems = orderedItems;
        this.status = status;
        idCount = orderID+1;
    }

    /**
     * Constructor of Order using reservationID
     * @param reservationID reservationID
     */
    public Order(String reservationID){
        this.orderID = idCount;
        Calendar c = Calendar.getInstance();
        String d = sdf.format(c.getTime());
        this.date = d;
        this.reservationID = reservationID;
        idCount++;
    }


    public static int getIdCount() {
        return idCount;
    }

    public static void setIdCount(int ID) {
        idCount = ID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    /**
     * orderID getter
     * @return orderID
     */
    public Integer getOrderID() {
        return orderID;
    }

    /**
     * Datetime object getter
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * Datetime object setter
     * @param date date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * reservationID getter
     * @return reservationID
     */
    public String getReservationID() {
        return reservationID;
    }

    /**
     * reservationID setter
     * @param reservationID reservationID
     */
    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    /**
     * Staff getter
     * @return Staff
     */
    public String getStaff() {
        return Staff;
    }

    /**
     * Staff setter
     * @param staff staff
     */
    public void setStaff(String staff) {
        Staff = staff;
    }

    /**
     * orderedItems getter
     * @return orderedItems
     */
    public ArrayList<MenuItem> getOrderedItems() {
        return orderedItems;
    }

    /**
     * status getter
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * status setter
     * @param status status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Add items into ordered items
     * @param item item
     */
    public void addItem(MenuItem item){

        this.orderedItems.add(item);
    }
    /**
     * Remove items from ordereditems
     * @param item item
     * @return boolean
     * @throws IOException will throw io exception
     */
    public boolean removeItem(MenuItem item) throws IOException {
        for (MenuItem it : orderedItems) {

            if (it.getMenuItemID().equals(item.getMenuItemID())) {
                this.orderedItems.remove(it);
                return true;
            }
        }
        return false;
    }

    /**
     * Simply prints out Order details
     */
    public void viewOrder() {
        Formatter fmt = new Formatter();
        System.out.println(PrintColor.YELLOW_BOLD);
        System.out.println("ID    Staff      Date                       ReservationID                    Status   ");
        System.out.print(PrintColor.RESET);
        System.out.println(this);
        System.out.println(PrintColor.YELLOW_BOLD);
        System.out.println("======================================================================================");
        System.out.println("MenuID                                      Name                           Quantity  ");
        System.out.println("======================================================================================");
        System.out.print(PrintColor.RESET);
        for (MenuItem item : orderedItems) {
            System.out.printf("%s        %-30s     %-5s \n", item.getMenuItemID(), item.getName(), item.getQuantity());
        }
        System.out.println(PrintColor.YELLOW_BOLD);
        System.out.println("======================================================================================");
        System.out.print(PrintColor.RESET);
    }

    public String toString() {
        return (String.format(orderID +  "\t"+ Staff+ "    \t" +    date + "\t" +  reservationID + "       \t" + status ));
        //return (String.format("%s        %-30s   %-5s \n",orderID, Staff, date , reservationID , status ));
    }

}
