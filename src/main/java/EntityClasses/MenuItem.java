package EntityClasses;

/**
 * MenuItem entity class
 * @author Daniel Chu Jia Hao
 * @version 1.0
 * @since 2021-11-07
 */
public class MenuItem {
	/*
	Attributes of Menu Item
	 */
	private String menuItemID;
	private String name;
	private String description;
	private double price;
	private Integer quantity;

	/**
	 * Constructor of MenuItem
	 * @param menuItemID
	 * @param name
	 * @param description
	 * @param price
	 * @param quantity
	 */
	public MenuItem(String menuItemID, String name, String description, double price, Integer quantity) {
		this.menuItemID = menuItemID;
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
	}

	/**
	 * Empty Constructor for menuitem
	 */
	public MenuItem() {}

	/**
	 * print function for all attributes
	 */
	public void print(){
		System.out.println(this.getMenuItemID());
		System.out.println(this.getName());
		System.out.println(this.getDescription());
		System.out.println(this.getPrice());
		System.out.println(this.getQuantity().toString());
	}

	/*
	Accessor and Mutator
	 */
	public String getMenuItemID() {
		return menuItemID;
	}

	public void setMenuItemID(String menuItemID) {
		this.menuItemID = menuItemID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}

