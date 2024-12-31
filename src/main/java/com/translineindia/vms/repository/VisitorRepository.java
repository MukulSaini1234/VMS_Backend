package com.translineindia.vms.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.translineindia.vms.entity.Visitor;
import com.translineindia.vms.entity.VisitorId;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor,VisitorId>{
	
//	public List<Visitor> findByName(String name);
	
	public Optional<Visitor> findByCmpCdAndEmail(String cmpCd, String email);

	
//	public List<Visitor> findByNameContains(String name); 
	
	@Query("SELECT MAX(SUBSTRING(u.visitorId, 3)) FROM Visitor u WHERE u.visitorId LIKE 'VS%'")
    String findMaxSerialNumber();
	
	@Query(value="SELECT * FROM GetOfficesList(:cmpCd, :offCd)",nativeQuery = true)
    List<Object[]> getOfficesList(@Param("cmpCd") String  cmpCd,@Param("offCd") String offCd);
	
	//@Query(name="SELECT * FROM GetOfficesList(?1, ?2)",nativeQuery = true)
    //List<Object[]> getOfficesList(String  cmpCd,String offCd);
    
    @Query("SELECT MAX(v.visitorId) FROM Visitor v WHERE v.cmpCd=:cmpCd")
    Optional<String> getMaxVisitorId(String cmpCd);
}
//