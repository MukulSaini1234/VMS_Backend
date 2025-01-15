package com.translineindia.vms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.translineindia.vms.entity.Admin;
import com.translineindia.vms.entity.AdminId;
import com.translineindia.vms.entity.Login;



public interface AdminRepo extends JpaRepository<Admin,AdminId>{
            
	 @Query("SELECT a FROM Admin a WHERE a.cmpCd=:cmpCd AND (a.email=:AdminId OR a.AdminId=:AdminId)")
	    public Optional<Admin> getAdmin(@Param("cmpCd") String cmpCd, @Param("AdminId") String adminIdOrEmail);
}
