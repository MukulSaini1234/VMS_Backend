package com.translineindia.vms.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "vis_req_mst")
@Data
public class VisitorRequestMst {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vis_mst_id")
	private Long id;
	
	private String empId;
	
	private String empName;
	
	private String visOrganization;
	
	private String purpose;
	
	private LocalDate fromDate;
	
	private LocalDate toDate;
	
	private String visitorAddress;
		
	private Boolean hasVehicle;		
		
	private String vehicleNo;
	
	private String driverName;		
	
	private String vehicleType;
		
	private String driverDlNo;
	
	private String driverDlUpto;
	
	// added on 08-01-25
	private String visitorId;
	
	// added on 09-01-25
	private String empEmail;
	
//	@OneToMany(mappedBy = "visitorRequestMst", cascade = CascadeType.ALL, orphanRemoval = true)
	@OneToMany(mappedBy = "visitorRequestMst",cascade = CascadeType.ALL , orphanRemoval = true)
	@JsonManagedReference
	private List<VisitorRequestDtls> visitorDtls;
}