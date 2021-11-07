package EntityClasses;

import Enumerations.FoodCategory;
import Enumerations.PrintColor;

import java.util.ArrayList;

public class SetMeal extends MenuItem {
    String type = "setmeal";

    private ArrayList<MenuItem> setOfItem ;


    public SetMeal(String menuItemID, String name, String description, double price, Integer quantity, ArrayList<MenuItem> ala) {
        super(menuItemID, name, description, price, quantity);
        setOfItem = ala;
    }

    @Override
    public void print() {
        System.out.println(" -------------------------------");
        System.out.print(PrintColor.GREEN_BOLD);

        System.out.println(" Menu ID: " + this.getMenuItemID() + " | " );
        System.out.print(PrintColor.BLUE);
        System.out.println(" Set Meal Name: "+ this.getName() + " | ");
        System.out.println(" Set Meal Description: "+ this.getDescription());
        System.out.print(PrintColor.YELLOW_BOLD);
        System.out.println(" Set Meal Price: " + this.getPrice()+ " | "+ " Set Meal Quantity: "+ this.getQuantity().toString());
        System.out.println(" Type: "+ this.getType());
        System.out.println("");
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<MenuItem> getSetOfItem() {
        return setOfItem;
    }

    public void setSetOfItem(ArrayList<MenuItem> setOfIteme) {
        this.setOfItem = setOfIteme;
    }



}
