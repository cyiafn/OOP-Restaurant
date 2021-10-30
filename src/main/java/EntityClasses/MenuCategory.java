package EntityClasses;

import Enumerations.FoodCategory;
import Enumerations.PrintColor;

import java.util.*;

public class MenuCategory {
    private String _description;
    private FoodCategory _category;
    private ArrayList<MenuItem> _menuItem;

    /**
     * Constructor for MenuCategory
     * @param _category
     * @param _description
     * @param _menuItem
     */
    public MenuCategory(FoodCategory _category, String _description, ArrayList<MenuItem> _menuItem) {
        this._category = _category;
        this._description = _description;
        this._menuItem = _menuItem;
    }

    /**
     * Insert Menu Item into the Menu
     * @param item
     */
    public void InsertSingleMenuItem(MenuItem item) {
        this._menuItem.add(item);
    }

//    public void insert_single_menu_item_into_seat_meal(MenuItem item) {
//        this._menuItem.add(item);
//    }

    /**
     * Delete Menu Item in the Menu
     * @param item
     */
    public void DeleteSingleMenuItem(MenuItem item) {
        // searching for the item id
        this._menuItem.remove(item);
    }

    /**
     * Find the menu item by string id
     * @param id
     * @return
     */
    public MenuItem FindById(String id) {

        MenuItem found_menu_item = _menuItem.stream().filter(s ->
                s.get_menuItemID().equals(id)
        ).findFirst().orElse(null);

        return found_menu_item;
    }

    /**
     * Find the menu item by name
     * @param name
     * @return
     */
    public MenuItem FindByName(String name) {

        MenuItem found_menu_item = _menuItem.stream().filter(s -> s.get_name().equals(name)).findFirst().orElse(null);

        return found_menu_item;
    }

    /**
     * Update the menu item by the name , description , price, and quantity
     * @param mi
     * @param name
     * @param _description
     * @param price
     * @param quantity
     */
    public void UpdateMenuItem(MenuItem mi , String name, String _description, double price, int quantity) {
        int index= this._menuItem.indexOf(mi);
        mi.set_name(name);
        mi.set_description(_description);
        mi.set_price(price);
        mi.set_quantity(quantity);
        this._menuItem.set(index, mi);
    }

    /**
     * Print the menu item using print function
     * Based on inheritance.
     */
    public void print(){
        for(MenuItem mi : this.get_menuItem())
        {
            System.out.print(PrintColor.RED);
            System.out.println(" Category:    "+ this.get_category().toString());
            System.out.print(PrintColor.RESET);
            if (mi instanceof SetMeal) {
                SetMeal sm = (SetMeal)mi;
                sm.print();
            }
            if(mi instanceof Alacarte)
            {
                Alacarte ac = (Alacarte) mi;
                ac.print();
            }
        }
    }

    /**
     * Below is normal setter and getter
     * Accessor and mutator
     * @return
     */
    public FoodCategory get_category() {
        return _category;
    }

    public void set_category(FoodCategory _category) {
        this._category = _category;
    }

    public ArrayList<MenuItem> get_menuItem() {
        return _menuItem;
    }

    public void set_menuItem(ArrayList<MenuItem> _menuItem) {
        this._menuItem = _menuItem;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }



}
