//package com.translineindia.vms.entity;
//
//import com.translineindia.vms.config.EncryptionUtil;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.PrePersist;
//import jakarta.persistence.PreUpdate;
//import jakarta.persistence.Table;
//import jakarta.persistence.Transient;
//import lombok.Data;
//
//@Entity
//@Table(name = "vis_req_dtls")
//@Data
//public class VisitorRequestDtls {
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;	
//	private String name;
//	private String dob;//store in encrypted format
//	private String idProof;
//	
//	private String idproofNo;//store in encrypted format
//	
////	@Transient
////	private String encryptedIdProof;
//	
//	
//	private String contactNo;//store in encrypted format
//	private String accessories;
//	private String photo;
//	private String idProofFile;
//	
//	@ManyToOne
//	private VisitorRequestMst visitorRequestMst;
//	
//	@PrePersist//runs before save	
//	private void generateUserId() {
//			
//		 try {
//			 String secretKey = EncryptionUtil.generateKey();
//			 this.encryptedIdProof=EncryptionUtil.encrypt(idproofNo, secretKey);
//		 }catch(Exception ex) {
//			 
//		 }
//	}
//
//	
//	@PreUpdate//runs before update
//	private void prePersist() {
//		 try {
//			 String secretKey = EncryptionUtil.generateKey();
//			 this.encryptedPassword=EncryptionUtil.encrypt(password, secretKey);
//		 }catch(Exception ex) {
//			 
//		 }
//	}
//	
//}

//package com.translineindia.vms.entity;
//
//import com.translineindia.vms.config.EncryptionUtil;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.PrePersist;
//import jakarta.persistence.PreUpdate;
//import jakarta.persistence.Table;
//import jakarta.persistence.Transient;
//import lombok.Data;
//
//@Entity
//@Table(name = "vis_req_dtls")
//@Data
//public class VisitorRequestDtls {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String name;
//    private String dob; // Store in encrypted format
//    private String idProof;
//
//    private String idProofNo; // Store in encrypted format
//
//    private String contactNo; // Store in encrypted format
//    private String accessories;
//    private String photo;
//    private String idProofFile;
//
//    @ManyToOne
//    private VisitorRequestMst visitorRequestMst;
//
//    @PrePersist // Runs before save
//    @PreUpdate // Runs before update
//    private void encryptSensitiveData() {
//        try {
//            String secretKey = EncryptionUtil.generateKey();
//
//            if (idProofNo != null) {
//                this.idProofNo = EncryptionUtil.encrypt(idProofNo, secretKey);
//            }
//
//            if (dob != null) {
//                this.dob = EncryptionUtil.encrypt(dob, secretKey);
//            }
//
//            if (contactNo != null) {
//                this.contactNo = EncryptionUtil.encrypt(contactNo, secretKey);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            throw new RuntimeException("Error encrypting sensitive data", ex);
//        }
//    }
//    
//    
//
//    @Transient
//    public String decryptIdProofNo() {
//        try {
//            String secretKey = EncryptionUtil.generateKey();
//            return EncryptionUtil.decrypt(this.idProofNo, secretKey);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            throw new RuntimeException("Error decrypting idProofNo", ex);
//        }
//    }
//}



package com.translineindia.vms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.translineindia.vms.config.EncryptionUtil;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "vis_req_dtls")
@Data
public class VisitorRequestDtls {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String dob; // Stored in encrypted format
    private String idProof;
    private String idProofNo; // Stored in encrypted format
    private String contactNo; // Stored in encrypted format
    private String accessories;
    private String photo;
    private String idProofFile;

    @ManyToOne
    @JoinColumn(name = "visitor_request_mst_id", referencedColumnName = "vis_mst_id")
    @JsonBackReference
    private VisitorRequestMst visitorRequestMst;

    @PrePersist
    @PreUpdate
    private void encryptSensitiveData() {
        try {
            String secretKey = EncryptionUtil.generateKey(); // Replace with consistent key management

            if (idProofNo != null && !idProofNo.isEmpty()) {
                this.idProofNo = EncryptionUtil.encrypt(idProofNo, secretKey);
            }

            if (dob != null && !dob.isEmpty()) {
                this.dob = EncryptionUtil.encrypt(dob, secretKey);
            }

            if (contactNo != null && !contactNo.isEmpty()) {
                this.contactNo = EncryptionUtil.encrypt(contactNo, secretKey);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error encrypting sensitive data", ex);
        }
    }

  

}

