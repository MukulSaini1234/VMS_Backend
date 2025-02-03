package com.translineindia.vms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.translineindia.vms.entity.EmpId;
import com.translineindia.vms.entity.Employee;
import com.translineindia.vms.entity.Login;
import com.translineindia.vms.entity.LoginId;
import com.translineindia.vms.entity.employeeTest;

import java.util.List;


public interface EmployeeRepository extends JpaRepository<Employee,EmpId>{

	@Query(value = "SELECT * FROM emp_master WHERE cmp_cd = :cmpCd AND emp_id = :empId", nativeQuery = true)
	public Optional<Employee> findByCmpCdAndEmpId(String cmpCd, String empId);
	
	public Optional<employeeTest> findByEmpId(String empId);
	
}
