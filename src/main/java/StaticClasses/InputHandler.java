package StaticClasses;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
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
public final class InputHandler {

    /**
	 * Helper function to request user for datetime.
	 * @param after This signifies if the function checks if the provided date and time is after now or not.
	 * @return LocalDateTime obj.
	 */
	public static LocalDateTime getDate(boolean after){
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
	 * Helper function to get customer's name.
	 * @return user's name.
	 */
	public static String getName(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter your name: ");
		String name = sc.nextLine();

		return name.strip();
	}

    /**
	 * Helper function to get users contact no, matching digit regex.
	 * @return User's contact number in string.
	 */
	public static String getContactNo(){
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
     * Helper function for getting integers
     * @param min min allowed int
     * @param max max allowed int
     * @param req request string for user
     * @param er error string for uwser
     * @return the integer.
     */
	public static int getInt(int min, int max, String req, String er){
		Scanner sc = new Scanner(System.in);
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
		Scanner sc = new Scanner(System.in);
		double output;
		do {
			System.out.println(req);
			String unsafeInput = sc.next();
			sc.nextLine();
			if (unsafeInput.matches("\\d+\\.\\d+")){
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
}
