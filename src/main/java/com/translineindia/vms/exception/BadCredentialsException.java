package com.translineindia.vms.exception;

import javax.naming.AuthenticationException;

public class BadCredentialsException extends AuthenticationException{

	public BadCredentialsException(String name) {
		super("Wrong credentials");
	}
}
