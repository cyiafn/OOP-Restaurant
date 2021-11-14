package EntityClasses;

/**
 * MenuItem entity class
 * @author CHU JIA HAO
 * @version 1.0
 * @since 2021-11-07
 */
public class MenuItem {
	/**
	 * The id of a menu item
	 */
	private String menuItemID;
	/**
	 * The name of a menu item
	 */
	private String name;
	/**
	 * The description of a menu item
	 */
	private String description;
	/**
	 * The price of a menu item
	 */
	private double price;
	/**
	 * The quantity of the menu item
	 */
	private Integer quantity;

	/**
	 * Constructor of MenuItem
	 * @param menuItemID menuitem id
	 * @param name name of menu item
	 * @param description description of menu item
	 * @param price price of menuitem
	 * @param quantity quantity of menu item
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

	/**
	 * Get the menu item id of this menu item
	 * @return menuitem id of this menu item
	 */
	public String getMenuItemID() {
		return menuItemID;
	}

	/**
	 * Changes the menu item id from this menu item
	 * @param menuItemID a menu item id
	 */
	public void setMenuItemID(String menuItemID) {
		this.menuItemID = menuItemID;
	}

	/**
	 * Get the menu item name from this menu item
	 * @return name of this menu item
	 */
	public String getName() {
		return name;
	}

	/**
	 * Changes the name of this menu item
	 * @param name name of menu item
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the description of this menu item
	 * @return description of menu item
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Changes the description of this menu item
	 * @param description description of menu item
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get the price of this menu item
	 * @return price of this menu item
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Change the price of this menu item
	 * @param price price of this menu item
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Get the quantity of this menu item
	 * @return the quantity of this menuitem
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * Change the quantity of this menu item
	 * @param quantity the quantity of this menu item
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}

