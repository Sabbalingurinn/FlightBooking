package solid.api;

import static spark.Spark.*;

public class ControllerAPI {
    public static void main(String[] args) {
        port(4567);

        // Þjónar static skrár eins og index.html úr resources/public
        staticFileLocation("/public");

        // Register API routes
        BookingAPI.registerRoutes();
        FlightAPI.registerRoutes();

        System.out.println("Server keyrir á http://localhost:4567");
    }
}
