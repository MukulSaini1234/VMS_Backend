package com.translineindia.vms.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VisitorLoginDTO {	
	@NotEmpty
	private String cmpCd;
		
	private String visitorId;
		
	@NotEmpty
	private String firstName;
	
	@NotEmpty
	private String lastName;
	
	@NotEmpty
	private String password;
	
	
	private String phoneNo;
	 
	@NotEmpty
	@Email
	private String email;
			
	
	private String VisCmpName;
	
	@Size(max = 200)
	private String address;
	
	private String username;
}
