package EntityClasses;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class Invoice {
    private static int idCount = 1;
    private int orderId;
    private int invoiceId;
    private double subTotal = 0;
    private double total = 0;
//    private ArrayList<Order> orders;  // GET ORDER FROM ORDER
    private Order orders;

    public enum Membership{ IS_MEMBER, NOT_MEMBER }
    private Membership memberStatus;
    private String date;

    private String staffName;
    private int tableNo;

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

//    public Invoice(int orderId, ArrayList<MenuItem> orders){
//        this.invoiceId = orderId ;
//        this.orders = orders;
//
//        Calendar c = Calendar.getInstance();
//        this.date = sdf.format(c.getTime());
//        this.memberStatus = Membership.NOT_MEMBER;
//        this.staffName = "NULL"; // GET FROM ORDERS
//        this.tableNo = 0; // GET FROM ORDERS
//    }

    public Invoice(int orderId, Order orders){
        this.invoiceId = orderId ;
        this.orders = orders;
        Calendar c = Calendar.getInstance();
        this.date = sdf.format(c.getTime());
        this.memberStatus = Membership.NOT_MEMBER;
        this.staffName = orders.getStaff(); // GET FROM ORDERS
        this.tableNo = 0; // GET FROM ORDERS
    }

    public int getInvoiceId(){ return invoiceId; }
    public void setInvoiceId(int invoiceId){ this.invoiceId = invoiceId; }

    public Order getOrders(){ return orders; }

    public double getSubTotal(){ return subTotal; }
    public void setSubTotal( double subTotal ){ this.subTotal = subTotal; }

    public double getTotal(){ return total; }
    public void setTotal( double total ){ this.total = total; }

    public Membership getMemberStatus(){ return memberStatus; }
    public void setMemberStatus(Membership m){ memberStatus = m; }

    public String[] getLineCSVFormat(){
        DateTimeFormatter timeObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String[] row = {String.valueOf(invoiceId), this.sdf.format(timeObj), String.valueOf(memberStatus), staffName, Integer.toString(tableNo),
                String.valueOf(orders),};
        return row;
    }

}
