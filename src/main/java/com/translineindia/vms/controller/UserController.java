package com.translineindia.vms.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.translineindia.vms.config.AuthUtils;
import com.translineindia.vms.dtos.AdminLoginDTO;
import com.translineindia.vms.dtos.LoginDTO;
import com.translineindia.vms.dtos.NewLoginDTO;
import com.translineindia.vms.dtos.OfficeDTO;
import com.translineindia.vms.dtos.PasswordChangeReqDTO;
import com.translineindia.vms.dtos.VisitorRequestMstDTO;
import com.translineindia.vms.entity.OffMst;
import com.translineindia.vms.security.JwtHelper;
import com.translineindia.vms.service.UserService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {

	@Autowired
	private JwtHelper jwtHelper;
		
	private PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
			
	@Autowired
	private UserService userService;
	
	
	@PutMapping("/changePassword")
	@RolesAllowed({"USER"})
    public ResponseEntity<LoginDTO> changePassword(
                            @RequestBody PasswordChangeReqDTO passwordChangeRequest) {
        try {
//        	String cmpCd=AuthUtils.getCurrentUser().getCmpCd();
//            LoginDTO updatedUser = loginServiceImpl.changePassword(cmpCd,email, passwordChangeRequest.getNewPassword());
        	System.out.println("email :"+passwordChangeRequest.getEmail());
        	System.out.println(passwordChangeRequest.getCmpCd());
        	System.out.println(passwordChangeRequest.getNewPassword());
        	LoginDTO loginDTO = userService.changePassword(passwordChangeRequest);
            return ResponseEntity.ok(loginDTO);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }
	
	@PostMapping("/register")
	@RolesAllowed({"ADMIN"})
	 public ResponseEntity createUser(@RequestBody @Valid NewLoginDTO newLoginDTO){ 	 
//		AdminLoginDTO adminDTO = userService.createUser(newLoginDTO);	 
		return ResponseEntity.status(HttpStatus.CREATED).body("Created");
	 }
    
//    @GetMapping("/visitor-details")
//   	public ResponseEntity<LoginDTO> getVisitorDetails(){    	
//       	LoginDTO visitorDTO=new LoginDTO();
//       	BeanUtils.copyProperties(AuthUtils.getCurrentVisitor(), visitorDTO);
//   		return ResponseEntity.ok(visitorDTO);
//   	}
//	
	
	 @GetMapping(value={"/visitor-details","/user-details"})
//	 @PreAuthorize("hasRole('USER')")
	 @RolesAllowed({"USER"})
   	public ResponseEntity<LoginDTO> getVisitorDetails(){    	
       	LoginDTO visitorDTO=new LoginDTO();
       	BeanUtils.copyProperties(AuthUtils.getCurrentUser(), visitorDTO);
       	System.out.println("return DTO:"+visitorDTO);
   		return ResponseEntity.ok(visitorDTO);
   	}
	 
	 // added on 15-01-25
	 @RolesAllowed({"USER","ADMIN"})
	 @GetMapping("getAllOffices")
	 public ResponseEntity<?> getOfficeList(String cmpCd){
		 List<OffMst> offList = userService.getOfficesList(cmpCd);
		 if(!offList.isEmpty()) {
			 return ResponseEntity.ok(offList);
		 }
		 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Offices to show");
	 }
	 
	
		
}
