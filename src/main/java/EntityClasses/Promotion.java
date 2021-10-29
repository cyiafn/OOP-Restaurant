package EntityClasses;

import java.util.Date;
public class Promotion {
	private Integer _promotionPercentage;
	private Integer _promotionMenuID;
	private double _promotionPrice;
	private Date _startDate;
	private Date _endDate;
	private float _duration;

	public Integer getPromotionPercentage() {
		return this._promotionPercentage;
	}

	public void setPromotionPercentage(Integer aPromotionPercentage) {
		this._promotionPercentage = aPromotionPercentage;
	}

	public Integer getPromotionMenuID() {
		return this._promotionMenuID;
	}

	public void setPromotionMenuID(Integer aPromotionMenuID) {
		this._promotionMenuID = aPromotionMenuID;
	}

	public double getPromotionPrice() {
		return this._promotionPrice;
	}

	public void setPromotionPrice(double aPromotionPrice) {
		this._promotionPrice = aPromotionPrice;
	}

	public Date getStartDate() {
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

	public float getDuration() {
		return this._duration;
	}

	public void setDuration(float aDuration) {
		this._duration = aDuration;
	}
}
