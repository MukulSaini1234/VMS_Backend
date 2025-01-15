package com.translineindia.vms.controller;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.transline.vms.utils.ShortUniqueIdGenerator;
import com.translineindia.vms.config.AuthUtils;
import com.translineindia.vms.dtos.AppointmentDTO;
import com.translineindia.vms.dtos.PasswordChangeReqDTO;
import com.translineindia.vms.dtos.LoginDTO;
import com.translineindia.vms.dtos.VisitorLoginDTO;
import com.translineindia.vms.dtos.VisitorRequestDtlsDTO;
import com.translineindia.vms.dtos.VisitorRequestMstDTO;
import com.translineindia.vms.entity.AppointmentEntity;
import com.translineindia.vms.entity.VisitorRequestMst;
import com.translineindia.vms.security.JwtHelper;
import com.translineindia.vms.security.UserPrincipal;
import com.translineindia.vms.service.AppointmentService;
import com.translineindia.vms.service.UserService;

import jakarta.persistence.criteria.Path;
import jakarta.validation.Valid;
import jakarta.servlet.ServletContext;

@RestController
@RequestMapping("/api/visitors")
@CrossOrigin(origins = "*",allowedHeaders = "*", allowCredentials = "false")
public class VisitorController {
	
	@Autowired
	private UserService visitorService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ServletContext servletContext;
	
	 
    @Autowired
	private AppointmentService appointmentService;
   
   
//	@PostMapping(path="/appointment",consumes = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<String> Appointment(@Valid @RequestBody AppointmentDTO dto) throws IOException{
//		System.out.println(dto);
////		System.out.println(params);
//		List<AppointmentEntity> responseDto = appointmentService.RequestAppointment(dto);
//		return ResponseEntity.status(HttpStatus.CREATED).body("Ok");
//	}
	
//    @PostMapping("/visitRequest")
//    public ResponseEntity visitRequest(@RequestParam Map<String,String> params) {
//    	System.out.println("param "+params);
//    	AppointmentDTO responseDto = appointmentService.RequestAppointment(dto);		
//		return ResponseEntity.status(HttpStatus.CREATED).body("Ok");
//    	
//    }
	
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
    
    @GetMapping("/test")
    public String test() {    	    	
        return "It Works";
    }
	
	
    // New api added on 07-01-24
    @PostMapping(value ="/request" , consumes = "multipart/form-data") // working now on 8Th 
    public VisitorRequestMstDTO createVisitorRequest(@ModelAttribute @Valid VisitorRequestMstDTO request) {
    	System.out.println("request :"+request);
    	
    	
    	VisitorRequestMstDTO res = appointmentService.createVisitorRequest(request);
//        return ResponseEntity.ok("Appointment request saved successfully.");
    	return res;
    	
    }
    
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("getAllRequests")
    public ResponseEntity<List<VisitorRequestMst>> getRequests(@RequestParam String cmpCd){
    	System.out.println("cmpCd: "+cmpCd);
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	System.out.println("autheication "+authentication);
    	System.out.println("Authentication role: "+authentication.getPrincipal());
    
    	List<VisitorRequestMst> vsm = appointmentService.getAllVisitorRequest(cmpCd);
    	System.out.println("vsm: "+vsm);
    	return ResponseEntity.status(HttpStatus.OK).body(vsm);
    	
    }
    
    
    
    // Get API ADDED on 08-01-25
//    @PreAuthorize("hasRole('VISITOR')")
    @GetMapping("getAppointmentRequests")
    public ResponseEntity<List<VisitorRequestMst>> getVisitorRequests(@RequestParam String cmpCd, String visitorId) {
    	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         System.out.println(authentication.getCredentials());
    	List<VisitorRequestMst> req = appointmentService.getVisitorDetailsByVisitorId(visitorId);
    	return ResponseEntity.status(HttpStatus.OK).body(req);
    }
    // Put api added on 08-01-25
    @PatchMapping("update-vehicle")
    public VisitorRequestMstDTO updateVehicleDetails(
                                                  @RequestBody VisitorRequestMstDTO updatedVisitorRequest) {
//    	System.out.print("id ;"+id);
    	System.out.print("vis dt:"+updatedVisitorRequest);
        return appointmentService.updateVehicleDetails(updatedVisitorRequest);
    }
    
//    @PreAuthorize("hasRole('ADMIN')")
//    public VisitorRequestMstDTO getRequests(@RequestParam String cmpCd, String offCd) {
//    	System.out.println("");
//    	
//    }

    
}