package EntityClasses;

import Enumerations.FoodCategory;
import Enumerations.PrintColor;

import java.util.*;

/**
 * MenuCategory entity class
 * @author Daniel Chu Jia Hao
 * @version 1.0
 * @since 2021-11-07
 */
public class MenuCategory {
    /*
    Attributes of MenuCategory
     */
    private String description;
    private FoodCategory category;
    private ArrayList<MenuItem> menuItem;

    /**
     * Constructor for MenuCategory
     * @param category
     * @param description
     * @param menuItem
     */
    public MenuCategory(FoodCategory category, String description, ArrayList<MenuItem> menuItem) {
        this.category = category;
        this.description = description;
        this.menuItem = menuItem;
    }

    /**
     * Insert Menu Item into the Menu
     * @param item
     */
    public void insertSingleMenuItem(MenuItem item) {
        this.menuItem.add(item);
    }

    /**
     * Delete Menu Item in the Menu
     * @param item
     */
    public void deleteSingleMenuItem(MenuItem item) {
        // searching for the item id
        this.menuItem.remove(item);
    }

    /**
     * Find the menu item by string id
     * @param id
     * @return a menu item
     */
    public MenuItem findById(String id) {
        return  menuItem.stream().filter(s ->
                s.getMenuItemID().equals(id)
        ).findFirst().orElse(null);
    }

    /**
     * Find the menu item by name
     * @param name
     * @return
     */
    public MenuItem findByName(String name) {

        MenuItem found_menu_item = menuItem.stream().filter(s -> s.getName().equals(name)).findFirst().orElse(null);

        return found_menu_item;
    }

    /**
     * Update the menu item by reset the id of the new menu item
     * @param mi
     * @param mim
     */
    public void updateMenuItem(MenuItem mi , MenuItem mim) {
        int index= this.menuItem.indexOf(mi);
        mim.setMenuItemID(mi.getMenuItemID());
        this.menuItem.set(index, mim);
    }

    /**
     * Print the menu item using print function
     * Based on inheritance.
     */
    public void print(){
        for(MenuItem mi : this.getMenuItem())
        {
            System.out.print(PrintColor.RED);
            System.out.println(" Category:    "+ this.getCategory().toString());
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
     * Retrieve all set meal item id in the menu
     * @return ArrayList<String> which belong to Set meal item id
     */
    public ArrayList<String> retrieveAllSeatMealID(){
        ArrayList<String> listOfSmenuID = new ArrayList<>();
        for(MenuItem mi : this.getMenuItem())
        {
            if(mi instanceof SetMeal)
            {
                listOfSmenuID.add(mi.getMenuItemID());
            }
        }
        return listOfSmenuID;
    }

    /**
     * Below is normal setter and getter
     * Accessor and mutator
     * @return
     */
    public FoodCategory getCategory() {
        return category;
    }

    public void setCategory(FoodCategory category) {
        this.category = category;
    }

    public ArrayList<MenuItem> getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(ArrayList<MenuItem> menuItem) {
        this.menuItem = menuItem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
