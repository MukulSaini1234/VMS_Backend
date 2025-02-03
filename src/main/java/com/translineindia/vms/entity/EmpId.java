package com.translineindia.vms.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpId implements Serializable{

	private String cmpCd;
	
	private String empId;
}
