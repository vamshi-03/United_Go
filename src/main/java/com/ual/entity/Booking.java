package com.ual.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
//import lombok.Data;
import lombok.Data;

@Entity
@Data
public class Booking {
	@Id
	private String pnr;
	private Integer noOfPassengers;
	private double fare;
	
	@ManyToOne()
	@JoinColumn(name="flight_id")
	private Flight flight;
}
