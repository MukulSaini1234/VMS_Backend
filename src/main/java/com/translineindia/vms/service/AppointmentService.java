package com.translineindia.vms.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.translineindia.vms.config.EncryptionUtil;
import com.translineindia.vms.dtos.AppointmentDTO;
import com.translineindia.vms.dtos.VisitorLoginDTO;
import com.translineindia.vms.dtos.VisitorRequestDtlsDTO;
import com.translineindia.vms.dtos.VisitorRequestMstDTO;
import com.translineindia.vms.entity.AppointmentEntity;
import com.translineindia.vms.entity.Login;
import com.translineindia.vms.entity.VisitorRequestDtls;
import com.translineindia.vms.entity.VisitorRequestMst;
import com.translineindia.vms.exception.ConflictException;
import com.translineindia.vms.repository.AppointmentRepo;
import com.translineindia.vms.repository.VisitorRequestDetailsRepo;
import com.translineindia.vms.security.UserPrincipal;

import jakarta.transaction.Transactional;
import lombok.Data;

@Service
@Data
public class AppointmentService {
	
	@Autowired
	private AppointmentRepo repo;
	
	@Autowired
	private VisitorRequestDetailsRepo dtlsRepo;
	 private static final String uploadDir = "uploads/";
	
//	public List<AppointmentEntity> RequestAppointment(AppointmentDTO dto){
//		
//		List<AppointmentEntity> requests = dto.getVisitorDetails().stream().map(visitorDTO -> {
//            AppointmentEntity request = new AppointmentEntity();
//            // Set common fields
//            request.setEmpid(dto.getEmpId());
//            request.setEmpName(dto.getEmpName());
//            request.setEmpEmail(dto.getEmpEmail());
//            request.setPurpose(dto.getPurpose());
//            request.setForDays(dto.getForDays());
//            request.setFromDate(dto.getFromDate());
//            
//          
//            // Set visitor-specific fields
//            
//            request.setVisName(visitorDTO.getVisName());
//            request.setVisDob(visitorDTO.getVisDob());
//            request.setVisNum(visitorDTO.getVisNum());
//            request.setVisAccessories(visitorDTO.getVisAccessories());
////            request.setVisPhoto(visitorDTO.getVisPhoto());
////            request.setVisVehicle(visitorDTO.getVisVehicle());
////            request.setVisDriverName(visitorDTO.getVisDriverName());
////            request.setVisVehicleNo(visitorDTO.getVisVehicleNo());
////            request.setVisVehicleType(visitorDTO.getVisVehicleType());
////            request.setVisDriverDlNo(visitorDTO.getVisDriverDlNo());
////            request.setVisDriverDlUpto(visitorDTO.getVisDriverDlUpto());
//
//            return request;
//        }).collect(Collectors.toList());
//
//        return repo.save(requests);
//		
//	
//		
//	}
//	
	
//	public AppointmentDTO RequestAppointment(AppointmentDTO newAppointmentDTO) throws IOException {
//		AppointmentEntity newRequest = new AppointmentEntity();
////		VehicleDetailsDTO vehicleDetails = new VehicleDetailsDTO();
//		newRequest.setEmpid(newAppointmentDTO.getEmpId());
//		newRequest.setEmpName(newAppointmentDTO.getEmpName());
//		newRequest.setEmpEmail(newAppointmentDTO.getEmpEmail());
//		newRequest.setPurpose(newAppointmentDTO.getPurpose());
//		newRequest.setForDays(newAppointmentDTO.getForDays());
//		newRequest.setFromDate(newAppointmentDTO.getFromDate());
//		newRequest.setVisitorAdd(newAppointmentDTO.getVisitorAdd());
//		newRequest.setIdProof(newAppointmentDTO.getIdProof());
//		newRequest.setFileData(newAppointmentDTO.getFileData().getBytes());
//		newRequest.setVisPhoto(newAppointmentDTO.getFileData().getBytes());
//		newRequest.setVisName(newAppointmentDTO.getVisName());
//		newRequest.setVisDob(newAppointmentDTO.getVisDob());
//		newRequest.setVisNum(newAppointmentDTO.getVisNum());
//		newRequest.setVisAccessories(newAppointmentDTO.getVisAccessories());
//
//		System.out.println("newRequest :"+newRequest);
//		
//		newRequest.setVisDrivername(
//			    Optional.ofNullable(newAppointmentDTO)
//			            .map(AppointmentDTO::getVehicleDetails)
//			            .map(VehicleDetailsDTO::getVisDrivername)
//			            .orElse(null) 
//			);
////
////			newRequest.setVisVehicleno(
////			    Optional.ofNullable(newAppointmentDTO)
////			            .map(AppointmentDTO::getVehicleDetails)
////			            .map(VehicleDetailsDTO::getVisVehicleno)
////			            .orElse(null) 
////			);
////
////			newRequest.setVisVehicleType(
////			    Optional.ofNullable(newAppointmentDTO)
////			            .map(AppointmentDTO::getVehicleDetails)
////			            .map(VehicleDetailsDTO::getVisVehicleType)
////			            .orElse(null)
////			);
////
////			newRequest.setVisDriverDlNo(
////			    Optional.ofNullable(newAppointmentDTO)
////			            .map(AppointmentDTO::getVehicleDetails)
////			            .map(VehicleDetailsDTO::getVisDriverDlNo)
////			            .orElse(null) 
////			);
////
////	
////		
////		newRequest.setVisDriverDlUpto(
////			    Optional.ofNullable(newAppointmentDTO) 
////			            .map(AppointmentDTO::getVehicleDetails) 
////			            .map(VehicleDetailsDTO::getVisDriverDlUpto) 
////			            .orElse(null)
////			);
//
//        AppointmentDTO responseDTO = new AppointmentDTO();
//		System.out.println("responseDto: "+responseDTO);
//		AppointmentEntity savedRequest = repo.save(newRequest);
//		BeanUtils.copyProperties(savedRequest, responseDTO);	    
//		return responseDTO;
//	}
//	
	
	
//	 Following code added on 07-01-25 //working now on 8th
//	 public VisitorRequestMstDTO createVisitorRequest(VisitorRequestMstDTO visitorRequestMstDTO) {
//	        // Map DTO to Entity for VisitorRequestMst
//	        VisitorRequestMst visitorRequestMst = new VisitorRequestMst();
//	        VisitorRequestMstDTO mstData= new VisitorRequestMstDTO();
//	        visitorRequestMst.setEmpId(visitorRequestMstDTO.getEmpId());
//	        visitorRequestMst.setEmpName(visitorRequestMstDTO.getEmpName());
//	        visitorRequestMst.setVisOrganization(visitorRequestMstDTO.getVisOrganization());
//	        visitorRequestMst.setPurpose(visitorRequestMstDTO.getPurpose());
//	        visitorRequestMst.setFromDate(visitorRequestMstDTO.getFromDate());
//	        visitorRequestMst.setToDate(visitorRequestMstDTO.getToDate());
//	        visitorRequestMst.setVisitorAddress(visitorRequestMstDTO.getVisitorAddress());
//	        visitorRequestMst.setHasVehicle(visitorRequestMstDTO.getHasVehicle());
//	        visitorRequestMst.setVisitorId(visitorRequestMstDTO.getVisitorId());
//	        visitorRequestMst.setVehicleNo(visitorRequestMstDTO.getVehicleNo());
//	        visitorRequestMst.setDriverName(visitorRequestMstDTO.getDriverName());
//	        visitorRequestMst.setVehicleType(visitorRequestMstDTO.getVehicleType());
//	        visitorRequestMst.setDriverDlNo(visitorRequestMstDTO.getDriverDlNo());
//	        visitorRequestMst.setDriverDlUpto(visitorRequestMstDTO.getDriverDlUpto());
//	        visitorRequestMst.setEmpEmail(visitorRequestMstDTO.getEmpEmail());
//	        
//	        // Save VisitorRequestMst to get the generated ID
//	        visitorRequestMst = repo.save(visitorRequestMst);
//
////	         Map each VisitorDetailsDTO to VisitorRequestDtls and associate with the saved VisitorRequestMst
//	        List<VisitorRequestDtlsDTO> visitorDtlsList = visitorRequestMstDTO.getVisitorDtls();
//	        System.out.println("vis detials :"+visitorDtlsList);
//	        for (VisitorRequestDtlsDTO visitorRequestDtlsDTO : visitorDtlsList) {
//	            VisitorRequestDtls visitorRequestDtls = new VisitorRequestDtls();
//	            visitorRequestDtls.setName(visitorRequestDtlsDTO.getName());
//	            visitorRequestDtls.setIdProof(visitorRequestDtlsDTO.getIdProof());
//	            visitorRequestDtls.setIdProofNo(visitorRequestDtlsDTO.getIdproofNo());
//	            visitorRequestDtls.setContactNo(visitorRequestDtlsDTO.getContactNo());
//	            visitorRequestDtls.setAccessories(visitorRequestDtlsDTO.getAccessories());
//	            visitorRequestDtls.setDob(visitorRequestDtlsDTO.getDob());
////	            Map<String,MultipartFile> files = new HashMap();
////	            List<MultipartFile> idProofAndPhoto = new ArrayList();
////	            idProofAndPhoto.add(visitorRequestDtlsDTO.getIdProofFile());
////	            idProofAndPhoto.add(visitorRequestDtlsDTO.getPhoto());
////	            files.put("idProod", visitorRequestDtlsDTO.getIdProofFile());
////	            files.put("photo", visitorRequestDtlsDTO.getPhoto());
////	            String fileStat = saveFile(visitorRequestDtlsDTO.getIdProofFile());0
//	           
//	            visitorRequestDtls.setPhoto(visitorRequestDtlsDTO.getPhoto());
//	            boolean FileUploadStatus = saveFile(visitorRequestDtlsDTO.getIdProofFile(),uploadDir); 
//	            System.out.println("fileStat :"+ FileUploadStatus);
//
//	            // Set the foreign key relationship
//	            visitorRequestDtls.setVisitorRequestMst(visitorRequestMst); // Set the parent object
//	            
//	            // Save the VisitorRequestDtls entity
//	            VisitorRequestDtls savedData= dtlsRepo.save(visitorRequestDtls);
//	           
//	            BeanUtils.copyProperties(visitorRequestMst, visitorDtlsList);
//	            BeanUtils.copyProperties(visitorDtlsList,mstData);
//	            
//	        }
//	        
//	        return mstData;
//	    }
//	
	 
	 
	 
	 public VisitorRequestMstDTO createVisitorRequest(VisitorRequestMstDTO visitorRequestMstDTO) {
		    System.out.println("visitorRequest Mst dTo:"+visitorRequestMstDTO);
		    // Map DTO to Entity for VisitorRequestMst
		    VisitorRequestMst visitorRequestMst = new VisitorRequestMst();

		    visitorRequestMst.setCmpCd(visitorRequestMstDTO.getCmpCd());
		    visitorRequestMst.setEmpId(visitorRequestMstDTO.getEmpId());
		    visitorRequestMst.setEmpName(visitorRequestMstDTO.getEmpName());
		    visitorRequestMst.setVisOrganization(visitorRequestMstDTO.getVisOrganization());
		    visitorRequestMst.setPurpose(visitorRequestMstDTO.getPurpose());
		    visitorRequestMst.setFromDate(visitorRequestMstDTO.getFromDate());
		    visitorRequestMst.setToDate(visitorRequestMstDTO.getToDate());
		    visitorRequestMst.setVisitorAddress(visitorRequestMstDTO.getVisitorAddress());
		    visitorRequestMst.setHasVehicle(visitorRequestMstDTO.getHasVehicle());
		    visitorRequestMst.setVisitorId(visitorRequestMstDTO.getVisitorId());
		    visitorRequestMst.setVehicleNo(visitorRequestMstDTO.getVehicleNo());
		    visitorRequestMst.setDriverName(visitorRequestMstDTO.getDriverName());
		    visitorRequestMst.setVehicleType(visitorRequestMstDTO.getVehicleType());
		    visitorRequestMst.setDriverDlNo(visitorRequestMstDTO.getDriverDlNo());
		    visitorRequestMst.setDriverDlUpto(visitorRequestMstDTO.getDriverDlUpto());
		    visitorRequestMst.setEmpEmail(visitorRequestMstDTO.getEmpEmail());

		    // Save VisitorRequestMst to get the generated ID
		    visitorRequestMst = repo.save(visitorRequestMst);

		    // Map and save VisitorRequestDtls
		    List<VisitorRequestDtlsDTO> visitorDtlsList = visitorRequestMstDTO.getVisitorDtls();
		    for (VisitorRequestDtlsDTO visitorRequestDtlsDTO : visitorDtlsList) {
		        VisitorRequestDtls visitorRequestDtls = new VisitorRequestDtls();

		        visitorRequestDtls.setName(visitorRequestDtlsDTO.getName());
		        visitorRequestDtls.setIdProof(visitorRequestDtlsDTO.getIdProof());
		        visitorRequestDtls.setIdProofNo(visitorRequestDtlsDTO.getIdproofNo());
		        visitorRequestDtls.setContactNo(visitorRequestDtlsDTO.getContactNo());
		        visitorRequestDtls.setAccessories(visitorRequestDtlsDTO.getAccessories());
		        
		        visitorRequestDtls.setDob(visitorRequestDtlsDTO.getDob());

		        // Save ID proof file
		        boolean fileUploadStatus = saveFile(visitorRequestDtlsDTO.getIdProofFile(), uploadDir);
		        if (!fileUploadStatus) {
		            throw new RuntimeException("File upload failed for: " + visitorRequestDtlsDTO.getName());
		        }

		        visitorRequestDtls.setPhoto(visitorRequestDtlsDTO.getPhoto());
		        visitorRequestDtls.setVisitorRequestMst(visitorRequestMst); // Set the parent object

		        // Save the VisitorRequestDtls entity
		        dtlsRepo.save(visitorRequestDtls);
		    }

		    // Prepare the response DTO
		    VisitorRequestMstDTO responseDTO = new VisitorRequestMstDTO();
		    BeanUtils.copyProperties(visitorRequestMst, responseDTO);

		    // Set Visitor Details in the response DTO
		    List<VisitorRequestDtlsDTO> responseVisitorDtlsList = visitorDtlsList.stream()
		        .map(dto -> {
		            VisitorRequestDtlsDTO responseDtls = new VisitorRequestDtlsDTO();
		            BeanUtils.copyProperties(dto, responseDtls);
		            return responseDtls;
		        }).collect(Collectors.toList());

		    responseDTO.setVisitorDtls(responseVisitorDtlsList);

		    return responseDTO;
		}

	 
	 
	 
	 
	 public VisitorRequestMstDTO updateVehicleDetails(VisitorRequestMstDTO updatedVisitorRequest) {
	        // Retrieve the existing request by ID
	        VisitorRequestMst existingRequest = repo.findById(updatedVisitorRequest.getId())
	                .orElseThrow(() -> new RuntimeException("Visitor request not found with ID " + updatedVisitorRequest.getId()));

	        // Update the vehicle details
	        existingRequest.setVehicleNo(updatedVisitorRequest.getVehicleNo());
	        existingRequest.setDriverName(updatedVisitorRequest.getDriverName());
	        existingRequest.setVehicleType(updatedVisitorRequest.getVehicleType());
	        existingRequest.setDriverDlNo(updatedVisitorRequest.getDriverDlNo());
	        existingRequest.setDriverDlUpto(updatedVisitorRequest.getDriverDlUpto());
//	        existingRequest.setHasVehicle(updatedVisitorRequest.getHasVehicle());

	        VisitorRequestMst savedDtls=repo.save(existingRequest);
			VisitorRequestMstDTO masterDTO=new VisitorRequestMstDTO();
			BeanUtils.copyProperties(savedDtls, masterDTO);
			return masterDTO;	
	    }
	
	 // added on 14-01-25
	 public List<VisitorRequestMst> getAllVisitorRequest(String cmpCd){    // off_cd needd to be added in this 
		 System.out.println("cmpCd: "+cmpCd);
		 List<VisitorRequestMst> allRequests = repo.findByCmpCd(cmpCd);
         System.out.println("output :"+allRequests);
		 return allRequests;
	 }
	 
	 
	 // Added on 04-02-25
	 public VisitorRequestMst getVisitDetails(String cmpCd,String visitId) {
		 System.out.println("cmpcd: "+cmpCd);
		 System.out.println("visitId:"+visitId);
		 VisitorRequestMst req = repo.findByCmdAndId(cmpCd, visitId);
		 System.out.println("visit req: "+req);
		 return req;
	 }
	 
	 
	 // added on 14-01-25
	 
	 public List<VisitorRequestMst> getAllRequestsForReception(String cmpCd){
		 System.out.println("cmpCd: "+cmpCd);
		 List<VisitorRequestMst> allRequests = repo.requestsForReception(cmpCd);
         System.out.println("output :"+allRequests);
		 return allRequests;
	 }
	 
	 
	 // added on 17-01-25
	 public List<VisitorRequestMst> getRequestsByStatus(String cmpCd,String status){
		 System.out.println("cmpCd: "+cmpCd);
		 List<VisitorRequestMst> allRequests = repo.getRequestsByStatus(cmpCd, status);
         System.out.println("output :"+allRequests);
		 return allRequests;
	 }
	 
	 
	 // Following code added on 08-01-25
	 public List<VisitorRequestMst> getVisitorDetailsByVisitorId(String visitorId) {
		 
		 System.out.println("visitor Id:"+visitorId);
		 List<VisitorRequestMst> resp = repo.findByVisitorId(visitorId);
		 VisitorRequestMstDTO visitReq = new VisitorRequestMstDTO();
		 return resp;
	 
		 
    
		 
//		 List<VisitorRequestMstDTO> dtos = resp.stream()
//				    .map(entity -> {
//				        VisitorRequestMstDTO dto = new VisitorRequestMstDTO();
//				        dto.setId(entity.getId());
////				        dto.setVisitorName(entity.getV);
////				        dto.setPurpose(entity.getPurpose());
//				        dto.setEmpId(entity.getEmpId());
//				        dto.setEmpName(entity.getEmpName());
//				        dto.setEmpEmail(entity.getEmpEmail());
//				        dto.setVisOrganization(entity.getVisOrganization());
//				        dto.setPurpose(entity.getPurpose());
//				        dto.setFromDate(entity.getFromDate());
//				        dto.setToDate(entity.getToDate());
//				        dto.setVisitorAddress(entity.getVisitorAddress());
//				        dto.setHasVehicle(entity.getHasVehicle());
//				        dto.setVehicleNo(entity.getVehicleNo());
//				        dto.setDriverName(entity.getDriverName());
//				        dto.setVehicleType(entity.getVehicleType());
//				        dto.setDriverDlNo(entity.getDriverDlNo());
//				        dto.setDriverDlUpto(entity.getDriverDlUpto());
//				        dto.setVisitorId(entity.getVisitorId());
//				       
//				    })
//				    .collect(Collectors.toList());
//		 return dto;
				       
//				        VisitorRequestDtlsDTO dtls= new VisitorRequestDtlsDTO();
//				        dtls.setName(entity.getVisitorDtls().getName());
				        
				        
//				        entity.getEmpId(),
//				        entity.getEmpName(),
//				        entity.getEmpEmail(),
//				        entity.getPurpose(),
//				        entity.getFromDate(),
//				        entity.getToDate(),
//				        entity.getVisitorAddress(),
//				        entity.getHasVehicle(),
//				        entity.getVehicleNo(),
//				        entity.getDriverName(),
//				        entity.getVehicleType(),
//				        entity.getDriverDlNo(),
//				        entity.getDriverDlUpto(),
//				        entity.getVisitorId(),
//				        entity.getVisitorDtls()
				     
		 
//		 List<VisitorRequestMstDTO> dtos = resp.stream()
//				    .map(entity -> new VisitorRequestMstDTO(
//				        entity.getId(),
//				        entity.getEmpId(),
//				        entity.getEmpName(),
//				        entity.getEmpEmail(),
//				        entity.getPurpose(),
//				        entity.getFromDate(),
//				        entity.getToDate(),
//				        entity.getVisitorAddress(),
//				        entity.getHasVehicle(),
//				        entity.getVehicleNo(),
//				        entity.getDriverName(),
//				        entity.getVehicleType(),
//				        entity.getDriverDlNo(),
//				        entity.getDriverDlUpto(),
//				        entity.getVisitorId(),
//				        entity.getVisitorDtls()
//				    ))
//				    .collect(Collectors.toList());
		 
		 
//		 System.out.println("Visitor list rep:"+resp);
//		 return resp;
		 
//	         Execute the query
//	        List<Object[]> results = repo.findVisitorDetailsByVisitorId(visitorId);
//
//	        if (results.isEmpty()) {
//	            return null; // Or throw an exception if no results found
//	        }
//
//	        // Create the master DTO from the first row (master table data is the same for all rows)
//	        Object[] firstRow = results.get(0);
//	        VisitorRequestMstDTO masterDTO = new VisitorRequestMstDTO();
//	        masterDTO.setVisitorId((String) firstRow[0]);
//	        masterDTO.setEmpName((String) firstRow[1]);
//	        masterDTO.setVisOrganization((String) firstRow[2]);
//	        masterDTO.setPurpose((String) firstRow[3]);
////	        masterDTO.setFromDate((Date) firstRow[4]);
//	        masterDTO.setVisitorAddress((String) firstRow[5]);
//	        masterDTO.setVehicleNo((String) firstRow[10]);
//	        masterDTO.setDriverName((String) firstRow[11]);
//	        masterDTO.setVehicleType((String) firstRow[12]);
//	        masterDTO.setDriverDlNo((String) firstRow[13]);
//	        masterDTO.setDriverDlUpto((String) firstRow[14]);
//
//	        // Create the list of detail DTOs
//	        List<VisitorRequestDtlsDTO> detailDTOs = new ArrayList<>();
//	        for (Object[] row : results) {
//	            VisitorRequestDtlsDTO detailDTO = new VisitorRequestDtlsDTO();
//	            detailDTO.setName((String) row[6]);
////	            detailDTO.setDob((Date) row[7]);
//	            detailDTO.setContactNo((String) row[8]);
//	            detailDTO.setAccessories((String) row[9]);
//	            detailDTO.setIdproofNo((String) row[15]);
//	            detailDTOs.add(detailDTO);
//	        }
//
//	        // Set the details list in the master DTO
//	        masterDTO.setVisitorDtls(detailDTOs);
//            System.out.println("vis details :"+masterDTO);  
//	        return masterDTO;
	    }
	 
	 // Added on 30-01-25
	 public String updateReqStatusReception(VisitorRequestMstDTO updatedVisitorRequest) {
		 System.out.println("updated "+updatedVisitorRequest);
		    try {
		        // Retrieve the existing request by ID
		        VisitorRequestMst existingRequest = repo.findById(updatedVisitorRequest.getId())
		                .orElseThrow(() -> new RuntimeException("Visitor request not found with ID " + updatedVisitorRequest.getId()));

		        // Update the necessary fields
		        existingRequest.setRec_req_status(updatedVisitorRequest.getReqStatus());
		        existingRequest.setRec_req_remarks(updatedVisitorRequest.getStatusRemarks());
		        existingRequest.setFace_registeration(updatedVisitorRequest.getFace_registeration());
              
		        // Save the updated entity
		        repo.save(existingRequest);

		        // Return the updated status
		        return updatedVisitorRequest.getReqStatus();
		    } catch (RuntimeException ex) {
		        throw ex; // Let the controller handle runtime exceptions
		    } catch (Exception ex) {
		        throw new RuntimeException("Failed to update request status: " + ex.getMessage());
		    }
		}

	 
	 
	 
	 
	// Following code added on 15-01-25
	 public String updateReqStatus(VisitorRequestMstDTO updatedVisitorRequest) {
		 System.out.println("updated "+updatedVisitorRequest);
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            
            // Get the authorities (roles) associated with the user
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            
            // Check if the user has a specific role (e.g., "ROLE_ADMIN")
            boolean hasAdminRole = authorities.stream()
                                              .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
            boolean hasEmpRole = authorities.stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_EMP"));
	       
            System.out.println("hasAdminRole :"+hasAdminRole);
            System.out.println("hasEmpRole :"+hasEmpRole);
	        if(hasAdminRole) {
	        
		    try {
		        // Retrieve the existing request by ID
		        VisitorRequestMst existingRequest = repo.findById(updatedVisitorRequest.getId())
		                .orElseThrow(() -> new RuntimeException("Visitor request not found with ID " + updatedVisitorRequest.getId()));

		        // Update the necessary fields
		        existingRequest.setReqStatus(updatedVisitorRequest.getReqStatus());
		       
               
                	existingRequest.setAdmin_req_status(updatedVisitorRequest.getAdmin_req_status());
                	existingRequest.setAdmin_req_remarks(updatedVisitorRequest.getAdmin_req_remarks());
                	existingRequest.setOffCd(updatedVisitorRequest.getOff_cd());
                
		        // Save the updated entity
		        repo.save(existingRequest);
                return updatedVisitorRequest.getReqStatus();
		    } catch (RuntimeException ex) {
		        throw ex; // Let the controller handle runtime exceptions
		    } catch (Exception ex) {
		        throw new RuntimeException("Failed to update request status: " + ex.getMessage());
		    }
		    
	        }
//	        else if(hasEmpRole) {
	        	
	        	 VisitorRequestMst existingRequest = repo.findById(updatedVisitorRequest.getId())
			                .orElseThrow(() -> new RuntimeException("Visitor request not found with ID " + updatedVisitorRequest.getId()));

			        // Update the necessary fields
			        existingRequest.setReqStatus(updatedVisitorRequest.getReqStatus());
			       
	                if(updatedVisitorRequest.isExtension()) {
	                	System.out.println("test 1");
	                	existingRequest.setFromDate(updatedVisitorRequest.getExFromDate());
	                	existingRequest.setToDate(updatedVisitorRequest.getExToDate());
	                	existingRequest.setIsExtension("Y");
	                	existingRequest.setStatusRemarks(updatedVisitorRequest.getStatusRemarks()+"changed date from "+updatedVisitorRequest.getFromDate()+"to "+updatedVisitorRequest.getToDate());
	                }
	                else {
	                	System.out.println("test 2");
	                	 existingRequest.setStatusRemarks(updatedVisitorRequest.getStatusRemarks());
	                }
			        // Save the updated entity
			        repo.save(existingRequest);
	                return updatedVisitorRequest.getReqStatus();
//			    }
	        	
	        }
	        
		


	 public boolean saveFile(MultipartFile file, String uploadDir) {
		    if (file == null || file.isEmpty()) {
		        System.err.println("The file is null or empty.");
		        return false;
		    }

		    try {
		        // Ensure the upload directory exists
		        File dir = new File(uploadDir);
		        if (!dir.exists() && !dir.mkdirs()) {
		            System.err.println("Failed to create the upload directory: " + uploadDir);
		            return false;
		        }

		        // Prepare the file name
		        String originalFilename = file.getOriginalFilename();
		        if (originalFilename == null || originalFilename.trim().isEmpty()) {
		            System.err.println("The file name is null or empty.");
		            return false;
		        }

		        String prefixedFilename = "CO001_" + originalFilename;
		        Path filePath = Paths.get(uploadDir, prefixedFilename);

		        // Save the file
		        Files.write(filePath, file.getBytes());
		        System.out.println("File saved successfully at: " + filePath);
		        return true;
		    } catch (IOException e) {
		        System.err.println("An error occurred while saving the file: " + e.getMessage());
		        e.printStackTrace();
		        return false;
		    }
	 
	 
//	 public boolean saveFile(MultipartFile file, String uploadDir) {
//		    System.out.println("file: " + file);
//
//		    if (file == null || file.isEmpty()) {
//		        return false; 
//		    }
//
//		    try {
//		        File dir = new File(uploadDir);
//		        if (!dir.exists() && !dir.mkdirs()) {
//		            return false; 
//		        }
//                String originalFilename = "CO001_" + file.getOriginalFilename();
//		        if (originalFilename == null || originalFilename.trim().isEmpty()) {
//		            return false; 
//		        }
//
//		        Path filePath = Paths.get(uploadDir, originalFilename);
//		        Files.write(filePath, file.getBytes(), StandardOpenOption.CREATE);
//		    } catch (IOException e) {
//		        e.printStackTrace(); 
//		        return false;
//		    }
//                return true; 
//		}
	 
	 
	 


	 }
}
