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
import com.translineindia.vms.dtos.LoginDTO;
import com.translineindia.vms.dtos.NewLoginDTO;
import com.translineindia.vms.dtos.OfficeDTO;
import com.translineindia.vms.dtos.PasswordChangeReqDTO;
//import com.example.demo.excep.ResourceNotFoundException;
import com.translineindia.vms.dtos.VisitorLoginDTO;
import com.translineindia.vms.entity.AppointmentEntity;
import com.translineindia.vms.entity.Login;
import com.translineindia.vms.entity.LoginId;
import com.translineindia.vms.entity.OffMst;
import com.translineindia.vms.repository.AppointmentRepo;
import com.translineindia.vms.repository.LoginRepository;
import com.translineindia.vms.repository.OffRepo;
import com.translineindia.vms.exception.*;

@Service
public class UserService {
	
	@Autowired
	private LoginRepository loginRepo;
	
	@Autowired
	private AppointmentRepo appointmentRepo;
	
	@Autowired
	private OffRepo offRepo;
		
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
	
	
//	public String getNextVisitorId(String cmpCd) {
//	    String prefix = "VS";
//	    String prevId = "";
//	    Optional<String> data = loginRepo.getMaxVisitorId(cmpCd);
//
//	    if (data.isPresent()) {
//	        prevId = data.get();
//	    } else {
//	        prevId = String.format("%s%08d", prefix, 0); // Correct initial ID with prefix and zero-padded sequence
//	    }
//
//	    int prevSeq;
//	    try {
//	        prevSeq = Integer.parseInt(prevId.substring(prefix.length())); // Extract numeric part after prefix
//	    } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
//	        throw new IllegalArgumentException("Invalid previous visitor ID format: " + prevId, e);
//	    }
//
//	    int nextSeq = prevSeq + 1; // Increment sequence
//	    return String.format("%s%08d", prefix, nextSeq); // Generate next visitor ID
//	}

	
	
	public LoginDTO createUser(NewLoginDTO dto) {
		
		//cmpCd exist or not
		
		//email already exist in this cmpCd
//		visitorRepo.findByCmpCdAndEmail(newVisitorDTO.getCmpCd(),newVisitorDTO.getEmail())
//		.orElseThrow(()->new AlreadyExistsException("Email Already Registered"));
		
		 // Check if the email already exists for the provided cmpCd
	    boolean exists = loginRepo.findByCmpCdAndEmail(dto.getCmpCd(), dto.getEmail()).isPresent();
	    if (exists) {
	        throw new AlreadyExistsException("Email already registered");
	    }
	    
	    exists = loginRepo.findById(new LoginId(dto.getCmpCd(), dto.getUserId())).isPresent();
	    if (exists) {
	        throw new AlreadyExistsException("User ID already exists");
	    }
		
	    System.out.println("dto: "+dto);
		Login login=new Login();
		login.setFirstName(dto.getFirstName());
		login.setLastName(dto.getLastName());
		login.setEmail(dto.getEmail());				
		login.setPassword(passwordEncoder.encode(dto.getPassword()));
//		visitor.setUserId(getNextVisitorId(dto.getCmpCd()));
		login.setUserId(dto.getUserId());
		login.setCmpCd(dto.getCmpCd());		
		login.setRefCmpName(dto.getRefCmpName());
		login.setAddress(dto.getAddress());
		login.setRole(dto.getRole());
		System.out.println("Login :"+login);
						
//		if(visitorRepo.findByEmail(visitor.getEmail())==null) {		
		try {
			Login savedUser=loginRepo.save(login); 
			LoginDTO loginDTO=new LoginDTO();
			BeanUtils.copyProperties(savedUser, loginDTO);
			return loginDTO;	
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
        String maxSerialStr = loginRepo.findMaxSerialNumber();
        int newSerial = (maxSerialStr != null) ? Integer.parseInt(maxSerialStr) + 1 : 1;
        return "VS" + String.format("%04d", newSerial); // Pad with leading zeros if needed
    }
	
	public Login getUserByIdOrEmail(String cmpCd, String IdOrEmail) {
		Optional<Login> visitor=loginRepo.getUser(cmpCd, IdOrEmail);
		return visitor.isPresent()?visitor.get():null;
	}	
	
	public LoginDTO changePassword(PasswordChangeReqDTO passwordChangeReq) {
		System.out.println("cmpCd :"+passwordChangeReq.getCmpCd());
		System.out.println("email: "+passwordChangeReq.getEmail());
		System.out.println("newPassword: "+passwordChangeReq.getNewPassword());
	    Login login = loginRepo.findByCmpCdAndEmail(passwordChangeReq.getCmpCd(), passwordChangeReq.getEmail())
	            .orElseThrow(() -> new ResourceNotFoundException("User", "email", passwordChangeReq.getEmail()));
	    System.out.println("User email :"+login.getEmail());
	    System.out.println("User password: "+login.getPassword());
	    if (passwordEncoder.matches(passwordChangeReq.getNewPassword(), login.getPassword())) {
	        throw new DuplicateEntryException("New password cannot be the same as the current password.");
	    }

	    String encodedPassword = passwordEncoder.encode(passwordChangeReq.getNewPassword());
	    login.setPassword(encodedPassword); 

	    login.setPasswordUpdatedDate(LocalDateTime.now());
	    Login savedUser = loginRepo.save(login);
	    LoginDTO updatedUser = new LoginDTO() ;

	    updatedUser.setEmail(savedUser.getEmail());	    
	    updatedUser.setPasswordUpdatedDate(savedUser.getPasswordUpdatedDate());
	    return updatedUser;
	}
	
	public List<OffMst> getOfficesList(String cmpCd){
		List<OffMst> offList = offRepo.findByCmpCd(cmpCd);

		return offList;
	}
	
}
