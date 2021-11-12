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

/**
 * PromotionManager is a controller
 * Which handle the lifecycle of the Promotion Entity
 * @author Chia Songcheng
 * @version 1.0
 * @since 2021-11-07
 */

public class PromotionManager{
	/**
	 * List of setItems allowed
	 */
	List<String> setItems = new ArrayList<String>();
	private static PromotionManager instance = null;
	/**
	 * Constant for file name reservation.
	 */
	String filename = "Promotion.csv";
	/**
	 * ArrayList of promotions to keep track of current promotions
	 */

	ArrayList<Promotion> promotions = new ArrayList<>();

	/**
	 * For observer pattern design to keep track of subjects
	 * @param promotionList
	 */
	List<Map<String , Double>> promotionList = new ArrayList<>();

	/**
	 * Static method
	 * Promotion Manager check exist only one at a time
	 *
	 * @return PromotionManager
	 */

	public static PromotionManager getInstance() throws IOException, CsvException {
		if (instance == null) {
			instance = new PromotionManager();
		}
		return instance;
	}

	/**
	 * load Promotion and set menu data from csv and json into PromotionManager
	 */

	public PromotionManager() throws IOException, CsvException {
		loadPromotion();
		loadSetMenuItems();
		updateCurrentPromotions();
	}

	/**
	 * Create Promotion from user input
	 */
	public void createPromotion() throws IOException {
		String name = InputHandler.getString("Please enter promotion name");
		while(checkDupeName(name)){
			name = InputHandler.getString("There is already an entry with the same name.Please enter another promotion name");
		}
		String description = InputHandler.getString("Please enter promotion description");
		double price = InputHandler.getDouble(1,100,"Please enter promotion price :","Error input! Please recheck and retry again.");
		String startDate = InputHandler.stringDate("Please enter start date in the form of dd/MM/yyyy");
		String endDate  = InputHandler.stringDate("Please enter end date in the form of dd/MM/yyyy");
		String menuItemId = InputHandler.getString("Please type in the menu item id that you wish to input.");
		while(!checkSetMenu(menuItemId)){
			menuItemId = InputHandler.getString("Please try again, invalid set meal ID. Enter 0 to exit");
			if(menuItemId.equals("0")){
				System.out.println("=====exit======");
				return;
			}
		}
			Promotion p = new Promotion(UUID.randomUUID().toString(),name,description,startDate,endDate,price,menuItemId);
			this.promotions.add(p);
			//System.out.println(checkDateValid(startDate,endDate));
			if(checkDateValid(startDate,endDate)) {
				Subject subject = new Subject();
				MenuManager.getInstance().updateMenuManagerForSubject(subject);
				Map<String, Double> m = new HashMap<>();
				m.put(menuItemId, price);
				promotionList.add(m);
				subject.setState(promotionList);
			}
			else{
			}
			String[] test = p.getLineCSVFormat();
			Database.writeLine(filename,test);

	}
	/**
	 * Ensures the user does not enter the same promotion entry twice
	 * @return boolean whether there is a dupe or not
	 */
	public boolean checkDupeName(String name){
		for(Promotion promo: this.promotions){
			if(name.equals(promo.getPromotionName())){
				return true;
			}
		}
		return false;

		/**
		 * This function loads promotions from Promotion.csv and update promotions on the day and set those expired promotions to 0.
		 */
	}
	public void updateCurrentPromotions() throws IOException, CsvException {
		promotionList = new ArrayList<>();
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
		/**
		 * This function checks whether the promotion date is valid
		 * returns boolean whether the promotion is valid or not
		 */
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
	/**
	 * Ensures Menu item ID entered is correct
	 * @return boolean whether the setMenu is found
	 */
	public boolean checkSetMenu(String id){
		for (String result : setItems) {
			if (id.equals(result)) {
				System.out.println("==========Your setMenu is found=====");
				return true;
			}
		}
		return false;
	}

	/**
	 * Update Promotion
	 * @throws CsvException, this function write to csv text file
	 * @throws IOException, this function write to json text file
	 */
	public void updatePromotion() throws IOException, CsvException {
		updateCurrentPromotions();
		loadPromotion();
		loadSetMenuItems();
		String name = InputHandler.getString("Please enter the name of the promotion you would like to edit ");
		for( Promotion resp:this.promotions){
			String promoName = resp.getPromotionName();
			if(promoName.equals(name)){
				System.out.println("==========ID found!=======");
				int choice;
				String startDate;
				String endDate;
				System.out.println("(1) Change Name		\t 	(2)Change Description");
				System.out.println("(3) Change Duration	\t	(4)Change Price");
				System.out.println("(5) Change MenuID	\t	");
				System.out.println("========================");
				choice = InputHandler.getInt(1,6,"Please enter your choice","Invalid choice please try again");
				switch(choice){
					case 1:
						String newName = InputHandler.getString("Enter the new promotion name");
						while(checkDupeName(newName)){
							newName = InputHandler.getString("Entered name has already been used, Please enter another name or press 0 to exit");
							if(newName.equals("0")){
								return;
							}
						}
						resp.setPromotionName(newName);
						Database.updateLine(filename,resp.getPromotionID(),resp.getLineCSVFormat());
						break;
					case 2:
						String newDes = InputHandler.getString("Please enter new promotion description");
						resp.setDescription(newDes);
						Database.updateLine(filename,resp.getPromotionID(),resp.getLineCSVFormat());
						break;
					case 3:
						int choice2 = InputHandler.getInt(1,2,"(1) To update start date\t (2) To change end date","Invalid choice");
						switch(choice2){
							case 1:
								String newStartDate = InputHandler.stringDate("Please enter new start date in the form of dd/M/yyyy");
								endDate = resp.getEndDate();
								int counter =2;
								while(checkStartDate(newStartDate,endDate)){
									if(counter==0){
										return;
									}
									newStartDate = InputHandler.stringDate("Start date is after end date.Please enter new start date in the form of dd/M/yyyy."+counter +" tries left");
									if(newStartDate.equals("0")){
										return;
									}
									counter--;
								}
								resp.setStartDate(newStartDate);
								System.out.println("Start date successfully updated");
								if(!checkDateValid(newStartDate,endDate)){
									Subject subject = new Subject();
									MenuManager.getInstance().updateMenuManagerForSubject(subject);
									String menuItemId = resp.getMenuItemID();
									Map<String, Double> m = new HashMap<>();
									m.put(menuItemId,0.00);
									promotionList.add(m);
									subject.setState(promotionList);
									promotionList.remove(m);
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
								int count =2;
								while(checkEndDate(startDate,newEndDate)){
									if(count==0){
										return;
									}
									newEndDate = InputHandler.stringDate("End date is before start date.Please enter new start date in the form of dd/M/yyyy."+count +" tries left");
									count--;
								}
								resp.setEndDate(newEndDate);
								if(!checkDateValid(startDate,newEndDate)){
									Subject subject = new Subject();
									MenuManager.getInstance().updateMenuManagerForSubject(subject);
									String menuItemId = resp.getMenuItemID();
									//double price = resp.getPromotionPrice();
									Map<String, Double> m = new HashMap<>();
									m.put(menuItemId,0.00);
									promotionList.add(m);
									subject.setState(promotionList);
									promotionList.remove(m);
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
					case 4:
						Double newPrice = InputHandler.getDouble(1,100,"Please enter new promotion price","Err invalid input");
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
					case 5:
						String newMenuID = InputHandler.getString("Please enter a new promotion MenuID");
						String oldMenuID = resp.getMenuItemID();
						int count = 2;
						while(!checkSetMenu(newMenuID)){
							if(count==0){
								return;
							}
							newMenuID = InputHandler.getString("Please enter a new promotion MenuID , invalid MenuID entered" + count +" tries left");
							count--;
						}
						resp.setMenuItemID(newMenuID);
						startDate = resp.getStartDate();
						endDate= resp.getEndDate();
						if(!checkDateValid(startDate,endDate)){
							Subject subject = new Subject();
							MenuManager.getInstance().updateMenuManagerForSubject(subject);
							Map<String, Double> m = new HashMap<>();
							m.put(oldMenuID, 0.00);
							promotionList.add(m);
							subject.setState(promotionList);
							promotionList.remove(m);
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

	/**
	 * Check starting date to ensure end date cannot be after end date
	 * @return boolean whether the start date is valid
	 */
	public boolean checkStartDate(String startDate, String endDate){
		DateFormat inputFormatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date testEndDate = inputFormatter.parse(endDate);
			Date testStartDate = inputFormatter.parse(startDate);
			if(testStartDate.after(testEndDate)){
				return true;
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * Check End date to ensure end date cannot be before start date
	 * return boolean whether the end date is valid
	 */
	public boolean checkEndDate(String startDate, String endDate){
		DateFormat inputFormatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date testEndDate = inputFormatter.parse(endDate);
			Date testStartDate = inputFormatter.parse(startDate);
			if(testEndDate.before(testStartDate)){
				return true;
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Loads promotion from csv file
	 * @throws CsvException, cause this function write to csv text file
	 * @throws IOException
	 */
	public void loadPromotion() throws IOException, CsvException {
		ArrayList<HashMap<String, String>> listPromotions = Database.readAll(filename);
		this.promotions = new ArrayList<>();
		for (HashMap<String, String> i: listPromotions){
			this.promotions.add(new Promotion(String.valueOf(i.get("promotionID")),String.valueOf(i.get("name")), String.valueOf(i.get("description")),String.valueOf(i.get("startDate")),String.valueOf(i.get("endDate")),Double.valueOf(i.get("price")),String.valueOf(i.get("menuItemID"))));
		}
	}
	/**
	 * Helper function to view Promotion
	 */
	public void viewPromotion(){
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

	/**
	 * Helper function to get all promotions
	 */
	public ArrayList<Promotion> allPromotion(){
		return this.promotions;
	}
	/**
	 * Delete Promotion
	 * @throws IOException
	 * @throws CsvException
	 */
	public void deletePromotion() throws CsvException, IOException {
		String name = InputHandler.getString("Please enter the name of the promotion you would like to delete");
		for(Promotion resp : this.promotions) {
			if(resp.getPromotionName().equals(name)) {
				int confirm = InputHandler.getInt(0, 1, "(1) Confirm Deletion\t (0) Back", "invalid choice");
				if (confirm == 1) {
					String startDate=resp.getStartDate();
					String endDate=resp.getEndDate();
					if(!checkDateValid(startDate,endDate)){
						Database.removeLine(filename, resp.getPromotionID());
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
						Database.removeLine(filename, resp.getPromotionID());
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

	/**
	 * loads set menu item from csv file
	 * @throws IOException
	 */
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
					listOfSetItems.add(menuItemID);
				}
				j.getAndIncrement();
			});
			i.getAndIncrement();
		});
		this.setItems = listOfSetItems;
	}
}