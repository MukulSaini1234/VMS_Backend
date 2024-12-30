package com.translineindia.vms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cmp_mst")
public class CmpMst {

	@Id
	@Column(name = "CMP_CD",length = 10)
	private String cmpCd;
	
	@Column(name="CMP_NAME",length = 100,nullable = false)
	private String cmpName; 
	
	@Column(name="CMP_ADD",length = 200,nullable = false)
	private String cmpAddress;
	
	@Column(name = "CITY",length = 30,nullable = false)
	private String cmpCity;
	
	@Column(name = "STATE",length = 30,nullable = false)
	private String cmpState;
	
	@Column(name = "PHN1",length = 15,nullable = false)
	private String phn1;
	
	@Column(name = "PHN2",length = 15)
	private String phn2;
	
	@Column(name = "FAX",length = 15)
	private String fax;
	
	@Column(name = "E_MAIL",length = 60)
	private String email;
	
	@Column(name = "WEBSITE",length = 80)
	private String website;
	
	@Column(name = "SYS_DATE",length = 15,nullable = false)
	private String sysDate;
	
	@Column(name = "DB_NAME_BIOAS" , length = 20 ,nullable = false)
	private String dbNameBioas;
	
	@Column(name = "DB_NAME_ADMS" , length = 20 ,nullable = false)
	private String dbNameAdms;
	
	

}
