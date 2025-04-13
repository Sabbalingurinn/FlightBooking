package solid.controllers;

import solid.db.BookingDB;
import solid.models.Booking;

import java.util.List;

public class BookingController {
    private final BookingDB bookingDB;

    // Production constructor
    public BookingController() {
        this.bookingDB = new BookingDB();
    }

    // Test constructor (accepts testable BookingDB)
    public BookingController(BookingDB testBookingDB) {
        this.bookingDB = testBookingDB;
    }

    // Creates a new booking and returns it
    public Booking createBooking(String bookingId, String travelerSsn, String flightNumber,
                                 String seatNumber, Boolean hasPriorityBoarding,
                                 Boolean hasOnboardMeal, Boolean hasExtraLuggage, double flightPrice) {

        Booking booking = new Booking(bookingId, travelerSsn, flightNumber, seatNumber,
                hasPriorityBoarding, hasOnboardMeal, hasExtraLuggage, flightPrice);

        try {
            bookingDB.createBooking(booking);
            this.chooseSeat(booking);
        } catch (Exception e) {
            System.err.println("Failed to create booking: " + e);
        }

        return booking;
    }

    public void cancelBooking(Booking booking) {
        try {
            bookingDB.cancelBooking(booking.getBookingId());
            this.removeSeat(booking);
        } catch (Exception e) {
            System.err.println("Failed to cancel booking: " + e);
        }
    }

    public void cancelBooking(String bookingId) {
        try {
            bookingDB.cancelBooking(bookingId);
        } catch (Exception e) {
            System.err.println("Failed to cancel booking: " + e);
        }
    }

    public Booking getBookingById(String bookingId) {


        return bookingDB.getBooking(bookingId);
    }

    public List<Booking> getBookingsBySsn(String ssn) {
        return bookingDB.getBookings(ssn);
    }

    public List<Booking> getBookings() {
        return bookingDB.getAllBookings();
    }

    private void chooseSeat(Booking booking) {
        try {
            bookingDB.addSeat(booking.getSeatNumber(), booking.getFlightNumber());
        } catch (Exception e) {
            System.err.println("Failed to add seat: " + e);
        }
    }

    private void removeSeat(Booking booking) {
        try {
            bookingDB.removeSeat(booking.getSeatNumber(), booking.getFlightNumber());
        } catch (Exception e) {
            System.err.println("Failed to remove seat: " + e);
        }
    }
}