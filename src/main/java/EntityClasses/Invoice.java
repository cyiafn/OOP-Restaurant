package EntityClasses;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class Invoice {
    private static int idCount = 1;
    private String invoiceId;
    private int tableNo;
    private double subTotal = 0;
    private double subTotalAD = 0;
    private double svcChargeAmt = 0;
    private double gstAmt = 0;
    private double memberDiscAmt = 0;

    private double total = 0;
//    private ArrayList<Order> orders;  // GET ORDER FROM ORDER
    private Order orders;

    public enum Membership{ IS_MEMBER, NOT_MEMBER }
    private Membership memberStatus;
    private String date;
    private String staffName;


    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    SimpleDateFormat Id = new SimpleDateFormat("yyyyMMdd");


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

    public Invoice(Order orders){
        Calendar c = Calendar.getInstance();
        this.date = sdf.format(c.getTime());
        this.invoiceId = Id.format(c.getTime()) + orders.getOrderID(); //Using of date format + orderID to create invoiceID
        this.memberStatus = Membership.NOT_MEMBER;

        this.orders = orders;
        this.staffName = orders.getStaff(); // GET FROM ORDERS
        this.tableNo = 0; // GET FROM ORDERS
    }

    public String getInvoiceId(){ return invoiceId; }
    public void setInvoiceId(String invoiceId){ this.invoiceId = invoiceId; }
    public String getDate(){return date;}

    public int getTableNo(){ return tableNo;}
    public void setTableNo(int tableNo){ this.tableNo = tableNo;}
    public Order getOrders(){ return orders; }

    public double getSubTotal(){ return subTotal; }
    public void setSubTotal( double subTotal ){ this.subTotal = subTotal; }

    public double getSubTotalAD(){ return subTotalAD;}
    public void setSubtotalAD(double subTotalAD){ this.subTotalAD = subTotalAD; }

    public double getSvcChargeAmt() { return svcChargeAmt; }
    public void setSvcChargeAmt( double svcChargeAmt ) { this.svcChargeAmt = svcChargeAmt;}

    public double getGstAmt() { return gstAmt; }
    public void setGstAmt(double gstAmt) { this.gstAmt = gstAmt; }

    public double getMemberDiscAmt() { return memberDiscAmt; }
    public void setMemberDiscAmt(double memberDiscAmt) { this.memberDiscAmt = memberDiscAmt; }

    public double getTotal(){ return total; }
    public void setTotal( double total ){ this.total = total; }

    public Membership getMemberStatus(){ return memberStatus; }
    public void setMemberStatus(Membership m){ memberStatus = m; }

//    public String[] getLineCSVFormat(){
//        DateTimeFormatter timeObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        String[] row = {String.valueOf(invoiceId), this.sdf.format(timeObj), String.valueOf(memberStatus), staffName, Integer.toString(tableNo),
//                String.valueOf(orders),};
//        return row;
//    }

}
