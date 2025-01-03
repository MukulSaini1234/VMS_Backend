package com.translineindia.vms.exception;

import lombok.Data;

@Data
public class ConflictException extends RuntimeException{
	private Exception exception;
	public ConflictException(String message, Exception exception) {
		super(message);
		this.exception=exception;
	}
}
