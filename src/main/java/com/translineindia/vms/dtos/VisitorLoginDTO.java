package com.translineindia.vms.dtos;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class VisitorLoginDTO {
	@NotEmpty
	private String cmpCd;
	@NotEmpty
	private String offCd;
	@NotEmpty
	private String name;
	@NotEmpty
	private String password;
	@NotEmpty
	private String phoneNo;
	
	@NotEmpty
	@Email
	private String email;

}
