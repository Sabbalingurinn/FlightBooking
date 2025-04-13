package solid.models;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Flight {
    private final String flightNumber;
    private final String airline;
    private final String srcAirport;
    private final String destAirport;

    // Dates
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;

    // Seats
    private int availableSeatCount;
    private int takenSeatCount;
    private List<Seat> seat;

    private Double price;

    public Flight(String flightNumber, String airline, String srcAirport, String destAirport,
                  LocalDateTime departureDateTime, LocalDateTime arrivalDateTime, int availableSeatCount, double price) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.srcAirport = srcAirport;
        this.destAirport = destAirport;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.availableSeatCount = availableSeatCount;
        this.takenSeatCount = 0;
        this.price = price;
    }

    public Map<String, Object> getFlightDetails() {
        Map<String, Object> details = new HashMap<>();
        details.put("flightNumber", flightNumber);
        details.put("airline", airline);
        details.put("sourceAirport", srcAirport);
        details.put("destinationAirport", destAirport);
        details.put("departureTime", departureDateTime);
        details.put("arrivalTime", arrivalDateTime);
        details.put("availableSeatCount", availableSeatCount);
        details.put("takenSeatCount", takenSeatCount);
        details.put("price", price);
        return details;
    }

    // Getters
    public String getFlightNumber() { return this.flightNumber; }
    public String getAirline() { return this.airline; }
    public String getSrcAirport() { return this.srcAirport; }
    public String getDestAirport() { return this.destAirport; }
    public LocalDateTime getDepartureTime() { return this.departureDateTime; }
    public LocalDateTime getArrivalTime() { return this.arrivalDateTime; }
    public int getAvailableSeats() { return this.availableSeatCount; }
    public int getTakenSeatCount() { return this.takenSeatCount; }
    public double getPrice() { return this.price; }

    // Setters
    public void setDepartureDateTime(LocalDateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }
    public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }
    public void setAvailableSeats(int availableSeats) {
        this.availableSeatCount = availableSeats;
    }

    /*
    public void increaseTakenSeats() {
        this.availableSeatCount -= 1;
        this.takenSeatCount += 1;
    }

    public void decreaseTakenSeats() {
        this.availableSeatCount += 1;
        this.takenSeatCount -= 1;
    }
    */

    /*
    public void increaseAvailableSeats() {
        this.availableSeatCount -= 1;
        this.takenSeatCount += 1;
    }

    public void decreaseAvailableSeats() {
        this.availableSeatCount += 1;
        this.takenSeatCount -= 1;
    }
    */

    /*
    @Override
    public String toString() {
        return String.format("Flight %s (%s) from %s to %s at %s, ETA at %s, available seats: %d Price: $%.2f",
                this.flightNumber, this.airline, this.srcAirport, this.destAirport, this.departureDateTime,
                this.arrivalDateTime, this.availableSeatCount, this.takenSeatCount, this.price);
    }
    */
}
