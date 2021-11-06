package EntityClasses;

import ControlClasses.MenuManager;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class Promotion {
	//private Integer _promotionPercentage;
	private String _promotionID;
	private double _promotionPrice;
	//private Date _startDate;
	//private Date _endDate;
	private String _promotionDuration;
	private String _promotionDescription;
	private String _menuItemID;
	private String _promotionName;


	public Promotion(String id,String name,String _description,String duration, double price , String menuItemID){
		this._promotionID = id;
		this._promotionDescription = _description;
		this._promotionDuration=duration;
		this._promotionPrice = price;
		this._menuItemID = menuItemID;
		this._promotionName = name;

	}
	public Promotion(){

	}
	public String getPromotionName(){
		return this._promotionName;
	}
	public void setPromotionName(String name){
		this._promotionName = name;
	}

	public void setMenuItemID(String menuItemId){
		this._menuItemID = menuItemId;
	}
	public String getMenuItemID(){
		return this._menuItemID;
	}



	public String getPromotionID() {
		return this._promotionID;
	}

	public void setPromotionID(String aPromotionMenuID) {
		this._promotionID = aPromotionMenuID;
	}

	public double getPromotionPrice() {
		return this._promotionPrice;
	}

	public void setPromotionPrice(double aPromotionPrice) {
		this._promotionPrice = aPromotionPrice;
	}

	public void setDuration(String duration){
		this._promotionDuration = duration;
	}
	public String getDuration(){
		return this._promotionDuration;
	}

	public String[] getLineCSVFormat(){
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String[] row = {this._promotionID, this._promotionName, this._promotionDescription,this._promotionDuration,Double.toString(this._promotionPrice), this._menuItemID};
		return row;
	}

	/*public Date getStartDate() {
		return this._startDate;
	}

	public void setStartDate(Date aStartDate) {
		this._startDate = aStartDate;
	}

	public Date getEndDate() {
		return this._endDate;
	}

	public void setEndDate(Date aEndDate) {
		this._endDate = aEndDate;
	}
*/
	public String getDescription() {
		return this._promotionDescription;
	}

	public void set_description(String description) {
		this._promotionDescription= description;
	}





}
