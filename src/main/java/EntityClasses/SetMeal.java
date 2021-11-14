package EntityClasses;
import Enumerations.PrintColor;
import java.util.ArrayList;

/**
 * SetMeal entity class
 * @author CHU JIA HAO
 * @version 1.0
 * @since 2021-11-07
 */
public class SetMeal extends MenuItem {
    /**
     * Type of Menu Item
     * in this case is setmeal
     */
    String type = "setmeal";
    /**
     * This attribute to store a set of Menu Item
     */
    private ArrayList<MenuItem> setOfItem ;
    /**
     * Promotion price of set meal
     */
    private  double promotionPrice= 0;
    /**
     * Promotion status of set meal
     */
    private boolean promotionStatus = false;

    /**
     * Constructor of Set Meal
     * @param menuItemID menu item id
     * @param name name of menu item
     * @param description description of menu item
     * @param price price of menu item
     * @param quantity quantity of menu item
     * @param ala a list of menu item
     */
    public SetMeal(String menuItemID, String name, String description, double price, Integer quantity, ArrayList<MenuItem> ala) {
        super(menuItemID, name, description, price, quantity);
        setSetOfItem(ala);
    }

    /**
     * Constructor of Set Meal
     * @param menuItemID menu item id
     * @param name name of menu item
     * @param description description of menu item
     * @param price price of menu item
     * @param promotionPrice promotion price of set meal
     * @param quantity quantity of menu item
     * @param ala a list of menu item
     */
    public SetMeal(String menuItemID, String name, String description, double price,double promotionPrice ,Integer quantity,ArrayList<MenuItem> ala) {
        super(menuItemID, name, description, price, quantity);
        setSetOfItem(ala);
        setPromotionPrice(promotionPrice);
    }


    /**
     * override function from parent class print()
     */
    @Override
    public void print() {
        System.out.println(" -------------------------------");
        if(this.promotionPrice!= 0)
        {
            System.out.print(PrintColor.GREEN_UNDERLINED);
            System.out.println(" This is a promotion Set Meal!");
            System.out.println(" Now entire set meal only need "+ this.getPromotionPrice() + " !!!");
            System.out.println(" Do not miss this oppurtunity!");
            System.out.print(PrintColor.RESET);
        }
        else{
            System.out.print(PrintColor.GREEN_UNDERLINED);
            System.out.println(" Promotion Price is : "+ this.getPromotionPrice());
        }
        System.out.print(PrintColor.GREEN_BOLD);
        System.out.println(" Menu ID: " + this.getMenuItemID() + " | " );
        System.out.print(PrintColor.BLUE);
        System.out.println(" Set Meal Name: "+ this.getName() + " | ");
        System.out.println(" Set Meal Description: "+ this.getDescription());
        System.out.print(PrintColor.YELLOW_BOLD);
        System.out.println(" Set Meal Price: " + this.getPrice()+ " | "+ " Set Meal Quantity: "+ this.getQuantity().toString());
        System.out.println(" Type: "+ this.getType());
        System.out.println();
        for(MenuItem mi : this.setOfItem)
        {
            System.out.print(PrintColor.GREEN_BOLD);
            System.out.println("   {");
            System.out.print(PrintColor.BLUE);
            System.out.println("     Name: "+ mi.getName() + " | ");
            System.out.println("     Description: "+ mi.getDescription());
            System.out.print(PrintColor.YELLOW_BOLD);
            System.out.println("     Price: " + mi.getPrice()+ " | "+ "Quantity: "+ mi.getQuantity().toString());
            System.out.println("   }");
        }
        System.out.print(PrintColor.RESET);
        System.out.println(" -------------------------------");
    }

    /**
     * Get type of set meal
     * @return a string
     */
    public String getType() {
        return type;
    }

    /**
     * Change type of set meal
     * @param type type of set meal
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get a list of menu item in set meal
     * @return a list of menu item
     */
    public ArrayList<MenuItem> getSetOfItem() {
        return setOfItem;
    }

    /**
     * Change a list of menu item in set meal
     * @param setOfIteme a list of menu item
     */
    public void setSetOfItem(ArrayList<MenuItem> setOfIteme) {
        this.setOfItem = setOfIteme;
    }

    /**
     * Get promotion price of set meal
     * @return promotion price
     */
    public double getPromotionPrice() {
        return promotionPrice;
    }

    /**
     * Change the promotio price of set meal
     * @param promotionPrice a promotion price
     */
    public void setPromotionPrice(double promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    /**
     * Get promotion status of set meal
     * @return promotionStatus
     */
    public boolean isPromotionStatus() {
        return promotionStatus;
    }

    /**
     * Change promotion status of set meal
     * @param promotionStatus a boolean of promotion status
     */
    public void setPromotionStatus(boolean promotionStatus) {
        this.promotionStatus = promotionStatus;
    }



}
