package com.translineindia.vms.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.translineindia.vms.dtos.VisitorLoginDTO;
import com.translineindia.vms.security.JwtHelper;
import com.translineindia.vms.service.VisitorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/visitors")
public class VisitorController {
	
	private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtHelper jwtHelper;
	
	@Autowired
	private VisitorService visitorService;
	

//	@PostMapping()
//	public ResponseEntity<VisitorLoginDTO> createUser(@RequestBody @Valid VisitorLoginDTO newVisitorDTO){		
//		return ResponseEntity.status(HttpStatus.CREATED).body(VisitorService.createUser(newVisitorDTO));
//	}
	
	@PostMapping()
	 public ResponseEntity<Map<String,Object>> createUser(@RequestBody @Valid VisitorLoginDTO newVisitorDTO){ 
	 Map<String, Object> response = new HashMap<>();
	 VisitorLoginDTO visitorDTO = visitorService.createUser(newVisitorDTO);
	 response.put("username",visitorDTO.getVisitorId());
	 response.put("email",visitorDTO.getEmail());
	 
	 return ResponseEntity.status(HttpStatus.CREATED).body(response);
	 }
	


    public VisitorController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtHelper jwtHelper) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtHelper = jwtHelper;
    }

   
	
		
}