package com.translineindia.vms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "EMP_MASTER")
@Data
public class GetEmpDetailsEntity {

	@Id
	private String empId;
	private String name;
    private String email;
	
}
