package com.ual.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

//import org.hibernate.cfg.Environment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ual.dto.BookingDTO;
import com.ual.dto.FlightDTO;
import com.ual.entity.Booking;
import com.ual.entity.Flight;
import com.ual.exception.UnitedGoException;
import com.ual.repository.BookingRepository;
import com.ual.repository.FlightRepository;

@Service("bookingservice")
public class BookingServiceImpl implements BookingService{
	
	@Autowired
	FlightRepository flightRepository;
	
	@Autowired
	BookingRepository bookingRepository;
	
	
	
	ModelMapper mapper = new ModelMapper();

	@Override
	public BookingDTO bookFlight(Integer noOfPax, String flightId) throws UnitedGoException {
		// TODO Auto-generated method stub
		Optional<Flight> optional = flightRepository.findByflightId(flightId);
		Flight flight = optional.orElseThrow(()-> new UnitedGoException("Service.INVALID_FLIGHT_ID"));
		
		if(flight.getAvailableSeats() < noOfPax) throw new UnitedGoException("Service.INSUFFICIENT_SEATS");
		
		FlightDTO ft = mapper.map(flight, FlightDTO.class);
		BookingDTO booking = new BookingDTO();
		booking.setFare(ft.getFare()*noOfPax);
		booking.setNoOfPassengers(noOfPax);
		booking.generatePnr();
		booking.setFlight(ft);
		
		Booking bk = mapper.map(booking, Booking.class);
		ft.setAvailableSeats(ft.getAvailableSeats()-noOfPax);
		flight = mapper.map(ft, Flight.class);
		flightRepository.save(flight);
		bookingRepository.save(bk);
		return booking;
	}

	@Override
	public BookingDTO viewBooking(String pnr) throws UnitedGoException {
		// TODO Auto-generated method stub
		Optional<Booking> optional = bookingRepository.findById(pnr);
		Booking booking = optional.orElseThrow(()->new UnitedGoException("Service.INVALID_PNR"));
		
		return mapper.map(booking, BookingDTO.class);
	}

	@Override
	public String cancelBooking(String pnr) throws UnitedGoException {
		// TODO Auto-generated method stub
		Optional<Booking> optional = bookingRepository.findById(pnr);
		Booking booking = optional.orElseThrow(()->new UnitedGoException("Service.INVALID_PNR"));
		Flight flight = booking.getFlight();
		
//		Flight flight=booking.getFlight();
		LocalDate flightDate=flight.getAvailableDate();
		String depTime=flight.getDepartureTime();
		DateTimeFormatter timeFormatter=DateTimeFormatter.ofPattern("HH:mm");
		LocalTime time=LocalTime.parse(depTime, timeFormatter);
		LocalDateTime dateOfJourney=LocalDateTime.of(flightDate, time);
		LocalDateTime cancellationCutOff=dateOfJourney.minusHours(6);
		LocalDateTime now=LocalDateTime.now();
		
		if(now.isEqual(dateOfJourney)|| now.isAfter(dateOfJourney)) {
			// that means flight has started. cancellation can't done
			throw new UnitedGoException("Service.Booking.Cancellation_not_allowed");
		}
		
		FlightDTO dto = mapper.map(flight, FlightDTO.class);
		dto.setAvailableSeats(dto.getAvailableSeats()+booking.getNoOfPassengers());
		flight = mapper.map(dto, Flight.class);
		flightRepository.save(flight);
		bookingRepository.deleteById(pnr);
		
		if(now.isAfter(cancellationCutOff) && now.isBefore(dateOfJourney)) {
			return "Service.BOOKING_CANCELLED_WITHOUT_REFUND";
		}
		
		if(now.isBefore(cancellationCutOff)) {
			//cancellation with refund initiated
			
			
			return "Service.BOOKING_CANCELLED_WITH_REFUND";
			
		}
		
		return "Service.BOOKING_CANCELLED_WITH_REFUND";
	}
//org.springdoc
//springdoc-openapi-starter-webmvc-ui
	//version 2.8.9
}
