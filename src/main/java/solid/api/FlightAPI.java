package solid.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import solid.controllers.FlightController;
import solid.models.Flight;
import spark.Request;
import spark.Response;
import spark.Route;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static spark.Spark.*;

public class FlightAPI {
    private static final FlightController flightController = new FlightController();
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public static Route allFlights = (req, res) -> {
        res.type("application/json");
        return gson.toJson(flightController.searchFlights());
    };

    private static final Route mainFlightsRoute = (req, res) -> {
        String filterType = req.queryParams("filter");
        String filterValuesParam = req.queryParams("filter_values");
        String sortType = req.queryParams("sort");

        List<Flight> flights = flightController.searchFlights();

        System.out.println(filterValuesParam);

        if (filterType != null && filterValuesParam != null) {
            List<String> filterValues = Arrays.asList(filterValuesParam.split(","));
            System.out.println(filterValues);
            flights = flightController.filterBy(flights, filterType, filterValues);
        }


        if (sortType != null) {
            flights = flightController.sortBy(flights, sortType);
        }

        return gson.toJson(flights);
    };


    public static Route searchByNumber = (Request req, Response res) -> {
        String flightNumber = req.params(":flightNumber");
        Flight flight = flightController.getFlight(flightNumber);
        if (flight != null) {
            res.type("application/json");
            return gson.toJson(flight);
        } else {
            res.status(404);
            return "Flight not found";
        }
    };

    public static Route randomFlight = (req, res) -> {
        Flight flight = flightController.getRandomFlight();
        if (flight != null) {
            res.type("application/json");
            return gson.toJson(flight);
        } else {
            res.status(404);
            return "No flights available";
        }
    };


    public static void registerRoutes() {
        // Get all flights

        get("/api/flights", mainFlightsRoute);
        get("/api/flights/", mainFlightsRoute);

        // Get flight by flight number
        get("/api/flights/number/:flightNumber", searchByNumber);
        get("/api/flights/number/:flightNumber/", searchByNumber);

        // Get a random flight
        get("/api/flights/random", randomFlight);
        get("/api/flights/random/", randomFlight);
    }
}