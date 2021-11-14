package ControlClasses;

import EntityClasses.*;
import Enumerations.TaxDiscount;
import StaticClasses.Database;
import StaticClasses.RevenueReport;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;

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
    /**
     * gst tax rate in fraction
     */
    private final double gst = TaxDiscount.GST;
    /**
     * service charge
     */
    private final double serviceCharge = TaxDiscount.SERVICE_CHARGE;
    /**
     * member discount to be given based on TaxDiscount class
     */
    private final double memberDiscount = TaxDiscount.MEMBER_DISCOUNT;
    /**
     * file where invoice will be saved and loaded
     */
    private static final String invoiceFile = "Invoice.csv";
    /**
     * instance of payment manager
     */
    private static PaymentManager instance = null;
    /**
     * list of invoices to be loaded
     */
    ArrayList<Invoice> invoiceList = new ArrayList<Invoice>(); //List of Invoices

    /**
     * Constructor
     */
    public PaymentManager(){
        ArrayList<Invoice> invoiceList = new ArrayList<Invoice>(); //List of Invoices
        init();
    }

    /**
     * Singleton Design where only an instance of PaymentManager is created
     * @return instance
     */
    public static PaymentManager getInstance(){
        if (instance == null)
            instance = new PaymentManager();
        return instance;
    }

    /**
     * Lazy load where Database is loaded only when the Payment Manager instance is called
     */
    public void init(){
        loadFromDB();
    }

    /**
     * Method to create invoice based on information from teh Order entity class
     * @param order
     * @param isMember
     */
    public void createInvoice(Order order, int isMember) {
        ArrayList<MenuItem> itemList = order.getOrderedItems();
        Invoice invoice = new Invoice(order);
        if(isMember==1){
            invoice.setMemberStatus(Invoice.Membership.IS_MEMBER);
        }

        retrieveTableNo(invoice);
        computeInvoice(invoice);
        invoiceList.add(invoice);
        saveToDB(invoiceList);
        System.out.println("Invoice Created");
        displayPayment(invoice);
        try {
            OrderManager.getInstance().deleteOrder(invoice.getOrders());
            ReservationManager.getInstance().closeReservation(invoice.getOrders().getReservationID());
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that will retrieve the invoice in the database based on invoiceID
     * @param invoiceID
     * @return invoice
     */
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

    /**
     * This will call the methods in the static class revenue report
     * switch case will select which static method to call accordingly
     * @param date date input by user
     * @param i choice selected by user
     */
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

    /**
     * Method to retrieve the table number by calling the Reservation Manager
     * @param invoice
     * @return integer
     */
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

    /**
     * Method to compute the taxation amount and saving the variables into the invoice attributes using Getter and Setter
     * @param invoice
     */
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

    /**
     * Method to display information based on information in the Invoice entity class.
     * All relevant information is formatted to show a mock of a physical invoice for easy reading
     * @param invoice
     */
    public void displayPayment(Invoice invoice){
        Formatter fmt = new Formatter();
        String displayInvoiceId = invoice.getInvoiceId();
        String displayStaff = invoice.getOrders().getStaff();
        String date = invoice.getDate();
        String displayDate = date.substring(0, date.indexOf(' '));
        String displayTime = date.substring(date.indexOf(' ')+1);
        String foodType = null;

        int displayTableNo = invoice.getTableNo();
        double displaySubtotal = invoice.getSubTotal();
        double displaySubtotalAD = invoice.getSubTotalAD();
        double displaySvcCharge = invoice.getSvcChargeAmt();
        double displayGST = invoice.getGstAmt();
        double displayMemberDiscount = invoice.getMemberDiscAmt();
//        System.out.println(PrintColor.YELLOW_BOLD);
        System.out.printf("=============================================================================================================================\n");
        System.out.printf("                                             Michelin Western Restaurant\n");
        System.out.printf("                                               50 Nanyang Ave, 639798\n");
        System.out.printf("                                                  Tel: 6791 1744\n");
        System.out.printf("=============================================================================================================================\n");
//        System.out.print(PrintColor.RESET);
        System.out.printf("Invoice ID: %-30s                                                                 Date: %s\n", displayInvoiceId, displayDate);
        System.out.printf("    Server: %-20s        ",displayStaff);System.out.printf("Table No.: %-5s                                                   Time: %s\n", displayTableNo, displayTime);
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
        System.out.println("ID                                      Food Type    Item                                   \t\t Qty   Price per Item(S$)");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
        for(MenuItem item: invoice.getOrders().getOrderedItems()) {
            if (item instanceof Alacarte) {
                foodType = ((Alacarte) item).getType();
                System.out.printf("%s    %-10s   %-45s   %-10s  %-6.2f\n", item.getMenuItemID(), foodType, item.getName(), item.getQuantity(), item.getPrice());
            }
        }
        for (MenuItem item2 : invoice.getOrders().getOrderedItems()) {
            if (item2 instanceof SetMeal) {
                String foodName = item2.getName();
                foodType = ((SetMeal) item2).getType();
                boolean isPromo = ((SetMeal) item2).isPromotionStatus();
                if(isPromo)
                    foodName = foodName + " (Promo Price)";
                System.out.printf("%s    %-10s   %-45s   %-10s  %-6.2f\n", item2.getMenuItemID(), foodType, foodName, item2.getQuantity(), item2.getPrice());
            }
        }
        System.out.println(fmt);
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("SUBTOTAL                                                                                                         %-10.2f\n", displaySubtotal);
        if (invoice.getMemberStatus() == Invoice.Membership.IS_MEMBER) {
            System.out.printf("%.2f%% ADDITIONAL MEMBER DISCOUNT                                                                               -%-5.2f\n", memberDiscount * 100, displayMemberDiscount);
            System.out.printf("SUBTOTAL AFTER DISCOUNT                                                                                          %-10.2f\n", displaySubtotalAD);
        } else
            System.out.println("NOT MEMBER");

        System.out.printf("10%% SVC CHG                                                                                                      %-10.2f\n", displaySvcCharge);
        System.out.printf("7%% GST                                                                                                           %-10.2f\n", displayGST);
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("TOTAL                                                                                                            %-10.2f\n", invoice.getTotal());
        System.out.println("=============================================================================================================================\n");
        System.out.println("                                             Thank You For Dining With Us!");
        System.out.println("=============================================================================================================================\n");
    }

//    public void displayPayment(Invoice invoice){
//        Formatter fmt = new Formatter();
//        String displayInvoiceId = invoice.getInvoiceId();
//        String displayStaff = invoice.getOrders().getStaff();
//        String date = invoice.getDate();
//        String displayDate = date.substring(0, date.indexOf(' '));
//        String displayTime = date.substring(date.indexOf(' ')+1);
//        String foodType = null;
//
//        int displayTableNo = invoice.getTableNo();
//        double displaySubtotal = invoice.getSubTotal();
//        double displaySubtotalAD = invoice.getSubTotalAD();
//        double displaySvcCharge = invoice.getSvcChargeAmt();
//        double displayGST = invoice.getGstAmt();
//        double displayMemberDiscount = invoice.getMemberDiscAmt();
//        System.out.println(PrintColor.YELLOW_BOLD);
//        System.out.printf("====================================================================================================\n");
//        System.out.printf("                                   Michelin Western Restaurant\n");
//        System.out.printf("                                      50 Nanyang Ave, 639798\n");
//        System.out.printf("                                          Tel: 6791 1744\n");
//        System.out.printf("====================================================================================================\n");
//        System.out.print(PrintColor.RESET);
//        System.out.printf("Invoice ID: %s                                                        Date: %s\n", displayInvoiceId, displayDate);
//        System.out.printf("    Server: %s                  Table No.: %s                                  Time: %s\n", displayStaff, displayTableNo, displayTime);
//        System.out.println("----------------------------------------------------------------------------------------------------");
//        System.out.println("ID                                      Food Type    Item                                   \t\t Qty   Price per Item(S$)");
//        System.out.println("----------------------------------------------------------------------------------------------------");
//        for(MenuItem item: invoice.getOrders().getOrderedItems()) {
//            if (item instanceof Alacarte) {
//                foodType = ((Alacarte) item).getType();
//                System.out.printf("%s    %-10s   %-45s   %-10s  %-6.2f\n", item.getMenuItemID(), foodType, item.getName(), item.getQuantity(), item.getPrice());
//            }
//        }
//        for (MenuItem item2 : invoice.getOrders().getOrderedItems()) {
//                if (item2 instanceof SetMeal) {
//                    String foodName = item2.getName();
//                    foodType = ((SetMeal) item2).getType();
//                    boolean isPromo = ((SetMeal) item2).isPromotionStatus();
//                    if(isPromo)
//                        foodName = foodName + " (Promo Price)";
//                    System.out.printf("%s    %-10s   %-45s   %-10s  %-6.2f\n", item2.getMenuItemID(), foodType, foodName, item2.getQuantity(), item2.getPrice());
//                }
//        }
//        System.out.println(fmt);
//                System.out.println("----------------------------------------------------------------------------------------------------");
//                System.out.printf("SUBTOTAL                                                                           %-10.2f\n", displaySubtotal);
//                if (invoice.getMemberStatus() == Invoice.Membership.IS_MEMBER) {
//                    System.out.printf("%.2f%% ADDITIONAL MEMBER DISCOUNT                                                 -%-5.2f\n", memberDiscount * 100, displayMemberDiscount);
//                    System.out.printf("SUBTOTAL AFTER DISCOUNT                                                            %-10.2f\n", displaySubtotalAD);
//                } else
//                    System.out.println("NOT MEMBER");
//
//                System.out.printf("10%% SVC CHG                                                                        %-10.2f\n", displaySvcCharge);
//                System.out.printf("7%% GST                                                                             %-10.2f\n", displayGST);
//                System.out.println("----------------------------------------------------------------------------------------------------");
//                System.out.printf("TOTAL                                                                              %-10.2f\n", invoice.getTotal());
//                System.out.println("====================================================================================================");
//                System.out.println("                                Thank You For Dining With Us!");
//                System.out.println("====================================================================================================");
//    }

    /**
     * Saves the invoices from the list of invoice to the database
     * @param invoiceList
     */
   public void saveToDB(ArrayList<Invoice> invoiceList){
       try {
           Database.savePayment(invoiceFile, invoiceList);
       } catch (IOException e) {
           System.out.printf("Unable to save to %s\n", invoiceFile);
           e.printStackTrace();
       }
   }

    /**
     * Loads the invoices from the database and save them into a list of invoices
     */
    public void loadFromDB(){
        try {
            this.invoiceList = Database.readInvoice(invoiceFile);
        } catch (IOException e) {
            System.out.printf("Unable to Load from %s\n", invoiceFile);
            e.printStackTrace();
        }
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
