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
import com.transline.vms.utils.ApiResponse;
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
    

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("getAllRequests")
  public ResponseEntity<Optional<List<VisitorRequestMst>>> getRequests(@RequestParam String cmpCd){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  	Optional<List<VisitorRequestMst>> vsm = Optional.ofNullable(appointmentService.getAllVisitorRequest(cmpCd));
  	if(vsm.isPresent() && !vsm.isEmpty()) {
  		return  ResponseEntity.status(HttpStatus.OK).body(vsm);
  	}
  	else {
  		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  	}
  	
  }
    
    // Get API ADDED on 08-01-25
//    @PreAuthorize("hasRole('VISITOR')")
    @GetMapping("getAppointmentRequests")
    public ResponseEntity<?> getVisitorRequests(@RequestParam String cmpCd, String visitorId) {
    	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         System.out.println(authentication.getCredentials());
         Optional<List<VisitorRequestMst>> req = 
        	        Optional.ofNullable(appointmentService.getVisitorDetailsByVisitorId(visitorId)); 
               if (req.isPresent() && !req.get().isEmpty()) {
        	        return ResponseEntity.status(HttpStatus.OK).body(req.get());
        	    } else {
        	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No requests found for this user");
        	    }
    }
    
    
    // Put api added on 08-01-25
    @PutMapping("/update-vehicle")
    public ResponseEntity<ApiResponse> updateVehicleDetails(
            @RequestBody VisitorRequestMstDTO updatedVisitorRequest) {
        try {
            VisitorRequestMstDTO mstDTO = appointmentService.updateVehicleDetails(updatedVisitorRequest);
            ApiResponse response = new ApiResponse("Vehicle details updated successfully", true);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            ApiResponse response = new ApiResponse("Failed to update vehicle details: " + ex.getMessage(), false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    
    // Added on 15-01-25
	 // Get Api to change the status of visit request
    @PutMapping("updateReqStatus")
    public ResponseEntity<ApiResponse> updateReqStatus(@RequestBody VisitorRequestMstDTO updatedVisitorRequest) {
        try {
            String status = appointmentService.updateReqStatus(updatedVisitorRequest);
            if(status.equalsIgnoreCase("A")) {
            	status = "Approved";
            }
            else {
            	status = "Denied"; 
            } 
//            return ResponseEntity.ok("Request " + status);
            return new ResponseEntity<ApiResponse>(new ApiResponse("Request "+status,true), HttpStatus.OK);
//            return new ResponseEntity<ApiResponse>(new ApiResponse("Request :"+status, true), HttpStatus.OK);
        } catch (RuntimeException ex) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + ex.getMessage());
        	  return new ResponseEntity<ApiResponse>(new ApiResponse("Error:  "+ex.getMessage(),false), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + ex.getMessage());
        	  return new ResponseEntity<ApiResponse>(new ApiResponse("An Unexpected error occurred "+ex.getMessage(),true), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}