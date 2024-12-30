package com.transline.VMS.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.transline.VMS.entities.Visitor;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor,Long>{
	
	public List<Visitor> findByName(String name);
	
	public List<Visitor> findByNameContains(String name); 
	
	@Query("SELECT MAX(SUBSTRING(u.username, 3)) FROM User u WHERE u.username LIKE 'VS%'")
    String findMaxSerialNumber();
}
//