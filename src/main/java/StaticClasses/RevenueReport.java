/**
 RevenueReport Class
 @author Ong Yew Han
 @version 1.3
 @since 2021-11-08
 */
package StaticClasses;

import ControlClasses.PaymentManager;
import EntityClasses.*;
import Enumerations.PrintColor;

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
                HashMap<String, String> itemList = new HashMap<String, String >();
                HashMap<String, Integer> qtyList = new HashMap<String, Integer>();
                HashMap<String, Double> priceList = new HashMap<String, Double>();

                HashMap<String, String> itemList2 = new HashMap<String, String >();
                HashMap<String, Integer> qtyList2 = new HashMap<String, Integer>();
                HashMap<String, Double> priceList2 = new HashMap<String, Double>();
                for (Invoice invoice : revenueList){
                        String itemKey;
                        // Add Alacarte items to list first
                        for(MenuItem item: invoice.getOrders().getOrderedItems()){
                                // Check item id
                                //if item id is in orderedItem
                                // Update item with same itemid
                                // Get item.qty
                                // Add to order
                                if (item instanceof Alacarte) {
                                        itemKey = item.getName();
                                        // If item is already in list
                                        // Compute qty
                                        if (itemList.containsKey(itemKey)) {
                                                int itemQty = qtyList.get(itemKey);
                                                qtyList.put(itemKey, itemQty + item.getQuantity());
                                        } else { //Else add item to list
                                                itemList.put(itemKey, item.getMenuItemID());
                                                qtyList.put(itemKey, item.getQuantity());
                                                priceList.put(itemKey, item.getPrice());
                                        }
                                }
                        }
                }
                for (Invoice invoice : revenueList) {
                        String itemKey;
                        // Add set menu items
                        for(MenuItem item2: invoice.getOrders().getOrderedItems()){
                                // Check item id
                                //if item id is in orderedItem
                                // Update item with same itemid
                                // Get item.qty
                                // Add to order
                                if (item2 instanceof SetMeal) {
                                        itemKey = item2.getName();
                                        boolean isPromo = ((SetMeal) item2).isPromotionStatus();
                                        if(isPromo)
                                                itemKey = itemKey + " (Promo Price)";
                                        // If item is already in list
                                        // Compute qty
                                        if (itemList2.containsKey(itemKey)) {
                                                int itemQty = qtyList2.get(itemKey);
                                                qtyList2.put(itemKey, itemQty + item2.getQuantity());
                                        } else { //Else add item to list
                                                itemList2.put(itemKey, item2.getMenuItemID());
                                                qtyList2.put(itemKey, item2.getQuantity());
                                                priceList2.put(itemKey, item2.getPrice());
                                        }
                                }
                        }
                        totalSubTotal += invoice.getSubTotal();
                        totalSubTotalAD += invoice.getSubTotalAD();
                        totalMemberDiscAmt += invoice.getMemberDiscAmt();
                        totalGstAmt += invoice.getGstAmt();
                        totalSvcChargeAmt += invoice.getSvcChargeAmt();
                        totalRevenue += invoice.getTotal();
                }
                displayResult(itemList, qtyList, priceList
                        ,itemList2, qtyList2, priceList2); //Generate the Revenue created
                clearData();
        }

        private static void displayResult(HashMap<String, String> itemList, HashMap<String, Integer> qtyList, HashMap<String, Double> priceList,
                                          HashMap<String, String> itemList2, HashMap<String, Integer> qtyList2, HashMap<String, Double> priceList2){
                System.out.println(PrintColor.YELLOW_BOLD);
                System.out.printf("===============================================================================================================================\n");
                System.out.printf("\t\t\t\tREVENUE REPORT FOR "); System.out.print(PrintColor.BLUE); System.out.print(reportDate); System.out.print(PrintColor.YELLOW_BOLD);
                System.out.printf("\n===============================================================================================================================\n");
                System.out.println("ID                                      Food Type    Item                                   \t\t Qty   Revenue per Item(S$)");
                System.out.print(PrintColor.RESET);
                for(Map.Entry<String, String> entry: itemList.entrySet()){
                        String itemID = entry.getValue();
                        String itemName = entry.getKey();
                        String foodType = "Alacarte";
                        int qty = qtyList.get(itemName);
                        double price = priceList.get(itemName);
                        double revenuePerItem = price * qty;
                        //System.out.println(itemID +"    \t" + itemName +"    \t" + qty+"    \t" + revenuePerItem);
                        System.out.printf("%s    %-10s   %-45s   %-10s  %-6.2f\n",itemID, foodType, itemName, qty, revenuePerItem);
                }
                System.out.println();
                for(Map.Entry<String, String> entry: itemList2.entrySet()){
                        String itemID = entry.getValue();
                        String itemName = entry.getKey();
                        String foodType = "Set Meal";
                        int qty = qtyList2.get(itemName);
                        double price = priceList2.get(itemName);
                        double revenuePerItem = price * qty;
                        //System.out.println(itemID +"    \t" + itemName +"    \t" + qty+"    \t" + revenuePerItem);
                        System.out.printf("%s    %-10s   %-45s   %-10s  %-6.2f\n",itemID, foodType, itemName, qty, revenuePerItem);
                }
                //                for(Map.Entry<String, String> entry: itemList.entrySet()){
//                        String itemID = entry.getValue();
//                        String itemName = entry.getKey();
//                        int qty = qtyList.get(itemName);
//                        double price = priceList.get(itemName);
//                        double revenuePerItem = price * qty;
//                        //System.out.println(itemID +"    \t" + itemName +"    \t" + qty+"    \t" + revenuePerItem);
//                        System.out.printf("%s        %-30s   %-5s %-6.2f\n",itemID, itemName, qty, revenuePerItem);
//                }

                System.out.printf("-------------------------------------------------------------------------------------------------------------------------------\n");
                System.out.printf("Total Subtotal Revenue: %.2f\n", totalSubTotal);
                System.out.printf("Total Member Discount Given: -%.2f\n", totalMemberDiscAmt);
                System.out.printf("Total Subtotal Revenue After Discount: %.2f\n", totalSubTotalAD);
                System.out.printf("Total Gst Amount Received: %.2f\n", totalGstAmt);
                System.out.printf("Total Service Charge Amount Received: %.2f\n", totalSvcChargeAmt);
                System.out.printf("Total Revenue: %.2f\n", totalRevenue);
                System.out.println("--------------------------------------------------------------------------------------------------------------------------------\n");
        }
        private static void clearData(){
                totalSubTotal = 0;
                totalSubTotalAD = 0;
                totalMemberDiscAmt = 0;
                totalGstAmt = 0;
                totalSvcChargeAmt = 0;
                totalRevenue = 0;
        }

}