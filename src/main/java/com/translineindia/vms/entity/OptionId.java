package com.translineindia.vms.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OptionId implements Serializable{

	private String cmpCd;
	
	private String modId;
	
	private String optionId;
}
