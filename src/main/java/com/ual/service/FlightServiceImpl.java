package com.ual.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ual.dto.FlightDTO;
import com.ual.entity.Flight;
import com.ual.exception.UnitedGoException;
import com.ual.repository.FlightRepository;

@Service("flightservice")
public class FlightServiceImpl implements FlightService{

	
	
	@Autowired
	FlightRepository flightRepository;
	
	ModelMapper mapper = new ModelMapper();
	
	@Override
	public List<FlightDTO> searchFlight(String source, String destination, LocalDate date) throws UnitedGoException {
		
		Optional<List<Flight>> optional = flightRepository.findBySourceAndDestinationAndAvailableDateOrderByFare(source, destination, date);
		List<Flight> list = optional.orElseThrow(()-> new UnitedGoException("Service.NO_FLIGHTS_FOUND"));
		if(list.isEmpty())
			throw new UnitedGoException("Service.NO_FLIGHTS_FOUND");
		List<FlightDTO> lt = list.stream().map(a -> mapper.map(a, FlightDTO.class)).collect(Collectors.toList());
		return lt;
	}

	@Override
	public String addFlight(FlightDTO flight) throws UnitedGoException {
		Flight ft = mapper.map(flight, Flight.class);
		System.out.println(flight);
		flightRepository.save(ft);
		
//		return new ResponseEntity<>("Successfully Added flight!",HttpStatus.OK);
		return "Service.FLIGHT_ADDED";
//		return flightRepository.s
	}

}
