package com.ual.utility;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.ual.exception.UnitedGoException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ControllerExceptionHandler {

	@Autowired
	Environment env;
	
	@ExceptionHandler(UnitedGoException.class)
	public ResponseEntity<Error> exceptionHandler1(UnitedGoException ex){
		Error err = new Error();
		err.setErrMessage(env.getProperty(ex.getMessage()));
		err.setErrCode(HttpStatus.BAD_REQUEST.value());
		err.setTime(LocalDateTime.now());
		ResponseEntity<Error> response = new ResponseEntity<Error> (err,HttpStatus.BAD_REQUEST);
		return response;
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Error> exceptionHandler1(ConstraintViolationException ex){
		Error err = new Error();
		err.setErrMessage(ex.getConstraintViolations().stream().map(a -> a.getMessage()).collect(Collectors.joining(",")));
		err.setErrCode(HttpStatus.BAD_REQUEST.value());
		err.setTime(LocalDateTime.now());
		ResponseEntity<Error> response = new ResponseEntity<Error> (err,HttpStatus.BAD_REQUEST);
		return response;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Error> exceptionHandler1(MethodArgumentNotValidException ex){
		Error err = new Error();
		err.setErrMessage(ex.getBindingResult().getAllErrors().stream().map(a -> a.getDefaultMessage()).collect(Collectors.joining(",")));
		err.setErrCode(HttpStatus.BAD_REQUEST.value());
		err.setTime(LocalDateTime.now());
		ResponseEntity<Error> response = new ResponseEntity<Error> (err,HttpStatus.BAD_REQUEST);
		return response;
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Error> exceptionHandler1(MethodArgumentTypeMismatchException ex){
		Error err = new Error();
		err.setErrMessage(ex.getMessage());
		err.setErrCode(HttpStatus.BAD_REQUEST.value());
		err.setTime(LocalDateTime.now());
		ResponseEntity<Error> response = new ResponseEntity<Error> (err,HttpStatus.BAD_REQUEST);
		return response;
	}
}
