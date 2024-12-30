package com.transline.vms.entity;

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
}
