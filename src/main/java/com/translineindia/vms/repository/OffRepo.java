package com.translineindia.vms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.translineindia.vms.entity.OffMst;
import com.translineindia.vms.entity.OfficeId;


public interface OffRepo extends JpaRepository<OffMst, OfficeId> {

	
}
