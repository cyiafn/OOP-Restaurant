package EntityClasses;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
/**
 * Invoice entity class
 @author Ong Yew Han
 @version 1.3
 @since 2021-10-30
 */
public class Invoice {
    /**
     * Invoice Id for invoice
     */
    private String invoiceId;
    /**
     * table no. of invoice
     */
    private int tableNo;
    /**
     * subtotal of invoice
     */
    private double subTotal = 0;
    /**
     * subtotal after discount of invoice
     */
    private double subTotalAD = 0;
    /**
     * service charge amount of invoice
     */
    private double svcChargeAmt = 0;
    /**
     * gst amount of invoice
     */
    private double gstAmt = 0;
    /**
     * member discount amount given
     */
    private double memberDiscAmt = 0;
    /**
     * total amount payable in invoice
     */
    private double total = 0;
    /**
     * order object from Order class
     */
    private final Order orders;

    /**
     * Membership Enum for Invoice
     * IS_MEMBER
     * NOT_MEMBER
     */
    public enum Membership{
        /**
         * is a Member
         */
        IS_MEMBER,
        /**
         * not a Member
         */
        NOT_MEMBER }
    /**
     * Membership Status of Invoice
     */
    private Membership memberStatus;
    /**
     * date of Invoice
     */
    private final String date;

    /**
     * staff name on invoice
     */
    private final String staffName;
    /**
     * Date time formatter for Date
     */
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    /**
     * Date time formatter for creating Invoice ID
     */
    SimpleDateFormat Id = new SimpleDateFormat("yyyyMMddss");

    /**
     * Constructor for invoice
     * @param orders from Order class
     */
    public Invoice(Order orders){
        Calendar c = Calendar.getInstance();
        this.date = sdf.format(c.getTime());
        this.invoiceId = Id.format(c.getTime()) +  orders.getOrderID(); //Using of date format + orderID + Time + to create invoiceID
        this.memberStatus = Membership.NOT_MEMBER;

        this.orders = orders;
        this.staffName = orders.getStaff(); // GET FROM ORDERS
        this.tableNo = 0; // GET FROM ORDERS
    }

    /**
     * Overloaded Constructor for invoice when reading params from database
     * @param invoiceId from DB
     * @param date from DB
     * @param tableNo from DB
     * @param subTotal from DB
     * @param memberDiscAmt from DB
     * @param subTotalAD from DB
     * @param gstAmt from DB
     * @param svcChargeAmt from DB
     * @param total from DB
     * @param orders from DB
     */
    public Invoice(String invoiceId, String date, int tableNo, double subTotal, double memberDiscAmt, double subTotalAD, double gstAmt,
                   double svcChargeAmt, double total, Order orders){
        this.invoiceId = invoiceId;
        this.date = date;
        this.tableNo = tableNo;
        this.staffName = orders.getStaff();
        this.subTotal = subTotal;
        this.memberDiscAmt = memberDiscAmt;
        this.subTotalAD = subTotalAD;
        this.gstAmt = gstAmt;
        this.svcChargeAmt = svcChargeAmt;
        this.total = total;
        this.orders = orders;
        if(memberDiscAmt==0)
            this.memberStatus = Membership.NOT_MEMBER;
        else
            this.memberStatus = Membership.IS_MEMBER;
    }

    /**
     * Invoice ID getter
     * @return invoiceId
     */
    public String getInvoiceId(){ return invoiceId; }

    /**
     * Invoice ID setter
     * @param invoiceId invoice ID to be set
     */
    public void setInvoiceId(String invoiceId){ this.invoiceId = invoiceId; }

    /**
     * date getter
     * @return date
     */
    public String getDate(){return date;}

    /**
     * table number getter
     * @return tableNo
     */
    public int getTableNo(){ return tableNo;}

    /**
     * table number setter
     * @param tableNo table number to be set
     */
    public void setTableNo(int tableNo){ this.tableNo = tableNo;}

    /**
     * Order getter
     * @return orders
     */
    public Order getOrders(){ return orders; }

    /**
     * Sub total getter
     * @return subTotal
     */
    public double getSubTotal(){ return subTotal; }

    /**
     * Sub total setter
     * @param subTotal Sub total to be set
     */
    public void setSubTotal( double subTotal ){ this.subTotal = subTotal; }
    /**
     * Sub total after discount getter
     * @return subTotalAD
     */
    public double getSubTotalAD(){ return subTotalAD;}

    /**
     * Sub total after discount setter
     * @param subTotalAD Sub total after discount
     */
    public void setSubtotalAD(double subTotalAD){ this.subTotalAD = subTotalAD; }

    /**
     * Service Charge Amount getter
     * @return svcChargeAmt
     */
    public double getSvcChargeAmt() { return svcChargeAmt; }

    /**
     * Service Charge Amount setter
     * @param svcChargeAmt cumulated service charge amount
     */
    public void setSvcChargeAmt( double svcChargeAmt ) { this.svcChargeAmt = svcChargeAmt;}

    /**
     * Gst Amount getter
     * @return gstAmt
     */
    public double getGstAmt() { return gstAmt; }

    /**
     * Gst Amount setter
     * @param gstAmt cumulated gst amount
     */
    public void setGstAmt(double gstAmt) { this.gstAmt = gstAmt; }

    /**
     * Member discount amount getter
     * @return memeberDiscAmt
     */
    public double getMemberDiscAmt() { return memberDiscAmt; }

    /**
     * Member discount amount setter
     * @param memberDiscAmt Member discount amt to be given
     */
    public void setMemberDiscAmt(double memberDiscAmt) { this.memberDiscAmt = memberDiscAmt; }

    /**
     * Total amount getter
     * @return total
     */
    public double getTotal(){ return total; }

    /**
     * Total amount setter
     * @param total total amount
     */
    public void setTotal( double total ){ this.total = total; }

    /**
     * Membership status getter
     * @return memberStatus
     */
    public Membership getMemberStatus(){ return memberStatus; }

    /**
     * Membership status setter
     * @param m membership status based on Membership
     */
    public void setMemberStatus(Membership m){ memberStatus = m; }

}
