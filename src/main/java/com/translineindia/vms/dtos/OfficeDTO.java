package com.translineindia.vms.dtos;

import jakarta.validation.constraints.NotNull;

public class OfficeDTO {
	
	@NotNull
	private String cmpCd;
	
	private String offCd;
	
	private String offName;
	
	private String offtype;
	
	private String ctlCd;
	
	private String offAdd;
	
	private String email;
	
	private String phoneNo;
	
}
