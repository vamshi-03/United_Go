package com.ual.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ual.dto.BookingDTO;
import com.ual.dto.FlightDTO;
import com.ual.exception.UnitedGoException;
import com.ual.service.BookingServiceImpl;
import com.ual.service.FlightServiceImpl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@RestController
@RestControllerAdvice
@RequestMapping("/united-go")
@Validated
public class UnitedGoController {
	@Autowired
	Environment env;

	@Autowired
	FlightServiceImpl flightService;
	
	@Autowired
	BookingServiceImpl bookingService;
	
	@GetMapping(value="/flights/{source}/{destination}/{date}")
	public ResponseEntity<List<FlightDTO>> searchFlights(@Pattern(regexp="^[a-zA-Z]+$",message="{Validator.INVALID_SOURCE}")@NotNull @PathVariable String source,@NotNull@Pattern(regexp="^[a-zA-Z]+$",message="{Validator.INVALID_DESTINATION}") @PathVariable String destination, @FutureOrPresent @PathVariable LocalDate date) throws UnitedGoException{
//		LocalDate d = LocalDate.parse(date);
		System.out.println(source+" "+destination);
		return new ResponseEntity<>(flightService.searchFlight(source, destination, date),HttpStatus.OK);
	}
	
	@PostMapping(value="/flights/add")
	public ResponseEntity<String> addFlight(@Valid @RequestBody FlightDTO flight) throws UnitedGoException{
		System.out.println(flight.getAvailableDate());
		return new ResponseEntity<>(env.getProperty(flightService.addFlight(flight)),HttpStatus.OK);
	}
	
	@PostMapping(value="/flights/book")
	public ResponseEntity<BookingDTO> bookFlight(@Min(value=1, message="{Validator.INVALID_PAX}") @Max(value=4,message="{Validator.INVALID_PAX}") @RequestParam Integer noOfPax, @Pattern(regexp = "^[A-Z]{2}-[0-9]{3}$", message = "{Validator.INVALID_FLIGHTID}") @RequestParam String flightId) throws UnitedGoException{
		return new ResponseEntity<>(bookingService.bookFlight(noOfPax, flightId),HttpStatus.OK);
	}
	
	@GetMapping(value="/bookings/{pnr}")
	public ResponseEntity<BookingDTO> viewBooking(@Pattern(regexp = "^[A-Z]{3}[0-9]{3}$$", message = "{Validator.INVALID_PNR}") @PathVariable String pnr)throws UnitedGoException{
		return new ResponseEntity<>(bookingService.viewBooking(pnr),HttpStatus.OK);
	}
	
	
	@DeleteMapping(value = "/bookings/{pnr}/cancel")
	public ResponseEntity<String> cancelBooking(@Pattern(regexp = "^[A-Z]{3}[0-9]{3}$", message = "{Validator.INVALID_PNR}") @PathVariable String pnr) throws UnitedGoException{
		return new ResponseEntity<>(env.getProperty(bookingService.cancelBooking(pnr)),HttpStatus.OK);
	}
}
