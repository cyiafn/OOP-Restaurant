package ControlClasses;

import EntityClasses.*;
import Enumerations.FoodCategory;
import StaticClasses.Database;
import StaticClasses.InputHandler;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.UUID;

public class PromotionManager{
	String name;
	private Object _attribute;
	List<String> setItems = new ArrayList<String>();
	private static PromotionManager instance = null;
	String filename = "Promotion.csv";
	ArrayList<Promotion> promotions = new ArrayList<>();
	List<Map<String , Double>> promotionList = new ArrayList<>();
	Promotion p = new Promotion(); // Current promotion
	public static PromotionManager getInstance() throws IOException, CsvException {
		if (instance == null) {
			instance = new PromotionManager();
		}
		return instance;
	}
	public PromotionManager(ArrayList<Promotion> promotionList){

	}
	public PromotionManager() throws IOException, CsvException {
		loadPromotion();
		loadSetMenuItems();
		//this.setItems.forEach(response ->{
		//	System.out.println(response);
		//});
	}


	public void createPromotion() throws IOException {
		//this._promotions = (ArrayList<Promotion>) Database.read(filename);
		Scanner sc = new Scanner (System.in);
		String name = InputHandler.getString("Please enter promotion name");
		String description = InputHandler.getString("Please enter promotion description");
		double price = InputHandler.getDouble(1,200,"Please enter promotion price :","Error input! Please recheck and retry again.");
		String duration = InputHandler.getString("Please enter duration of the promotion");
		//MenuItem test = new ArrayList<>();
		String menuItemId = InputHandler.getString("Please type in the menu item id that you wish to input.");
		MenuItem item = MenuManager.getInstance().findByIdForMenuItem(menuItemId);
		if(item!=null) {
			this.p = new Promotion(UUID.randomUUID().toString(),name,description,duration,price,menuItemId);
			this.promotions.add(p);
			// MenuItem test5 = new MenuItem(p,p.getMenuItemID());
			/**
			 * For ryan , This is what you need, i accept the Map<String, Double>, which is menu item id and promotion price
			 * you can pull from your promotion and cast to the type Map<String, Double> and pass to my menu manager observer
			 */
			Subject subject = new Subject();
			MenuManager.getInstance().updateMenuManagerForSubject(subject);
			Map<String, Double> m = new HashMap<>();
			m.put(menuItemId, price);
			promotionList.add(m);
			subject.setState(promotionList);
			/**
			 * no need do the below
			 */
			//p.notifyAllObservers();
			//p.setState(1);
			//MenuItem test2 = new MenuItem(p.setState(2));
			String[] test = {p.getPromotionID(), p.getPromotionName(),p.getDescription(),p.getDuration(),String.valueOf(p.getPromotionPrice()),p.getMenuItemID()};
			//System.out.println("hi");
			Database.writeLine(filename,test);
		}


		/*promotionList.forEach((resp)->{
			resp.getListOfMenuItem().forEach((tes)->{
				System.out.println(tes.get_name());
			});
		});*/

	}
	public void updatePromotion() throws IOException, CsvException {
		//loadPromotion();
		Scanner intScan = new Scanner(System.in);
		Scanner strScan = new Scanner(System.in);
		String id = InputHandler.getString("Please enter the ID of the promotion you would like to edit ");
		//System.out.println(id);
		for( Promotion resp:this.promotions){
			String promoID = resp.getPromotionID();
			//System.out.println(promoID);
			//System.out.println(id);
			if(promoID.equals(id)){
				System.out.println("==========ID found!=======");
				int choice;
				System.out.println("(1)Change Promotion ID	\t(2) Change Name");
				System.out.println("(3)Change Description ID\t(4) Change Duration");
				System.out.println("(5)Change Price			\t(2) Change MenuID");
				System.out.println("========================");
				choice = intScan.nextInt();
				switch(choice){
					case 1:
						//System.out.println("Enter the new promotion ID");
						String newID = InputHandler.getString("Enter the new promotion ID");
						resp.setPromotionID(newID);
						Database.updateLine(filename,resp.getPromotionID(),resp.getLineCSVFormat());
						break;
					case 2:
						//System.out.println("Enter the new promotion name");
						String newName = InputHandler.getString("Enter the new promotion name");
						resp.setPromotionName(newName);
						Database.updateLine(filename,resp.getPromotionID(),resp.getLineCSVFormat());
						break;
					case 3:
						//System.out.println("Please enter new promotion description");
						String newDes = InputHandler.getString("Please enter new promotion description");
						resp.set_description(newDes);
						Database.updateLine(filename,resp.getPromotionID(),resp.getLineCSVFormat());
						break;
					case 4:
						//System.out.println("Please enter new promotion duration");
						String newDur = InputHandler.getString("Please enter new promotion duration");
						resp.setDuration(newDur);
						Database.updateLine(filename,resp.getPromotionID(),resp.getLineCSVFormat());
						break;
					case 5:
						//System.out.println("Please enter new promotion price");
						Double newPrice = InputHandler.getDouble(1,200,"Please enter new promotion price","Err invalid input");
						resp.setPromotionPrice(newPrice);
						Database.updateLine(filename,resp.getPromotionID(),resp.getLineCSVFormat());
						break;
					case 6:
						//System.out.println("Please enter a new promotion MenuID");
						String newMenuID = InputHandler.getString("Please enter a new promotion MenuID");
						resp.setMenuItemID(newMenuID);
						Database.updateLine(filename,resp.getPromotionID(),resp.getLineCSVFormat());
						break;
					default:
						System.out.println("You have entered an invalid choice");
						break;
				}

				//String[] line = {"322e6170-8f6a-4482-a333-ba5994147d51","Family day","Bundle for family","6/11/2021 - 7/11/2021","150.0","9f2b9bc0-3bfe-4137-b751-44be577a2478"};

			}
		}
	}
	public Promotion RetrievePromotionItem() {
		throw new UnsupportedOperationException();
	}
	public void addPromotion(Promotion p){
		this.promotions.add(p);
	}

	public void loadPromotion() throws IOException, CsvException {
		ArrayList<HashMap<String, String>> listPromotions = Database.readAll(filename);
		//add tables to ID and creates arraylist for each table.
		promotions = new ArrayList<>();
		for (HashMap<String, String> i: listPromotions){
			//System.out.println("hi");
			//System.out.println(String.valueOf(i.get("duration")));
//Promotion p  = new Promotion(String.valueOf(i.get("promotionID")),String.valueOf(i.get("name")), String.valueOf(i.get("description")),String.valueOf(i.get("duration")),Double.parseDouble(i.get("price")),String.valueOf(i.get("menuItemID")));
//_promotions.add(p);
			promotions.add(new Promotion(String.valueOf(i.get("promotionID")),String.valueOf(i.get("name")), String.valueOf(i.get("description")),String.valueOf(i.get("duration")),Double.parseDouble(i.get("price")),String.valueOf(i.get("menuItemID"))));
		}
	}
	public void ViewPromotion() throws IOException, CsvException {
		//loadPromotion();
		for(Promotion response : this.promotions){
			System.out.println("====================");
			System.out.println("Promotion ID: "+ response.getPromotionID());
			System.out.println("Promotion Name: "+ response.getPromotionName());
			System.out.println("Promotion Description: "+ response.getDescription());
			System.out.println("Promotion Price: "+ response.getPromotionPrice());
			System.out.println("Promotion Duration: "+ response.getDuration());
			System.out.println("Promotion Item ID: " + response.getMenuItemID());
			//MenuItem test = MenuManager.getInstance().findByIdForMenuItem(response.getMenuItemID());
			//System.out.println("Promotion Item: "+test.getMenuItemID());
			//System.out.println(test.get_name());
			//System.out.println(test.get_description());
			//System.out.println(test.get_price());
			//System.out.println(test.get_quantity());

		}
		// form the menu here


	}

	public void deletePromotion() throws CsvException, IOException {
		String dltID = InputHandler.getString("Please enter the ID of the promotion you would like to delete");
		for(Promotion resp : this.promotions) {
			if(resp.getPromotionID().equals(dltID)) {
				int confirm = InputHandler.getInt(0, 1, "(1) Confirm Deletion\t (0) Back", "invalid choice");
				if (confirm == 1) {
					Database.removeLine(filename, dltID);
					loadPromotion();
					System.out.println("The promotion has been successfully deleted");
					return;
				} else {
					return;
				}
			}
		}
		System.out.println("ID of the promotion was not found");
	}
	public void loadSetMenuItems() throws IOException {
		List<String> listOfSetItems = new ArrayList<String>();
		Map data = Database.LoadFromJsonFile("csv/menu.json");
		List<Map> data_cat = (List<Map>) data.get("menuCategory");
		AtomicInteger i = new AtomicInteger();
		data_cat.forEach(res ->{
			List<Map> data_cat2 = (List<Map>) data_cat.get(i.intValue()).get("menuItem");
			AtomicInteger j = new AtomicInteger();
			data_cat2.forEach(response ->{ //menuItem
				//System.out.println(response);
				String test =data_cat2.get(j.intValue()).get("type").toString();
				AtomicInteger k = new AtomicInteger();
				if(test.equals("setmeal")){
					List<Map> data_cat3 = (List<Map>) data_cat2.get(j.intValue()).get("setOfItem");
					data_cat3.forEach(resp->{
						String menuItemID =  data_cat3.get(k.intValue()).get("menuItemID").toString();
						//System.out.println(menuItemID);
						listOfSetItems.add(menuItemID);
						k.getAndIncrement();
					});

				}


				//System.out.println(data_cat2.get(j.intValue()).get("type"));
				j.getAndIncrement();
			});
			i.getAndIncrement();
		});
		this.setItems = listOfSetItems;
	}
		/*promotionList.forEach((resp)->{
			System.out.println("==========================");
			System.out.println("Promotion menu ID: "+resp.getPromotionMenuID());
			System.out.println("Promotion name: "+ resp.getPromotionName());
			System.out.println("Promotion description: "+resp.getDescription());
			System.out.println("Promotion duration: " + resp.getDuration());
			System.out.println("Promotion Price: "+resp.getPromotionPrice());
			resp.getListOfMenuItem().forEach((tes)->{
				System.out.println("==========================");
				System.out.println("Promotion item ID: "+tes.get_menuItemID());
				System.out.println("Promotion item name: "+ tes.get_name());
				System.out.println("Promotion item description: "+tes.get_description());



			});
		});*/

	/**
	 * This function is responsible for format the database output into the Menu Class object
	 * Usage:: Menu new_menu = formatDatabaseMapIntoMenu(data);
	 * This function is cooperate with the `LoadFromJsonFile` in `Database.java`
	 * If you want to use JSON file as well, this function you need to rewrite yourself to suit your class
	 * @param data, is a map
	 * @return a Menu
	 */
	/*@SuppressWarnings("unchecked")
	public ArrayList<Promotion> formatDatabaseMapIntoMenu(Map data) {
		List<Map> data_cat = (List<Map>) data.get("_promotions");
		// Create the menu object here
		// first level: Menu Category
		// 8 times
		ArrayList<Promotion> new_menu_cat = new ArrayList<>();
		AtomicInteger i = new AtomicInteger(0);
		data_cat.forEach(
				(k) -> {

//                    System.out.println(k);
//                    System.out.println(i );

					// menu item
					List<Map> real_cat = (List<Map>) data_cat.get(i.intValue()).get("listOfMenuItem");
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
					String id = data_cat.get(i.intValue()).get("promotionMenuID").toString();
					String des = data_cat.get(i.intValue()).get("description").toString();
					String name =  data_cat.get(i.intValue()).get("promotionName").toString();
					double price = Double.parseDouble(data_cat.get(i.intValue()).get("promotionPrice").toString());
					String duration = data_cat.get(i.intValue()).get("duration").toString();
					Promotion p = new Promotion(id,name,des,duration,price,new_menu_item);
					new_menu_cat.add(p);
					i.getAndIncrement();
				}
		);
		ArrayList<Promotion> _promotions = new_menu_cat;
		// form the menu here
		//String id = data.get("_promotions").toString();

		return _promotions;
	}*/
	/*public void ViewPromotion() throws IOException {

		Map data = Database.LoadFromJsonFile(filename.trim());
		List<Map> data_cat = (List<Map>) data.get("_Promotions");
		AtomicInteger m = new AtomicInteger(0);
		data_cat.forEach( //for each promotion
				(response) ->{
					System.out.println("============================");
					String name = data_cat.get(m.intValue()).get("_Promo").toString();
					String duration = data_cat.get(m.intValue()).get("_duration").toString();
					double price = Double.parseDouble(data_cat.get(m.intValue()).get("_promotionalPrice").toString());
					System.out.println("Promotion name is " + name);
					System.out.println("Duration of this promotion is from "+duration);
					System.out.println("Promotion price is $" + price );
					AtomicInteger o = new AtomicInteger(0);
					List<Map> data_cat_2 =(List<Map>) data_cat.get(m.intValue()).get("_PromoItem");
					data_cat_2.forEach(
							(resp) ->{
								String individualName = data_cat_2.get(o.intValue()).get("_name").toString();
								String description = data_cat_2.get(o.intValue()).get("_description").toString();
								int quantity =Integer.parseInt(data_cat_2.get(o.intValue()).get("_quantity").toString());
								String type = data_cat_2.get(o.intValue()).get("_type").toString();
								System.out.println("======================");
								System.out.println("Promotion Item Name is " + individualName);
								System.out.println("Description : "+ description);
								System.out.println("Quantity : "+ quantity);
								System.out.println("Type : " + type);
								if(data_cat_2.get(o.intValue()).get("_type").toString().equals("setmeal")){
									AtomicInteger j = new AtomicInteger(0);
									List<Map> sub_setmeal = (List<Map>) data_cat_2.get(o.intValue()).get("_set_of_item");
									if(!sub_setmeal.isEmpty()){
										sub_setmeal.forEach(
												(res)->{
													System.out.println("=========================");
													int sub_quantity = Integer.parseInt(sub_setmeal.get(j.intValue()).get("_quantity").toString()) ;
													String sub_des = sub_setmeal.get(j.intValue()).get("_description").toString();
													String sub_name =   sub_setmeal.get(j.intValue()).get("_name").toString();
													//String type2 = sub_setmeal.get(j.intValue()).get("_type").toString();
													System.out.println("Set meal item Name is " + sub_name);
													System.out.println("Description : " + sub_des);
													System.out.println("Quantity : "+sub_quantity);
													//System.out.println("Type : " + type2);
													j.getAndIncrement();
												}
										);

									}

								}
								o.getAndIncrement();

							}


					);
					m.getAndIncrement();
				}
		);
	}*/


}
