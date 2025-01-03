package com.translineindia.vms.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.translineindia.vms.entity.Visitor;

import lombok.Data;

@Data
public class VisitorLogin implements UserDetails{	
//	private String cmpCd;
//	private String visitorId;
//	private String username;
//	private String firstName;
//	private String lastName;
//	private String phoneNo;
//	private String email;
//	private String password;
//	private String address;
//	private String visCmpCd;
	private String cmpCd;
	private Visitor visitor;
	
	public VisitorLogin(Visitor visitor) {
		this.visitor=visitor;
	}
	
	public Visitor getVisitor() {
		return this.visitor;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public String getPassword() {		
		return visitor.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return visitor.getVisitorId();
	}
}
