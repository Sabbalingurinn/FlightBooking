package solid.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import solid.models.Booking;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingControllerTest {

    private BookingController controller;
    private InMemoryBookingDB fakeBookingDB;

    @BeforeEach
    void setUp() {
        fakeBookingDB = new InMemoryBookingDB();
        controller = new BookingController(fakeBookingDB);
    }

    @Test
    void createBooking() {
        Booking booking = controller.createBooking("B1", "0101991234", "FL001", "1A",
                true, false, true, 300.0);
        assertNotNull(booking);
        assertEquals("B1", booking.getBookingId());
        assertEquals(300.0 + 15.0 + 20.0, booking.getTotalCost()); // includes priority + luggage
    }

    @Test
    void cancelBooking() {
        Booking booking = controller.createBooking("B2", "0101991234", "FL001", "1B",
                false, false, false, 100.0);
        controller.cancelBooking(booking);
        assertNull(controller.getBookingById("B2"));
    }

    @Test
    void cancelBookingById() {
        controller.createBooking("B3", "0101991234", "FL001", "1C",
                false, false, true, 150.0);
        controller.cancelBooking("B3");
        assertNull(controller.getBookingById("B3"));
    }

    @Test
    void getBookingById() {
        controller.createBooking("B4", "0101991234", "FL001", "1D",
                false, true, false, 180.0);
        Booking booking = controller.getBookingById("B4");
        assertNotNull(booking);
        assertEquals("1D", booking.getSeatNumber());
    }

    @Test
    void getBookingsBySsn() {
        controller.createBooking("B5", "0101991234", "FL001", "1E", true, false, false, 200.0);
        controller.createBooking("B6", "0101991234", "FL001", "1F", false, true, true, 220.0);
        List<Booking> bookings = controller.getBookingsBySsn("0101991234");
        assertEquals(2, bookings.size());
    }

    @Test
    void getAllBookings() {
        controller.createBooking("B7", "0101991234", "FL001", "2A", false, false, false, 100.0);
        controller.createBooking("B8", "0202992345", "FL002", "2B", false, true, true, 300.0);
        List<Booking> all = controller.getBookings();
        assertEquals(2, all.size());
    }

    // In-memory stub for BookingDB
    static class InMemoryBookingDB extends solid.db.BookingDB {
        private final List<Booking> bookings = new ArrayList<>();

        @Override
        public void createBooking(Booking booking) {
            bookings.add(booking);
        }

        @Override
        public void cancelBooking(String bookingId) {
            bookings.removeIf(b -> b.getBookingId().equals(bookingId));
        }

        @Override
        public Booking getBooking(String bookingId) {
            return bookings.stream().filter(b -> b.getBookingId().equals(bookingId)).findFirst().orElse(null);
        }

        @Override
        public List<Booking> getBookings(String ssn) {
            return bookings.stream().filter(b -> b.getTravelerSsn().equals(ssn)).toList();
        }

        @Override
        public List<Booking> getAllBookings() {
            return new ArrayList<>(bookings);
        }

        @Override
        public void addSeat(String seatNumber, String flightNumber) {
            // simulate seat booking
        }

        @Override
        public void removeSeat(String seatNumber, String flightNumber) {
            // simulate seat removal
        }
    }
}