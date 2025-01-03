package com.translineindia.vms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.translineindia.vms.entity.AppointmentEntity;

@Repository
public interface AppointmentRepo extends JpaRepository<AppointmentEntity , String>{
	
}
