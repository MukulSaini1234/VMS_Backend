package com.translineindia.vms.controller;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.translineindia.vms.config.AuthUtils;
import com.translineindia.vms.dtos.AppointmentDTO;
import com.translineindia.vms.dtos.VisitorDTO;
import com.translineindia.vms.dtos.VisitorLoginDTO;
import com.translineindia.vms.security.JwtHelper;
import com.translineindia.vms.security.VisitorLogin;
import com.translineindia.vms.service.AppointmentService;
import com.translineindia.vms.service.VisitorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/visitors")
public class VisitorController {
	
	@Autowired
	private VisitorService visitorService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	 
    @Autowired
	private AppointmentService appointmentService;
   
    @GetMapping("/visitor-details")
	public ResponseEntity<VisitorDTO> getVisitorDetails(){    	
    	VisitorDTO visitorDTO=new VisitorDTO();
    	BeanUtils.copyProperties(AuthUtils.getCurrentVisitor(), visitorDTO);
		return ResponseEntity.ok(visitorDTO);
	}
   
	@PostMapping(path="/appointment",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity Appointment(@Valid AppointmentDTO dto) throws IOException{
		System.out.println(dto);
//		System.out.println(params);
		AppointmentDTO responseDto = appointmentService.RequestAppointment(dto);		
		return ResponseEntity.status(HttpStatus.CREATED).body("Ok");
	}
	
    @PostMapping("/visitRequest")
    public ResponseEntity visitRequest(@RequestParam Map<String,String> params) {
    	System.out.println("param "+params);
//    	AppointmentDTO responseDto = appointmentService.RequestAppointment(dto);		
		return ResponseEntity.status(HttpStatus.CREATED).body("Ok");
    	
    }
	
    @PostMapping(value="/files",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity handleFiles(@RequestParam MultipartFile file) {
    	System.out.println("param "+file.getOriginalFilename());
//    	AppointmentDTO responseDto = appointmentService.RequestAppointment(dto);		
		return ResponseEntity.status(HttpStatus.OK).body(file.getOriginalFilename());
    	
    }
    
    @GetMapping("/getEmployeeDetails")
    public ResponseEntity getEmployee(@RequestParam(required=false,defaultValue="CO001") String cmpCd, String empId) {    	
    	String apiUrl = "http://localhost:8081/employee?empId="+empId+"&cmpCd="+cmpCd;
        return ResponseEntity.ok(restTemplate.getForObject(apiUrl, String.class)); //
    }
	
}