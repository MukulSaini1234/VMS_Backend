package com.translineindia.vms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.translineindia.vms.dtos.AppointmentDTO;
import com.translineindia.vms.dtos.VisitorRequestMstDTO;
import com.translineindia.vms.entity.AppointmentEntity;
import com.translineindia.vms.entity.VisitorRequestMst;

@Repository
public interface AppointmentRepo extends JpaRepository<VisitorRequestMst ,Long>{
	
	
//	public List<AppointmentDTO> getByCmpcdAndId(String cmp_cd, String refId);
	@Query(value = "SELECT a FROM Appointment a WHERE a.cmp_cd = :cmp_cd AND a.ref_id = :refId", nativeQuery = true)
	public List<AppointmentDTO> getByCmpcdAndId(@Param("cmp_cd") String cmp_cd, @Param("refId") String refId);

	// New method created for fetching appointment requests after adding another table ie , vis_mst and vis_Dtla
//	public List<VisitorRequestMstDTO> getVisitorDtlsByVisitorId(@Param("cmp_cd") String cmp_cd, @Param("visitor_id") String visitor_id);
	
	 @Query(value = """
		        SELECT vm.visitor_id, vm.emp_name, vm.vis_organization, vm.purpose, 
		               vm.from_date, vm.visitor_address, 
		               vd.name, vd.dob, vd.contact_no, vd.accessories, vm.vehicle_no, 
		               vm.driver_name, vm.vehicle_type, vm.driver_dl_no, vm.driver_dl_upto, vd.id_proof_no
		        FROM vis_req_mst vm
		        LEFT JOIN vis_req_dtls vd
		        ON vm.vis_mst_id = vd.visitor_request_mst_id
		        WHERE vm.visitor_id = :visitorId
		    """, nativeQuery = true)
		    List<Object[]> findVisitorDetailsByVisitorId(@Param("visitorId") String visitorId);

		// Added on 09-01-25    
		    @Query(value = "SELECT * FROM vis_req_mst vm WHERE vm.visitor_id = :visitorId", nativeQuery = true)
		    public List<VisitorRequestMst> findByVisitorId(String visitorId);

        // Added on 14-01-25
		    @Query(value = "SELECT * FROM vis_req_mst v WHERE v.cmp_cd = :cmpCd AND v.req_status IS NULL", nativeQuery = true)
		    public List<VisitorRequestMst> findByCmpCd(@Param("cmpCd") String cmpCd);  // will be including office list as well

		// Added on 29-01-25
		    @Query(value = "SELECT *FROM vis_req_mst v WHERE v.cmp_cd = :cmpCd AND v.req_status ='A'", nativeQuery = true)
		    public List<VisitorRequestMst> requestsForReception(@Param("cmpCd") String cmpCd); // for all offices 
		    
		// Added on 17-01-25
		    @Query(value = "SELECT * FROM vis_req_mst v WHERE v.cmp_cd = :cmpCd AND v.req_status = :reqStatus", nativeQuery = true)
		    public List<VisitorRequestMst> getRequestsByStatus(String cmpCd, String reqStatus);
		    
		    // Added on 04-02-2025
		    @Query(value = "SELECT *FROM vis_req_mst v WHERE v.cmp_cd = :cmpCd AND v.vis_mst_id = :visitId", nativeQuery = true)
		    public VisitorRequestMst findByCmdAndId(String cmpCd, String visitId);
		    
		    // Added on 11-02-25
		    @Query(value = "SELECT *FROM vis_req_mst v WHERE v.cmp_cd = :cmpCd AND v.emp_id = :empId",nativeQuery = true)
		    public List<VisitorRequestMst> findByCmpAndEmpId(String cmpCd, String empId);
		    

	
}
