package com.ual.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Flight {
	@Id
	private String flightId;
	private String airline;
	private String source;
	private String destination;
	private String arrivalTime;
	private String departureTime;
	private Integer availableSeats;
	private LocalDate availableDate;
	private double fare;
	
}
