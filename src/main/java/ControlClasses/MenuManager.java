package ControlClasses;

import EntityClasses.*;
import Enumerations.FoodCategory;
import Enumerations.PrintColor;
import StaticClasses.Database;
import groovy.json.JsonBuilder;
import groovy.json.JsonOutput;

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
	public MenuManager() {

	}
	/**
	 * Overloading method
	 */
	public MenuManager(Menu _menu) {
		this._menu = _menu;
	}

	public static MenuManager retrieveInstance() {
		if (instance == null) {
			instance = new MenuManager();
		}
		return instance;
	}


	private Integer checkCurrentMenuQuantity() {
		// Load from csv and check the quantity
		return 1;
	}

	private String[] getUserInputForMenuItemNameDescription(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the menu name: ");
		String name = sc.nextLine();

		System.out.println("Please enter the menu description: ");
		String description = sc.nextLine();

		String[] nd = { name, description};
		return nd;
	}

	private Alacarte CreateAlacarteFromUserInput() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the menuItem name: ");
		String name = sc.nextLine();

		System.out.println("Please enter the menuItem description: ");
		String description = sc.nextLine();

		System.out.println("Please enter the menuItem price: ");
		Integer price = Integer.parseInt(sc.nextLine());

		System.out.println("Please enter the menuItem quantity: ");
		Integer quantity = Integer.parseInt(sc.nextLine());

		Alacarte mi = new Alacarte(UUID.randomUUID().toString(),
				name,  description,
				price, quantity);

		return mi;
	}

	private MenuItem CreateMenuItemFromUserInput() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the menuItem name: ");
		String name = sc.nextLine();

		System.out.println("Please enter the menuItem description: ");
		String description = sc.nextLine();

		System.out.println("Please enter the menuItem price: ");
		Integer price = Integer.parseInt(sc.nextLine());

		System.out.println("Please enter the menuItem quantity: ");
		Integer quantity = Integer.parseInt(sc.nextLine());

		MenuItem mi = new MenuItem(UUID.randomUUID().toString(),
				name,  description,
				price, quantity);

		return mi;
	}


	private SetMeal CreateSetMealFromUserInput() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the menuItem name: ");
		String name = sc.nextLine();

		System.out.println("Please enter the menuItem description: ");
		String description = sc.nextLine();

		System.out.println("Please enter the menuItem price: ");
		Double price = Double.parseDouble(sc.nextLine());

		System.out.println("Please enter the menuItem quantity: ");
		Integer quantity = Integer.parseInt(sc.nextLine());

		// Using switch case here
		// resrict user to input only 3 times
		int i = 1;
		ArrayList<MenuItem> list_of_mi = new ArrayList<>();
		while(i<4){
			System.out.print(PrintColor.RED);
			System.out.println("This section is to create menu item in the set meal.");
			System.out.println("You are creating "+ ordinal(i) +" menu item in current set meal.");
			System.out.println("** Minimum sub menu item is 1. Maximum sub menu item is 3. **");
			System.out.println("Press # to exit or press any key to continue.");
			System.out.print(PrintColor.RESET);
			String c = sc.nextLine();
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
				MenuItem mi=CreateMenuItemFromUserInput();
				list_of_mi.add(mi);
			}
			i++;
		}

		System.out.println("How many menu item you wish to create in this SetMeal? Maximum is 3");

		SetMeal sm = new SetMeal(
				UUID.randomUUID().toString(),
				name,
				description,
				price,
				quantity,
				list_of_mi
		);
		return sm;
	}






	public Menu createSingleMenuWhenNoMenuExisted() {

		ArrayList<MenuItem> mi_arr = new ArrayList<>(
				Arrays.asList(new Alacarte(UUID.randomUUID().toString(), "Pork Belly Buns",
								"Thick slices of French toast bread, brown sugar, half-and-half and vanilla, " +
										"topped with powdered sugar. With two eggs served any style, " +
										"and your choice of smoked bacon or smoked ham.", 98.58, 1)
						, new Alacarte(UUID.randomUUID().toString(), "Kebab",
								"Two butter croissants of your choice (plain, almond or cheese). " +
										"With a side of herb butter or house-made hazelnut spread. "
								, 85.82, 1)
						, new Alacarte(UUID.randomUUID().toString(), "Katsu Curry",
								"Smoked salmon, poached eggs, diced red onions and Hollandaise sauce on an English muffin. With a side of roasted potatoes."
								, 36.95, 1)
						, new Alacarte(UUID.randomUUID().toString(), "Pork Sausage Roll",
								"Three eggs with cilantro, tomatoes, onions, avocados and melted Emmental cheese. With a side of roasted potatoes, and your choice of toast or croissant."
								, 80.51, 1)
						, new Alacarte(UUID.randomUUID().toString(), "Pizza",
								"28-day aged 300g USDA Certified Prime Ribeye, rosemary-thyme garlic butter, with choice of two sides."
								, 78.92, 1)
						, new Alacarte(UUID.randomUUID().toString(), "Vodka",
								"Vodka is traditionally made from potatoes or fermented cereal grains. Some brands also make it from other substances like fruit or sugar."+
										"\n One of the most used and loved alcoholic drink, vodka is either consumed neat or as cocktails like Martini, Bloody Mary and Cosmopolitan."
								, 37.39, 1)
						, new Alacarte(UUID.randomUUID().toString(), "7up Sugar Free Lemon & Lime Can 24 x 330ml",
								"7 up Sugar Free is a low calorie Lemon and Lime flavoured soft drink, available for delivery to homes, offices and workplaces directly from our warehouse in South London. "+
										"\n Clear, bright and colourless liquid with a lively carbonation. Delicate, enticing aroma of lemon and lime, fused and dusted with powdered sugar. "
								, 60.39, 1)
						, new Alacarte(UUID.randomUUID().toString(), "Som Tam",
								"Three eggs with cilantro, tomatoes, onions, avocados and melted Emmental cheese. With a side of roasted potatoes, and your choice of toast or croissant."
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


		mymenu.InsertSingleMenuItemOnSingleMenuCategroy("MAINCOURSE",
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

	public Menu formatDatabaseMapIntoMenu(Map data) {
		List<Map> data_cat = (List<Map>) data.get("_menuCategory");
		// Create the menu object here
		// first level: Menu Category
		// 8 times
		ArrayList<MenuCategory> new_menu_cat = new ArrayList<MenuCategory>();
		AtomicInteger i = new AtomicInteger(0);
		data_cat.forEach(
				(k) -> {

//                    System.out.println(k );
//                    System.out.println(i );

					// menu item
					List<Map> real_cat = (List<Map>) data_cat.get(i.intValue()).get("_menuItem");
					ArrayList<MenuItem> new_menu_item = new ArrayList<MenuItem>();
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
		Menu new_menu = new Menu(
				id,
				name,
				desp,
				new_menu_cat
		);

		return new_menu;
	}


	public void displayMenu() throws IOException {

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
			Database.writeToJsonFile(menu, filename.trim());
			System.out.println("Welcome to " + menu.get_name());
		}
		Map data = Database.loadFromJsonFile(filename.trim());
		Menu new_menu = formatDatabaseMapIntoMenu(data);
		// So other function can use it
		_menu = new_menu;

		// TODO: REMOVE after testing
//		JsonBuilder builder = new JsonBuilder(new_menu);
//		String json_str= builder.toString();
//		System.out.println(json_str);
	}


	// TODO: MOVE THIS TO CONSTRUCTOR OF MENU MANAGER
	public HashMap<Integer, FoodCategory> createHashMap() {
		int i =1;
		for (FoodCategory s : FoodCategory.values()){
			FoodCategoryMap.put(i, s);
			i++;
		}
		// System.out.println(FoodCategoryMap);
		return FoodCategoryMap;

	}

	public void printFoodCategoryHashMap() {
		// Iterating HashMap through for loop
		for (Map.Entry<Integer, FoodCategory> set :
				FoodCategoryMap.entrySet()) {
			System.out.println(set.getKey() + "  |  " + set.getValue());
		}
	}
	public void CreateMenuItem() throws IOException {
		// Asking which category you want to choose
		// Display the Hashmap of the category
		createHashMap();
		printFoodCategoryHashMap();
		Scanner sc = new Scanner(System.in);
		System.out.println("Please choose a category to create your menu item: " +
				"\n By typing in the category number: ");
		System.out.println();
		Integer category_number = Integer.parseInt(sc.nextLine());
		FoodCategory enum_cat =  FoodCategoryMap.get(category_number);

		MenuCategory mc = _menu.get_single_menu_categroy(enum_cat.toString());
		// Print the menu item in the category
		if(mc.get_menuItem().size()==0)
		{
			System.out.println("You have no menu item");
		}
		else{
			System.out.println("Here is your menu item in the Category : " + enum_cat.toString());
			mc.print();
		}

		// Ask the user for the type of menu item
		String type = GetTypeOfFoodItem();
		System.out.println(type);
		if(type.equals("alacarte") )
		{
			// Take in user input for menu item creation
			MenuItem mi = CreateAlacarteFromUserInput();
			Menu m = this.get_menu();
			m.InsertSingleMenuItemOnSingleMenuCategroy(enum_cat.toString(),mi);
			this.set_menu(m);
		}
		else if (type.equals("setmeal"))
		{
			// Take in user input for menu item creation
			MenuItem mi = CreateSetMealFromUserInput();
			Menu m = this.get_menu();
			m.InsertSingleMenuItemOnSingleMenuCategroy(enum_cat.toString(),mi);
			this.set_menu(m);
		}
		mc.print();
		//MenuCategory mc = _menu.get_single_menu_categroy(enum_cat.toString());
		confirmation(this.get_menu());

	}

	private void confirmation(Menu m) throws IOException {
		System.out.println("Please confirm that you want to save/delete/update this food item into your menu.");
		System.out.println("Type 1 to save. Type 0 to cancel this operation.");
		Scanner sc = new Scanner(System.in);
		System.out.println(" 1 | Yes ");
		System.out.println(" 0 | No ");
		Integer answer = Integer.parseInt(sc.nextLine());

		if(answer == 1){
			Database.writeToJsonFile(m ,filename.trim());
		}
		else{
			// nothing
		}
	}

	public String GetTypeOfFoodItem() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the type of Menu Item you want to create: ");
		System.out.println(" Alacarte ");
		System.out.println(" SetMeal ");
		String type = sc.nextLine();

		return type.toLowerCase(Locale.ROOT) ;
	}

//	public void RemoveFromMenu() {
//		this._menu.remove();
//	}

//	public void AddMenuCategory(FoodCategory fc , MenuItem mi) {
//		this._menu.get_menuCategory().insert_single_menu_item_into_seat_meal(mi);
//	}

	public void ViewMenu() {
		for(MenuCategory mc : this._menu.get_menuCategory()){
			mc.print();
		}
		// PrintMenu();
	}

//	public void RetrieveMenuItem() {
//		return this._menu;
//	}



	public void UpdateMenuItem() throws IOException {
		System.out.println("Please type in the menu item id that you wish to update.");
		Scanner sc = new Scanner(System.in);
		String id = sc.nextLine();

		MenuItem mi = CreateMenuItemFromUserInput();

		Menu m = this.get_menu();
		int res= m.Update(id, mi.get_name(), mi.get_description(), mi.get_price(), mi.get_quantity());
		if(res ==1 ) {
			this.set_menu(m);
			//PrintMenu();
			confirmation(this.get_menu());
		}
	}

	public void DeleteMenuItem() throws IOException {
		// Ask delete menu item
		System.out.println("Please type in the menu item id that you wish to delete.");
		Scanner sc = new Scanner(System.in);
		String id = sc.nextLine();
		Menu m = this.get_menu();
		int res= m.Delete(id);
		if(res ==1 ) {
			this.set_menu(m);
			//PrintMenu();
			confirmation(this.get_menu());
		}
	}

	public void PrintMenu() {
		// Iterating HashMap through for loop
		JsonBuilder builder = new JsonBuilder(this.get_menu());
		String json_str= builder.toString();
		String json_beauty = JsonOutput.prettyPrint(json_str);
		System.out.println(json_beauty);
	}

	/**
	 * Useful class
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
}
