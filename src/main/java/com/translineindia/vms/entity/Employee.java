package com.translineindia.vms.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(
	name = "emp_master",
	uniqueConstraints = @UniqueConstraint(columnNames = {"cmp_cd","emp_id"}, name = "")
)
@Data
@IdClass(EmpId.class)

public class Employee {
	@Id
	@Column(name = "CMP_CD")
	private String cmpCd;	
	
	@Id
	@Column(name = "empId")
	private String empId;
	
//	@Column(name = "email")
    private String email;
	
	@Column(name  = "pass_word")
    private String password;
	
	
	@Transient
	private String role;
    
}
