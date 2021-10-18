import java.util.Vector;

public class ReservationManager {
	private ArrayList<Table> _tables;
	private HashMap<int, ArrayList<Reservation>> _reservations;
	private RRPSS _unnamed_RRPSS_;
	private Vector<Table> _unnamed_Table_ = new Vector<Table>();
	private Reservation _unnamed_Reservation_;

	public Boolean LoadTables() {
		throw new UnsupportedOperationException();
	}

	public Boolean RemoveReservation() {
		throw new UnsupportedOperationException();
	}

	public Boolean CreateReservation() {
		throw new UnsupportedOperationException();
	}

	public void CheckReservation() {
		throw new UnsupportedOperationException();
	}

	public int CheckAvailability(Date aDate, Time aTime, int aNo_of_pax) {
		throw new UnsupportedOperationException();
	}

	public Boolean LoadReservations() {
		throw new UnsupportedOperationException();
	}
}