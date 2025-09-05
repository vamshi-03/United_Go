package com.ual.service;

import org.springframework.http.ResponseEntity;

import com.ual.dto.BookingDTO;
import com.ual.exception.UnitedGoException;

public interface BookingService {
	public BookingDTO bookFlight(Integer noOfPax,String flightId) throws UnitedGoException;
	public BookingDTO viewBooking(String pnr)throws UnitedGoException;
	public String cancelBooking(String pnr) throws UnitedGoException;
	
}
