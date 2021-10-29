package EntityClasses;

import Enumerations.FoodCategory;
import Enumerations.PrintColor;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

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


        public Menu(Map map) {
                this._iD = (String) map.get("_id")  ;
                this._name = (String) map.get("_name");
                this._description = (String) map.get("_description");
                this._menuCategory = (ArrayList<MenuCategory>) map.get("_menuCategory");
        }
        public Menu() { }

        /**
         * This is overloading constructor for getting single menu category based on user input
         * @param fc
         * @return
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
         * @return
         */
        public void InsertSingleMenuItemOnSingleMenuCategroy(String fc, MenuItem item) {

                for(MenuCategory mc : _menuCategory){
                        if( mc.get_category() == FoodCategory.valueOf(fc.toUpperCase(Locale.ROOT)))
                        {
                                mc.insert_single_menu_item(item);
                        }
                }
        }

        public void insert_single_menu_item_on_single_menu_categroy_with_seat_meal(String fc, MenuItem item) {
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

        public int Delete(String item_id) {

                for(MenuCategory mc : _menuCategory){
                        if(mc.FindById(item_id)!= null){
                                MenuItem mi= mc.FindById(item_id);
                                mi.print();
                                mc.delete_single_menu_item(mi);
                                return 1;
                        }
                }
                System.out.println(" -------------------------------");
                System.out.print(PrintColor.RED);
                System.out.println("Sorry, unable to find the menu id to delete.");
                System.out.println("There is nothing we can do.");
                System.out.println("Remember that we only can delete the Alacarte item or SetMeal Item.");
                System.out.print(PrintColor.RESET);
                System.out.println(" -------------------------------");
                return 0; // failed
        }

        public int Update(String item_id, String name, String description, double price, int quantity) {

                for(MenuCategory mc : _menuCategory){
                        if(mc.FindById(item_id)!= null){
                                MenuItem mi= mc.FindById(item_id);
                                mi.print();
                                mc.UpdateMenuItem(mi , name, description, price, quantity);
                                return 1;
                        }
                }
                System.out.println(" -------------------------------");
                System.out.print(PrintColor.RED);
                System.out.println("Sorry, unable to find the menu id to update.");
                System.out.println("There is nothing we can do.");
                System.out.println("Remember that we only can update the Alacarte item or SetMeal Item.");
                System.out.print(PrintColor.RESET);
                System.out.println(" -------------------------------");
                return 0; // failed
        }

//        public void update_signle_menu_item_on_single_menu_category(String fc, MenuItem item) {
//
//                for(MenuCategory mc : _menuCategory){
//                        if( mc.get_category() == FoodCategory.valueOf(fc.toUpperCase(Locale.ROOT)))
//                        {
//                                mc(item);
//                        }
//                }
//        }




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
