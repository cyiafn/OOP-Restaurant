package EntityClasses;

import ControlClasses.MenuManager;

import java.io.IOException;

/**
 * MenuItem entity class
 * @author Daniel Chu Jia Hao
 * @version 1.0
 * @since 2021-11-07
 */
public class MenuItem extends Observer{
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
	public MenuItem(Subject subject,String id){
		this.menuItemID = id;
		this.subject=subject;
		this.subject.attach(this);
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
	public void update(){
		try {
			System.out.println(MenuManager.getInstance().findByIdForMenuItem(this.getMenuItemID()).getName()+" has a promotion! Do check it out");
		} catch (IOException e) {
			e.printStackTrace();
		}
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

