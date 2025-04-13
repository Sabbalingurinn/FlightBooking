# FlightBookingSystem
## HBV201G Final Project - Spring 2025
### Group 8F
Jakob Stefán Ívarsson - jakobstefan04 (), Óskar Víkingur Davíðsson - skarihacks (ovd2@hi.is), Sævar Breki Snorrason - Sabbalingurinn (sbs87@hi.is) og Viktor Óli Bjarkason - viktorob (vob7@hi.is)


The APIs are split into FlightAPI and BookingAPI, but run as one service (ControllerAPI)

# ControllerAPI Documentation

---
## BookingAPI Documentation

### 1. **Create a Booking**
- **URL**: `POST /api/bookings`
- **Description**: Creates a new booking and returns the created booking details.
- **Request Body**:
    ```json
    {
      "bookingId": "B1001",
      "travelerSsn": "123-45-6789",
      "flightNumber": "FL1001",
      "seatNumber": "10A",
      "hasPriorityBoarding": true,
      "hasOnboardMeal": true,
      "hasExtraLuggage": false,
      "flightPrice": 150.00
    }
    ```
- **Response** (201 - Created):
    ```json
    {
      "bookingId": "B1001",
      "travelerSsn": "123-45-6789",
      "flightNumber": "FL1001",
      "seatNumber": "10A",
      "hasPriorityBoarding": true,
      "hasOnboardMeal": true,
      "hasExtraLuggage": false,
      "flightPrice": 150.00
    }
    ```

---

### 2. **Get a Booking by ID**
- **URL**: `GET /api/bookings/:id`
- **Description**: Retrieves a booking by its ID.
- **Response** (Success):
    ```json
    {
      "bookingId": "B1001",
      "travelerSsn": "123-45-6789",
      "flightNumber": "FL1001",
      "seatNumber": "10A",
      "hasPriorityBoarding": true,
      "hasOnboardMeal": true,
      "hasExtraLuggage": false,
      "flightPrice": 150.00
    }
    ```
- **Response** (404 - Not Found):
    ```json
    "Booking not found"
    ```

---

### 3. **Get All Bookings**
- **URL**: `GET /api/bookings`
- **Description**: Retrieves a list of all bookings.
- **Response**:
    ```json
    [
      {
        "bookingId": "B1001",
        "travelerSsn": "123-45-6789",
        "flightNumber": "FL1001",
        "seatNumber": "10A",
        "hasPriorityBoarding": true,
        "hasOnboardMeal": true,
        "hasExtraLuggage": false,
        "flightPrice": 150.00
      },
      {
        "bookingId": "B1002",
        "travelerSsn": "987-65-4321",
        "flightNumber": "FL1002",
        "seatNumber": "12B",
        "hasPriorityBoarding": false,
        "hasOnboardMeal": false,
        "hasExtraLuggage": true,
        "flightPrice": 200.00
      }
    ]
    ```

---

### 4. **Cancel a Booking**
- **URL**: `DELETE /api/bookings/:id`
- **Description**: Cancels a booking by its ID.
- **Response** (204 - No Content):
    ```json
    "Booking successfully canceled"
    ```
- **Response** (404 - Not Found):
    ```json
    "Booking not found"
    ```

---

# FlightAPI Documentation

---

## Endpoints

### 1. **Get All Flights**
- **URL**: `GET /api/flights`
- **Description**: Retrieves all flights.
- **Response**:
    ```json
    [
      {
        "flightNumber": "FL1001",
        "srcAirport": "JFK",
        "destAirport": "LAX",
        "departureTime": "2025-05-01T10:00",
        "arrivalTime": "2025-05-01T13:00",
        "price": 200.00,
        "airline": "Airline A"
      },
      {
        "flightNumber": "FL1002",
        "srcAirport": "LAX",
        "destAirport": "JFK",
        "departureTime": "2025-05-02T14:00",
        "arrivalTime": "2025-05-02T17:00",
        "price": 250.00,
        "airline": "Airline B"
      }
    ]
    ```

---

### 2. **Search Flights by Source and Destination**
- **URL**: `GET /api/flights?srcAirport={srcAirport}&destAirport={destAirport}`
- **Description**: Searches for flights based on source and destination airports.
- **Query Parameters**:
  - `srcAirport`: The source airport code.
  - `destAirport`: The destination airport code.
- **Example URL**: `/api/flights?srcAirport=JFK&destAirport=LAX`
- **Response**:
    ```json
    [
      {
        "flightNumber": "FL1001",
        "srcAirport": "JFK",
        "destAirport": "LAX",
        "departureTime": "2025-05-01T10:00",
        "arrivalTime": "2025-05-01T13:00",
        "price": 200.00,
        "airline": "Airline A"
      }
    ]
    ```

---

### 3. **Search Flights with Filters and Sorting**
- **URL**: `GET /api/flights?srcAirport={srcAirport}&destAirport={destAirport}&minPrice={minPrice}&maxPrice={maxPrice}&airline={airline}&departureTime={departureTime}&arrivalTime={arrivalTime}&filter={filterType}&filter_values={filterValues}&sort={sortType}`
- **Description**: Searches for flights with optional filters such as price range, airline, and dates, and allows sorting by different criteria.
- **Query Parameters**:
  - `srcAirport`: The source airport code (optional).
  - `destAirport`: The destination airport code (optional).
  - `minPrice`: The minimum flight price (optional).
  - `maxPrice`: The maximum flight price (optional).
  - `airline`: The airline name (optional).
  - `departureTime`: The departure date and time (optional).
  - `arrivalTime`: The arrival date and time (optional).
  - `filter`: The filter type, such as `airline`, `departure`, `arrival` (optional).
  - `filter_values`: The comma-separated list of filter values (optional).
  - `sort`: The sort type, such as `price`, `departure`, `arrival`, or `airline` (optional).
- **Example URL**: `/api/flights?srcAirport=JFK&destAirport=LAX&minPrice=150&maxPrice=300&airline=Airline A&sort=price`
- **Response**:
    ```json
    [
      {
        "flightNumber": "FL1001",
        "srcAirport": "JFK",
        "destAirport": "LAX",
        "departureTime": "2025-05-01T10:00",
        "arrivalTime": "2025-05-01T13:00",
        "price": 200.00,
        "airline": "Airline A"
      }
    ]
    ```

---

### 4. **Get a Flight by Flight Number**
- **URL**: `GET /api/flights/number/:flightNumber`
- **Description**: Retrieves a flight by its flight number.
- **Response** (Success):
    ```json
    {
      "flightNumber": "FL1001",
      "srcAirport": "JFK",
      "destAirport": "LAX",
      "departureTime": "2025-05-01T10:00",
      "arrivalTime": "2025-05-01T13:00",
      "price": 200.00,
      "airline": "Airline A"
    }
    ```
- **Response** (404 - Not Found):
    ```json
    "Flight not found"
    ```

---

### 5. **Get a Random Flight**
- **URL**: `GET /api/flights/random`
- **Description**: Retrieves a random flight.
- **Response**:
    ```json
    {
      "flightNumber": "FL1002",
      "srcAirport": "LAX",
      "destAirport": "JFK",
      "departureTime": "2025-05-02T14:00",
      "arrivalTime": "2025-05-02T17:00",
      "price": 250.00,
      "airline": "Airline B"
    }
    ```

---

# FlightBooking
