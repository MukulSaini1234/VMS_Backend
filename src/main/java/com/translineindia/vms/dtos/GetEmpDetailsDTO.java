package com.translineindia.vms.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetEmpDetailsDTO {
	private String empId;
	private String name;
	private String email;
}
