package com.translineindia.vms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.translineindia.vms.entity.GetEmpDetailsEntity;

@Repository
public interface GetEmpDetailsRepo extends JpaRepository<GetEmpDetailsEntity, String> {
	Optional<GetEmpDetailsEntity> findByempId(String empId);
}
