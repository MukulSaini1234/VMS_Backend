package com.translineindia.vms.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.translineindia.vms.config.AuthUtils;
import com.translineindia.vms.dtos.JwtRequest;
import com.translineindia.vms.dtos.JwtResponse;
import com.translineindia.vms.dtos.LoginDTO;
import com.translineindia.vms.dtos.NewLoginDTO;
import com.translineindia.vms.dtos.PasswordChangeReqDTO;
import com.translineindia.vms.dtos.VisitorLoginDTO;
import com.translineindia.vms.entity.Login;
import com.translineindia.vms.repository.LoginRepository;
import com.translineindia.vms.security.JwtHelper;
import com.translineindia.vms.security.UserPrincipal;
import com.translineindia.vms.service.CustomUserDetailsService;
import com.translineindia.vms.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;

import com.translineindia.vms.security.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins =  "*" , allowedHeaders = "*", allowCredentials = "false")
public class AuthController {
	
	@Autowired
	private JwtHelper jwtHelper;
		
	private PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
			
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> loginVisitor(@Valid @RequestBody JwtRequest jwtRequest) {		 	
		//validate if user exists or not		
		Login visitor = userService.getUserByIdOrEmail(jwtRequest.getCmpCd(),jwtRequest.getUserId());		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 if(visitor!=null && passwordEncoder.matches(jwtRequest.getUserPw(), visitor.getPassword())) {
			 	String userNameToken = jwtRequest.getCmpCd()+ "::" +jwtRequest.getUserId();
			    String token = this.jwtHelper.generateToken(userNameToken);
			    System.out.println("userNameToken :"+userNameToken);
				long expirationTime = System.currentTimeMillis() + this.jwtHelper.getTokenValidity() * 1000;
				
				String time = sdf.format(new Date(expirationTime));
				
				 
				JwtResponse response = JwtResponse.builder()
						.status("success")
						.message("Login Successfull")
						.token(token)
						.timestamp(time)
						.Login(visitor)
						.build();
				return new ResponseEntity<>(response, HttpStatus.OK);
			 
		 }else {	
			 JwtResponse response= JwtResponse.builder().status("failed")
			 .message("Bad Credentials")
			 .timestamp(sdf.format(new Date()))
			 .build();
			 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		 }
		    
		// if user exists then generate token and return jwt Response
		//VS$cmpCd$visitorId || VS$cmpCd$email			
	}
	
	
	@PostMapping("/register")
	 public ResponseEntity<LoginDTO> createUser(@RequestBody @Valid VisitorLoginDTO newVisitorDTO){
		System.out.println("visitor dto: "+newVisitorDTO);
		System.out.println("vis cmp name:"+newVisitorDTO.getVisCmpName());
		if(!newVisitorDTO.getUserId().startsWith("VS")) {
			throw new RuntimeException("Visitor Id must be started with VS");
		}
		
		NewLoginDTO dto=new NewLoginDTO();
		BeanUtils.copyProperties(newVisitorDTO, dto);
		dto.setRole("VISITOR");
		
		LoginDTO loginDTO = userService.createUser(dto);	 
		return ResponseEntity.status(HttpStatus.CREATED).body(loginDTO);
	 }	
}
