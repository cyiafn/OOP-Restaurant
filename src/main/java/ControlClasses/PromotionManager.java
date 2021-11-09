package ControlClasses;

import EntityClasses.*;
import StaticClasses.Database;
import StaticClasses.InputHandler;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.UUID;

public class PromotionManager{
	List<String> setItems = new ArrayList<String>();
	private static PromotionManager instance = null;
	String filename = "Promotion.csv";
	ArrayList<Promotion> promotions = new ArrayList<>();
	List<Map<String , Double>> promotionList = new ArrayList<>();
	public static PromotionManager getInstance() throws IOException, CsvException {
		if (instance == null) {
			instance = new PromotionManager();
		}
		return instance;
	}
	public PromotionManager() throws IOException, CsvException {
		loadPromotion();
		loadSetMenuItems();
		checkDateAfter();
	}


	public void createPromotion() throws IOException {
		String name = InputHandler.getString("Please enter promotion name");
		String description = InputHandler.getString("Please enter promotion description");
		double price = InputHandler.getDouble(1,200,"Please enter promotion price :","Error input! Please recheck and retry again.");
		String startDate = InputHandler.stringDate("Please enter start date in the form of dd/MM/yyyy");
		String endDate  = InputHandler.stringDate("Please enter end date in the form of dd/MM/yyyy");
		String menuItemId = InputHandler.getString("Please type in the menu item id that you wish to input.");
		while(!checkSetMenu(menuItemId)){
			menuItemId = InputHandler.getString("Please try again, invalid set meal ID. Enter 0 to exit");
			if(menuItemId.equals("0")){
				System.out.println("=====exit======");
				break;
			}
		}
		MenuItem item = MenuManager.getInstance().findByIdForMenuItem(menuItemId);
			Promotion p = new Promotion(UUID.randomUUID().toString(),name,description,startDate,endDate,price,menuItemId);
			this.promotions.add(p);
			/**
			 * For ryan , This is what you need, i accept the Map<String, Double>, which is menu item id and promotion price
			 * you can pull from your promotion and cast to the type Map<String, Double> and pass to my menu manager observer
			 */
			System.out.println(checkDateValid(startDate,endDate));
			if(checkDateValid(startDate,endDate)) {
				Subject subject = new Subject();
				MenuManager.getInstance().updateMenuManagerForSubject(subject);
				Map<String, Double> m = new HashMap<>();
				m.put(menuItemId, price);
				promotionList.add(m);
				subject.setState(promotionList);
			}
			else{ //not valid date for the promotion just add to database. on day active. will autoset on launch
				//Subject subject = new Subject();
				//MenuManager.getInstance().updateMenuManagerForSubject(subject);
				//Map<String, Double> m = new HashMap<>();
				//m.put(menuItemId,0.00);
				//promotionList.add(m);
				//subject.setState(promotionList);
			}
			String[] test = p.getLineCSVFormat();
			Database.writeLine(filename,test);

	}
	public void checkDateAfter() throws IOException, CsvException {
		ArrayList<HashMap<String, String>> listPromotions = Database.readAll(filename);
		DateFormat inputFormatter = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		for (HashMap<String, String> i: listPromotions){

			String endDate = String.valueOf(i.get("endDate"));
			String startDate = String.valueOf(i.get("startDate"));
			try {
				Date testEndDate = inputFormatter.parse(endDate);
				Date testStartDate = inputFormatter.parse(startDate);
				String now = sdf.format(new Date());
				Date currentDate =  inputFormatter.parse(now);
				if (currentDate.after(testStartDate) && currentDate.before(testEndDate) || currentDate.equals(testStartDate)||currentDate.equals(endDate)){
					Subject subject = new Subject();
					MenuManager.getInstance().updateMenuManagerForSubject(subject);
					String menuItemId = String.valueOf(i.get("menuItemID"));
					double price = Double.valueOf(i.get("price"));
					Map<String, Double> m = new HashMap<>();
					m.put(menuItemId, price);
					promotionList.add(m);
					subject.setState(promotionList);
				}
				//else if(currentDate.after(testEndDate)){
				else{ //no valid promotion for that day
					Subject subject = new Subject();
					MenuManager.getInstance().updateMenuManagerForSubject(subject);
					String menuItemId = String.valueOf(i.get("menuItemID"));
					Map<String, Double> m = new HashMap<>();
					m.put(menuItemId, 0.00);
					promotionList.add(m);
					subject.setState(promotionList);
					promotionList.remove(m);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}


		}

	}
	public boolean checkDateValid(String startDate,String endDate){
		DateFormat inputFormatter = new SimpleDateFormat("dd/M/yyyy");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
		try {

			Date testStartDate = inputFormatter.parse(startDate);
			Date testEndDate = inputFormatter.parse(endDate);
			String now = sdf.format(new Date());
			Date currentDate =  inputFormatter.parse(now);
			if(currentDate.equals(testEndDate)||currentDate.equals(testStartDate)||currentDate.after(testStartDate) && currentDate.before(testEndDate)){
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
return false;
	}
	public boolean checkSetMenu(String id){
		for (String result : setItems) {
			if (id.equals(result)) {
				System.out.println("==========Your setMenu is found=====");
				return true;
			}
		}
		return false;
	}
	public void updatePromotion() throws IOException, CsvException {
		String id = InputHandler.getString("Please enter the ID of the promotion you would like to edit ");
		for( Promotion resp:this.promotions){
			String promoID = resp.getPromotionID();
			if(promoID.equals(id)){
				System.out.println("==========ID found!=======");
				int choice;
				String startDate;
				String endDate;
				System.out.println("(1)Change Promotion ID	\t(2) Change Name");
				System.out.println("(3)Change Description ID\t(4) Change Duration");
				System.out.println("(5)Change Price			\t(6) Change MenuID");
				System.out.println("========================");
				choice = InputHandler.getInt(1,6,"Please enter your choice","Invalid choice please try again");
				switch(choice){
					case 1:
						String newID = InputHandler.getString("Enter the new promotion ID");
						resp.setPromotionID(newID);
						Database.updateLine(filename,resp.getPromotionID(),resp.getLineCSVFormat());
						break;
					case 2:
						String newName = InputHandler.getString("Enter the new promotion name");
						resp.setPromotionName(newName);
						Database.updateLine(filename,resp.getPromotionID(),resp.getLineCSVFormat());
						break;
					case 3:
						String newDes = InputHandler.getString("Please enter new promotion description");
						resp.setDescription(newDes);
						Database.updateLine(filename,resp.getPromotionID(),resp.getLineCSVFormat());
						break;
					case 4:
						int choice2 = InputHandler.getInt(1,2,"(1) To update start date\t (2) To change end date","Invalid choice");
						switch(choice2){
							case 1:
								String newStartDate = InputHandler.stringDate("Please enter new start date in the form of dd/M/yyyy");
								endDate = resp.getEndDate();
								resp.setStartDate(newStartDate);
								if(!checkDateValid(newStartDate,endDate)){
									Database.updateLine(filename,resp.getPromotionID(),resp.getLineCSVFormat());
								}
								else{//valid
									Subject subject = new Subject();
									MenuManager.getInstance().updateMenuManagerForSubject(subject);
									String menuItemId = resp.getMenuItemID();
									double price = resp.getPromotionPrice();
									Map<String, Double> m = new HashMap<>();
									m.put(menuItemId, price);
									promotionList.add(m);
									subject.setState(promotionList);
									Database.updateLine(filename,resp.getPromotionID(),resp.getLineCSVFormat());
								}
								break;
							case 2:
								String newEndDate = InputHandler.stringDate("Please enter new end date in the form of dd/M/yyyy");
								startDate = resp.getStartDate();
								resp.setEndDate(newEndDate);
								if(!checkDateValid(startDate,newEndDate)){
									Database.updateLine(filename,resp.getPromotionID(),resp.getLineCSVFormat());
								}
								else{
									Subject subject = new Subject();
									MenuManager.getInstance().updateMenuManagerForSubject(subject);
									String menuItemId = resp.getMenuItemID();
									double price = resp.getPromotionPrice();
									Map<String, Double> m = new HashMap<>();
									m.put(menuItemId, price);
									promotionList.add(m);
									subject.setState(promotionList);
									Database.updateLine(filename,resp.getPromotionID(),resp.getLineCSVFormat());
								}
								;break;
						}
						break;
					case 5:
						Double newPrice = InputHandler.getDouble(1,200,"Please enter new promotion price","Err invalid input");
						resp.setPromotionPrice(newPrice);
						startDate = resp.getStartDate();
						endDate = resp.getEndDate();
						if(!checkDateValid(startDate,endDate)){
							Database.updateLine(filename,resp.getPromotionID(),resp.getLineCSVFormat());
						}
						else{
							Subject subject = new Subject();
							MenuManager.getInstance().updateMenuManagerForSubject(subject);
							String menuItemId = resp.getMenuItemID();
							double price = resp.getPromotionPrice();
							Map<String, Double> m = new HashMap<>();
							m.put(menuItemId, price);
							promotionList.add(m);
							subject.setState(promotionList);
							Database.updateLine(filename,resp.getPromotionID(),resp.getLineCSVFormat());
						}

						break;
					case 6:
						String newMenuID = InputHandler.getString("Please enter a new promotion MenuID");
						resp.setMenuItemID(newMenuID);
						startDate = resp.getStartDate();
						endDate= resp.getEndDate();
						if(!checkDateValid(startDate,endDate)){
							Database.updateLine(filename,resp.getPromotionID(),resp.getLineCSVFormat());
						}
						else{
							Subject subject = new Subject();
							MenuManager.getInstance().updateMenuManagerForSubject(subject);
							String menuItemId = resp.getMenuItemID();
							double price = resp.getPromotionPrice();
							Map<String, Double> m = new HashMap<>();
							m.put(menuItemId, price);
							promotionList.add(m);
							subject.setState(promotionList);
							Database.updateLine(filename,resp.getPromotionID(),resp.getLineCSVFormat());
						}
						break;
					default:
						System.out.println("You have entered an invalid choice");
						break;
				}
			}
		}
	}

	public void loadPromotion() throws IOException, CsvException {
		ArrayList<HashMap<String, String>> listPromotions = Database.readAll(filename);
		this.promotions = new ArrayList<>();
		for (HashMap<String, String> i: listPromotions){
			this.promotions.add(new Promotion(String.valueOf(i.get("promotionID")),String.valueOf(i.get("name")), String.valueOf(i.get("description")),String.valueOf(i.get("startDate")),String.valueOf(i.get("endDate")),Double.valueOf(i.get("price")),String.valueOf(i.get("menuItemID"))));
		}
	}
	public void viewPromotion() throws IOException, CsvException {
		for(Promotion response : this.promotions){
			System.out.println("====================");
			System.out.println("Promotion ID: "+ response.getPromotionID());
			System.out.println("Promotion Name: "+ response.getPromotionName());
			System.out.println("Promotion Description: "+ response.getDescription());
			System.out.println("Promotion Price: "+ response.getPromotionPrice());
			System.out.println("Promotion Duration " +response.getStartDate() + " - "+response.getEndDate());
			System.out.println("Promotion Item ID: " + response.getMenuItemID());
		}
	}

	public void deletePromotion() throws CsvException, IOException {
		String dltID = InputHandler.getString("Please enter the ID of the promotion you would like to delete");
		for(Promotion resp : this.promotions) {
			if(resp.getPromotionID().equals(dltID)) {
				int confirm = InputHandler.getInt(0, 1, "(1) Confirm Deletion\t (0) Back", "invalid choice");
				if (confirm == 1) {
					String startDate=resp.getStartDate();
					String endDate=resp.getEndDate();
					if(!checkDateValid(startDate,endDate)){
						Database.removeLine(filename, dltID);
						loadPromotion();
						System.out.println("The promotion has been successfully deleted");
					}else{
						Subject subject = new Subject();
						MenuManager.getInstance().updateMenuManagerForSubject(subject);
						String menuItemId = resp.getMenuItemID();
						double price = resp.getPromotionPrice();
						Map<String, Double> m = new HashMap<>();
						m.put(menuItemId, 0.00);
						promotionList.add(m);
						subject.setState(promotionList);
						promotionList.remove(m);
						Database.removeLine(filename, dltID);
						loadPromotion();


					}

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
				String test =data_cat2.get(j.intValue()).get("type").toString();
				if(test.equals("setmeal")){
					String menuItemID = data_cat2.get(j.intValue()).get("menuItemID").toString();
					System.out.println(menuItemID);
					listOfSetItems.add(menuItemID);
				}
				j.getAndIncrement();
			});
			i.getAndIncrement();
		});
		this.setItems = listOfSetItems;
	}
}