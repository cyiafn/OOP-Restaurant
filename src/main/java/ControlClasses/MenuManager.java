package ControlClasses;

import EntityClasses.*;
import Enumerations.FoodCategory;
import Enumerations.PrintColor;
import StaticClasses.Database;
import StaticClasses.InputHandler;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class MenuManager {
	/**
	 * Constant for file name reservation.
	 */
	public HashMap<Integer,FoodCategory> FoodCategoryMap = new HashMap<>();
	private Menu _menu;
	String filename =  "csv/menu.json";

	public Menu get_menu() {
		return _menu;
	}

	public void set_menu(Menu _menu) {
		this._menu = _menu;
	}

	private static MenuManager instance = null;

	/**
	 * Original method
	 */
	public MenuManager() throws IOException {
		init();
	}
	/**
	 * Overloading method
	 */
	public MenuManager(Menu _menu) {
		this._menu = _menu;
	}

	/**
	 * Static method
	 * Menu Manager check exist only one at a time
	 *
	 * @return MenuManger
	 */
	public static MenuManager getInstance() throws IOException {
		if (instance == null) {
			instance = new MenuManager();
		}
		return instance;
	}

	/**
	 * Create Alacarte menu item from user input
	 * Modular the function and DO DRY
	 * Return Alacarte menu item
	 * @return an Alacarte menu item
	 */
	private Alacarte createAlacarteFromUserInput() {
		String name = InputHandler.getString("Please enter the menuItem name: ");

		String description = InputHandler.getString("Please enter the menuItem description: ");

		double price = InputHandler.getDouble(1,100,
				"Please enter the menuItem price: ", "Error input! Please recheck and retry again.");

		int quantity = InputHandler.getInt(1,10,
				"Please enter the menuItem quantity: ", "Error input! Please recheck and retry again. \n Range of quantity is 1-10");


		return new Alacarte(UUID.randomUUID().toString(),
				name,  description,
				price, quantity);
	}

	/**
	 * Create Normal menu item from user input
	 * Modular the function and DO DRY
	 * Return menu item
	 * @return a menu item
	 */
	private MenuItem createMenuItemFromUserInput() {

		String name = InputHandler.getString("Please enter the menuItem name: ");

		System.out.println("Please enter the menuItem description: ");
		String description = InputHandler.getString("Please enter the menuItem description: ");


		double price = InputHandler.getDouble(1,100,
				"Please enter the menuItem price: ", "Error input! Please recheck and retry again.");

		int quantity = InputHandler.getInt(1,10,
				"Please enter the menuItem quantity: ", "Error input! Please recheck and retry again. \n Range of quantity is 1-10.");

		return  new MenuItem(UUID.randomUUID().toString(),
				name,  description,
				price, quantity);
	}

	/**
	 * Create SetMeal menu item from user input
	 * Modular the function and DO DRY
	 * Return SetMeal menu item
	 * @return a set meal
	 */
	private SetMeal createSetMealFromUserInput() {

		String name = InputHandler.getString("Please enter the menuItem name: ");

		String description = InputHandler.getString("Please enter the menuItem description: ");

		double price = InputHandler.getDouble(1,100,
				"Please enter the menuItem price: ", "Error input! Please recheck and retry again.");

		int quantity = InputHandler.getInt(1,10,
				"Please enter the menuItem quantity: ", "Error input! Please recheck and retry again. \n Range of quantity is 1-10.");


		// Using switch case here
		// resrict user to input only 3 times
		int i = 1;
		ArrayList<MenuItem> list_of_mi = new ArrayList<>();
		while(i<4){
			System.out.print(PrintColor.RED);
			System.out.println("This section is to create menu item in the set meal.");
			System.out.println("You are creating "+ ordinal(i) +" menu item in current set meal.");
			System.out.println("** Minimum sub menu item is 1. Maximum sub menu item is 3. **");
			System.out.print(PrintColor.RESET);
			String c = InputHandler.getString("Press # to exit or press any key to continue.");
			if (c.equals("#"))
			{
				if(i == 1)
				{
					continue;
				}
				else{
					break;
				}
			}
			else{
				MenuItem mi=createMenuItemFromUserInput();
				list_of_mi.add(mi);
			}
			i++;
		}

		System.out.println("How many menu item you wish to create in this SetMeal? Maximum is 3");


		return new SetMeal(
				UUID.randomUUID().toString(),
				name,
				description,
				price,
				quantity,
				list_of_mi
		);
	}


	/**
	 * This function create a default menu when there is no menu file exists
	 *
	 * @return a Menu
	 */
	public Menu createSingleMenuWhenNoMenuExisted() {

		ArrayList<MenuItem> mi_arr = new ArrayList<>(
				Arrays.asList(new Alacarte(UUID.randomUUID().toString(), "Pork Belly Buns",
								"Thick slices of French toast bread, brown sugar, half-and-half and vanilla, \n " +
										"topped with powdered sugar. With two eggs served any style, \n " +
										"and your choice of smoked bacon or smoked ham.", 98.58, 1)
						, new Alacarte(UUID.randomUUID().toString(), "Kebab",
								"Two butter croissants of your choice (plain, almond or cheese). " +
										"\n With a side of herb butter or house-made hazelnut spread. "
								, 85.82, 1)
						, new Alacarte(UUID.randomUUID().toString(), "Katsu Curry",
								"Smoked salmon, poached eggs, diced red onions and Hollandaise sauce on an English muffin. \n With a side of roasted potatoes."
								, 36.95, 1)
						, new Alacarte(UUID.randomUUID().toString(), "Pork Sausage Roll",
								"Three eggs with cilantro, tomatoes, onions, avocados and melted Emmental cheese. \n With a side of roasted potatoes, and your choice of toast or croissant."
								, 80.51, 1)
						, new Alacarte(UUID.randomUUID().toString(), "Pizza",
								"28-day aged 300g USDA Certified Prime Ribeye, rosemary-thyme garlic butter, with choice of two sides."
								, 78.92, 1)
						, new Alacarte(UUID.randomUUID().toString(), "Vodka",
								"Vodka is traditionally made from potatoes or fermented cereal grains. \n Some brands also make it from other substances like fruit or sugar."+
										"\n One of the most used and loved alcoholic drink, vodka is either consumed neat or \n as cocktails like Martini, Bloody Mary and Cosmopolitan."
								, 37.39, 1)
						, new Alacarte(UUID.randomUUID().toString(), "7up Lemon & Lime",
								"7up Lemon & Lime is a low calorie Lemon and Lime flavoured soft drink, available for delivery to homes, \n offices and workplaces directly from our warehouse in South London. "+
										"\n Clear, bright and colourless liquid with a lively carbonation. Delicate, \n enticing aroma of lemon and lime, fused and dusted with powdered sugar. "
								, 60.39, 1)
						, new Alacarte(UUID.randomUUID().toString(), "Som Tam",
								"Three eggs with cilantro, tomatoes, onions, avocados and melted Emmental cheese. \n With a side of roasted potatoes, and your choice of toast or croissant."
								, 16.78 , 1)

				)

		);


		ArrayList<MenuCategory>  mc_arr= new ArrayList<>();
		int i = 0;
		for (FoodCategory s : FoodCategory.values()){
			ArrayList<MenuItem> new_mi = new ArrayList<>();
			new_mi.add(mi_arr.get(i));
			MenuCategory mc= new MenuCategory(s , s.toString(), new_mi);
			mc_arr.add(mc);
			i++;
		}



		Menu mymenu = new Menu( UUID.randomUUID().toString(),  "Royale RRPSS Menu",
				"We sell various cuisine, including vegetarian and special seat meal," +
						"which prepare by our michelin 5 star chef : Gordan Ramsay.", mc_arr);


		mymenu.insertSingleMenuItemOnSingleMenuCategroy("MAINCOURSE",
				new SetMeal(UUID.randomUUID().toString(), "Pineapple and roquefort salad",
						"A crunchy salad featuring fresh pineapple and roquefort",
						108,1,
						new ArrayList<>(
								Arrays.asList(
										new MenuItem(
												UUID.randomUUID().toString(),"Wine and pasta casserole",
												"Moist cake made with fresh caraway and roasted chestnut",
												108/3, 1
										),
										new MenuItem(
												UUID.randomUUID().toString(),"Jalapeno and ham pizza",
												"Thin and crispy pizza topped with fresh jalapeno and smoked ham",
												108/3, 1
										),
										new MenuItem(
												UUID.randomUUID().toString(),"Basil and pineapple kebab",
												"Skewer-cooked dried basil and fresh pineapple served in warm pitta pocketsn",
												108/3, 1
										)

								)
						)

				)
		);

		// Update function

		// delete function
		// mymenu.delete_signle_menu_item_on_single_menu_category();
		return mymenu;
	}

	/**
	 * This function is responsible for format the database output into the Menu Class object
	 * Usage:: Menu new_menu = formatDatabaseMapIntoMenu(data);
	 * This function is cooperate with the `LoadFromJsonFile` in `Database.java`
	 * If you want to use JSON file as well, this function you need to rewrite yourself to suit your class
	 * @param data, is a map
	 * @return a Menu
	 */
	@SuppressWarnings("unchecked")
	public Menu formatDatabaseMapIntoMenu(Map data) {
		List<Map> data_cat = (List<Map>) data.get("_menuCategory");
		// Create the menu object here
		// first level: Menu Category
		// 8 times
		ArrayList<MenuCategory> new_menu_cat = new ArrayList<>();
		AtomicInteger i = new AtomicInteger(0);
		data_cat.forEach(
				(k) -> {

//                    System.out.println(k );
//                    System.out.println(i );

					// menu item
					List<Map> real_cat = (List<Map>) data_cat.get(i.intValue()).get("_menuItem");
					ArrayList<MenuItem> new_menu_item = new ArrayList<>();
					// for each to get menu item
					AtomicInteger m = new AtomicInteger(0);
					real_cat.forEach(
							(l) -> {
								String uid = real_cat.get(m.intValue()).get("_menuItemID").toString();
								String des = real_cat.get(m.intValue()).get("_description").toString();
								String name =   real_cat.get(m.intValue()).get("_name").toString();
								double price = Double.parseDouble(real_cat.get(m.intValue()).get("_price").toString());
								int quantity = Integer.parseInt(real_cat.get(m.intValue()).get("_quantity").toString());
//                                System.out.println(l );
								if(real_cat.get(m.intValue()).get("_type").toString().equals("setmeal"))
								{
									// Means it is set meal
									AtomicInteger o = new AtomicInteger(0);
									List<Map> sub_setmeal = (List<Map>) real_cat.get(m.intValue()).get("_set_of_item");
									if(!sub_setmeal.isEmpty())
									{
										ArrayList<MenuItem> sub_setmeal_mi = new ArrayList<>();
										sub_setmeal.forEach(
												(p) -> {
													String sub_uid = sub_setmeal.get(o.intValue()).get("_menuItemID").toString();
													String sub_des = sub_setmeal.get(o.intValue()).get("_description").toString();
													String sub_name =   sub_setmeal.get(o.intValue()).get("_name").toString();
													double sub_price = Double.parseDouble(sub_setmeal.get(o.intValue()).get("_price").toString());
													int sub_quantity = Integer.parseInt(sub_setmeal.get(o.intValue()).get("_quantity").toString()) ;
													MenuItem mii = new MenuItem(
															sub_uid,sub_name ,sub_des , sub_price, sub_quantity
													);
													sub_setmeal_mi.add(mii);
													o.getAndIncrement();
												}
										);

										SetMeal menu_item = new SetMeal(
												uid,
												name,
												des,
												price,
												quantity,
												sub_setmeal_mi
										);
										new_menu_item.add(menu_item);
										m.getAndIncrement();
									}

								}
								else
								{
									// Means it is alacarte
									Alacarte menu_item = new Alacarte(
											uid,
											name,
											des,
											price,
											quantity
									);
									new_menu_item.add(menu_item);
									m.getAndIncrement();
								}
							}
					);

					String des = data_cat.get(i.intValue()).get("_description").toString();
					String cat =  data_cat.get(i.intValue()).get("_category").toString();
					FoodCategory enum_cat = FoodCategory.valueOf(cat);
					MenuCategory mc = new MenuCategory(
							enum_cat,
							des,
							new_menu_item
					);

					new_menu_cat.add(mc);
					i.getAndIncrement();
				}
		);

		// form the menu here
		String id = data.get("_iD").toString();
		String name = data.get("_name").toString();
		String desp = data.get("_description").toString();

		return new Menu(
				id,
				name,
				desp,
				new_menu_cat
		);
	}


	/**
	 * This display Menu function is the entry point of Menu UI
	 * Do not remove this method because this function loaded the Menu from database
	 * If there is no menu file in the csv directory, it will create a new file with default menu values.
	 *
	 * @throws IOException, cause this read/ write from json text file
	 */
	public void init() throws IOException {

		File f = new File(filename.trim());
		if(f.exists() && !f.isDirectory())
		{
			// do nothing
			System.out.println();
			System.out.println("================================================");
			System.out.println("There is an existing menu. Loading existing menu.");
		}else
		{
			// Create default menu and write your json file
			System.out.println("There has no menu in your restaurant. Creating new menu.");
			Menu menu = createSingleMenuWhenNoMenuExisted();
			Database.WriteToJsonFile(menu, filename.trim());
			System.out.println("Welcome to " + menu.get_name());
		}
		Map data = Database.LoadFromJsonFile(filename.trim());
		Menu new_menu = formatDatabaseMapIntoMenu(data);
		// So other function can use it
		_menu = new_menu;

		// TODO: REMOVE after testing
		//		JsonBuilder builder = new JsonBuilder(new_menu);
		//		String json_str= builder.toString();
		//		System.out.println(json_str);
	}




	/**
	 * // TODO: MOVE THIS TO CONSTRUCTOR OF MENU MANAGER
	 * Hashmap for food category selection for user iput
	 * @return a Hashmap which contain food category
	 */
	public HashMap<Integer, FoodCategory> createHashMap() {
		int i =1;
		for (FoodCategory s : FoodCategory.values()){
			FoodCategoryMap.put(i, s);
			i++;
		}
		// System.out.println(FoodCategoryMap);
		return FoodCategoryMap;

	}

	/**
	 * Print function for Food category Hash Map
	 */
	public void printFoodCategoryHashMap() {
		// Iterating HashMap through for loop
		for (Map.Entry<Integer, FoodCategory> set :
				FoodCategoryMap.entrySet()) {
			System.out.println(set.getKey() + "  |  " + set.getValue());
		}
	}

	/**
	 * Get type of food
	 *
	 * @return return a string, Alacarte / Set Meal
	 */
	public int getTypeOfFoodItem() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the type of Menu Item you want to create: ");
		System.out.println("1 for alacarte , 2 for set meal ");
		int type = InputHandler.getInt(1,2,"(1) | Alacarte \n(2) | SetMeal ", "Error input! Please type in 1 or 2");

		return type ;
	}

	/**
	 * Helper function to view Menu
	 */
	public void viewMenu() {
		for(MenuCategory mc : this._menu.get_menuCategory()){
			mc.print();
		}
	}

	/**
	 * Modular Confirmation function
	 * Follow DRY format
	 * Reuse in creation / Update/ Delete menu item function
	 * @param m, is a menu
	 * @throws IOException, this function write to json text file
	 */
	private void confirmation(Menu m) throws IOException {
		System.out.println("Please confirm that you want to save/delete/update this food item into your menu.");
		System.out.println("Type 1 to save. Type 0 to cancel this operation.");
		System.out.println(" 1 | Yes ");
		System.out.println(" 0 | No ");

		int answer = InputHandler.getInt(0,1,
				" ", "Error input! Please recheck and retry again.");


		if(answer == 1){
			System.out.println("Save successfully!");
			Database.WriteToJsonFile(m ,filename.trim());
		}
		else{
			System.out.println("Canceled this operation.");
			// nothing
		}
	}

	/**
	 * Create Menu Item in this function
	 *
	 * @throws IOException, cause this function write to json text file
	 */
	public void createMenuItem() throws IOException {
		// Asking which category you want to choose
		// Display the Hashmap of the category
		createHashMap();
		printFoodCategoryHashMap();
//		Scanner sc = new Scanner(System.in);
		Integer category_number = InputHandler.getInt(1,FoodCategory.values().length ,"Please choose a category to create your menu item: " +
				"\n By typing in the category number: ", "Error input! Please type from 1-8");
		FoodCategory enum_cat =  FoodCategoryMap.get(category_number);

		MenuCategory mc = _menu.get_single_menu_categroy(enum_cat.toString());
		// Print the menu item in the category
		if(mc.get_menuItem().size()==0)
		{
			System.out.println("You have no menu item");
		}
		else{
			System.out.println("Here is your menu item in the Category : " + enum_cat);
			mc.print();
		}

		// Ask the user for the type of menu item
		int type = getTypeOfFoodItem();
		System.out.println(type);
		if(type==1 )
		{
			// Take in user input for menu item creation
			MenuItem mi = createAlacarteFromUserInput();
			Menu m = this.get_menu();
			m.insertSingleMenuItemOnSingleMenuCategroy(enum_cat.toString(),mi);
			this.set_menu(m);
		}
		else if (type==2)
		{
			// Take in user input for menu item creation
			MenuItem mi = createSetMealFromUserInput();
			Menu m = this.get_menu();
			m.insertSingleMenuItemOnSingleMenuCategroy(enum_cat.toString(),mi);
			this.set_menu(m);
		}
		mc.print();
		//MenuCategory mc = _menu.get_single_menu_categroy(enum_cat.toString());
		confirmation(this.get_menu());

	}

	/**
	 * Update Menu Item
	 * Only update for set meal and alacarte
	 * Cannot update sub menu item in set meal
	 * Delete and create the set meal if you need to change the sub meal item
	 * @throws IOException
	 */
	public void updateMenuItem() throws IOException {
		String id = InputHandler.getString("Please type in the menu item id that you wish to update.");

		int res_find=findById(id);
		if(res_find== 1)
		{
			MenuItem mi = new MenuItem();
			int type = this._menu.GetTypeByID(id);
			if(type == 1)
			{
				mi = createAlacarteFromUserInput();
			}
			else if (type ==2 )
			{
				mi = createSetMealFromUserInput();
			}

			Menu m = this.get_menu();
			int res= m.Update(id, mi);
			if(res ==1 ) {
				this.set_menu(m);
				//PrintMenu();
				confirmation(this.get_menu());
			}
		}

	}

	/**
	 * Delete Menu Item
	 * Only delete the alacarte / set meal menu item
	 * Will not search though the sub meal menu item
	 * @throws IOException
	 */
	public void deleteMenuItem() throws IOException {
		// Ask delete menu item
		String id = InputHandler.getString("Please type in the menu item id that you wish to delete.");

		int res_find=findById(id);
		if(res_find== 1)
		{
			Menu m = this.get_menu();
			int res= m.Delete(id);
			if(res ==1 ) {
				this.set_menu(m);
				confirmation(this.get_menu());
			}
		}

	}

	/**
	 * Delete Menu Item
	 * Only delete the alacarte / set meal menu item
	 * Will not search though the sub meal menu item
	 * @throws IOException
	 */
	public void teardown(String id) throws IOException {
		int res_find=findById(id);
		if(res_find== 1)
		{
			Menu m = this.get_menu();
			int res= m.Delete(id);
			if(res ==1 ) {
				this.set_menu(m);
				confirmation(this.get_menu());
			}
		}

	}

	/**
	 * Helper class to format 1 to 1st
	 * Input integer number
	 * Usage::  ordinal(1) => 1st
	 */
	public static String ordinal(int i) {
		String[] suffixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
		switch (i % 100) {
			case 11:
			case 12:
			case 13:
				return i + "th";
			default:
				return i + suffixes[i % 10];

		}
	}


	/**
	 * Find by id
	 * Follow DRY
	 * @param id , Menu Item ID
	 * @return 1,0, 1 is success to find a menu item, 0 is find nothing
	 */
	public int findById(String id){
		int res = _menu.findById(id);
		if(res==0)
		{
			System.out.println(" -------------------------------");
			System.out.print(PrintColor.RED);
			System.out.println("Sorry, unable to find the menu id to update/delete.");
			System.out.println("There is nothing we can do.");
			System.out.println("Remember that we only can update the Alacarte item or SetMeal Item.");
			System.out.print(PrintColor.RESET);
			System.out.println(" -------------------------------");

		}
		else{
			System.out.println(" -------------------------------");
			System.out.print(PrintColor.GREEN);
			System.out.println("We found your menu item");
			System.out.print(PrintColor.RESET);
			System.out.println(" -------------------------------");
		}
		return res;
	}

	/**
	 * Find by id overloading method requested by Gotwin
	 * Follow DRY
	 * @param id , Menu Item
	 * @return 1,0, 1 is success to find a menu item, 0 is find nothing
	 */
	public MenuItem findByIdForMenuItem(String id){
		MenuItem res = _menu.findByIdForMenuItem(id);
		return res;
	}

	public MenuItem findByNameForMenuItem(String name) {
		MenuItem res = _menu.findByNameForMenuItem(name);
		return res;
	}


}
