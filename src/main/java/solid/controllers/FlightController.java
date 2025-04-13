package solid.controllers;

import solid.models.Flight;
import solid.db.FlightDB;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class FlightController {
    private final FlightDB flightDB;
    private final List<Flight> inMemoryFlights;

    // Default constructor (uses real DB)
    public FlightController() {
        this.flightDB = new FlightDB();
        this.inMemoryFlights = null;
    }

    // Test constructor (uses in-memory flight list)
    public FlightController(List<Flight> testFlights) {
        this.flightDB = null;
        this.inMemoryFlights = testFlights;
    }

    private static LocalDateTime parseAsDT(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateStr, formatter);
    }

    // Returns all flights
    public List<Flight> searchFlights() {
        if (inMemoryFlights != null) {
            return inMemoryFlights;
        } else {
            assert flightDB != null;
            return flightDB.searchFlights();
        }
    }

    // Search flights by source and destination
    public List<Flight> searchFlights(String srcAirport, String destAirport) {
        assert flightDB != null;
        return flightDB.searchFlights(srcAirport, destAirport, null, null, null, null, null);
    }

    public List<Flight> searchFlights(String srcAirport, String destAirport,
                                      String departureStr, String arrivalStr, Double minPrice, Double maxPrice, String airline) {

        List<Flight> flights;
        if (inMemoryFlights != null) {
            flights = inMemoryFlights;
        } else {
            assert flightDB != null;
            flights = flightDB.searchFlights(srcAirport, destAirport, parseAsDT(departureStr), parseAsDT(arrivalStr), minPrice, maxPrice, airline);
        }

        return flights;
    }

    // Get flight by flight number
    public Flight getFlight(String flightNumber) {
        if (inMemoryFlights != null) {
            return inMemoryFlights.stream()
                    .filter(f -> f.getFlightNumber().equalsIgnoreCase(flightNumber))
                    .findFirst().orElse(null);
        }
        assert flightDB != null;
        return flightDB.getFlight(flightNumber);
    }

    public List<Flight> sortBy(List<Flight> flights, String sortType) {
        return switch (sortType) {
            case "airline" -> flights.stream()
                    .sorted(Comparator.comparing(Flight::getAirline))
                    .collect(Collectors.toList());
            case "departure" -> flights.stream()
                    .sorted(Comparator.comparing(Flight::getDepartureTime))
                    .collect(Collectors.toList());
            case "arrival" -> flights.stream()
                    .sorted(Comparator.comparing(Flight::getArrivalTime))
                    .collect(Collectors.toList());
            default ->
                // sets price as the default sorting method
                    flights.stream()
                            .sorted(Comparator.comparing(Flight::getPrice))
                            .collect(Collectors.toList());
        };
    }

    public List<Flight> filterBy(List<Flight> flights, String filterType, List<String> filters) {
        return switch (filterType.toLowerCase()) {
            case "airline" -> flights.stream()
                    .filter(f -> filters.contains(f.getAirline()))
                    .collect(Collectors.toList());
            case "departure" -> flights.stream()
                    .filter(f -> filters.contains(f.getDepartureTime().toLocalDate().toString()))
                    .collect(Collectors.toList());
            case "arrival" -> flights.stream()
                    .filter(f -> filters.contains(f.getArrivalTime().toLocalDate().toString()))
                    .collect(Collectors.toList());
            default -> flights;
        };
    }

    public Flight getRandomFlight() {
        List<Flight> flights = searchFlights();
        if (flights.isEmpty()) return null;
        return flights.get(new Random().nextInt(flights.size()));
    }
}