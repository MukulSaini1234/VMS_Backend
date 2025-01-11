////package com.translineindia.vms.entity;
////
////import com.translineindia.vms.config.EncryptionUtil;
////
////import jakarta.persistence.Entity;
////import jakarta.persistence.GeneratedValue;
////import jakarta.persistence.GenerationType;
////import jakarta.persistence.Id;
////import jakarta.persistence.ManyToOne;
////import jakarta.persistence.PrePersist;
////import jakarta.persistence.PreUpdate;
////import jakarta.persistence.Table;
////import jakarta.persistence.Transient;
////import lombok.Data;
////
////@Entity
////@Table(name = "vis_req_dtls")
////@Data
////public class VisitorRequestDtls {
////	
////	@Id
////	@GeneratedValue(strategy = GenerationType.IDENTITY)
////	private Long id;	
////	private String name;
////	private String dob;//store in encrypted format
////	private String idProof;
////	
////	private String idproofNo;//store in encrypted format
////	
//////	@Transient
//////	private String encryptedIdProof;
////	
////	
////	private String contactNo;//store in encrypted format
////	private String accessories;
////	private String photo;
////	private String idProofFile;
////	
////	@ManyToOne
////	private VisitorRequestMst visitorRequestMst;
////	
////	@PrePersist//runs before save	
////	private void generateUserId() {
////			
////		 try {
////			 String secretKey = EncryptionUtil.generateKey();
////			 this.encryptedIdProof=EncryptionUtil.encrypt(idproofNo, secretKey);
////		 }catch(Exception ex) {
////			 
////		 }
////	}
////
////	
////	@PreUpdate//runs before update
////	private void prePersist() {
////		 try {
////			 String secretKey = EncryptionUtil.generateKey();
////			 this.encryptedPassword=EncryptionUtil.encrypt(password, secretKey);
////		 }catch(Exception ex) {
////			 
////		 }
////	}
////	
////}
//
////package com.translineindia.vms.entity;
////
////import com.translineindia.vms.config.EncryptionUtil;
////
////import jakarta.persistence.Entity;
////import jakarta.persistence.GeneratedValue;
////import jakarta.persistence.GenerationType;
////import jakarta.persistence.Id;
////import jakarta.persistence.ManyToOne;
////import jakarta.persistence.PrePersist;
////import jakarta.persistence.PreUpdate;
////import jakarta.persistence.Table;
////import jakarta.persistence.Transient;
////import lombok.Data;
////
////@Entity
////@Table(name = "vis_req_dtls")
////@Data
////public class VisitorRequestDtls {
////
////    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
////    private Long id;
////    private String name;
////    private String dob; // Store in encrypted format
////    private String idProof;
////
////    private String idProofNo; // Store in encrypted format
////
////    private String contactNo; // Store in encrypted format
////    private String accessories;
////    private String photo;
////    private String idProofFile;
////
////    @ManyToOne
////    private VisitorRequestMst visitorRequestMst;
////
////    @PrePersist // Runs before save
////    @PreUpdate // Runs before update
////    private void encryptSensitiveData() {
////        try {
////            String secretKey = EncryptionUtil.generateKey();
////
////            if (idProofNo != null) {
////                this.idProofNo = EncryptionUtil.encrypt(idProofNo, secretKey);
////            }
////
////            if (dob != null) {
////                this.dob = EncryptionUtil.encrypt(dob, secretKey);
////            }
////
////            if (contactNo != null) {
////                this.contactNo = EncryptionUtil.encrypt(contactNo, secretKey);
////            }
////        } catch (Exception ex) {
////            ex.printStackTrace();
////            throw new RuntimeException("Error encrypting sensitive data", ex);
////        }
////    }
////    
////    
////
////    @Transient
////    public String decryptIdProofNo() {
////        try {
////            String secretKey = EncryptionUtil.generateKey();
////            return EncryptionUtil.decrypt(this.idProofNo, secretKey);
////        } catch (Exception ex) {
////            ex.printStackTrace();
////            throw new RuntimeException("Error decrypting idProofNo", ex);
////        }
////    }
////}
//
//
//
//package com.translineindia.vms.entity;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.translineindia.vms.config.EncryptionUtil;
//
//import jakarta.persistence.*;
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
//    private String dob; // Stored in encrypted format
//    private String idProof;
//    private String idProofNo; // Stored in encrypted format
//    private String contactNo; // Stored in encrypted format
//    private String accessories;
//    private String photo;
//    private String idProofFile;
//
//    @ManyToOne
//    @JoinColumn(name = "visitor_request_mst_id", referencedColumnName = "vis_mst_id")
//    @JsonBackReference
//    private VisitorRequestMst visitorRequestMst;
//    private static final String SECRET_KEY = EncryptionUtil.generateKey();
//
//
//    @PrePersist
//    @PreUpdate
//    private void encryptSensitiveData() {
//        try {
//            String secretKey = EncryptionUtil.generateKey(); // Replace with consistent key management
//
//            if (idProofNo != null && !idProofNo.isEmpty()) {
//                this.idProofNo = EncryptionUtil.encrypt(idProofNo, secretKey);
//            }
//
//            if (dob != null && !dob.isEmpty()) {
//                this.dob = EncryptionUtil.encrypt(dob, secretKey);
//            }
//
//            if (contactNo != null && !contactNo.isEmpty()) {
//                this.contactNo = EncryptionUtil.encrypt(contactNo, secretKey);
//            }
//        } catch (Exception ex) {
//            throw new RuntimeException("Error encrypting sensitive data", ex);
//        }
//    }
//    
//    
//    @PostLoad
//    private void decryptSensitiveData() {
//        try {
//            String secretKey = EncryptionUtil.generateKey(); // Replace with consistent key management
//
//            if (idProofNo != null && !idProofNo.isEmpty()) {
//                this.idProofNo = EncryptionUtil.decrypt(idProofNo, secretKey);
//            }
//
//            if (dob != null && !dob.isEmpty()) {
//                this.dob = EncryptionUtil.decrypt(dob, secretKey);
//            }
//
//            if (contactNo != null && !contactNo.isEmpty()) {
//                this.contactNo = EncryptionUtil.decrypt(contactNo, secretKey);
//            }
//        } catch (Exception ex) {
//            throw new RuntimeException("Error decrypting sensitive data", ex);
//        }
//    }
//
//
//  
//
//}
//




package com.translineindia.vms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.translineindia.vms.config.EncryptionUtil;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    private String photo; //changed from string on 11-01-25

    private String idProofFile;

    @ManyToOne
    @JoinColumn(name = "visitor_request_mst_id", referencedColumnName = "vis_mst_id")
    @JsonBackReference
    private VisitorRequestMst visitorRequestMst;


  
    @PreUpdate
	@PrePersist
	 public void encryptSensitiveData() {
		try {
			this.idProofNo = EncryptionUtil.encrypt2(this.idProofNo);
			this.dob = EncryptionUtil.encrypt2(this.dob);
			this.contactNo = EncryptionUtil.encrypt2(this.contactNo);
	    } catch (Exception e) {
			System.out.println("Error encrypting sensitive data");
			throw new RuntimeException("Failed to encrypt sensitive data", e);
		}
	}
	
//	@PostLoad
//	public void decryptSensitiveData() {
//		try {
//			if (this.dob != null && !this.dob.isEmpty()) {
//				this.dob = EncryptionUtil.decrypt2(this.dob);
//				System.out.println("Decrypted DOB: " + this.dob); 
//			}
//			if (this.idProofNo != null && !this.idProofNo.isEmpty()) {
//				this.idProofNo = EncryptionUtil.decrypt2(this.idProofNo);
//			}
//			if (this.contactNo != null && !this.contactNo.isEmpty()) {
//				this.contactNo = EncryptionUtil.decrypt2(this.contactNo);
//			}
//			
//		} catch (Exception e) {
//			System.out.println("Error decrypting sensitive data");
//			throw new RuntimeException("Failed to decrypt sensitive data", e);
//		}
//	}
   
}
