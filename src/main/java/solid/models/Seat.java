package solid.models;

public class Seat {
    private final String seatNumber;
    private Boolean isTaken;
    private String flightNumber;

    public Seat(String seatNumber, String flightNumber, Boolean isTaken) {
        this.seatNumber = seatNumber;
        this.isTaken = isTaken;
        this.flightNumber = flightNumber;
    }

    public Seat(String seatNumber, String flightId) {
        this(seatNumber, flightId, false);
    }

    public String getSeatNumber() {
        return this.seatNumber;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightId(String flightId) {
        this.flightNumber = flightNumber;
    }

    public Boolean getIsTaken() {
        return isTaken;
    }

    public void setIsTaken(Boolean isTaken) {
        this.isTaken = isTaken;
    }
}
