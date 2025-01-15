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
		List<SimpleGrantedAuthority> roles=new ArrayList();
		if(login.getRole().equalsIgnoreCase("ADMIN")) {
			roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));			
			roles.add(new SimpleGrantedAuthority("ROLE_USER"));
		}else if(login.getRole().equalsIgnoreCase("VISITOR")) {
			roles.add(new SimpleGrantedAuthority("ROLE_VISITOR"));
			roles.add(new SimpleGrantedAuthority("ROLE_USER"));
		}else {
			roles.add(new SimpleGrantedAuthority("ROLE_"+login.getRole().toUpperCase()));
		}
		return roles;
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
