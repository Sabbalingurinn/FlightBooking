DROP TABLE IF EXISTS Booking;
DROP TABLE IF EXISTS Flight;
DROP TABLE IF EXISTS Traveler;
DROP TABLE IF EXISTS Seat;

CREATE TABLE Traveler (
    ssn VARCHAR(10) PRIMARY KEY,
    name VARCHAR(55) NOT NULL,
    passportId VARCHAR(8) UNIQUE
);

CREATE TABLE Flight (
    flight_number VARCHAR(8) PRIMARY KEY,
    airline VARCHAR(25) NOT NULL,
    src_airport VARCHAR(3) NOT NULL,
    dest_airport VARCHAR(3) NOT NULL,
    departure_time VARCHAR(19) NOT NULL,
    arrival_time VARCHAR(19) NOT NULL,
    available_seats INTEGER NOT NULL,
    price INT NOT NULL
);

CREATE TABLE Booking (
    booking_id VARCHAR(20) PRIMARY KEY,
    traveler_ssn VARCHAR(10) NOT NULL,
    flight_number VARCHAR(12) NOT NULL,
    seat_number VARCHAR(5),
    extra_luggage BOOLEAN DEFAULT FALSE,
    on_board_meal BOOLEAN DEFAULT FALSE,
    priority_boarding BOOLEAN DEFAULT FALSE,

    FOREIGN KEY (traveler_ssn) REFERENCES Traveler(ssn) ON DELETE CASCADE,
    FOREIGN KEY (flight_number) REFERENCES Flight(flight_number) ON DELETE CASCADE,
    FOREIGN KEY (seat_number, flight_number) REFERENCES Seat(seat_number, flight_number)
);

CREATE TABLE Seat (
    seat_number VARCHAR(5),
    isTaken BOOLEAN DEFAULT FALSE,
    flight_number VARCHAR(12) NOT NULL,

    PRIMARY KEY (seat_number, flight_number),
    FOREIGN KEY (flight_number) REFERENCES Flight(flight_number) ON DELETE CASCADE
);

