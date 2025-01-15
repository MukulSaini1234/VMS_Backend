package com.translineindia.vms.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.translineindia.vms.config.AuthUtils;
import com.translineindia.vms.dtos.LoginDTO;
import com.translineindia.vms.dtos.PasswordChangeReqDTO;
import com.translineindia.vms.security.JwtHelper;
import com.translineindia.vms.service.UserService;

@RestController
@RequestMapping("Users")
public class UserController {

	@Autowired
	private JwtHelper jwtHelper;
		
	private PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
			
	@Autowired
	private UserService userService;
	
	
	@PutMapping("/changePassword")
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
    
//    @GetMapping("/visitor-details")
//   	public ResponseEntity<LoginDTO> getVisitorDetails(){    	
//       	LoginDTO visitorDTO=new LoginDTO();
//       	BeanUtils.copyProperties(AuthUtils.getCurrentVisitor(), visitorDTO);
//   		return ResponseEntity.ok(visitorDTO);
//   	}
//	
}
