package com.translineindia.vms.dtos;


import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewLoginDTO {	
	@NotEmpty
	private String cmpCd;
		
	@NotEmpty
	private String userId;
		
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
			
	
	private String refCmpName;
	
	@Size(max = 200)
	private String address;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime passwordUpdatedDate;
	
	@NotEmpty
	private String role;
}
