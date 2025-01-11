//package com.translineindia.vms.service;
//
//public class AdminService {
//
//	
//	  public Admin getAdminByIdOrEmail(String cmpCd, String idOrEmail) {
//	        // Fetch admin from the database using cmpCd and idOrEmail
//	        // Example code:
//	        // return adminRepository.findByCmpCdAndIdOrEmail(cmpCd, idOrEmail);
//	        return null; // Replace with actual implementation
//	    }
//	
//}
package com.translineindia.vms.service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.translineindia.vms.dtos.AppointmentDTO;
import com.translineindia.vms.dtos.PasswordChangeReqDTO;
//import com.example.demo.excep.ResourceNotFoundException;
import com.translineindia.vms.dtos.VisitorLoginDTO;
import com.translineindia.vms.entity.AppointmentEntity;
import com.translineindia.vms.entity.Visitor;
import com.translineindia.vms.entity.VisitorId;
import com.translineindia.vms.repository.AppointmentRepo;
import com.translineindia.vms.repository.VisitorRepository;

import com.translineindia.vms.exception.*;

@Service
public class AdminService {
	
	@Autowired
	private VisitorRepository visitorRepo;
	
	@Autowired
	private AppointmentRepo appointmentRepo;
		
	private PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

	
//	public String getNextVisitorId(String cmpCd) {		
//		String prevId="";
//		String prefix="VS";
//		Optional<String> data=visitorRepo.getMaxVisitorId(cmpCd);
//		if(data.isPresent()) {
//			prevId=data.get();
//		}else {
//			prevId=String.format("%s%08d", 1);
//		}
//		int prevSeq=Integer.parseInt(prevId.substring(prefix.length()))+1;
//		String nextId=String.format("%s%08d", prevSeq+1);
//		return nextId;
//	}
	
	
	public String getNextVisitorId(String cmpCd) {
	    String prefix = "AD";
	    String prevId = "";
	    Optional<String> data = visitorRepo.getMaxVisitorId(cmpCd);

	    if (data.isPresent()) {
	        prevId = data.get();
	    } else {
	        prevId = String.format("%s%08d", prefix, 0); // Correct initial ID with prefix and zero-padded sequence
	    }

	    int prevSeq;
	    try {
	        prevSeq = Integer.parseInt(prevId.substring(prefix.length())); // Extract numeric part after prefix
	    } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
	        throw new IllegalArgumentException("Invalid previous admin ID format: " + prevId, e);
	    }

	    int nextSeq = prevSeq + 1; // Increment sequence
	    return String.format("%s%08d", prefix, nextSeq); // Generate next visitor ID
	}

	
	
	public VisitorLoginDTO createUser(VisitorLoginDTO newVisitorDTO) {
		
		//cmpCd exist or not
		
		//email already exist in this cmpCd
//		visitorRepo.findByCmpCdAndEmail(newVisitorDTO.getCmpCd(),newVisitorDTO.getEmail())
//		.orElseThrow(()->new AlreadyExistsException("Email Already Registered"));
		
		 // Check if the email already exists for the provided cmpCd
	    boolean exists = visitorRepo.findByCmpCdAndEmail(newVisitorDTO.getCmpCd(), newVisitorDTO.getEmail()).isPresent();
	    if (exists) {
	        throw new AlreadyExistsException("Email already registered with the provided company code.");
	    }
		 		
		Visitor visitor=new Visitor();
		visitor.setFirstName(newVisitorDTO.getFirstName());
		visitor.setLastName(newVisitorDTO.getLastName());
		visitor.setEmail(newVisitorDTO.getEmail());				
		visitor.setPassword(passwordEncoder.encode(newVisitorDTO.getPassword()));
		visitor.setVisitorId(getNextVisitorId(newVisitorDTO.getCmpCd()));
		visitor.setCmpCd(newVisitorDTO.getCmpCd());		
		visitor.setVisitorCmpName(newVisitorDTO.getVisCmpName());
		visitor.setAddress(newVisitorDTO.getAddress());
		visitor.setRole("ADMIN");
		System.out.println("visitor :"+visitor);
						
//		if(visitorRepo.findByEmail(visitor.getEmail())==null) {		
		try {
			Visitor savedVisitor=visitorRepo.save(visitor); 
			VisitorLoginDTO visitorDTO=new VisitorLoginDTO();
			BeanUtils.copyProperties(savedVisitor, visitorDTO);
			return visitorDTO;	
		}catch(Exception ex) {
			throw new ConflictException("Some error has occurred", ex);
		}
		
//		VisitorLoginDTO visitorDTO=new VisitorLoginDTO();
//		visitorDTO.setVisitorId(savedVisitor.getVisitorId());
//		visitorDTO.setFirstName(savedVisitor.getFirstName());
//		visitorDTO.setLastName(savedVisitor.getLastName());
//		visitorDTO.setEmail(savedVisitor.getEmail());
//		visitorDTO.setAddress(savedVisitor.getAddress());			
	}
	
	
	public String generateUsername() {
	    String maxSerialStr = visitorRepo.findMaxSerialNumber();
        if (maxSerialStr != null && maxSerialStr.startsWith("AD")) {
	    String numericPart = maxSerialStr.substring(2);
             int newSerial = Integer.parseInt(numericPart) + 1;
             return "AD" + String.format("%04d", newSerial);
	    }

	   return "AD0001"; 
	}
	
	/*
	 *  public String generateUsername() {
        String maxSerialStr = visitorRepo.findMaxSerialNumber().startsWith("AD");
        int newSerial = (maxSerialStr != null) ? Integer.parseInt(maxSerialStr) + 1 : 1;
        return "AD" + String.format("%04d", newSerial); // Pad with leading zeros if needed
    }*/

	
	public Visitor getVisitorByIdOrEmail(String cmpCd, String IdOrEmail) {
		Optional<Visitor> visitor=visitorRepo.getVisitor(cmpCd, IdOrEmail);
		return visitor.isPresent()?visitor.get():null;
	}	
	
	public VisitorLoginDTO changePassword(PasswordChangeReqDTO passwordChangeReq) {
		System.out.println("cmpCd :"+passwordChangeReq.getCmpCd());
		System.out.println("email: "+passwordChangeReq.getEmail());
		System.out.println("newPassword: "+passwordChangeReq.getNewPassword());
	    Visitor visitor = visitorRepo.findByCmpCdAndEmail(passwordChangeReq.getCmpCd(), passwordChangeReq.getEmail())
	            .orElseThrow(() -> new ResourceNotFoundException("User", "email", passwordChangeReq.getEmail()));
	    System.out.println("vis email :"+visitor.getEmail());
	    System.out.println("vis password: "+visitor.getPassword());
	    if (passwordEncoder.matches(passwordChangeReq.getNewPassword(), visitor.getPassword())) {
	        throw new DuplicateEntryException("New password cannot be the same as the current password.");
	    }

	    String encodedPassword = passwordEncoder.encode(passwordChangeReq.getNewPassword());
	    visitor.setPassword(encodedPassword); 

	    visitor.setPasswordUpdatedDate(LocalDateTime.now());
	    Visitor savedVisitor = visitorRepo.save(visitor);
	    VisitorLoginDTO newVisitorCred = new VisitorLoginDTO() ;

	    newVisitorCred.setEmail(savedVisitor.getEmail());
	    newVisitorCred.setPassword(savedVisitor.getPassword());
	    newVisitorCred.setPasswordUpdatedDate(savedVisitor.getPasswordUpdatedDate());
	    return newVisitorCred;
	}
	
	
	
}
