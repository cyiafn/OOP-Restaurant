package StaticClasses;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.Scanner;
/**
 Static input handler + validator class.
 @author Chen Yifan
 @version 1.0
 @since 2021-10-19
*/

/**
 * Static class to request for user inputs and validate.
 */
public class InputHandler {
	public static Scanner sc = new Scanner(System.in);
    /**
	 * Helper function to request user for datetime.
	 * @param after This signifies if the function checks if the provided date and time is after now or not.
	 * @return LocalDateTime obj.
	 */
	public static LocalDateTime getDate(boolean after){
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
	 * Helper function to request user for date only.
	 * @param after This signifies if the function checks if the provided date and time is after now or not.
	 * @return LocalDateTime obj.
	 */
	public static LocalDate getDateOnly(boolean after){
		LocalDate datetime;
		do {
			System.out.println("Please enter the date in the format YYYY-MM-DD:");
			String dt = sc.next();
			sc.nextLine();
			String dateFormat = "yyyy-MM-dd";
			//parse date strictly
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter
				.ofPattern(dateFormat).withResolverStyle(ResolverStyle.LENIENT);
			try {
				datetime = LocalDate.parse(dt.strip(), dateTimeFormatter);
				if (after){
					if (datetime.isAfter(LocalDate.now())){
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
	 * Helper function to request user for date.
	 * Ensures that string input is formatted according to dd/MM/yyyy
	 * @return date in string
	 */
	public static String stringDate(String promptMessage){
		String date;
		do {
			System.out.println(promptMessage);
			date = sc.nextLine();
			//sc.nextLine();
			//String dateFormat = "dd/MM/yyyy";
			DateFormat inputFormatter = new SimpleDateFormat("dd/MM/yyyy");
			DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
			//parse date strictly
			try {
				Date date1 =inputFormatter.parse(date);
				String dateOutput = outputFormatter.format(date1);
				return dateOutput;
			} catch (ParseException e) {
				System.out.println("Invalid date input");
				continue;
			}
		}while (true);
	}

	/**
	 * Helper function to request user for date.
	 * Ensures that string input is formatted according to dd/MM/yyyy
	 * Output string will be yyyyMMdd
	 * @return date in string
	 */
	public static String stringDate2(String promptMessage){
		String date;
		do {
			System.out.println(promptMessage);
			date = sc.nextLine();
			//sc.nextLine();
			//String dateFormat = "dd/MM/yyyy";
			DateFormat inputFormatter = new SimpleDateFormat("dd/MM/yyyy");
			DateFormat outputFormatter = new SimpleDateFormat("yyyyMMdd");
			//parse date strictly
			try {
				Date date1 =inputFormatter.parse(date);
				String dateOutput = outputFormatter.format(date1);
				return dateOutput;
			} catch (ParseException e) {
				System.out.println("Invalid date input");
				continue;
			}
		}while (true);
	}

	/**
	 * Helper function to request user for date.
	 * Ensures that string input is formatted according to MM/yyyy
	 * Output string will be yyyyMM
	 * @return date in string
	 */
	public static String stringDate3(String promptMessage){
		String date;
		do {
			System.out.println(promptMessage);
			date = sc.nextLine();
			//sc.nextLine();
			//String dateFormat = "dd/MM/yyyy";
			DateFormat inputFormatter = new SimpleDateFormat("MM/yyyy");
			DateFormat outputFormatter = new SimpleDateFormat("yyyyMM");
			//parse date strictly
			try {
				Date date1 =inputFormatter.parse(date);
				String dateOutput = outputFormatter.format(date1);
				return dateOutput;
			} catch (ParseException e) {
				System.out.println("Invalid date input");
				continue;
			}
		}while (true);
	}
	/**
	 * Helper function to request user for date.
	 * Ensures that string input is formatted according to yyyy
	 * Output string will be yyyy
	 * @return date in string
	 */
	public static String stringDate4(String promptMessage){
		String date;
		do {
			System.out.println(promptMessage);
			date = sc.nextLine();
			//sc.nextLine();
			//String dateFormat = "dd/MM/yyyy";
			DateFormat inputFormatter = new SimpleDateFormat("yyyy");
			DateFormat outputFormatter = new SimpleDateFormat("yyyy");
			//parse date strictly
			try {
				Date date1 =inputFormatter.parse(date);
				String dateOutput = outputFormatter.format(date1);
				return dateOutput;
			} catch (ParseException e) {
				System.out.println("Invalid date input");
				continue;
			}
		}while (true);
	}

	/**
	 * Helper function to get customer's name.
	 * @return user's name.
	 */
	public static String getName(){
		System.out.println("Please enter your name: ");
		String name = sc.nextLine();

		return name.strip();
	}

    /**
	 * Helper function to get users contact no, matching digit regex.
	 * @return User's contact number in string.
	 */
	public static String getContactNo(){
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
     * Helper function for getting integers
     * @param min min allowed int
     * @param max max allowed int
     * @param req request string for user
     * @param er error string for uwser
     * @return the integer.
     */
	public static int getInt(int min, int max, String req, String er){
		int output;
		do {
			System.out.println(req);
			String unsafeInput = sc.next();
			sc.nextLine();
			if (unsafeInput.matches("\\d+")){
				output = Integer.parseInt(unsafeInput);
				if (!(output >= min && output <= max)){
					output = -1;
					System.out.println(er);
				}
			}
			else{
				output = -1;
				System.out.println(er);
			}
		} while(output == -1);
		return output;
	}

	/**
	 * Helper function for getting Doubles
	 * @param min min allowed int
	 * @param max max allowed int
	 * @param req request string for user
	 * @param er error string for uwser
	 * @return the integer.
	 */
	public static double getDouble(int min, int max, String req, String er){
		double output;
		do {
			System.out.println(req);
			String unsafeInput = sc.next();
			sc.nextLine();
			// allow 10 or 10.00
			if (unsafeInput.matches("\\d+")|| unsafeInput.matches("\\d+\\.\\d+")){
				output = Double.parseDouble(unsafeInput);
				if (!(output >= min && output <= max)){
					output = -1;
					System.out.println(er);
				}
			}
			else{
				output = -1;
				System.out.println(er);
			}
		} while(output == -1);
		return output;
	}

	/**
	 * Helper function for getting String
	 * @param promptMessage the message to warn user
	 * @return the String.
	 */
	public static String getString(String promptMessage) {
		System.out.println(promptMessage);
		String name = sc.nextLine();
		return name.strip();
	}
}
