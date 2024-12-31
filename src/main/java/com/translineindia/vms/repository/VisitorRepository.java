package com.translineindia.vms.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.translineindia.vms.entity.Visitor;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor,Long>{
	
	public List<Visitor> findByName(String name);
	
	public List<Visitor> findByNameContains(String name); 
	
	@Query("SELECT MAX(SUBSTRING(u.username, 3)) FROM Visitor u WHERE u.username LIKE 'VS%'")
    String findMaxSerialNumber();
	
	@Query(value ="SELECT * FROM GetOfficesList(:cmpCd, :offCd)",nativeQuery = true)
    List<Object[]> getOfficesList(@Param("cmpCd") String  cmpCd,@Param("offCd") String offCd);
	
	//@Query(name="SELECT * FROM GetOfficesList(?1, ?2)",nativeQuery = true)
    //List<Object[]> getOfficesList(String  cmpCd,String offCd);
}
//