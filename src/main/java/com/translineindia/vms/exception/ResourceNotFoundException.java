package com.translineindia.vms.exception;

public class ResourceNotFoundException extends RuntimeException{
	
	public  ResourceNotFoundException(String resourceName, String fieldName, String fieldvalue) {
		super(resourceName+" Not Found with "+fieldName+"="+fieldvalue);
	}
	
}
