package com.ual.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ual.entity.Flight;

public interface FlightRepository extends JpaRepository<Flight, String> {
	Optional<List<Flight>> findBySourceAndDestinationAndAvailableDateOrderByFare(String source, String destination, LocalDate date);
	Optional<Flight> findByflightId(String flightId);
}
