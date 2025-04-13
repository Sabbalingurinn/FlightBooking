package solid.models;

import java.util.ArrayList;
import java.util.List;

public class Traveler {
    private final String ssn;
    private final String name;
    private final List<Booking> bookings;

    public Traveler(String ssn, String name, String passportId) {
        this.ssn = ssn;
        this.name = name;
        this.bookings = new ArrayList<Booking>();
    }

    // Getters
    public String getSsn() {
        return this.ssn;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public String getName() {
        return this.name;
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }

    public void removeBooking(Booking booking) {
        this.bookings.remove(booking);
    }
}
