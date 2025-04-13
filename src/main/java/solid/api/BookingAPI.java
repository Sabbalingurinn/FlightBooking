package solid.api;

import com.google.gson.Gson;
import solid.controllers.BookingController;
import solid.models.Booking;
import solid.db.FlightDB;

import static spark.Spark.*;

public class BookingAPI {
    private static final BookingController bookingController = new BookingController();
    private static final Gson gson = new Gson();

    public static void registerRoutes() {
        // Create a booking
        post("/api/bookings", (req, res) -> {
            try {
                BookingRequest data = gson.fromJson(req.body(), BookingRequest.class);

                // Athuga hvort flightNumber sé til
                FlightDB flightDB = new FlightDB();
                if (flightDB.getFlight(data.flightNumber) == null) {
                    res.status(400);
                    return "Flight number does not exist";
                }

                // Create the booking via the controller
                Booking booking = bookingController.createBooking(
                        data.bookingId,
                        data.travelerSsn,
                        data.flightNumber,
                        data.seatNumber,
                        data.hasPriorityBoarding,
                        data.hasOnboardMeal,
                        data.hasExtraLuggage,
                        data.flightPrice
                );

                // Set response status and return the booking as JSON
                res.status(201);
                return gson.toJson(booking);
            } catch (Exception e) {
                res.status(400);
                return "Failed to create booking: " + e.getMessage();
            }
        });

        // Get a booking by ID
        get("/api/bookings/:id", (req, res) -> {
            String bookingId = req.params(":id");
            Booking booking = bookingController.getBookingById(bookingId);
            if (booking != null) {
                return gson.toJson(booking);
            } else {
                res.status(404);
                return "Booking not found";
            }
        });

        // Get all bookings
        get("/api/bookings", (req, res) -> gson.toJson(bookingController.getBookings()));

        // Cancel a booking by ID
        delete("/api/bookings/:id", (req, res) -> {
            String bookingId = req.params(":id");
            bookingController.cancelBooking(bookingId);
            res.status(200);
            return "";
        });
    }

    // DTO for POST requests to create a booking
    public static class BookingRequest {
        public String bookingId;
        public String travelerSsn;
        public String flightNumber;
        public String seatNumber;
        public Boolean hasPriorityBoarding;
        public Boolean hasOnboardMeal;
        public Boolean hasExtraLuggage;
        public double flightPrice;
    }
}
