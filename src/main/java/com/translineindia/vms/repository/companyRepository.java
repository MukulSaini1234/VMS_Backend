package com.translineindia.vms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.translineindia.vms.dtos.companyDTO;
import com.translineindia.vms.entity.CmpMst;

public interface CompanyRepository extends JpaRepository<CmpMst,String>{
    
	
	@Query(value = "SELECT c.cmp_name, c.cmp_cd " +
            "FROM cmp_mst c " +
            "LEFT JOIN OPTIONS op ON c.CMP_CD = op.CMP_CD " +
            "WHERE op.OPTION_ID = 'allow_vms'", 
    nativeQuery = true)
List<Object[]> findCompanyWithOptionId();
	
}
