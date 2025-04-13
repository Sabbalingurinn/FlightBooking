package solid.db;

import solid.models.Flight;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FlightDB {
    private DB db;

    public FlightDB() {
        this.db = new DB();
    }

    private void initAndOpen() {
        this.db = new DB();
        db.open();
    }

    public static LocalDateTime parseAsDT(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateStr, formatter);
    }

    private Flight extractFlight(ResultSet rs) throws SQLException {
        return new Flight(
                rs.getString("flight_number"),
                rs.getString("airline"),
                rs.getString("src_airport"),
                rs.getString("dest_airport"),
                parseAsDT(rs.getString("departure_time")),
                parseAsDT(rs.getString("arrival_time")),
                rs.getInt("available_seats"),
                rs.getInt("price")
                );
    }

    public List<Flight> searchFlights(String srcAirport, String destAirport, LocalDateTime departureTime, LocalDateTime arrivalTime, Double minPrice, Double maxPrice, String airline) {
        List<Flight> flights = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM Flight WHERE src_airport = ? AND dest_airport = ?");

        // Add dynamic filters to the query
        if (minPrice != null && maxPrice != null) {
            query.append(" AND price BETWEEN ? AND ?");
        } else if (minPrice != null) {
            query.append(" AND price >= ?");
        } else if (maxPrice != null) {
            query.append(" AND price <= ?");
        }

        if (airline != null && !airline.isEmpty()) {
            query.append(" AND airline = ?");
        }
        if (departureTime != null) {
            query.append(" AND departure_time = ?");
        }
        if (arrivalTime != null) {
            query.append(" AND arrival_time = ?");
        }

        try {
            initAndOpen();

            PreparedStatement stmt = db.prepare(query.toString(), srcAirport, destAirport);

            int paramIndex = 3; // Start after src and dest parameters
            if (minPrice != null && maxPrice != null) {
                stmt.setDouble(paramIndex++, minPrice);
                stmt.setDouble(paramIndex++, maxPrice);
            } else if (minPrice != null) {
                stmt.setDouble(paramIndex++, minPrice);
            } else if (maxPrice != null) {
                stmt.setDouble(paramIndex++, maxPrice);
            }

            if (airline != null && !airline.isEmpty()) {
                stmt.setString(paramIndex++, airline);
            }

            if (departureTime != null) {
                stmt.setString(paramIndex++, departureTime.toString());  // Set departureTime as String
            }

            if (arrivalTime != null) {
                stmt.setString(paramIndex++, arrivalTime.toString());  // Set arrivalTime as String
            }

            // Execute the query and collect the results
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Flight flight = extractFlight(rs);
                flights.add(flight);
            }

        } catch (SQLException e) {
            System.err.println("Error searching flights: " + e.getMessage());
        } finally {
            db.close();
        }

        return flights;
    }

    // Returns all flights
    public List<Flight> searchFlights() {
        List<Flight> flights = new ArrayList<>();
        initAndOpen();

        try (ResultSet rs = db.query("SELECT * FROM Flight")) {
            while (rs.next()) {
                flights.add(extractFlight(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving flights: " + e.getMessage());
        } finally {
            db.close();
        }

        return flights;
    }

    /*
    public List<Flight> searchFlights(String srcAirport, String destAirport, LocalDateTime departureTime) {
        List<Flight> flights = new ArrayList<>();
        initAndOpen();

        try (ResultSet rs = db.query(
                "SELECT * FROM Flight WHERE src_airport = ? AND dest_airport = ? AND departure_time = ?",
                srcAirport, destAirport, departureTime.toString())) {
            while (rs.next()) {
                flights.add(extractFlight(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error searching flights: " + e.getMessage());
        } finally {
            db.close();
        }

        return flights;
    }
    */

    public Flight getFlight(String flightNumber) {
        Flight flight = null;
        initAndOpen();

        try (ResultSet rs = db.query("SELECT * FROM Flight WHERE flight_number = ?", flightNumber)) {
            if (rs.next()) {
                flight = extractFlight(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving flight: " + e.getMessage());
        } finally {
            db.close();
        }

        return flight;
    }

    public List<Flight> sortFlightsBy(String column) {
        List<Flight> flights = new ArrayList<>();
        initAndOpen();

        try (ResultSet rs = db.query("SELECT * FROM Flight ORDER BY " + column + " ASC")) {
            while (rs.next()) {
                flights.add(extractFlight(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error sorting flights: " + e.getMessage());
        } finally {
            db.close();
        }

        return flights;
    }

    public List<Flight> sortFlightsByPrice() {
        return sortFlightsBy("price");
    }

    public List<Flight> sortFlightsByDepartureTime() {
        return sortFlightsBy("departure_time");
    }

    public List<Flight> sortFlightsByArrivalTime() {
        return sortFlightsBy("arrival_time");
    }

    public List<Flight> filterByAirline(String airline) {
        List<Flight> flights = new ArrayList<>();
        initAndOpen();

        try (ResultSet rs = db.query("SELECT * FROM Flight WHERE airline = ?", airline)) {
            while (rs.next()) {
                flights.add(extractFlight(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error filtering flights: " + e.getMessage());
        } finally {
            db.close();
        }

        return flights;
    }
}
