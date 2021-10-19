/**
 * Reservation entity class
 * @author Chen Yifan
 * @version 1.0
 * @since 2021-10-19
 */
package EntityClasses;

import Enumerations.ReservationStatus;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Reservation class
 */
public class Reservation {
	/**
	 * Primary key, unique.
	 */
	private String reservationID;
	/**
	 * DateTime object of reservation
	 */
	private LocalDateTime dt;
	/**
	 * Number of pax for reservation
	 */
	private int noOfPax;
	/**
	 * Name of person who made reservation
	 */
	private String name;
	/**
	 * contact number of person who made reservation.
	 */
	private String contactNo;
	/**
	 * Table number the reservation is assigned to.
	 */
	private int tableNo;
	/**
	 * Whether or not the reservation is active (ACTIVE, REMOVED, EXPIRED, COMPLETED)
	 */
	private ReservationStatus status;

	/**
	 * Contructer for reservation, Reservation object shall be immutable except for status.
	 * @param rid Reservation ID
	 * @param dt Date time obj
	 * @param noOfPax	Number of Pax
	 * @param name	Name of person reserving
	 * @param contactNo	Contact number of person who reserved.
	 * @param tableNo table number the reservation is binded to
	 */
	public Reservation(String rid, LocalDateTime dt, int noOfPax, String name, String contactNo, int tableNo){
		this.reservationID = rid;
		this.dt = dt;
		this.noOfPax = noOfPax;
		this.name = name;
		this.contactNo = contactNo;
		this.tableNo = tableNo;
		this.status = ReservationStatus.CREATED;
	}

	/**
	 * Constructor for reservation, Reservation object shall be immutable except for status, this overloaded constructor
	 * is used during initial loading of all reservations.
	 * @param rid Reservation ID
	 * @param dt Date time obj
	 * @param noOfPax	Number of Pax
	 * @param name	Name of person reserving
	 * @param contactNo	Contact number of person who reserved.
	 * @param tableNo table number the reservation is binded to
	 * @param status setting current status
	 */
	public Reservation(String rid, LocalDateTime dt, int noOfPax, String name, String contactNo, int tableNo, ReservationStatus status){
		this.reservationID = rid;
		this.dt = dt;
		this.noOfPax = noOfPax;
		this.name = name;
		this.contactNo = contactNo;
		this.tableNo = tableNo;
		this.status = status;
	}
	/**
	 * Simply prints out details of reservation
	 */
	public void print() {
		//Formatting LocalDateTimeObj and printing it
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		System.out.println("Reservation for: " + this.name + "\nContact No: " + this.contactNo +"\nAt: " + this.dt.format(myFormatObj) +
				"\nFor: " + Integer.toString(noOfPax) + "pax\nAssigned: Table no. " + Integer.toString(this.tableNo) +
				"\nStatus: " + status.name());
	}

	/**
	 * ReservationID getter
	 * @return ReservationID
	 */
	public String getReservationID() {
		return reservationID;
	}

	/**
	 * Datetime object getter
	 * @return dt
	 */
	public LocalDateTime getDt() {
		return dt;
	}

	/**
	 * No of pax getter
	 * @return noOfPax
	 */
	public int getNoOfPax() {
		return noOfPax;
	}

	/**
	 * TableNo getter
	 * @return tableNo
	 */
	public int getTableNo() {
		return tableNo;
	}

	/**
	 * ContactNo getter
	 * @return contactNo
	 */
	public String getContactNo() {
		return contactNo;
	}

	/**
	 * Name getter
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Status getter
	 * @return ReservationStatus enum of status
	 */
	public ReservationStatus getStatus() {
		return status;
	}

	/**
	 * Setting status
	 * @param status enum
	 */
	public void setStatus(ReservationStatus status) {
		this.status = status;
	}

	public String[] getLineCSVFormat(){
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String[] row = {reservationID, this.dt.format(myFormatObj), Integer.toString(noOfPax), name,contactNo, Integer.toString(tableNo), status.name()};
		return row;
	}
}