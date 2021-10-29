package EntityClasses;

import Enumerations.FoodCategory;
import Enumerations.PrintColor;

import java.util.ArrayList;

public class SetMeal extends MenuItem {
    String _type = "setmeal";

    private ArrayList<MenuItem> _set_of_item ;
    public SetMeal(String _menuItemID, String _name, String _description, double _price, Integer _quantity) {
        super(_menuItemID, _name, _description, _price, _quantity);
    }

    public SetMeal(String _menuItemID, String _name, String _description, double _price, Integer _quantity, ArrayList<MenuItem> ala) {
        super(_menuItemID, _name, _description, _price, _quantity);
        _set_of_item = ala;
    }

    public void insert_seat_meal_menu_item(MenuItem mi) {
        this._set_of_item.add((mi));
    }

    @Override
    public void print() {
        System.out.println(" -------------------------------");
        System.out.print(PrintColor.GREEN_BOLD);

        System.out.println(" Menu ID: " + this.get_menuItemID() + " | " );
        System.out.print(PrintColor.BLUE);
        System.out.println(" Set Meal Name: "+ this.get_name() + " | ");
        System.out.println(" Set Meal Description: "+ this.get_description());
        System.out.print(PrintColor.YELLOW_BOLD);
        System.out.println(" Set Meal Price: " + this.get_price()+ " | "+ " Set Meal Quantity: "+ this.get_quantity().toString());
        System.out.println(" Set Meal Type: "+ this.get_type());
        System.out.println("");
        for(MenuItem mi : this._set_of_item)
        {
            System.out.print(PrintColor.GREEN_BOLD);
            System.out.println("   {");
            System.out.println("     Menu ID: " + mi.get_menuItemID() + " | ");
            System.out.print(PrintColor.BLUE);
            System.out.println("     Set Meal Name: "+ mi.get_name() + " | ");
            System.out.println("     Set Meal Description: "+ mi.get_description());
            System.out.print(PrintColor.YELLOW_BOLD);
            System.out.println("     Set Meal Price: " + mi.get_price()+ " | "+ "Quantity: "+ mi.get_quantity().toString());
            System.out.println("   }");
        }
        System.out.print(PrintColor.RESET);
        System.out.println(" -------------------------------");
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }



    public ArrayList<MenuItem> get_set_of_item() {
        return _set_of_item;
    }

    public void set_set_of_item(ArrayList<MenuItem> _set_of_iteme) {
        this._set_of_item = _set_of_iteme;
    }

    public MenuItem FindById(String name) {

        MenuItem found_menu_item = _set_of_item.stream().filter(s -> s.get_menuItemID().equals(name)).findFirst().orElse(null);

        return found_menu_item;
    }


}
