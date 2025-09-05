package com.ual.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import jakarta.persistence.JoinColumn;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BookingDTO {
	static int serial = 0;
	
	
	private String pnr;
	@Min(value = 1, message = "{Controller.MIN_PAX}")
	@Max(value=4, message = "{Controller.MAX_PAX")
	private Integer noOfPassengers;
	private double fare;

	private FlightDTO flight;
	
	
	public  void generatePnr() {
		serial++;
		if(serial<10)
			this.pnr= "UAL00"+serial;
		else if(serial<100)
			this.pnr= "UAL0"+serial;
		else
			this.pnr= "UAL"+serial;
		
	}
}
