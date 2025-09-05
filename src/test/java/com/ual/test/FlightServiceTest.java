package com.ual.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.ual.dto.FlightDTO;
import com.ual.exception.UnitedGoException;
import com.ual.service.FlightService;

@SpringBootTest
public class FlightServiceTest {
	@Autowired
	FlightService flightService;
	
	@Test
	public void testSearchFlightValid() throws UnitedGoException {
		
			String source = "Chennai";
			String destination = "Delhi";
			LocalDate date = LocalDate.of(2025, 11, 13);
			List<FlightDTO> response = flightService.searchFlight(source, destination, date);

	        // Assert
	        assertEquals(source, response.get(0).getSource());
	        assertEquals(destination, response.get(0).getDestination());
	}
	
	@Test
	public void testSearchFlightInValid() throws UnitedGoException{
			String source = "Chennai";
			String destination = "China";
			LocalDate date = LocalDate.of(2025, 11, 13);
			UnitedGoException exception = assertThrows(UnitedGoException.class,()->flightService.searchFlight(source, destination, date));

			System.out.println(exception.getMessage());
	        
			assertEquals("Service.NO_FLIGHTS_FOUND",exception.getMessage());
	}
	
	
}
