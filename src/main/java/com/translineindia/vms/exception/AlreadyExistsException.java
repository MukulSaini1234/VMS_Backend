package com.translineindia.vms.exception;

public class AlreadyExistsException extends RuntimeException{

	public  AlreadyExistsException(String resourceName) {
		super("Visitor named "+resourceName+" Already exists");
	}
	
	
}
