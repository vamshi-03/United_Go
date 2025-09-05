package com.ual.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ual.dto.FlightDTO;
import com.ual.exception.UnitedGoException;

public interface FlightService {

	public List<FlightDTO> searchFlight(String source, String destination, LocalDate date) throws UnitedGoException;
	public String addFlight(FlightDTO flight) throws UnitedGoException;
}
