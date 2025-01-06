package com.translineindia.vms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.translineindia.vms.dtos.AppointmentDTO;
import com.translineindia.vms.entity.AppointmentEntity;

@Repository
public interface AppointmentRepo extends JpaRepository<AppointmentEntity ,Long>{
	
	
//	public List<AppointmentDTO> getByCmpcdAndId(String cmp_cd, String refId);
	@Query(value = "SELECT a FROM Appointment a WHERE a.cmp_cd = :cmp_cd AND a.ref_id = :refId", nativeQuery = true)
	public List<AppointmentDTO> getByCmpcdAndId(@Param("cmp_cd") String cmp_cd, @Param("refId") String refId);

	
}
