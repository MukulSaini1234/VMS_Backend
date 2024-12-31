package com.translineindia.vms.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;

public class optionDTO {

	@NotEmpty
	private String CmpCd;
	
	@NotEmpty
	private String uloginId;
	
	private String offCd;
	
	@NotEmpty
	private String modId;
	
	@NotEmpty
	private String optionId;
	
	@NotEmpty
	private String optionVal;
	
	
}
