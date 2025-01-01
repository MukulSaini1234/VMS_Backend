package com.translineindia.vms.entity;


import com.translineindia.vms.config.EncryptionUtil;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(
	name = "visitor_login",
	uniqueConstraints = @UniqueConstraint(columnNames = {"cmp_cd","email"}, name = "UK_email")	
)
@Data
@IdClass(VisitorId.class)
public class Visitor {	
	@Id
	@Column(name = "cmp_cd")	
	private String cmpCd;
	
	@Id
	@Column(name = "visitor_id")	
	private String visitorId;
	
	@Column(name = "phone_no")
	private Long phoneNo;
		
	@Column(name = "first_name", length = 50,nullable = false)
	private String firstName;
	
	@Column(name = "last_name", length = 50,nullable = false)
	private String lastName;
	
	@Column(length = 50,nullable = false)//unique=true
	private String email;	
	
	@Column(length = 500 , name="address")
	private String address;
	
	@Column(length = 200, name="vis_cmp_name")
	private String VisitorCmpName;	
		
	@Column(length = 100,nullable = false)
	private String password;
}
