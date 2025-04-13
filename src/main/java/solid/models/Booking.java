package solid.models;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Booking {

    // Base info
    private final String bookingId;
    private final String travelerSsn;
    private final String flightNumber;
    private String seatNumber;

    // Extra services
    private Boolean hasPriorityBoarding;
    private Boolean hasOnBoardMeal;
    private Boolean hasExtraLuggage;

    // Cost information
    private Double totalCost;
    private Double flightPrice;
    private final Map<String, Double> totalCostBreakdown = new HashMap<>();

    // Constructor
    public Booking(String bookingId, String travelerSsn, String flightNumber, String seatNumber, Boolean hasPriorityBoarding, Boolean hasOnBoardMeal, Boolean hasExtraLuggage, Double flightPrice) {
        this.bookingId = bookingId;
        this.travelerSsn = travelerSsn;
        this.flightNumber = flightNumber;
        this.seatNumber = seatNumber;
        this.hasPriorityBoarding = hasPriorityBoarding;
        this.hasOnBoardMeal = hasOnBoardMeal;
        this.hasExtraLuggage = hasExtraLuggage;

        // Updates the totalCostBreakdown according to the options the traveler chose,
        // also updates the totalCost for the total value
        this.calculateTotalCostBreakdown(flightPrice);
    }

    // Calculates the total cost and updates the totalCostBreakDown
    // value according to the chosen options for the booking
    // and updates the totalCost value
    private void calculateTotalCostBreakdown(double flightPrice) {

        final Double PRIORITY_PRICE = 15.0;
        final Double MEAL_PRICE = 10.0;
        final Double EXTRA_LUGGAGE_PRICE = 20.0;

        double total = 0.0;

        // Map of chosen options
        Map<String, Boolean> selections = Map.of(
                "flight", true,
                "priority", hasPriorityBoarding,
                "meal", hasOnBoardMeal,
                "extra_luggage", hasExtraLuggage
        );

        Map<String, Double> prices = Map.of(
                "flight", flightPrice,
                "priority", PRIORITY_PRICE,
                "meal", MEAL_PRICE,
                "extra_luggage", EXTRA_LUGGAGE_PRICE
        );

        // Adds chosen options to the totalCostBreakDown
        for (String key : selections.keySet()) {
            boolean selected = selections.get(key);
            double price = selected ? prices.get(key) : 0.0;
            this.totalCostBreakdown.put(key, price);
            total += price;
        }

        this.totalCostBreakdown.put("total", total);
        this.totalCost = total;
    }

    public String getBookingId() {
        return this.bookingId;
    }

    public String getTravelerSsn() {
        return this.travelerSsn;
    }

    public String getFlightNumber() {
        return this.flightNumber;
    }
    public String getSeatNumber() {
        return this.seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Boolean getHasPriorityBoarding() {
        return this.hasPriorityBoarding;
    }

    // Ability to add or remove priority boarding from the booking
    public void setHasPriorityBoarding(Boolean hasPriorityBoarding) {
        this.hasPriorityBoarding = hasPriorityBoarding;
        this.calculateTotalCostBreakdown(flightPrice);
    }

    public Boolean getHasOnBoardMeal() {
        return this.hasOnBoardMeal;
    }

    // Ability to add or remove on board meal from the booking
    public void setHasOnBoardMeal(Boolean hasOnBoardMeal) {
        this.hasOnBoardMeal = hasOnBoardMeal;
        this.calculateTotalCostBreakdown(flightPrice);
    }

    public Boolean getHasExtraLuggage() {
        return this.hasExtraLuggage;
    }

    // Ability to add or remove on extra luggage from the booking
    public void setHasExtraLuggage(Boolean hasExtraLuggage) {
        this.hasExtraLuggage = hasExtraLuggage;
        this.calculateTotalCostBreakdown(flightPrice);
    }

    public Double getTotalCost() {
        return this.totalCost;
    }

    public Map<String, Double> getTotalCostBreakdown() {
        return this.totalCostBreakdown;
    }
}
