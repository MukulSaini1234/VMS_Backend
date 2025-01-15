package com.translineindia.vms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.translineindia.vms.entity.Login;
import com.translineindia.vms.entity.LoginId;

@Repository
public interface LoginRepository extends JpaRepository<Login,LoginId>{
	
//	public List<Visitor> findByName(String name);
	
	public Optional<Login> findByCmpCdAndEmail(String cmpCd, String email);

	
//	public List<Visitor> findByNameContains(String name); 
	
	@Query("SELECT MAX(SUBSTRING(u.userId, 3)) FROM Login u WHERE u.userId LIKE 'VS%'")
    String findMaxSerialNumber();
	

	@Query(value="SELECT * FROM GetOfficesList(:cmpCd, :offCd)",nativeQuery = true)
    List<Object[]> getOfficesList(@Param("cmpCd") String  cmpCd,@Param("offCd") String offCd);
	
	//@Query(name="SELECT * FROM GetOfficesList(?1, ?2)",nativeQuery = true)
    //List<Object[]> getOfficesList(String  cmpCd,String offCd);
    
    @Query("SELECT MAX(v.userId) FROM Login v WHERE v.cmpCd=:cmpCd")
    Optional<String> getMaxVisitorId(String cmpCd);
    
    public Login findByCmpCdAndFirstName(String cmpCd,String name);
    
    @Query("SELECT v FROM Login v WHERE v.cmpCd=:cmpCd AND (v.email=:userId OR v.userId=:userId)")
    public Optional<Login> getUser(@Param("cmpCd") String cmpCd, @Param("userId") String visitorIdOrEmail);
        /*
         * findBy=queryBy=readBy
         * existsBy 
         * countBy
         * deleteBy
         * 
         * */   
}
