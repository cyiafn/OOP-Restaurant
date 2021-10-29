package EntityClasses;

import java.util.Map;

public class MenuItem {
	private String _menuItemID;
	private String _name;
	private String _description;
	private double _price;
	private Integer _quantity;

	public MenuItem(String _menuItemID, String _name, String _description, double _price, Integer _quantity) {
		this._menuItemID = _menuItemID;
		this._name = _name;
		this._description = _description;
		this._price = _price;
		this._quantity = _quantity;

	}

	public MenuItem(Map map) {
		this._menuItemID = (String) map.get("_menuItemID") ;
		this._name = (String) map.get("_name") ;
		this._description = (String) map.get("_description");
		this._price = (Double) map.get("_price");
		this._quantity = (Integer) map.get("_quantity");
	}
	public MenuItem() {}

	public void print(){
		System.out.println(this.get_menuItemID());
		System.out.println(this.get_name());
		System.out.println(this.get_description());
		System.out.println(this.get_price());
		System.out.println(this.get_quantity().toString());
	}

	public String get_menuItemID() {
		return _menuItemID;
	}

	public void set_menuItemID(String _menuItemID) {
		this._menuItemID = _menuItemID;
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

	public double get_price() {
		return _price;
	}

	public void set_price(double _price) {
		this._price = _price;
	}

	public Integer get_quantity() {
		return _quantity;
	}

	public void set_quantity(Integer _quantity) {
		this._quantity = _quantity;
	}
}
