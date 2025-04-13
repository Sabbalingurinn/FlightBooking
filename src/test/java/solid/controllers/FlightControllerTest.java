package solid.controllers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import solid.models.Flight;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class FlightControllerTest {

    private FlightController flightController;
    private Flight flight1, flight2, flight3;

    @BeforeEach
    void setUp() {
        flight1 = new Flight("IA123", "Icelandair", "KEF", "LAX",
                LocalDateTime.of(2025, 4, 15, 10, 30),
                LocalDateTime.of(2025, 4, 15, 14, 45),
                150, 350.00);

        flight2 = new Flight("BA456", "British Airways", "LHR", "JFK",
                LocalDateTime.of(2025, 7, 22, 8, 15),
                LocalDateTime.of(2025, 7, 22, 11, 50),
                180, 500.00);

        flight3 = new Flight("DL789", "Delta Airlines", "ATL", "LAX",
                LocalDateTime.of(2025, 12, 5, 18, 0),
                LocalDateTime.of(2025, 12, 5, 21, 30),
                200, 400.00);

        List<Flight> testFlights = new ArrayList<>();
        testFlights.add(flight1);
        testFlights.add(flight2);
        testFlights.add(flight3);

        flightController = new FlightController(testFlights);
    }

    @Test
    void testSearchFlightByNumber() {
        Flight result = flightController.getFlight("IA123");
        assertEquals(1, result.size());
        assertEquals("IA123", result.get(0).getFlightNumber());
    }

    @Test
    void testSearchFlightByAirports() {
        List<Flight> result = flightController.searchFlights(null, "KEF", "LAX", null, null, null);
        assertEquals(1, result.size());
        assertEquals("KEF", result.get(0).getSrcAirport());
        assertEquals("LAX", result.get(0).getDestAirport());
    }

    @Test
    void testSortFlightsByPrice() {
        List<Flight> flights = flightController.searchFlights();
        List<Flight> sortedFlights = flightController.sortBy(flights,"price");

        assertEquals(flight1, sortedFlights.get(0)); // Cheapest first
        assertEquals(flight3, sortedFlights.get(1));
        assertEquals(flight2, sortedFlights.get(2)); // Most expensive last
    }

    @Test
    void testSortFlightsByDepartureTime() {
        List<Flight> flights = flightController.searchFlights();
        List<Flight> sortedFlights = flightController.sortBy(flights,"departure");

        // Laga
        assertEquals(flight1, sortedFlights.get(0)); // Earliest
        assertEquals(flight2, sortedFlights.get(1));
        assertEquals(flight3, sortedFlights.get(2)); // Latest
    }

    @Test
    void testFilterByAirline() {
        List<Flight> flights = flightController.searchFlights();
        List<Flight> sortedFlights = flightController.filterBy(flights, "airline", Collections.singletonList("AirIceland"));
        // assertEquals(1, result.size());
        // assertEquals("DL789", result.get(0).getFlightNumber());

        // Klára test
    }
}