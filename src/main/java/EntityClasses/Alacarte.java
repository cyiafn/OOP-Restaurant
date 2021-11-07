package EntityClasses;
import Enumerations.PrintColor;

/**
 * Alacarte entity class
 * @author Daniel Chu Jia Hao
 * @version 1.0
 * @since 2021-11-07
 */
public class Alacarte extends MenuItem {
    /**
     * Type of Menu Item
     * in this case is alacarte
     */
    String type = "alacarte";

    /**
     * Constructor of Alacarte
     * @param menuItemID
     * @param name
     * @param description
     * @param price
     * @param quantity
     */
    public Alacarte(String menuItemID, String name, String description, double price, Integer quantity) {
        super(menuItemID, name, description, price, quantity);
    }

    /**
     * override function from parent class print()
     */
    @Override
    public void print() {
        System.out.println(" -------------------------------");
        System.out.print(PrintColor.GREEN_BOLD);
        System.out.println(" Menu ID: " + this.getMenuItemID() + " | " );
        System.out.print(PrintColor.BLUE);
        System.out.println(" Alacarte Meal Name: "+ this.getName() + " | ");
        System.out.println(" Alacarte Description: "+ this.getDescription());
        System.out.print(PrintColor.YELLOW_BOLD);
        System.out.println(" Alacarte Price: " + this.getPrice()+ " | "+ " Alacarte Quantity: "+ this.getQuantity().toString());
        System.out.println(" Type: "+ this.getType());

        System.out.print(PrintColor.RESET);
        System.out.println(" -------------------------------");
    }
    /*
    Accessor and Mutator
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
