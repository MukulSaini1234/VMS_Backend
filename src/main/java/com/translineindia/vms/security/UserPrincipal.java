package com.translineindia.vms.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.translineindia.vms.entity.Login;

import lombok.Data;

@Data
public class UserPrincipal implements UserDetails{	
	private String cmpCd;
	private Login login;
	
	public UserPrincipal(Login login) {
		this.login=login;
	}
	
	public Login getLogin() {
		return this.login;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of(new SimpleGrantedAuthority(login.getRole().toUpperCase()));
	}

	@Override
	public String getPassword() {		
		return login.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return login.getUserId();
	}
}
