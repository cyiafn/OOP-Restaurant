package ControlClasses;

import EntityClasses.Reservation;
import EntityClasses.Table;
import Enumerations.PrintColor;
import Enumerations.ReservationStatus;
import StaticClasses.Database;
import StaticClasses.InputHandler;
import com.opencsv.exceptions.CsvException;
import org.javatuples.Pair;

import java.io.IOException;
import java.sql.Array;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.*;

/**
 Reservation Manager that handles reservations and tables.
 @author Chen Yifan
 @version 1.0
 @since 2021-10-19
*/

/**
 *Basically a singleton manager to manage reservations.
 */
public class ReservationManager {
	/**
	 * This attributes stores an arraylist of table objs.
	 */
	private final ArrayList<Table> tables;
	/**
	 * This attribute stores a hashmap of key: tableNo and value of ArrayList of reservations.
	 */
	private final HashMap<Integer, ArrayList<Reservation>> reservations;
	/**
	 * This attributes caches all createdReservations only to optimize cleanup.
	 */
	private ArrayList<Reservation> createdReservations;
	/**
	 * Simple counter for number of table.
	 */
	private int noOfTable;
	/**
	 * Constants for file name table.
	 */
	public static final String tableFile = "Table.csv";
	/**
	 * Constant for file name reservation.
	 */
	public static final String reservationFile = "Reservation.csv";
	/**
	 * Stores the single instance of reservation manager
	 */
	private static ReservationManager instance = null;

	/**
	 * Static method to create a new reservation manager if there isn't already one, else return
	 * @return singleton reservation managers
	 */
    public static ReservationManager getInstance() throws IOException, CsvException {
        if (instance == null) instance = new ReservationManager();
		//cleans up whenever ReservationManager is called.
		instance.cleanup();
        return instance;
    }
	/**
	 * Default constructor, initialises attributes and loads in data from CSVs.
	 * @throws IOException Read file exception.
	 * @throws CsvException Read CSV Exception.
	 */
	public ReservationManager() throws IOException, CsvException {
		tables = new ArrayList();
		reservations = new HashMap<>();
		createdReservations = new ArrayList<>();
		noOfTable = 0;
		loadTables();
		loadReservations();
	}

	/**
	 * Cleanup runs everytime any function in here runs to cleanup expiring reservations. (15mins)
	 * @throws IOException IO file read exception
	 * @throws CsvException CSV file read exception
	 */
	public void cleanup() throws IOException, CsvException {
		//clean up expires stuff past 15 mins if created, quietly
		ArrayList<Reservation> tempAr = new ArrayList<>();
		for (Reservation i: this.createdReservations){
			if (i.cleanup()){
				Database.updateLine(reservationFile, i.getReservationID(), i.getLineCSVFormat());
			}
			else{
				tempAr.add(i);
			}
		}
		this.createdReservations = tempAr;
	}

	/**
	 * Function to load table into attribute.
	 * @throws IOException IO file read exception
	 * @throws CsvException CSV file read exception
	 */
	private void loadTables() throws IOException, CsvException {
		//reads everything
		ArrayList<HashMap<String, String>> loadedTables = Database.readAll(tableFile);
		//add tables to ID and creates arraylist for each table.
		for (HashMap<String, String> i: loadedTables){
			tables.add(new Table(Integer.parseInt(i.get("tableNo")),Integer.parseInt(i.get("seatingCapacity"))));
			reservations.put(Integer.parseInt(i.get("tableNo")), new ArrayList<>());
			noOfTable += 1;
		}
	}

	/**
	 * Function to load reservation data into attribute
	 * @throws IOException IO file read exception
	 * @throws CsvException CSV file read exception
	 */
	private void loadReservations() throws IOException, CsvException {
		//loads everything
		ArrayList<HashMap<String, String>> loadedTables = Database.readAll(reservationFile);
		for (HashMap<String, String> i: loadedTables){
			//Conversion of string to LocalDateTime obj
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			LocalDateTime dateTime = LocalDateTime.parse(i.get("dt"), formatter);

			Reservation tempRes = new Reservation(
							i.get("reservationID"),
							dateTime,
							Integer.parseInt(i.get("noOfPax")),
							i.get("name"),
							i.get("contactNo"),
							Integer.parseInt(i.get("tableNo")),
							Enum.valueOf(ReservationStatus.class, i.get("status"))
							);
			if (tempRes.getStatus() == ReservationStatus.CREATED){
				this.createdReservations.add(tempRes);
			}
			reservations.get(Integer.parseInt(i.get("tableNo"))).add(tempRes);
		}
	}

	/**
	 * Orchestrating function to delete reservations.
	 * @return whether or not deletion is successfull
	 * @throws IOException IO file read exception
	 * @throws CsvException CSV file read exception
	 */
	public boolean deleteReservation() throws IOException, CsvException {
		//name;contactNo;dt
		Scanner sc = new Scanner(System.in);
		System.out.print(PrintColor.GREEN_BOLD);
		String name = InputHandler.getName();
		String contactNo = InputHandler.getContactNo();
		LocalDateTime datetime;
		datetime = InputHandler.getDate(true);

		//get all reservations with these attributes
		ArrayList<Reservation> temp = new ArrayList<>();
		for (Integer key: reservations.keySet()){
			for (Reservation i: reservations.get(key)){
				if (i.getName().equals(name) && i.getDt().equals(datetime) && i.getContactNo().equals(contactNo) && ReservationStatus.CREATED == i.getStatus()){
					temp.add(i);
				}
			}
		}
		System.out.print(PrintColor.RED);
		//no such attributes
		if (temp.size() == 0 ){
			System.out.println("There are no valid reservations!");
			return false;
		}
		System.out.print(PrintColor.RESET);
		//select which reservation to update
		for (int i = 0; i < temp.size(); i ++){
			System.out.print(i + 1 + ". ");
			temp.get(i).print();
			System.out.print("\n");
		}
		System.out.print(PrintColor.GREEN_BOLD);
		int opt = InputHandler.getInt(0,temp.size(), "Please select your option (0 to exit): ", "Invalid integer!");
		if (opt == 0){
			return false;
		}
		System.out.print(PrintColor.RESET);
		temp.get(opt-1).setStatus(ReservationStatus.REMOVED);
		Database.updateLine(reservationFile, temp.get(opt-1).getReservationID(), temp.get(opt-1).getLineCSVFormat());
		return true;
	}

	/**
	 * Orchestrating function to checkin a user. (enters the restaurant)
	 * @return the id of the reservation.
	 * @throws IOException IO file read exception
	 * @throws CsvException CSV file read exception
	 */
	public String checkin(String reservationId) throws IOException, CsvException {
		return updateReservation(ReservationStatus.ACTIVE, ReservationStatus.CREATED, reservationId);
	}

	/**
	 * Function to return the table number based on reservationid
	 * @param reservationId reservation unique identifier
	 * @return table number, -1 if don't have such reservation
	 * @throws IOException IO file read exception
	 * @throws CsvException CSV file read exception
	 */
	public int getTableNumber(String reservationId) throws IOException, CsvException {
		for (int a: reservations.keySet()){
			for (Reservation r: reservations.get(a)){
				if (r.getReservationID().equals(reservationId)){
					return a;
				}
			}
		}
		return -1;
	}

	/**
	 * function to close a reservation when someone is done with food.
	 * @param reservationId reservation unique identifier for closing reservation
	 * @return successful close or not
	 * @throws IOException IO file read exception
	 * @throws CsvException CSV file read exception
	 */
	public boolean closeReservation(String reservationId) throws IOException, CsvException {
		return !updateReservation(ReservationStatus.COMPLETED, ReservationStatus.ACTIVE, reservationId).equals("");
	}

	/**
	 * Function to update status of reservation.
	 * @param stat the enum of the status
	 * @param validState valid previous state
	 * @param reservationId id of the reservation to update
	 * @return "" for failure and id for success.
	 * @throws IOException IO file read exception
	 * @throws CsvException CSV file read exception
	 */
	private String updateReservation(ReservationStatus stat, ReservationStatus validState, String reservationId) throws CsvException, IOException {
		for (int a: reservations.keySet()){
			for (Reservation r: reservations.get(a)){
				if (r.getReservationID().equals(reservationId)){
					if (r.getStatus() == validState){
						r.setStatus(stat);
						Database.updateLine(reservationFile, reservationId, r.getLineCSVFormat());
						return r.getReservationID();
					}
					else{
						return "";
					}

				}
			}
		}
		return "";
	}

	/**
	 * Creates a reservation. Checks if possible.
	 * @return success
	 * @throws IOException IO file read exception
	 * @throws CsvException CSV file read exception
	 */
	public boolean createReservation() throws IOException, CsvException {
		Scanner sc = new Scanner(System.in);
		String timeFormat = "HH:mm";
		//parse date strictly
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter
			.ofPattern(timeFormat)
			.withResolverStyle(ResolverStyle.LENIENT);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		//name
		System.out.print(PrintColor.GREEN_BOLD);
		String name = InputHandler.getName();
		String contactNo = InputHandler.getContactNo();

		LocalDateTime datetime = InputHandler.getDate(true);
		while (datetime.toLocalTime().isBefore(LocalTime.parse("06:00", dateTimeFormatter)) || datetime.toLocalTime().isAfter(LocalTime.parse("21:00", dateTimeFormatter))){
			System.out.print(PrintColor.RED_BOLD);
			System.out.println("You can only make reservations between 0600 - 2100!");
			System.out.print(PrintColor.RESET);
			datetime = InputHandler.getDate(true);
		}
		//no of pax
		int noOfPax = InputHandler.getInt(1,10, "Please enter the number of people (1-10): ", "Invalid number of people!");
		System.out.print(PrintColor.RESET);
		int minTableSpace;
		if (noOfPax % 2 == 1){
			minTableSpace = noOfPax + 1;
		}
		else{
			minTableSpace = noOfPax;
		}
		for (Table i: tables){

			if (i.getSeatingCapacity() >= minTableSpace){
				ArrayList<Reservation> tempAr = reservations.get(i.getTableNo());
				boolean invalid = false;
				for (int j = 0; j < tempAr.size(); j ++){
					if ((tempAr.get(j).getStatus() == ReservationStatus.ACTIVE || tempAr.get(j).getStatus() == ReservationStatus.CREATED) && Math.abs(Duration.between(tempAr.get(j).getDt(), datetime).toMinutes()) <= 120){
						invalid = true;
						break;
					}
				}
				if (!invalid){
					Reservation newRes = new Reservation(UUID.randomUUID().toString(), datetime, noOfPax,name,contactNo, i.getTableNo());
					reservations.get(i.getTableNo()).add(newRes);
					createdReservations.add(newRes);
					Database.writeLine(reservationFile, newRes.getLineCSVFormat());
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Print details of reservation.
	 * @throws IOException IO file read exception
	 * @throws CsvException CSV file read exception
	 */
	public void checkReservation() throws IOException, CsvException {
		Scanner sc = new Scanner(System.in);
		//name
		System.out.print(PrintColor.GREEN_BOLD);
		String name = InputHandler.getName();
		String contactNo = InputHandler.getContactNo();
		LocalDateTime datetime = InputHandler.getDate(false);
		System.out.print(PrintColor.RESET);

		//get all reservations with these attributes
		ArrayList<Reservation> temp = new ArrayList<>();
		for (Integer key: reservations.keySet()){

			for (Reservation i: reservations.get(key)){
				if (i.getName().equals(name)  && i.getDt().equals(datetime) && i.getContactNo().equals(contactNo)  ){
					temp.add(i);
				}
			}
		}
		//no such attributes
		System.out.print(PrintColor.RED);
		if (temp.size() == 0 ){
			System.out.println("There are no valid reservations with those parameters!");
			return;
		}
		System.out.print(PrintColor.RESET);

		for (int i = 0; i < temp.size(); i ++){
			System.out.print(i + 1 + ". ");
			temp.get(i).print();
			System.out.print("\n");
		}
	}

	/**
	 * Check a table's availability in a 2-hour window. (Customer gets to dine in 2 hours)
	 * Assumes 6am - 9pm
	 * @throws IOException IO file read exception
	 * @throws CsvException CSV file read exception
	 */
	public void checkAvailability() throws IOException, CsvException {
		Scanner sc = new Scanner(System.in);
		String timeFormat = "HH:mm";
		//parse date strictly
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter
			.ofPattern(timeFormat)
			.withResolverStyle(ResolverStyle.LENIENT);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		int table = InputHandler.getInt(1, noOfTable, "Please enter the table number: ", "Invalid table number!");
		LocalDate datetime = InputHandler.getDateOnly(true);
		// get everything on that date
		ArrayList<Reservation> curRes = new ArrayList<>();
		for (Reservation r: reservations.get(table)){
			if ((r.getDt().toLocalDate().equals(datetime)) && (r.getStatus() == ReservationStatus.CREATED || r.getStatus() == ReservationStatus.ACTIVE)){
				curRes.add(r);
			}
		}
		//no reservation for that table that day
		if (curRes.size() == 0){
			System.out.print(PrintColor.GREEN);
			System.out.println("Reservations are available for the whole day!");
			System.out.print(PrintColor.RESET);
			return;
		}



		//Sorting to make it easier to filter
		Collections.sort(curRes, Comparator.comparing(Reservation::getDt));
		LocalTime morning = LocalTime.parse("06:00", dateTimeFormatter);
		LocalTime evening = LocalTime.parse("21:00", dateTimeFormatter);
		ArrayList<Pair<String, LocalTime>> bookedPeriods = new ArrayList<>();

		for (Reservation i: curRes){
			if (i.getDt().toLocalTime().minusHours(2).isBefore(morning)){
				bookedPeriods.add(new Pair<String, LocalTime>("BEFORE", morning));
			}
			else{
				bookedPeriods.add(new Pair<String, LocalTime>("BEFORE", i.getDt().toLocalTime().minusHours(2)));
			}
			bookedPeriods.add(new Pair<String, LocalTime>("AFTER", i.getDt().toLocalTime().plusHours(2)));

		}
		boolean printed = false;
		ArrayList<Pair<String, LocalTime>> filteredList = new ArrayList<>();
		//filters colliding timeblocks
		for (int i = 0; i < bookedPeriods.size() ; i ++){
			if (i == 0){
				filteredList.add(bookedPeriods.get(i));
				continue;
			}
			if (bookedPeriods.get(i).getValue0().equals("AFTER") && i + 1 < bookedPeriods.size() ){
				if (bookedPeriods.get(i).getValue1().equals(bookedPeriods.get(i + 1).getValue1()) || bookedPeriods.get(i).getValue1().isAfter(bookedPeriods.get(i + 1).getValue1())){
					i += 1;
					continue;
				}
			}
			filteredList.add(bookedPeriods.get(i));
		}

		System.out.print(PrintColor.YELLOW_BOLD);
		System.out.println("Available booking timings.");
		System.out.print(PrintColor.RESET);

		if (filteredList.get(0).getValue1().equals(morning)){
			//if someone books from morning onwards
			for (int i = 1; i < filteredList.size() - 1; i ++){
				if (filteredList.get(i).getValue0().equals("AFTER")){
					System.out.print(filteredList.get(i).getValue1().toString() + " - ");
					printed = true;
				}
				else{
					System.out.println(filteredList.get(i).getValue1().toString());
					printed = true;
				}
			}
		}
		else{
			// if someone doesn't book from morning onwards
			System.out.println(morning + " - " + filteredList.get(0).getValue1().toString());
			printed = true;
			for (int i = 1; i < filteredList.size() - 1; i ++){
				if (filteredList.get(i).getValue0().equals("AFTER")){
					System.out.print(filteredList.get(i).getValue1().toString() + " - ");
					printed = true;
				}
				else{
					System.out.println(filteredList.get(i).getValue1().toString());
					printed = true;
				}
			}
		}

		if (!(filteredList.get(filteredList.size() - 1).getValue1().equals(evening) || filteredList.get(filteredList.size() - 1).getValue1().isAfter(evening))){
			System.out.println(filteredList.get(filteredList.size() - 1).getValue1().toString() + " - " + evening);
			printed = true;
		}

		if (!printed){
			System.out.print(PrintColor.RED);
			System.out.println("There are no slots available for booking!");
			System.out.print(PrintColor.RESET);
		}

	}

	/**
	 * Gets all valid today created reservations after current time.
	 * @return Arraylist of eligible today reservations.
	 * @throws IOException Cannot interface with files
	 * @throws CsvException Cannot read/write CSV.
	 */
	public ArrayList<String> getTodaysCreatedReservations() throws IOException, CsvException {

		//get all eligible reservations
		ArrayList <String> output = new ArrayList<>();
		for (ArrayList<Reservation> i: reservations.values()){
			for (Reservation r: i){
				if (r.getDt().toLocalDate().equals(LocalDate.now()) && r.getStatus() == ReservationStatus.CREATED && r.getDt().toLocalTime().isAfter(LocalTime.now().minusHours(1))){
					output.add(r.getReservationID());
				}

			}
		}

		return output;

	}



}
