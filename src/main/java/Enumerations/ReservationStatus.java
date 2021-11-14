package Enumerations;

/**
 * Status of Reservation : CREATED, ACTIVE, REMOVED, EXPIRED, COMPLETED
 */
public enum ReservationStatus {
    /**
     * Reservation just created
     */
    CREATED,
    /**
     * The reservation is currently active, the person who made the reservation has entered the restaurant
     */
    ACTIVE,
    /**
     * The reservation has been deleted.
     */
    REMOVED,
    /**
     * The reservation has expired after 15 mins of the customer being late.
     */
    EXPIRED,
    /**
     * The reservation has been closed and payment has been made
     */
    COMPLETED
}
