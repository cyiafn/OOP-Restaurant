package EntityClasses;

import Enumerations.FoodCategory;
import Enumerations.PrintColor;

public class Alacarte extends MenuItem {
    String _type = "alacarte";

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public Alacarte(String _menuItemID, String _name, String _description, double _price, Integer _quantity) {
        super(_menuItemID, _name, _description, _price, _quantity);
    }

    @Override
    public void print() {
        System.out.println(" -------------------------------");
        System.out.print(PrintColor.GREEN_BOLD);
        System.out.println(" Menu ID: " + this.get_menuItemID() + " | " );
        System.out.print(PrintColor.BLUE);
        System.out.println(" Alacarte Meal Name: "+ this.get_name() + " | ");
        System.out.println(" Alacarte Description: "+ this.get_description());
        System.out.print(PrintColor.YELLOW_BOLD);
        System.out.println(" Alacarte Price: " + this.get_price()+ " | "+ " Alacarte Quantity: "+ this.get_quantity().toString());
        System.out.println(" Type: "+ this.get_type());

        System.out.print(PrintColor.RESET);
        System.out.println(" -------------------------------");
    }
}
