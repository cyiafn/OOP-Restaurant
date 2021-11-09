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
	Attributes of Menu Item
	 */
	private String promotionID;
	private double promotionPrice;
	private String promotionDescription;
	private String menuItemID;
	private String promotionName;
	private String promotionStartDate;
	private String promotionEndDate;

	/**
	 * Constructor of MenuItem
	 * @param promotionID promotion ID
	 * @param promotionPrice promotion price
	 * @param promotionDescription promotion description
	 * @param menuItemID promotion menu item ID
	 * @param promotionName promotion name
	 * @param promotionStartDate promotion start date
	 * @param promotionEndDate promotion end date
	 */

	public Promotion(String id,String name,String _description,String startDate,String endDate, double price , String menuItemID){
		this.promotionID = id;
		this.promotionDescription = _description;
		this.promotionStartDate = startDate;
		this.promotionEndDate = endDate;
		this.promotionPrice = price;
		this.menuItemID = menuItemID;
		this.promotionName = name;

	}
	/**
	 * Empty Constructor for Promotion
	 */
	public Promotion(){

	}

	/*
	Accessor and Mutator
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
	 */
	public void setPromotionName(String name){
		this.promotionName = name;
	}
	/**
	 * MenuItemID setter
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
	 * PromotionID setter
	 */
	public void setPromotionID(String aPromotionMenuID) {
		this.promotionID = aPromotionMenuID;
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
	 */
	public void setPromotionPrice(double aPromotionPrice) {
		this.promotionPrice = aPromotionPrice;
	}
	/**
	 * StartDate setter
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
	 */

	public void setDescription(String description) {
		this.promotionDescription= description;
	}





}
