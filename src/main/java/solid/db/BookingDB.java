package solid.db;

import solid.models.Booking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDB {
    private final FlightDB flightDB;
    private DB db;

    public BookingDB() {
        this.flightDB = new FlightDB();
    }

    private void initAndOpen() {
        this.db = new DB();
        db.open();
    }

    public void createBooking(Booking booking) {
        String query = "INSERT INTO Booking (booking_id, traveler_ssn, flight_number, seat_number, priority_boarding, on_board_meal, extra_luggage) VALUES (?, ?, ?, ?, ?, ?, ?)";
        initAndOpen();

        try {
            db.update(query,
                    booking.getBookingId(),
                    booking.getTravelerSsn(),
                    booking.getFlightNumber(),
                    booking.getSeatNumber(),
                    booking.getHasPriorityBoarding(),
                    booking.getHasOnBoardMeal(),
                    booking.getHasExtraLuggage()
            );
        } catch (SQLException e) {
            System.err.println("Failed to create booking: " + e.getMessage());
        } finally {
            db.close();
        }
    }

    // Cancels booking
    public void cancelBooking(String bookingId) {
        initAndOpen();

        try {
            db.update("DELETE FROM Booking WHERE booking_id = ?", bookingId);
        } catch (SQLException e) {
            System.err.println("Failed to cancel booking: " + e.getMessage());
        } finally {
            db.close();
        }
    }

    public Booking getBooking(String bookingId) {
        initAndOpen();
        Booking booking = null;

        try (ResultSet rs = db.query("SELECT * FROM Booking WHERE booking_id = ?", bookingId)) {
            if (rs.next()) {
                booking = extractBooking(rs);
            }
        } catch (SQLException e) {
            System.err.println("Failed to get booking: " + e.getMessage());
        } finally {
            db.close();
        }

        return booking;
    }

    public List<Booking> getBookings(String ssn) {
        List<Booking> bookings = new ArrayList<>();
        initAndOpen();

        try (ResultSet rs = db.query("SELECT * FROM Booking WHERE traveler_ssn = ?", ssn)) {
            while (rs.next()) {
                bookings.add(extractBooking(rs));
            }
        } catch (SQLException e) {
            System.err.println("Failed to get bookings: " + e.getMessage());
        } finally {
            db.close();
        }

        return bookings;
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        System.out.println("sdflkhsdkjfhd");
        initAndOpen();

        try (ResultSet rs = db.query("SELECT * FROM Booking")) {
            while (rs.next()) {
                bookings.add(extractBooking(rs));
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve all bookings: " + e.getMessage());
        } finally {
            db.close();
        }

        return bookings;
    }

    public void addSeat(String seatNumber, String flightNumber) {
        initAndOpen();

        try {
            db.update("UPDATE Flight SET available_seats = available_seats - 1 WHERE flight_number = ?", flightNumber);
            db.update("UPDATE Seat SET isTaken = TRUE WHERE flight_number = ? AND seat_number = ?", flightNumber, seatNumber);
        } catch(Exception e) {
            System.err.println("Failed to add seat: " + e.getMessage());
        } finally {
            db.close();
        }
    }

    public void removeSeat(String seatNumber, String flightNumber) {
        initAndOpen();

        try {
            db.update("UPDATE Flight SET available_seats = available_seats + 1 WHERE flight_number = ?", flightNumber);
            db.update("UPDATE Seat SET isTaken = FALSE WHERE flight_number = ? AND seat_number = ?", flightNumber, seatNumber);
        } catch(Exception e) {
            System.err.println("Failed to add seat: " + e.getMessage());
        } finally {
            db.close();
        }
    }

    private Booking extractBooking(ResultSet rs) throws SQLException {
        String bookingId = rs.getString("booking_id");
        String travelerSsn = rs.getString("traveler_ssn");
        String flightNumber = rs.getString("flight_number");
        String seatPosition = rs.getString("seat_number");
        boolean priority = rs.getBoolean("priority_boarding");
        boolean meal = rs.getBoolean("on_board_meal");
        boolean luggage = rs.getBoolean("extra_luggage");

        System.out.println(flightNumber);
        //Flight flight = flightDB.getFlight(flightNumber);
        //double flightPrice = flight.getPrice();

        return new Booking(bookingId, travelerSsn, flightNumber, seatPosition, priority, meal, luggage, 0.0);
    }
}
