package com.translineindia.vms.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuplicateEntryException extends RuntimeException {	
		
	public DuplicateEntryException() {
		super("Resource not found on server");
	}

	public DuplicateEntryException(String message) {
		super(message);
	}
		
}
