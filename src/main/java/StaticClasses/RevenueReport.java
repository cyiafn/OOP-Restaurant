/**
 RevenueReport Class
 @author Yew Han
 @version 1.0
 @since 2021-10-23
 */
package StaticClasses;

import ControlClasses.PaymentManager;
import EntityClasses.Invoice;
import EntityClasses.MenuItem;
import EntityClasses.Order;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RevenueReport {

        private static double totalSubTotal;
        private static double totalSubTotalAD = 0;
        private static double totalSvcChargeAmt = 0;
        private static double totalGstAmt;
        private static double totalMemberDiscAmt = 0;
        private static double totalRevenue;
        private static String reportDate;
        static ArrayList<Invoice> revenueList = new ArrayList<Invoice>();
        static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        public static void generateReportByDay(String date, ArrayList<Invoice> invoiceList) {
                reportDate = date.substring(0,4) + '/' + date.substring(4,6) + '/' + date.substring(6);
                ArrayList<Invoice> list = new ArrayList<Invoice>();
                for (Invoice invoice : invoiceList) {
                        String invoiceID = invoice.getInvoiceId();
                        if (invoiceID.contains(date)) {
                                list.add(invoice);
                        }
                }
                if (list.size()!=0)
                        generateReport(list);
                else
                        System.out.println("No records of Selected Day");
        }

        public static void generateReportByMonth(String date, ArrayList<Invoice> invoiceList) {
                reportDate = date.substring(0,4) + '/' + date.substring(4,6);
                ArrayList<Invoice> list = new ArrayList<Invoice>();
                for (Invoice invoice : invoiceList) {
                        String invoiceID = invoice.getInvoiceId();
                        if (invoiceID.contains(date)) {
                                list.add(invoice);
                        }
                }
                if (list.size()!=0)
                        generateReport(list);
                else
                        System.out.println("No records of Selected Month");
        }

        public static void generateReportByYear(String date, ArrayList<Invoice> invoiceList) {
                reportDate = date.substring(0,4);
                ArrayList<Invoice> list = new ArrayList<Invoice>();
                for (Invoice invoice : invoiceList) {
                        String invoiceID = invoice.getInvoiceId();
                        if (invoiceID.contains(date)) {
                                list.add(invoice);
                        }
                }
                if (list.size()!=0)
                        generateReport(list);
                else
                        System.out.println("No records of Selected Month");
        }

        private static void generateReport(ArrayList<Invoice> list) {
                revenueList = list;
//                ArrayList<HashMap<String, String>> itemList = new ArrayList<HashMap<String,String>>();
                HashMap<String, String> itemList = new HashMap<String, String >();
                HashMap<String, Integer> qtyList = new HashMap<String, Integer>();
                HashMap<String, Double> priceList = new HashMap<String, Double>();
                for (Invoice invoice : revenueList) {
                        String itemId;
                        for(MenuItem item: invoice.getOrders().getOrderedItems()){
                                // Check item id
                                //if item id is in orderedItem
                                // Update item with same itemid
                                // Get item.qty
                                // Add to order
                                itemId = item.getMenuItemID();
                                if(itemList.containsKey(itemId)){
                                        int itemQty = qtyList.get(itemId);
                                        qtyList.put(itemId, itemQty + item.getQuantity());
                                }
                                else {
                                        itemList.put(itemId, item.getName());
                                        qtyList.put(itemId, item.getQuantity());
                                        priceList.put(itemId, item.getPrice());
                                }
                        }
                        totalSubTotal += invoice.getSubTotal();
                        totalSubTotalAD += invoice.getSubTotalAD();
                        totalMemberDiscAmt += invoice.getMemberDiscAmt();
                        totalGstAmt += invoice.getGstAmt();
                        totalSvcChargeAmt += invoice.getSvcChargeAmt();
                        totalRevenue += invoice.getTotal();
                }
                displayResult(itemList, qtyList, priceList); //Generate the Revenue created
        }
        private static void displayResult(HashMap<String, String> itemList, HashMap<String, Integer> qtyList, HashMap<String, Double> priceList){
                Formatter fmt = new Formatter();
                System.out.printf("====================================================================================================\n");
                System.out.printf("\t\t\t\tREVENUE REPORT FOR %s\n", reportDate);
                System.out.printf("====================================================================================================\n");
                System.out.println("ID                                          Item                       \t\t Qty   Revenue per Item(S$)");
                for(Map.Entry<String, String> entry: itemList.entrySet()){
                        String itemID = entry.getKey();
                        String itemName = entry.getValue();
                        int qty = qtyList.get(itemID);
                        double price = priceList.get(itemID);
                        double revenuePerItem = price * qty;
                        //System.out.println(itemID +"    \t" + itemName +"    \t" + qty+"    \t" + revenuePerItem);
                        fmt.format("%s        %-30s   %-5s %-6.2f\n",itemID, itemName, qty, revenuePerItem);
                }
                System.out.println(fmt);
                System.out.printf("----------------------------------------------------------------------------------------------------\n");
                System.out.printf("Total Subtotal Revenue: %.2f\n", totalSubTotal);
                System.out.printf("Total Member Discount Given: -%.2f\n", totalMemberDiscAmt);
                System.out.printf("Total Subtotal Revenue After Discount: %.2f\n", totalSubTotalAD);
                System.out.printf("Total Gst Amount Received: %.2f\n", totalGstAmt);
                System.out.printf("Total Service Charge Amount Received: %.2f\n", totalSvcChargeAmt);
                System.out.printf("Total Revenue: %.2f\n", totalRevenue);
                System.out.println("====================================================================================================\n");
        }
        private static void clearData(){}

}