package com.translineindia.vms.dtos;

import java.util.List;

import lombok.Data;

@Data
public class EmployeeDTO {
	private String cmpCd;		
	private String empId;		
    private String email;
    private String password;
    private String role;

}
