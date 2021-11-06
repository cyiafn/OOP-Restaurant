package ControlClasses;

import EntityClasses.Invoice;
import EntityClasses.MenuItem;
import EntityClasses.Order;
import Enumerations.PrintColor;
import Enumerations.TaxDiscount;
import StaticClasses.Database;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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

    public void createInvoice(Order order){
        ArrayList<MenuItem> itemList = order.getOrderedItems();
        Invoice invoice = new Invoice(order.getOrderID(), order);
        invoice.setMemberStatus(Invoice.Membership.IS_MEMBER);
        computeSubTotal(invoice);
        computeTotal(invoice);
        displayPayment(invoice);
        System.out.println("Invoice Created");
    }


    public void createInvoice(Order order, int isMember){
        ArrayList<MenuItem> itemList = order.getOrderedItems();
        Invoice invoice = new Invoice(order.getOrderID(), order);
        if(isMember==1){
            invoice.setMemberStatus(Invoice.Membership.IS_MEMBER);
        }
        computeSubTotal(invoice);
        computeTotal(invoice);
        displayPayment(invoice);
        System.out.println("Invoice Created");
    }

    public Invoice retrieveInvoice(int invoiceID) {
        for (Invoice invoice : invoiceList) {
            if ( invoice.getInvoiceId() == invoiceID)
                return invoice;
        }
        return null;
    }



    public double computeSubTotal(Invoice invoice){
        // compute the subtotal of the invoice
        double subTotal = 0;
        //ArrayList<Order> orders = invoice.getOrders();
        for(MenuItem item: invoice.getOrders().getOrderedItems()) {
                    subTotal += item.get_price();
                }
        invoice.setSubTotal(subTotal);
        return subTotal;
    }

    public double computeTotal(Invoice invoice){
        double subTotal = invoice.getSubTotal();
        double total = subTotal + (subTotal * gst) + (subTotal * serviceCharge);
        //Check if member from Reservation
        if( invoice.getMemberStatus() == Invoice.Membership.IS_MEMBER )
            total *= (1-memberDiscount);
        invoice.setTotal(total);
        return total;
    }


    public void displayPayment(Invoice invoice){
        double displaySubtotal = invoice.getSubTotal();
        double displaySvcCharge = invoice.getSubTotal() * serviceCharge;
        double displayGST = invoice.getSubTotal() * gst;
        double displayMemberDiscount = invoice.getSubTotal() * memberDiscount;
        System.out.println(PrintColor.YELLOW_BOLD);
        System.out.println(
                "==============================\n" + "\tMichelin Western Restaurant\n" + "=============================="
        );
        System.out.print(PrintColor.RESET);
        for(MenuItem item: invoice.getOrders().getOrderedItems()){
            System.out.println(item.get_menuItemID() +"    \t" + item.get_name() +"    \t" + item.get_quantity()+"    \t" + item.get_price());
        }
        System.out.printf("SUBTOTAL                                                                %.2f\n", displaySubtotal);
        System.out.printf("10%% SVC CHG                                                             %.2f\n", displaySvcCharge);
        System.out.printf("7%% GST                                                                  %.2f\n", displayGST);

        System.out.println("---------------------------------------------------------------------------------");
        if(invoice.getMemberStatus() == Invoice.Membership.IS_MEMBER)
            System.out.printf("%.2f%% ADDITIONAL MEMBER DISCOUNT                                                            - %.2f\n", memberDiscount*100, displayMemberDiscount);
        else
            System.out.println("NOT MEMBER");
        System.out.printf("TOTAL                                                                   %.2f\n", invoice.getTotal());
    }

//
//    public void loadinDB() {
//        PaymentDB paymentdb = new PaymentDB();
//        try {
//            this.paymentList = paymentdb.read(fileName);
//            paymentdb.save(fileName, paymentList);
//            checkID(); // ADDED TO CHECK
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void savetoDB() {
//        PaymentDB paymentdb = new PaymentDB();
//        try {
//            paymentdb.save(fileName, paymentList);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
   public void savetoDB2() throws IOException {
    //OrderDB orderdb = new OrderDB();
    Database.saveOrder(invoiceFile, invoiceList);
}
    public void saveInvoice(Invoice invoice) throws IOException {
        Database.writeLine(invoiceFile, invoice.getLineCSVFormat());
    }

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
