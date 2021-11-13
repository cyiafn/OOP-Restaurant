package EntityClasses;

import Enumerations.FoodCategory;
import Enumerations.PrintColor;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Menu entity class
 * Only have one menu exists at any point in time
 *          *
 *          *      https://home.binwise.com/blog/types-of-menu
 *          *     a la carte menus,
 *          *     static menus,
 *          *     du jour menus,
 *          *     cycle menus, and
 *          *     fixed menus.
 *          *     Beverage Menu
 *          *     Dessert Menu?
 *          *     Possible menu vs normal menu
 * @author Daniel Chu Jia Hao
 * @version 1.0
 * @since 2021-11-07
 */
public class Menu {

        /**
        Attributes of Menu
         */
        private String id;
        private String name;
        private String description;
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
         * @param itemid
         * @param mim
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
         * @param id
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
         * @param id
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
         * @param id
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
         * @param name
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

        /*
        Accessors and Mutators
         */
        public String getId() {
                return id;
        }

        public void setId(String id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public ArrayList<MenuCategory> getMenuCategory() {
                return menuCategory;
        }

        public void setMenuCategory(ArrayList<MenuCategory> menuCategory) {
                this.menuCategory = menuCategory;
        }



}
