package com.translineindia.vms.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class companyDTO {

	@NotEmpty
	private String cmpCd;
	@NotEmpty
	private String cmpName;
	@NotEmpty
	private String cmpAddress;
	@NotEmpty
	private String cmpCity;
	@NotEmpty
	private String cmpState;
	@NotEmpty
	private String phn1;
	private String phn2;
	private String fax;
	@NotEmpty
	@Email
	private String email;
	private String website;
	private String sysDate;
	private String dbNameBioas;
	private String dbNameAdms;
}
