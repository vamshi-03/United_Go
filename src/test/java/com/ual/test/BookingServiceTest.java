package com.ual.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ual.dto.BookingDTO;
import com.ual.dto.FlightDTO;
import com.ual.exception.UnitedGoException;
import com.ual.repository.BookingRepository;
import com.ual.service.BookingService;

@SpringBootTest
public class BookingServiceTest {

	@Autowired
	BookingService bookingService;
	
	String pnr;
//	public String bookFlight(Integer noOfPax,String flightId) throws UnitedGoException;
//	public BookingDTO viewBooking(String pnr)throws UnitedGoException;
//	public String cancelBooking(String pnr) throws UnitedGoException;
	
	@Test
	public void testBookFlightValid() throws UnitedGoException {
		
			String flightId = "IG-103";
			Integer noOfPax = 3;
			
			BookingDTO response = bookingService.bookFlight(noOfPax, flightId);

	        // Assert
			pnr = response.getPnr();
			Pattern pattern = Pattern.compile("UAL[0-9]{3}");
		    Matcher matcher = pattern.matcher(response.getPnr());
			
	        assertTrue(matcher.find(),"Pattern Not Found in the response");
	}
	
	@Test
	public void testBookFlightInValid() throws UnitedGoException {
		
		String flightId = "AB-103";
		Integer noOfPax = 3;
		
//		String response = bookingService.bookFlight(noOfPax, flightId);

        UnitedGoException exception = assertThrows(UnitedGoException.class,()-> bookingService.bookFlight(noOfPax, flightId));
        assertEquals("Service.INVALID_FLIGHT_ID", exception.getMessage());
	}
	

	
	
	@Test
	public void testViewBookingValid() throws UnitedGoException{
		String pnr = "UAL001";
		
		
		BookingDTO booking = bookingService.viewBooking(pnr);
		
		assertEquals(pnr,booking.getPnr());
	}
	
	@Test
	public void testViewBookingInValid() throws UnitedGoException{
		String pnr = "ABC002";
		
		UnitedGoException exception = assertThrows(UnitedGoException.class,()->bookingService.viewBooking(pnr));
		
		assertEquals("Service.INVALID_PNR",exception.getMessage());
	}
	
//	@Test
//	public void testCancelBookingValid() throws UnitedGoException{
//		String pnr = "UAL101";
//		
//		String response = bookingService.cancelBooking(pnr);
//		System.out.println(response);
//		assertEquals(response,"Service.BOOKING_CANCELLED_WITHOUT_REFUND");
//	}
//	
//	@Test
//	public void testCancelBookingValid2() throws UnitedGoException{
//		String pnr = "UAL001";
//		String response = bookingService.cancelBooking(pnr);
//		
//		assertEquals(response,"Service.BOOKING_CANCELLED_WITH_REFUND");
//	}
	
	@Test
	public void testCancelBookingValid3() throws UnitedGoException{
		String pnr = "UAL100";
		
		UnitedGoException exception = assertThrows(UnitedGoException.class,()->bookingService.cancelBooking(pnr));
//		String response = bookingService.cancelBooking(pnr);
		
		assertEquals("Service.Booking.Cancellation_not_allowed",exception.getMessage());
	}
	
}
