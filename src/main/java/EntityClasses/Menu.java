package EntityClasses;

import Enumerations.FoodCategory;
import Enumerations.PrintColor;

import java.util.ArrayList;
import java.util.Locale;

public class Menu {

        /**
         *     VALENTINEDAYMENU,
         *     FATHERDAYMENU,
         *     MOTHERDAYMENU
         *
         *      https://home.binwise.com/blog/types-of-menu
         *     a la carte menus,
         *     static menus,
         *     du jour menus,
         *     cycle menus, and
         *     fixed menus.
         *     Beverage Menu
         *     Dessert Menu?
         *     Possible menu vs normal menu
         */
        private String _iD;
        private String _name;
        private String _description;
        private ArrayList<MenuCategory> _menuCategory;

        /**
         *
         * @param _iD
         * @param _name
         * @param _description
         * @param _menuCategory
         */
        public Menu(String _iD, String _name, String _description, ArrayList<MenuCategory> _menuCategory) {
                this._iD = _iD;
                this._name = _name;
                this._description = _description;
                this._menuCategory = _menuCategory;
        }

        public Menu() { }

        /**
         * This is overloading constructor for getting single menu category based on user input
         * @param fc
         * @return a menu category
         */
        public MenuCategory get_single_menu_categroy(String fc) {

                for(MenuCategory mc : _menuCategory){
                        if( mc.get_category() == FoodCategory.valueOf(fc.toUpperCase(Locale.ROOT)))
                        {
                                return mc;
                        }
                }
                return null;
        }

        /**
         * This is overloading constructor for getting single menu category based on user input
         * @param fc
         * @return void
         */
        public void insertSingleMenuItemOnSingleMenuCategroy(String fc, MenuItem item) {

                for(MenuCategory mc : _menuCategory){
                        if( mc.get_category() == FoodCategory.valueOf(fc.toUpperCase(Locale.ROOT)))
                        {
                                mc.insertSingleMenuItem(item);
                        }
                }
        }



        public void insertSingleMenuItemOnSingleMenuCategroyWithSeatMeal(String fc, MenuItem item) {
                for(MenuCategory mc : _menuCategory){
                        if( mc.get_category() == FoodCategory.valueOf(fc.toUpperCase(Locale.ROOT)))
                        {
                                for(MenuItem mi : mc.get_menuItem()){
                                        if (mi instanceof SetMeal) {
                                                SetMeal sm = (SetMeal)mi;
                                                sm.insert_seat_meal_menu_item(item);
                                        }
                                }
                        }
                }
        }

        /**
         * Delete function will search though the menu item and delete it
         * @param item_id
         * @return a integer, 1 is
         */
        public int GetTypeByID(String item_id) {

                for(MenuCategory mc : _menuCategory){
                        MenuItem mi= mc.findById(item_id);
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
         * Delete function will search though the menu item and delete it
         * @param item_id
         * @return a integer
         */
        public int Delete(String item_id) {

                for(MenuCategory mc : _menuCategory){
                        if(mc.findById(item_id)!= null){
                                MenuItem mi= mc.findById(item_id);
                                mi.print();
                                mc.deleteSingleMenuItem(mi);
                                return 1;
                        }
                }
                return 0; // failed
        }

        /**
         * Update function will update the menu item
         * @param item_id
         * @param mim
         * @return 0,1, 0 is failed, 1 is success
         */
        public int Update(String item_id, MenuItem mim) {

                for(MenuCategory mc : _menuCategory){
                        if(mc.findById(item_id)!= null) {
                                MenuItem mi = mc.findById(item_id);
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
                for(MenuCategory mc : _menuCategory){
                        if(mc.findById(id)!= null){
                                MenuItem mi= mc.findById(id);
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
                for(MenuCategory mc : _menuCategory){
                        if(mc.findById(id)!= null){
                                mi= mc.findById(id);
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
                for(MenuCategory mc : _menuCategory){
                        if(mc.findByName(name)!= null){
                                mi= mc.findByName(name);
                                return mi;
                        }
                }
                return mi;
        }

        public String get_iD() {
                return _iD;
        }

        public void set_iD(String _iD) {
                this._iD = _iD;
        }

        public String get_name() {
                return _name;
        }

        public void set_name(String _name) {
                this._name = _name;
        }

        public String get_description() {
                return _description;
        }

        public void set_description(String _description) {
                this._description = _description;
        }

        public ArrayList<MenuCategory> get_menuCategory() {
                return _menuCategory;
        }

        public void set_menuCategory(ArrayList<MenuCategory> _menuCategory) {
                this._menuCategory = _menuCategory;
        }



}
