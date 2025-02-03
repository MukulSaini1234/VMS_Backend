package com.translineindia.vms.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(
	name = "employee_test",
	uniqueConstraints = @UniqueConstraint(columnNames = {"cmp_cd","emp_id"}, name = "")
)
@Data
@IdClass(emptestId.class)

public class employeeTest {
	@Id
	@Column(name = "cmp_cd")
	private String cmpCd;	
	
	@Id
	@Column(name = "emp_id")
	private String empId;
	
	@Column(name = "email")
    private String email;
	
	@Column(name  = "password")
    private String password;
    
}
