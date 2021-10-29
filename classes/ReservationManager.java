import java.util.Vector;

public class ReservationManager {
	private ArrayList<Table> _tables;
	private HashMap<int, ArrayList<Reservation>> _reservations;
	private int _noOfTable;
	private String _tableFile = "Table.csv" {readOnly};
	private String _reservationFile = "Reservation.csv" {readOnly};
	private RRPSS _unnamed_RRPSS_;
	private Reservation _unnamed_Reservation_;
	private Vector<Table> _unnamed_Table_ = new Vector<Table>();
	private ReservationStatus _unnamed_ReservationStatus_;

	public Boolean LoadTables() {
		throw new UnsupportedOperationException();
	}

	public Boolean deleteReservation() {
		throw new UnsupportedOperationException();
	}

	public Boolean CreateReservation() {
		throw new UnsupportedOperationException();
	}

	public void CheckReservation() {
		throw new UnsupportedOperationException();
	}

	public void CheckAvailability() {
		throw new UnsupportedOperationException();
	}

	public Boolean LoadReservations() {
		throw new UnsupportedOperationException();
	}

	public Boolean CloseReservation() {
		throw new UnsupportedOperationException();
	}

	private LocalDateTime getUserDate() {
		throw new UnsupportedOperationException();
	}

	private String getUserName() {
		throw new UnsupportedOperationException();
	}

	private String getUserContactNo() {
		throw new UnsupportedOperationException();
	}

	private int getNoPax() {
		throw new UnsupportedOperationException();
	}

	public void cleanup() {
		throw new UnsupportedOperationException();
	}

	public String checkin() {
		throw new UnsupportedOperationException();
	}

	public String closeReservation() {
		throw new UnsupportedOperationException();
	}

	private String updateReservation(ReservationStatus aStat) {
		throw new UnsupportedOperationException();
	}
}