package EntityClasses;

import Enumerations.FoodCategory;
import Enumerations.PrintColor;

import java.util.*;

public class MenuCategory {
    private String _description;
    private FoodCategory _category;
    private ArrayList<MenuItem> _menuItem;



    public MenuCategory(FoodCategory _category, String _description, ArrayList<MenuItem> _menuItem) {
        this._category = _category;
        this._description = _description;
        this._menuItem = _menuItem;

    }

    public MenuCategory(Map map) {
        this._category = (FoodCategory) map.get("_category") ;
        this._description = (String) map.get("_description");
        this._menuItem = (ArrayList<MenuItem>) map.get("_menuItem");
    }
    public MenuCategory() { }


    public void insert_single_menu_item(MenuItem item) {
//
//        if(){
//
//        }
        this._menuItem.add(item);
    }

    public void insert_single_menu_item_into_seat_meal(MenuItem item) {
        this._menuItem.add(item);
    }

    public void delete_single_menu_item(MenuItem item) {
        // searching for the item id
        this._menuItem.remove(item);
    }

    public MenuItem FindById(String id) {

        MenuItem found_menu_item = _menuItem.stream().filter(s ->
                s.get_menuItemID().equals(id)
        ).findFirst().orElse(null);

//        if (found_menu_item == null) {
//            // try with set meat
//            _menuItem.add()
//        }

        return found_menu_item;
    }

    public MenuItem FindByName(String name) {

        MenuItem found_menu_item = _menuItem.stream().filter(s -> s.get_name().equals(name)).findFirst().orElse(null);

        return found_menu_item;
    }

    public void UpdateMenuItem(MenuItem mi , String name, String _description, double price, int quantity) {
        int index= this._menuItem.indexOf(mi);
        mi.set_name(name);
        mi.set_description(_description);
        mi.set_price(price);
        mi.set_quantity(quantity);
        this._menuItem.set(index, mi);
    }

//    public MenuItem UpdateSetMealMenuItem(String name, String _description, double price, int quantity) {
//
//    }


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
//				else{
//					mi.print();
//				}
        }
    }

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
