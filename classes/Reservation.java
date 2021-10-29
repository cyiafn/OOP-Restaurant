public class Reservation {
	private String _reservationID;
	private LocalDateTime _dt;
	private Integer _noOfPax;
	private String _name;
	private String _contactNo;
	private Boolean _tableNo;
	private ReservationStatus _status;
	private ReservationManager _unnamed_ReservationManager_;

	public void print() {
		throw new UnsupportedOperationException();
	}

	public String[] getLineCSVFormat() {
		throw new UnsupportedOperationException();
	}
}