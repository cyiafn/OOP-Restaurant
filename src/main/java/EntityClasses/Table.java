/**
 * Table entity class
 * @author Chen Yifan
 * @version 1.0
 * @since 2021-10-19
 */
package EntityClasses;

/**
 * Table class (immutable)
 */
public class Table {
	/**
	 * Table number.
	 */
	private final int tableNo;
	/**
	 * Seating capacity of table (even nos from 2-10).
	 */
	private final int seatingCapacity;

	/**
	 * Constructor when loading in from csv.
	 * @param tableNo Table Number
	 * @param seatingCapacity Max seating capacity.
	 */
	public Table(int tableNo, int seatingCapacity){
		this.tableNo = tableNo;
		this.seatingCapacity = seatingCapacity;
	}

	/**
	 * Getting the table number.
	 * @return tableNo
	 */
	public int getTableNo() {
		return tableNo;
	}

	/**
	 * Getting the seating capacity.
	 * @return seatingCapacity
	 */
	public int getSeatingCapacity(){
		return this.seatingCapacity;
	}
}
