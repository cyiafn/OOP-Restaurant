package ControlClasses;

import EntityClasses.Reservation;
import EntityClasses.Table;
import Enumerations.ReservationStatus;
import StaticClasses.Database;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
	private ArrayList<Table> tables;
	/**
	 * This attribute stores a hashmap of key: tableNo and value of ArrayList of reservations.
	 */
	private HashMap<Integer, ArrayList<Reservation>> reservations;
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
	 * Default constructor, initialises attributes and loads in data from CSVs.
	 * @throws IOException Read file exception.
	 * @throws CsvException Read CSV Exception.
	 */
	public ReservationManager() throws IOException, CsvException {
		tables = new ArrayList();
		reservations = new HashMap<>();
		noOfTable = 0;
		System.out.println("Loading table data...");
		loadTables();
		System.out.println("Loading reservation data...");
		loadReservations();
	}

	/**
	 * Helper function to request user for datetime.
	 * @param after This signifies if the function checks if the provided date and time is after now or not.
	 * @return LocalDateTime obj.
	 */
	private LocalDateTime getUserDate(boolean after){
		Scanner sc = new Scanner(System.in);
		LocalDateTime datetime;
		do {
			System.out.println("Please enter the date time in the format YYYY-MM-DD HH:MM ");
			String dt = sc.next();
			dt += " " + sc.next();
			sc.nextLine();
			String dateFormat = "yyyy-MM-dd HH:mm";
			//parse date strictly
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter
				.ofPattern(dateFormat).withResolverStyle(ResolverStyle.LENIENT);
			try {
				datetime = LocalDateTime.parse(dt.strip(), dateTimeFormatter);
				if (after){
					if (datetime.isAfter(LocalDateTime.now())){
						break;
					}
					else{
						System.out.println("Please enter a date after the current time!");
						continue;
					}

				}
				else{
					break;
				}

			} catch (DateTimeParseException e) {
				//continue loop
				System.out.println("Invalid date input!");
				continue;
			}
		}while (true);
		return datetime;
	}

	/**
	 * Helper function to get user name.
	 * @return user's name.
	 */
	private String getUserName(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter your name: ");
		String name = sc.nextLine();

		return name.strip();
	}

	/**
	 * Helper function to get users contact no, matching digit regex.
	 * @return User's contact number in string.
	 */
	private String getUserContactNo(){
		Scanner sc = new Scanner(System.in);
		String contactNo;
		//contactno unsafe input
		do {
			System.out.println("Please enter your contact number (no spaces or +): ");
			contactNo = sc.next();
			sc.nextLine();
			if (contactNo.matches("\\d+")){
				break;
			}
		}while (true);
		return contactNo.strip();
	}

	/**
	 * Helper function to get number of people.
	 * @return returns int of number of people.
	 */
	private int getNoOfPax(){
		Scanner sc = new Scanner(System.in);
		int noOfPax;
		do {
			System.out.println("Please enter the number of people you would like to reserve for: ");
			String unsafeInput = sc.next();
			sc.nextLine();
			if (unsafeInput.matches("\\d+")){
				noOfPax = Integer.parseInt(unsafeInput);
				if (!(noOfPax >= 2 && noOfPax <= 10)){
					noOfPax = -1;
					System.out.println("Please enter a valid integer!");
				}
			}
			else{
				noOfPax = -1;
				System.out.println("Please enter a valid integer!");
			}
		} while(noOfPax == -1);
		return noOfPax;
	}

	/**
	 * Cleanup runs everytime any function in here runs to cleanup expiring reservations. (15mins)
	 * @throws IOException IO file read exception
	 * @throws CsvException CSV file read exception
	 */
	public void cleanup() throws IOException, CsvException {
		//clean up expires stuff past 15 mins if created quietly
		for (Integer i: reservations.keySet()){
			for (Reservation j: reservations.get(i)){
				if (j.getStatus() == ReservationStatus.CREATED && Duration.between(j.getDt(), LocalDateTime.now()).toMinutes() >= 15){
					j.setStatus(ReservationStatus.EXPIRED);
					Database.updateLine(reservationFile, j.getReservationID(), j.getLineCSVFormat());
				}

			}
		}
	}

	/**
	 * Function to load table into attribute.
	 * @throws IOException IO file read exception
	 * @throws CsvException CSV file read exception
	 */
	public void loadTables() throws IOException, CsvException {
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
	public void loadReservations() throws IOException, CsvException {
		//loads everything
		ArrayList<HashMap<String, String>> loadedTables = Database.readAll(reservationFile);
		for (HashMap<String, String> i: loadedTables){
			System.out.println(i.toString());
			//Conversion of string to LocalDateTime obj
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			LocalDateTime dateTime = LocalDateTime.parse(i.get("dt"), formatter);

			reservations.get(Integer.parseInt(i.get("tableNo"))).add(new Reservation(
							i.get("reservationID"),
							dateTime,
							Integer.parseInt(i.get("noOfPax")),
							i.get("name"),
							i.get("contactNo"),
							Integer.parseInt(i.get("tableNo")),
							Enum.valueOf(ReservationStatus.class, i.get("status"))
							));
		}
	}

	/**
	 * Orchestrating function to delete reservations.
	 * @return whether or not deletion is successfull
	 * @throws IOException IO file read exception
	 * @throws CsvException CSV file read exception
	 */
	public boolean deleteReservation() throws IOException, CsvException {
		if (updateReservation(ReservationStatus.REMOVED) != ""){
			return true;
		}
		return false;
	}

	/**
	 * Orchestrating function to checkin a user. (enters the restaurant)
	 * @return the id of the reservation.
	 * @throws IOException IO file read exception
	 * @throws CsvException CSV file read exception
	 */
	public String checkin() throws IOException, CsvException {
		return updateReservation(ReservationStatus.ACTIVE);
	}

	/**
	 * Orchestrating function to close a reservation when someone is done with food.
	 * @return the id of the reservation.
	 * @throws IOException IO file read exception
	 * @throws CsvException CSV file read exception
	 */
	public String closeReservation() throws IOException, CsvException {
		return updateReservation(ReservationStatus.COMPLETED);
	}

	/**
	 * Function to update status of reservation.
	 * @param stat the enum of the status
	 * @return "" for failure and id for success.
	 * @throws IOException IO file read exception
	 * @throws CsvException CSV file read exception
	 */
	private String updateReservation(ReservationStatus stat) throws CsvException, IOException {
		//name;contactNo;dt
		cleanup();
		Scanner sc = new Scanner(System.in);
		String name = getUserName();
		String contactNo = getUserContactNo();
		LocalDateTime datetime;
		if (stat == ReservationStatus.REMOVED){
			datetime = getUserDate(true);
		}
		else{
			datetime = getUserDate(false);
		}

		//get all reservations with these attributes
		ArrayList<Reservation> temp = new ArrayList<>();
		for (Integer key: reservations.keySet()){
			for (Reservation i: reservations.get(key)){
				if (i.getName().equals(name) && i.getDt().equals(datetime) && i.getContactNo().equals(contactNo) && i.getStatus().equals(ReservationStatus.CREATED)){
					temp.add(i);
				}
			}
		}
		//no such attributes
		if (temp.size() == 0 ){
			System.out.println("There are no valid reservations!");
			return "";
		}
		//select which reservation to remove

		for (int i = 0; i < temp.size(); i ++){
			System.out.print(Integer.toString(i + 1) + ". ");
			temp.get(i).print();
			System.out.print("\n");
		}
		System.out.println("Please indicate which reservation (0 to cancel):");
		//handle unsafe inputs
		int opt;
		do {
			String unsafeInput = sc.next();
			if (unsafeInput.matches("\\d+")){
				opt = Integer.parseInt(unsafeInput);
				if (!(opt >= 0 && opt <= temp.size())){
					opt = -1;
					System.out.println("Please enter a valid integer!");
				}
			}
			else{
				opt = -1;
				System.out.println("Please enter a valid integer!");
			}
		} while(opt == -1);
		if (opt == 0){
			return "";
		}
		temp.get(opt-1).setStatus(stat);
		Database.updateLine(reservationFile, temp.get(opt-1).getReservationID(), temp.get(opt-1).getLineCSVFormat());
		return temp.get(opt-1).getReservationID();
	}

	/**
	 * Creates a reservation. Checks if possible.
	 * @return success
	 * @throws IOException IO file read exception
	 * @throws CsvException CSV file read exception
	 */
	public boolean createReservation() throws IOException, CsvException {
		cleanup();
		Scanner sc = new Scanner(System.in);
		//name
		String name = getUserName();
		String contactNo = getUserContactNo();

		LocalDateTime datetime = getUserDate(true);
		//no of pax
		int noOfPax = getNoOfPax();

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
					if ((tempAr.get(j).getStatus() == ReservationStatus.ACTIVE || tempAr.get(j).getStatus() == ReservationStatus.CREATED) && Math.abs(Duration.between(tempAr.get(j).getDt(), datetime).toMinutes()) < 120){
						invalid = true;
						break;
					}
				}
				if (!invalid){
					Reservation newRes = new Reservation(UUID.randomUUID().toString(), datetime, noOfPax,name,contactNo, i.getTableNo());
					reservations.get(i.getTableNo()).add(newRes);
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
		cleanup();
		Scanner sc = new Scanner(System.in);
		//name
		String name = getUserName();
		String contactNo = getUserContactNo();
		LocalDateTime datetime = getUserDate(false);

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
		if (temp.size() == 0 ){
			System.out.println("There are no valid reservations with those parameters!");
			return;
		}

		for (int i = 0; i < temp.size(); i ++){
			System.out.print(Integer.toString(i + 1) + ". ");
			temp.get(i).print();
			System.out.print("\n");
		}
	}

	/**
	 * Check a table's availability in a 2-hour window. (Customer gets to dine in 2 hours)
	 * @throws IOException IO file read exception
	 * @throws CsvException CSV file read exception
	 */
	public void checkAvailability() throws IOException, CsvException {

		cleanup();
		Scanner sc = new Scanner(System.in);
		int table;
		do {
			System.out.println("Please enter the table number you want to check: ");

			String unsafeInput = sc.next();
			sc.nextLine();
			if (unsafeInput.matches("\\d+")){
				table = Integer.parseInt(unsafeInput);
				if (!(table > 0 && table <= noOfTable)){
					table = -1;
					System.out.println("Please enter a valid table number!");
				}
			}
			else{
				table = -1;
				System.out.println("Please enter a valid integer!");
			}
		} while(table == -1);

		LocalDate datetime = getUserDate(false).toLocalDate();


		Collections.sort(reservations.get(table), Comparator.comparing(Reservation::getDt));
		int startingInd = -1;
		int endingInd = -1;
		for (int i = 0; i < reservations.get(table).size(); i ++){
			if (startingInd == -1 && reservations.get(table).get(i).getDt().toLocalDate().equals(datetime)){
				if (i != 0){
					startingInd = i - 1;
				}
				else{
					startingInd = i;
				}
			}
			if (endingInd == -1  && reservations.get(table).get(i).getDt().toLocalDate().isBefore(datetime)){
				endingInd = i;
			}
		}


		if (endingInd == -1){
			endingInd = reservations.get(table).size() - 1;
		}
		if (endingInd - startingInd + 1  == 0){
			System.out.println("Reservations are available for the whole day!");
			return;
		}
		String timeFormat = "HH:mm";
		//parse date strictly
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter
			.ofPattern(timeFormat)
			.withResolverStyle(ResolverStyle.LENIENT);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

		LocalTime morning = LocalTime.parse("00:00", dateTimeFormatter);
		LocalTime midnight = LocalTime.parse("23:59", dateTimeFormatter);
		System.out.println("These are the time periods available.");
		boolean printed = false;
		if (morning.plusHours(2).isBefore(reservations.get(table).get(startingInd).getDt().toLocalTime())){
			System.out.println("00:00 - " + reservations.get(table).get(startingInd).getDt().toLocalTime().minusMinutes(119).format(dateTimeFormatter));
			printed = true;
		}
		for (int i = startingInd; i < endingInd; i ++){
			if (i < endingInd){
				if (reservations.get(table).get(i).getDt().plusMinutes(119).isBefore(reservations.get(table).get(i + 1).getDt().minusMinutes(119))){
					printed = true;
					System.out.println(reservations.get(table).get(i).getDt().plusMinutes(119).format(formatter) + " - " +
							reservations.get(table).get(i + 1).getDt().format(formatter));
				}
			}
		}
		if (midnight.minusHours(2).isAfter(reservations.get(table).get(endingInd).getDt().toLocalTime())){
			System.out.println(reservations.get(table).get(endingInd).getDt().toLocalTime().plusMinutes(119).format(dateTimeFormatter) + " - 23:59");
			printed = true;
		}
		if (!printed){
			System.out.println("There are no reservation periods available!");
		}
	}



}