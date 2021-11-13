package EntityClasses;

import Enumerations.FoodCategory;
import Enumerations.PrintColor;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Menu entity class
 * Only have one menu exists at any point in time
 * @author Daniel Chu Jia Hao
 * @version 1.0
 * @since 2021-11-07
 */
public class Menu {
        /**
         * The id of the menu of Michelin Western Restaurant
         */
        private String id;
        /**
         * The name of the menu
         */
        private String name;
        /**
         * The description of the menu
         */
        private String description;
        /**
         * A menu can have multiple Categories.
         */
        private ArrayList<MenuCategory> menuCategory;

        /**
         * Constructor of Menu
         * @param id
         * @param name
         * @param description
         * @param menuCategory
         */
        public Menu(String id, String name, String description, ArrayList<MenuCategory> menuCategory) {
                this.id = id;
                this.name = name;
                this.description = description;
                this.menuCategory = menuCategory;
        }

        /**
         * Empty Constructor of Menu
         */
        public Menu() { }

        /**
         * This is overloading constructor for getting single menu category based on user input
         * @param fc
         * @return a menu category
         */
        public MenuCategory getSingleMenuCategroy(String fc) {

                for(MenuCategory mc : menuCategory){
                        if( mc.getCategory() == FoodCategory.valueOf(fc.toUpperCase(Locale.ROOT)))
                        {
                                return mc;
                        }
                }
                return null;
        }

        /**
         * This is overloading constructor for getting single menu category based on user input
         * @param fc
         */
        public void insertSingleMenuItemOnSingleMenuCategroy(String fc, MenuItem item) {

                for(MenuCategory mc : menuCategory){
                        if( mc.getCategory() == FoodCategory.valueOf(fc.toUpperCase(Locale.ROOT)))
                        {
                                mc.insertSingleMenuItem(item);
                        }
                }
        }


        /**
         * Delete function will search though the menu item and delete it
         * @param itemid
         * @return a integer, 1 is
         */
        public int getTypeByID(String itemid) {

                for(MenuCategory mc : menuCategory){
                        MenuItem mi= mc.findById(itemid);
                        if(mi instanceof Alacarte)
                        {
                                Alacarte ac = (Alacarte) mi;
                                return 1;
                        }
                        if (mi instanceof SetMeal) {
                                SetMeal sm = (SetMeal)mi;
                                return 2;
                        }
                }
                return 0; // failed
        }

        /**
         * delete function will search though the menu item and delete it
         * @param itemid
         * @return a integer
         */
        public int delete(String itemid) {

                for(MenuCategory mc : menuCategory){
                        if(mc.findById(itemid)!= null){
                                MenuItem mi= mc.findById(itemid);
                                mi.print();
                                mc.deleteSingleMenuItem(mi);
                                return 1;
                        }
                }
                return 0; // failed
        }

        /**
         * Update function will update the menu item
         * @param itemid menu item id
         * @param mim the menuitem object which have updated value from user input
         * @return 0,1, 0 is failed, 1 is success
         */
        public int update(String itemid, MenuItem mim) {

                for(MenuCategory mc : menuCategory){
                        if(mc.findById(itemid)!= null) {
                                MenuItem mi = mc.findById(itemid);
                                mi.print();
                                mc.updateMenuItem(mi, mim);
                                return 1;
                        }
                }
                return 0; // failed
        }

        /**
         * Find by ID
         * @param id menu item id
         * @return int , return 1 or 0, 1 is success, 0 is failed
         */
        public int findById(String id){
                for(MenuCategory mc : menuCategory){
                        if(mc.findById(id)!= null){
                                MenuItem mi= mc.findById(id);
                                return 1;
                        }
                }
                return 0;
        }

        /**
         * Find by ID
         * @param id
         * @return int , return 1 or 0, 1 is success, 0 is failed
         */
        public int findBySubstringId(String id){
                for(MenuCategory mc : menuCategory){
                        if(mc.findBySubstringId(id)!= null){
                                MenuItem mi= mc.findBySubstringId(id);
                                return 1;
                        }
                }
                return 0;
        }

        /**
         * Find by ID overloading method
         * @param id menu item id
         * @return MenuItem
         */
        public MenuItem findByIdForMenuItem(String id){
                MenuItem mi = new MenuItem();
                for(MenuCategory mc : menuCategory){
                        if(mc.findById(id)!= null){
                                mi= mc.findById(id);
                                return mi;
                        }
                }
                return mi;
        }

        /**
         * Find by ID overloading method
         * @param id menu item id
         * @return MenuItem
         */
        public MenuItem findBySubstringIdForMenuItem(String id){
                MenuItem mi = new MenuItem();
                for(MenuCategory mc : menuCategory){
                        if(mc.findBySubstringId(id)!= null){
                                mi= mc.findBySubstringId(id);
                                return mi;
                        }
                }
                return mi;
        }

        /**
         * Find by Name overloading method
         * @param name menu item name
         * @return MenuItem
         */
        public MenuItem findByNameForMenuItem(String name){
                MenuItem mi = new MenuItem();
                for(MenuCategory mc : menuCategory){
                        if(mc.findByName(name)!= null){
                                mi= mc.findByName(name);
                                return mi;
                        }
                }
                return mi;
        }

        /**
         * Get the id of this menu
         * @return the menu id
         */
        public String getId() {
                return id;
        }

        /**
         * Changes the id of this menu
         * @param id This menu id should not be empty
         */
        public void setId(String id) {
                this.id = id;
        }

        /**
         * Get the name of this menu.
         * @return the menu name
         */
        public String getName() {
                return name;
        }

        /**
         * Changes the name of this menu
         * @param name This menu name should not be empty
         */
        public void setName(String name) {
                this.name = name;
        }

        /**
         * Get the description of the menu
         * @return the menu description
         */
        public String getDescription() {
                return description;
        }

        /**
         * Changes the description of this menu
         * @param description menu item description
         */
        public void setDescription(String description) {
                this.description = description;
        }

        /**
         * Get all the menu categories that this menu have
         * @return a list of menu categories
         */
        public ArrayList<MenuCategory> getMenuCategory() {
                return menuCategory;
        }

        /**
         * Change the Menu Categories in this menu
         * @param menuCategory An arraylist of menu category
         */
        public void setMenuCategory(ArrayList<MenuCategory> menuCategory) {
                this.menuCategory = menuCategory;
        }



}
