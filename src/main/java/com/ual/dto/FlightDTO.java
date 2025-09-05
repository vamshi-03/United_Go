package com.ual.dto;

import java.time.LocalDate;

//import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Validated
public class FlightDTO {
	@Pattern(regexp = "^[A-Z]{2}-[0-9]{3}$", message = "{Validator.INVALID_FLIGHTID}") 
	private String flightId;
	private String airline;
	
	@Pattern(regexp="^[a-zA-Z]+$",message="{Validator.INVALID_SOURCE}")
	private String source;
	
	@Pattern(regexp="^[a-zA-Z]+$",message="{Validator.INVALID_SOURCE}")
	private String destination;
	
	@Pattern(regexp="^[1-9][0-9]{0,1}:[1-9][0-9]{0,1}$", message="Validator.INVALID_TIME")
	private String arrivalTime;
	
	@Pattern(regexp="^[1-9][0-9]{0,1}:[1-9][0-9]{0,1}$", message="Validator.INVALID_TIME")
	private String departureTime;
	
	private Integer availableSeats;
	
	@FutureOrPresent(message = "{Validator.INVALID_DATE}")
	private LocalDate availableDate;
	private double fare;
}
