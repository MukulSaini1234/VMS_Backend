package com.translineindia.vms.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.translineindia.vms.config.AuthUtils;
import com.translineindia.vms.dtos.VisitorDTO;
import com.translineindia.vms.dtos.VisitorLoginDTO;
import com.translineindia.vms.security.JwtHelper;
import com.translineindia.vms.security.VisitorLogin;
import com.translineindia.vms.service.VisitorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/visitors")
public class VisitorController {
	
	@Autowired
	private VisitorService visitorService;		
   
    @GetMapping("/visitor-details")
	public ResponseEntity<VisitorDTO> getVisitorDetails(){    	
    	VisitorDTO visitorDTO=new VisitorDTO();
    	BeanUtils.copyProperties(AuthUtils.getCurrentVisitor(), visitorDTO);
		return ResponseEntity.ok(visitorDTO);
	}
	
}