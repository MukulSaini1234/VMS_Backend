package com.translineindia.vms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(
		name = "options"
	 )	

@Data
@IdClass(OptionId.class)
public class options {
 
	@Id
	@Column(name = "cmp_cd")
	private String cmpCd;
	
	@Id
	@Column(name = "mod_id")
	private String modId;
	
	@Id
	@Column(name = "option_id")
	private String optionId;
	
	@Column(name = "ulogin_id")
	private String uloginId;
	
	@Column(name = "off_cd")
	private String offCd;
	
	@Column(name = "option_val")
	private String optionVal;
		
}
