package com.translineindia.vms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "OFF_MST")
@Data
@IdClass(OfficeId.class)
public class OffMst {

	@Id
	@Column(name = "cmp_cd", length=10)
	private String cmpCd;
	
	@Id
	@Column(name = "off_cd", length = 10)
	private String offCd;
	
	@Column(name = "off_name", length = 255)
	private String offName;
	
	@Column(name = "off_type", length = 10)
	private String offtype;
	
	@Column(name = "CTL_CD", length = 10)
	private String ctlCd;
	
	@Column(name = "OFF_ADD", length = 255)
	private String offAdd;
	
	@Column(name = "EMAIL_ID", length = 30)
	private String email;
	
	@Column(name = "PHONE_NO1", length = 255)
	private String phoneNo;
	
}
