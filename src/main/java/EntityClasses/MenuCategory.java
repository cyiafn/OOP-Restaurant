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
    /**
     * The description for menu category
     */
    private String description;
    /**
     * Enum type of food category in menu category entity class
     */
    private FoodCategory category;
    /**
     * Array List of menu Item in menu category entity class
     * A menu category can hold many menu items
     */
    private ArrayList<MenuItem> menuItem;

    /**
     * Constructor for MenuCategory
     * @param category An item from enum FoodCategory
     * @param description description of the menu
     * @param menuItem A list of menu item
     */
    public MenuCategory(FoodCategory category, String description, ArrayList<MenuItem> menuItem) {
        this.category = category;
        this.description = description;
        this.menuItem = menuItem;
    }

    /**
     * Insert Menu Item into the Menu
     * @param item A menu item
     */
    public void insertSingleMenuItem(MenuItem item) {
        this.menuItem.add(item);
    }

    /**
     * Delete Menu Item in the Menu
     * @param item A menu item
     */
    public void deleteSingleMenuItem(MenuItem item) {
        // searching for the item id
        this.menuItem.remove(item);
    }

    /**
     * Find the menu item by string id
     * @param id full menu id
     * @return a menu item
     */
    public MenuItem findById(String id) {
        return  menuItem.stream().filter(s ->
                s.getMenuItemID().equals(id)
        ).findFirst().orElse(null);
    }

    /**
     * Find the menu item by substring of string id
     * @param id substring of menu id
     * @return a menu item
     */
    public MenuItem findBySubstringId(String id) {
        return  menuItem.stream().filter(s ->
                s.getMenuItemID().startsWith(id)
        ).findFirst().orElse(null);
    }

    /**
     * Find the menu item by name
     * @param name full name of menu item
     * @return a menu item
     */
    public MenuItem findByName(String name) {

        MenuItem found_menu_item = menuItem.stream().filter(s -> s.getName().equals(name)).findFirst().orElse(null);

        return found_menu_item;
    }

    /**
     * Update the menu item by reset the id of the new menu item
     * @param mi the old menu item you wish to update
     * @param mim the menu item that hold user input
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
     * @return ArrayList of String which belong to Set meal item id
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
     * Get the Category of this menu category
     * @return an item from enum FoodCategory
     */
    public FoodCategory getCategory() {
        return category;
    }

    /**
     * Change the category of this menu category
     * @param category an item from enum FoodCategory
     */
    public void setCategory(FoodCategory category) {
        this.category = category;
    }

    /**
     * Get a list of menu item from this category
     * @return a list of menu item
     */
    public ArrayList<MenuItem> getMenuItem() {
        return menuItem;
    }

    /**
     * Change the list of menu item from this category
     * @param menuItem a list of menu item
     */
    public void setMenuItem(ArrayList<MenuItem> menuItem) {
        this.menuItem = menuItem;
    }

    /**
     * Get the description of this category
     * @return description of this category
     */
    public String getDescription() {
        return description;
    }

    /**
     * Changes the description of this category
     * @param description a description of this category
     */
    public void setDescription(String description) {
        this.description = description;
    }



}
