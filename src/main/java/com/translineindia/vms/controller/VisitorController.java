package com.translineindia.vms.controller;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import org.springframework.validation.BindingResult;
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

import jakarta.annotation.security.RolesAllowed;
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
    
//    @GetMapping("/getEmployeeDetails")
//    public ResponseEntity getEmployee(@RequestParam(required=false,defaultValue="CO001") String cmpCd, String empId) {    	
//    	String apiUrl = "http://localhost:8081/employee?empId="+empId+"&cmpCd="+cmpCd;
//    	System.out.println("test"+restTemplate.getForObject(apiUrl, String.class));
//        return ResponseEntity.ok(restTemplate.getForObject(apiUrl, String.class)); //
//    }
    
    @GetMapping("/getEmployeeDetails")
    public ResponseEntity getEmployee(@RequestParam(required = false, defaultValue = "CO001") String cmpCd, String empId) {     
        String apiUrl = "http://localhost:8081/employee?empId=" + empId + "&cmpCd=" + cmpCd;
        String response = restTemplate.getForObject(apiUrl, String.class);
        if (response == null || response.isEmpty()) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employee data found for empId: " + empId);
        }
        return ResponseEntity.ok(response);
    }

    
    @GetMapping("/test")
    public String test() {    	    	
        return "It Works";
    }
	
	
    // New api added on 07-01-24
//    @PostMapping(value ="/request" , consumes = "multipart/form-data") // working now on 8Th 
//    public VisitorRequestMstDTO createVisitorRequest(@Valid VisitorRequestMstDTO request, BindingResult result) {
//    	System.out.println("request :"+request);
//        VisitorRequestMstDTO res = appointmentService.createVisitorRequest(request);
////        return ResponseEntity.ok("Appointment request saved successfully.");
//    	return res;
//    }
    
    
    // new created on 27-01-25
    @PostMapping(value ="/request" , consumes = "multipart/form-data") // working now on 8Th 
    public VisitorRequestMstDTO createVisitorRequest(
			@RequestParam Map<String,String> params,
			@RequestParam Map<String,MultipartFile> files
//			@RequestParam Map<String,List<MultipartFile>> files2
			) 
    {
     	System.out.println("request :"+params);
     	System.out.println("files :"+files);
     // Initialize the master DTO
        VisitorRequestMstDTO visitorMstDTO = new VisitorRequestMstDTO();
        visitorMstDTO.setEmpId(params.get("empId"));
        visitorMstDTO.setEmpName(params.get("empName"));
        visitorMstDTO.setEmpEmail(params.get("empEmail"));
        visitorMstDTO.setVisOrganization(params.get("visOrganization"));
        visitorMstDTO.setPurpose(params.get("purpose"));
        visitorMstDTO.setFromDate(LocalDate.parse(params.get("fromDate")));
        visitorMstDTO.setToDate(LocalDate.parse(params.get("toDate")));
        visitorMstDTO.setVisitorAddress(params.get("visitorAddress"));
        visitorMstDTO.setHasVehicle(Boolean.parseBoolean(params.get("hasVehicle")));
        visitorMstDTO.setVehicleNo(params.get("vehicleNo"));
        visitorMstDTO.setDriverName(params.get("driverName"));
        visitorMstDTO.setVehicleType(params.get("vehicleType"));
        visitorMstDTO.setDriverDlNo(params.get("driverDlNo"));
        visitorMstDTO.setDriverDlUpto(params.get("driverDlUpto"));
        visitorMstDTO.setVisitorId(params.get("visitorId"));
        visitorMstDTO.setCmpCd(params.get("cmpCd"));
        visitorMstDTO.setReqStatus(params.get("reqStatus"));
//        visitorMstDTO.setStatusRemarks(params.get("statusRemarks"));
//        visitorMstDTO.setExtension(Boolean.parseBoolean(params.get("isExtension")));
//        visitorMstDTO.setExFromDate(LocalDate.parse(params.get("exFromDate")));
//        visitorMstDTO.setExToDate(LocalDate.parse(params.get("exToDate")));

        // Map the nested DTOs
        List<VisitorRequestDtlsDTO> visitorDetailsList = new ArrayList<>();
        int i = 0;
        while (params.containsKey("visitorDtls[" + i + "].name")) {
            VisitorRequestDtlsDTO visitorDtlsDTO = new VisitorRequestDtlsDTO();
            visitorDtlsDTO.setName(params.get("visitorDtls[" + i + "].name"));
            visitorDtlsDTO.setIdProof(params.get("visitorDtls[" + i + "].idProof"));
            visitorDtlsDTO.setIdproofNo(params.get("visitorDtls[" + i + "].idproofNo"));
            visitorDtlsDTO.setDob(params.get("visitorDtls[" + i + "].dob"));
            visitorDtlsDTO.setContactNo(params.get("visitorDtls[" + i + "].contactNo"));

            // Handle file mapping for photo or ID proof files
            MultipartFile photoFile = files.get("visitorDtls[" + i + "].photo");
            if (photoFile != null && !photoFile.isEmpty()) {
                visitorDtlsDTO.setPhoto(photoFile.getOriginalFilename());
            }

            MultipartFile idProofFile = files.get("visitorDtls[" + i + "].idProofFile");
            if (idProofFile != null && !idProofFile.isEmpty()) {
                visitorDtlsDTO.setIdProofFile(idProofFile);
            }

            visitorDetailsList.add(visitorDtlsDTO);
            i++;
        }

        visitorMstDTO.setVisitorDtls(visitorDetailsList);
        System.out.println("visitor Mst DTO "+visitorMstDTO);
        // Process the visitorMstDTO (e.g., save to database)
        // visitorService.save(visitorMstDTO);

//        return "Visitor request created successfully!";
        
      
      VisitorRequestMstDTO res = appointmentService.createVisitorRequest(visitorMstDTO);
//      return ResponseEntity.ok("Appointment request saved successfully.");
  
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
  		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  	}
  	
  }
  
  // Added on 17-01-25
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("getAcceptedRequests")
  public ResponseEntity<Optional<List<VisitorRequestMst>>> getAcceptedReq(@RequestParam String cmpCd){
	  Optional<List<VisitorRequestMst>> vsm = Optional.ofNullable(appointmentService.getRequestsByStatus(cmpCd,"A"));
	  if(vsm.isPresent() && !vsm.isEmpty()) {
		  return ResponseEntity.status(HttpStatus.OK).body(vsm);
	  }
	  else {
		  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	  }
  }
  
  
//Added on 17-01-25
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("getRejectedRequests")
 public ResponseEntity<Optional<List<VisitorRequestMst>>> getRejectedReq(@RequestParam String cmpCd){
	  Optional<List<VisitorRequestMst>> vsm = Optional.ofNullable(appointmentService.getRequestsByStatus(cmpCd,"D"));
	  if(vsm.isPresent() && !vsm.isEmpty()) {
		  return ResponseEntity.status(HttpStatus.OK).body(vsm);
	  }
	  else {
		  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	  }
 }
 
  @PreAuthorize("hasRole('RECEPTION')")
  @GetMapping("getAllReqForReception")
  public ResponseEntity<Optional<List<VisitorRequestMst>>> getRequestsForReception(@RequestParam String cmpCd){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  	Optional<List<VisitorRequestMst>> vsm = Optional.ofNullable(appointmentService.getAllRequestsForReception(cmpCd));
  	if(vsm.isPresent() && !vsm.isEmpty()) {
  		return  ResponseEntity.status(HttpStatus.OK).body(vsm);
  	}
  	else {
  		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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


    // added on 30-01-25
    @RolesAllowed({"RECEPTION"})
    @PutMapping("updateReqStatusReception")
    public ResponseEntity<ApiResponse> updateReqStatusRecep(@RequestBody VisitorRequestMstDTO updatedVisitorRequest){
    	 try {
             String status = appointmentService.updateReqStatusReception(updatedVisitorRequest);
             if(status.equalsIgnoreCase("A")) {
             	status = "Approved";
             }
             else {
             	status = "Denied"; 
             } 
//             return ResponseEntity.ok("Request " + status);
             return new ResponseEntity<ApiResponse>(new ApiResponse("Request "+status,true), HttpStatus.OK);
//             return new ResponseEntity<ApiResponse>(new ApiResponse("Request :"+status, true), HttpStatus.OK);
         } catch (RuntimeException ex) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + ex.getMessage());
         	  return new ResponseEntity<ApiResponse>(new ApiResponse("Error:  "+ex.getMessage(),false), HttpStatus.NOT_FOUND);
         } catch (Exception ex) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + ex.getMessage());
         	  return new ResponseEntity<ApiResponse>(new ApiResponse("An Unexpected error occurred "+ex.getMessage(),true), HttpStatus.INTERNAL_SERVER_ERROR);
         }
    }
    
    // Added on 15-01-25
	 // Get Api to change the status of visit request
    @RolesAllowed({"ADMIN","EMPLOYEE"})
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