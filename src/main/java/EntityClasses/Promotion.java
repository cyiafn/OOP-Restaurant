package EntityClasses;

import java.time.format.DateTimeFormatter;

/**
 * Promotion entity class
 * @author Chia Songcheng
 * @version 1.0
 * @since 2021-11-07
 */

public class Promotion extends Subject{
	/*
	Attributes of Promotion Item
	 */

	/**
	 * The id of a Promotion
	 */
	private String promotionID;

	/**
	 * The price of a Promotion
	 */
	private double promotionPrice;

	/**
	 * The description of a Promotion
	 */
	private String promotionDescription;

	/**
	 * The menu item id included in a Promotion
	 */
	private String menuItemID;

	/**
	 * The name of a Promotion
	 */
	private String promotionName;
	/**
	 * The start date of a Promotion
	 */
	private String promotionStartDate;

	/**
	 * The end date of a Promotion
	 */
	private String promotionEndDate;

	/**
	 * Constructor of Promotion
	 * @param id Promotion ID
	 * @param name promotion name
	 * @param description description of promotion
	 * @param price price of promotion
	 * @param startDate start date of promotion
	 * @param endDate end date of promotion
	 * @param menuItemID menu item included in promotion
	 */
	public Promotion(String id,String name,String description,String startDate,String endDate, double price , String menuItemID){
		this.promotionID = id;
		this.promotionDescription = description;
		this.promotionStartDate = startDate;
		this.promotionEndDate = endDate;
		this.promotionPrice = price;
		this.menuItemID = menuItemID;
		this.promotionName = name;


	}
	/**
	 * Empty Constructor for Promotion
	 */

	/**
	 * PromotionName getter
	 * @return promotionName
	 */
	public String getPromotionName(){
		return this.promotionName;
	}
	/**
	 * PromotionName setter
	 * @param name name of promotion
	 */
	public void setPromotionName(String name){
		this.promotionName = name;
	}
	/**
	 * MenuItemID setter
	 * @param menuItemId  menu item id  included in promotion
	 */
	public void setMenuItemID(String menuItemId){
		this.menuItemID = menuItemId;
	}
	/**
	 * MenuItemID getter
	 * @return MenuItemID
	 */
	public String getMenuItemID(){
		return this.menuItemID;
	}

	/**
	 * PromotionID getter
	 * @return promotionID
	 */
	public String getPromotionID() {
		return this.promotionID;
	}
	/**
	 * PromotionPrice getter
	 * @return promotionPrice
	 */
	public double getPromotionPrice() {
		return this.promotionPrice;
	}
	/**
	 * PromotionPrice setter
	 * @param aPromotionPrice  price of promotion
	 */
	public void setPromotionPrice(double aPromotionPrice) {
		this.promotionPrice = aPromotionPrice;
	}
	/**
	 * StartDate setter
	 * @param startDate  start date of promotion
	 */
	public void setStartDate(String startDate){
		this.promotionStartDate = startDate;
	}
	/**
	 * PromotionStartDate getter
	 * @return promotionStartDate
	 */
	public String getStartDate(){
		return this.promotionStartDate;
	}
	/**
	 * PromotionEndDate setter
	 * @param endDate  end date of promotion
	 */
	public void setEndDate(String endDate){
		this.promotionEndDate = endDate;
	}
	/**
	 * PromotionEndDate getter
	 * @return promotionEndDate
	 */
	public String getEndDate(){
		return this.promotionEndDate;
	}

	/**
	 * Generates an array of string formatting for writing to CSV
	 * @return  an array of string for writing to CSV
	 */
	public String[] getLineCSVFormat(){
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String[] row = {this.promotionID, this.promotionName, this.promotionDescription,this.promotionStartDate,this.promotionEndDate,Double.toString(this.promotionPrice), this.menuItemID};
		return row;
	}
	/**
	 * PromotionDescription getter
	 * @return promotionDescription
	 */
	public String getDescription() {
		return this.promotionDescription;
	}

	/**
	 * PromotionDescription setter
	 * @param description description of promotion
	 */

	public void setDescription(String description) {
		this.promotionDescription= description;
	}





}
