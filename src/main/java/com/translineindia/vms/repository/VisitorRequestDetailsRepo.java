package com.translineindia.vms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.translineindia.vms.entity.VisitorRequestDtls;

public interface VisitorRequestDetailsRepo extends JpaRepository<VisitorRequestDtls, Long> {
	
	
	  
}