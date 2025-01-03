package com.translineindia.vms.controller;

import java.util.HashMap;
import com.translineindia.vms.exception.*;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



import jakarta.annotation.Resource;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		HashMap map=new HashMap();
		List<ObjectError> objectErrors=ex.getBindingResult().getAllErrors();
		for(ObjectError obError:objectErrors) {
			FieldError fe=(FieldError)obError;
			map.put(fe.getField(), fe.getDefaultMessage());
		}
		return ResponseEntity.badRequest().body(map);
		//return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
	}
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		String msg=ex.getMessage();
		if(msg.toLowerCase().contains("request body is missing"))
			msg="Required request body is missing";
		return ResponseEntity.badRequest().body(msg);
		//return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity handleResourceNotFoundException(ResourceNotFoundException ex) {
		String msg=ex.getMessage();
		return ResponseEntity.badRequest().body(msg);
		//return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
	}
	
	@ExceptionHandler(AlreadyExistsException.class)
	public ResponseEntity handleAlreadyExistsException(AlreadyExistsException ex) {
		String msg = ex.getMessage();
		return ResponseEntity.badRequest().body(msg);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity BadCredentialException(BadCredentialsException ex) {
		return ResponseEntity.badRequest().body(null);
		
	}
	
	@ExceptionHandler(ConflictException.class)
	public ResponseEntity handle(ConflictException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());		
	}
	
}
