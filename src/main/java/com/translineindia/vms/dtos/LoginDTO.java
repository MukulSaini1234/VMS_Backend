package com.translineindia.vms.dtos;


import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LoginDTO {		
	private String cmpCd;		
	private String userId;		
	private String firstName;
	private String lastName;		
	private String phoneNo;
	private String email;
	private String refCmpName;
	private String address;
	private String role;
	private LocalDateTime passwordUpdatedDate;
	public static Object builder() {
		// TODO Auto-generated method stub
		return null;
	}
}
