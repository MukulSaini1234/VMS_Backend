package com.transline.vms.entity;


import com.transline.vms.config.EncryptionUtil;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	 uniqueConstraints = @UniqueConstraint(columnNames = {"email"}, name = "UK_email")	
)
@Data
public class Visitor {

	@Column
	private String cmpCd;
	
	@Column
	private String offCd;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username")
	private String username;
	
	@Column(name = "phone_no")
	private Long phoneNo;
		
	@Column(length = 50,nullable = false)
	private String name;
	
	@Column(length = 50,nullable = false)//unique=true
	private String email;
	
	@Column(length = 100,name = "password")
	private String encryptedPassword;
	
	@Transient
	private String password;
	
	@PrePersist//runs before save	
	private void generateUserId() {
			
		 try {
			 String secretKey = EncryptionUtil.generateKey();
			 this.encryptedPassword=EncryptionUtil.encrypt(password, secretKey);
		 }catch(Exception ex) {
			 
		 }
	}

	
	@PreUpdate//runs before update
	private void prePersist() {
		 try {
			 String secretKey = EncryptionUtil.generateKey();
			 this.encryptedPassword=EncryptionUtil.encrypt(password, secretKey);
		 }catch(Exception ex) {
			 
		 }
	}
	
	
	@PostLoad//runs after data retrieved
	private void postLoad() {
		try {
			 String secretKey = EncryptionUtil.generateKey();
			 this.password=EncryptionUtil.decrypt(encryptedPassword, secretKey);
		 }catch(Exception ex) {
			 
		 }
	}
	
	
}
