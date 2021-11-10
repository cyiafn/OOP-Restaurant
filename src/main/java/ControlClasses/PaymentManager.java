package ControlClasses;

import EntityClasses.Invoice;
import EntityClasses.MenuItem;
import EntityClasses.Order;
import Enumerations.PrintColor;
import Enumerations.TaxDiscount;
import StaticClasses.Database;
import StaticClasses.RevenueReport;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashSet;
import java.util.Set;

/**
 * PaymentManager is a controller
 * Which handle all thing related to payment in a Restaurant Reservation and Point of Sale System (RRPSS)
 * This includes the actions of printing an Invoice based on orders and also the ability to generate
 * a revenue report based on User's input period(by day, by month or by year)
 * @author Ong Yew Han
 * @version 1.7
 * @since 2021-11-09
 */
public class PaymentManager {
    private double gst = TaxDiscount.GST;
    private double serviceCharge = TaxDiscount.SERVICE_CHARGE;
    private double memberDiscount = TaxDiscount.MEMBER_DISCOUNT;


    private static final String invoiceFile = "Invoice.csv";
    private static PaymentManager instance = null;
    ArrayList<Invoice> invoiceList = new ArrayList<Invoice>(); //List of Invoices

    public PaymentManager(){
        ArrayList<Invoice> invoiceList = new ArrayList<Invoice>(); //List of Invoices
    }

    public static PaymentManager getInstance(){
        if (instance == null)
            instance = new PaymentManager();
        return instance;
    }

//    public void createInvoice(Order order){
//        ArrayList<MenuItem> itemList = order.getOrderedItems();
//        Invoice invoice = new Invoice(order.getOrderID(), order);
//        invoice.setMemberStatus(Invoice.Membership.IS_MEMBER);
//        computeSubTotal(invoice);
//        computeTotal(invoice);
//        displayPayment(invoice);
//        System.out.println("Invoice Created");
//    }


    public void createInvoice(Order order, int isMember) {
        ArrayList<MenuItem> itemList = order.getOrderedItems();
        Invoice invoice = new Invoice(order);
        if(isMember==1){
            invoice.setMemberStatus(Invoice.Membership.IS_MEMBER);
        }

        retrieveTableNo(invoice);
        computeInvoice(invoice);
        invoiceList.add(invoice);
        savetoDB(invoiceList);
        System.out.println("Invoice Created");
        displayPayment(invoice);
//        try {
//            OrderManager.getInstance().deleteOrder(invoice.getOrders());
//            ReservationManager.getInstance().closeReservation(invoice.getOrders().getReservationID());
//        } catch (IOException | CsvException e) {
//            e.printStackTrace();
//        }
    }


    public Invoice retrieveInvoice(String invoiceID) {
        try {
            loadFromDB();
            for (Invoice invoice : invoiceList) {
                if ( invoice.getInvoiceId().equals(invoiceID))
                    return invoice;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void generateRevenueReport(String date, int i){
        loadFromDB();
        switch (i) {
            case 1:
                RevenueReport.generateReportByDay(date, invoiceList);
                break;
            case 2:
                RevenueReport.generateReportByMonth(date, invoiceList);
                break;
            case 3:
                RevenueReport.generateReportByYear(date, invoiceList);
        }
    }

    public int retrieveTableNo(Invoice invoice) {
        String reservationId = invoice.getOrders().getReservationID();
        int tableNo = 0;
        try {
            tableNo = ReservationManager.getInstance().getTableNumber(reservationId);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        invoice.setTableNo(tableNo);
        return tableNo;
    }

    public void computeInvoice(Invoice invoice){
        double subTotal = 0;
        double memberDiscAmt = 0;
        double subTotalAD = 0;
        double gstAmt = 0 ;
        double svcChargeAmt = 0;
        double total = 0;

        for(MenuItem item: invoice.getOrders().getOrderedItems()) {
            subTotal += item.getPrice() * item.getQuantity();
        }
        if( invoice.getMemberStatus() == Invoice.Membership.IS_MEMBER ){
            memberDiscAmt = subTotal * memberDiscount; //Assume Member discount is applied on Subtotal before Tax
            subTotalAD = subTotal - memberDiscAmt;
        }
        else {
            subTotalAD = subTotal;
        }
        gstAmt = Math.round(subTotalAD * gst *100.00)/100.00;
        svcChargeAmt = Math.round(subTotalAD * serviceCharge *100.00)/100.00;
        total = subTotalAD + gstAmt + svcChargeAmt;
        invoice.setSubTotal(subTotal);
        invoice.setMemberDiscAmt(memberDiscAmt);
        invoice.setSubtotalAD(subTotalAD);
        invoice.setGstAmt(gstAmt);
        invoice.setSvcChargeAmt(svcChargeAmt);
        invoice.setTotal(total);
    }

    public void displayPayment(Invoice invoice){
        Formatter fmt = new Formatter();
        String displayInvoiceId = invoice.getInvoiceId();
        String displayStaff = invoice.getOrders().getStaff();
        String date = invoice.getDate();
        String displayDate = date.substring(0, date.indexOf(' '));
        String displayTime = date.substring(date.indexOf(' ')+1);
        int displayTableNo = invoice.getTableNo();

        double displaySubtotal = invoice.getSubTotal();
        double displaySubtotalAD = invoice.getSubTotalAD();
        double displaySvcCharge = invoice.getSvcChargeAmt();
        double displayGST = invoice.getGstAmt();
        double displayMemberDiscount = invoice.getMemberDiscAmt();
        System.out.println(PrintColor.YELLOW_BOLD);
        System.out.printf("====================================================================================================\n");
        System.out.printf("                                   Michelin Western Restaurant\n");
        System.out.printf("                                      50 Nanyang Ave, 639798\n");
        System.out.printf("                                          Tel: 6791 1744\n");
        System.out.printf("====================================================================================================\n");
        System.out.print(PrintColor.RESET);
        System.out.printf("Invoice ID: %s                                                        Date: %s\n", displayInvoiceId, displayDate);
        System.out.printf("    Server: %s                  Table No.: %s                                  Time: %s\n", displayStaff, displayTableNo, displayTime);
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("ID                                          Item                       \t\t Qty   Price per Item(S$)");
        System.out.println("----------------------------------------------------------------------------------------------------");
        for(MenuItem item: invoice.getOrders().getOrderedItems()){
            item.
            //System.out.println(item.getMenuItemID() +"    \t" + item.getName() +"    \t\t\t\t" + item.getQuantity()+"    \t" + item.getPrice());
            fmt.format("%s        %-30s   %-5s %-6.2f\n", item.getMenuItemID(), item.getName(), item.getQuantity(), item.getPrice());
        }
        System.out.println(fmt);
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.printf("SUBTOTAL                                                                           %-10.2f\n", displaySubtotal);
        if(invoice.getMemberStatus() == Invoice.Membership.IS_MEMBER){
            System.out.printf("%.2f%% ADDITIONAL MEMBER DISCOUNT                                                 -%-5.2f\n", memberDiscount*100, displayMemberDiscount);
            System.out.printf("SUBTOTAL AFTER DISCOUNT                                                            %-10.2f\n",displaySubtotalAD);
        }
        else
            System.out.println("NOT MEMBER");

        System.out.printf("10%% SVC CHG                                                                        %-10.2f\n", displaySvcCharge);
        System.out.printf("7%% GST                                                                             %-10.2f\n", displayGST);
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.printf("TOTAL                                                                              %-10.2f\n", invoice.getTotal());
        System.out.println("====================================================================================================");
        System.out.println("                                Thank You For Dining With Us!");
        System.out.println("====================================================================================================");
    }


   public void savetoDB(ArrayList<Invoice> invoiceList){
       try {
           Database.savePayment(invoiceFile, invoiceList);
       } catch (IOException e) {
           System.out.printf("Unable to save to %s\n", invoiceFile);
           e.printStackTrace();
       }
   }

    public void loadFromDB(){
        try {
            this.invoiceList = Database.readInvoice(invoiceFile);
        } catch (IOException e) {
            System.out.printf("Unable to Load from %s\n", invoiceFile);
            e.printStackTrace();
        }
    }

//    public void saveInvoice(Invoice invoice) throws IOException {
//        Database.writeLine(invoiceFile, invoice.getLineCSVFormat());
//    }

//    public boolean displayOrder() {
//        Set<Integer> s = new HashSet<>();
//        for (Order order : orderList) {
//            int i = order.getOrderID();
//            if (!s.contains(i)) {
//                s.add(i);
//            }
//        }
//        return orderList.size();
//    }
}
