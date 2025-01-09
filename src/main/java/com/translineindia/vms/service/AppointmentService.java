package com.translineindia.vms.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.translineindia.vms.config.EncryptionUtil;
import com.translineindia.vms.dtos.AppointmentDTO;
import com.translineindia.vms.dtos.VisitorLoginDTO;
import com.translineindia.vms.dtos.VisitorRequestDtlsDTO;
import com.translineindia.vms.dtos.VisitorRequestMstDTO;
import com.translineindia.vms.entity.AppointmentEntity;
import com.translineindia.vms.entity.Visitor;
import com.translineindia.vms.entity.VisitorRequestDtls;
import com.translineindia.vms.entity.VisitorRequestMst;
import com.translineindia.vms.exception.ConflictException;
import com.translineindia.vms.repository.AppointmentRepo;
import com.translineindia.vms.repository.VisitorRequestDetailsRepo;

import jakarta.transaction.Transactional;
import lombok.Data;

@Service
@Data
public class AppointmentService {
	
	@Autowired
	private AppointmentRepo repo;
	
	@Autowired
	private VisitorRequestDetailsRepo dtlsRepo;
	
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
	 public void createVisitorRequest(VisitorRequestMstDTO visitorRequestMstDTO) {
	        // Map DTO to Entity for VisitorRequestMst
	        VisitorRequestMst visitorRequestMst = new VisitorRequestMst();
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

//	         Map each VisitorDetailsDTO to VisitorRequestDtls and associate with the saved VisitorRequestMst
	        List<VisitorRequestDtlsDTO> visitorDtlsList = visitorRequestMstDTO.getVisitorDtls();
	        System.out.println("vis detials :"+visitorDtlsList);
	        for (VisitorRequestDtlsDTO visitorRequestDtlsDTO : visitorDtlsList) {
	            VisitorRequestDtls visitorRequestDtls = new VisitorRequestDtls();
	            visitorRequestDtls.setName(visitorRequestDtlsDTO.getName());
	            visitorRequestDtls.setIdProof(visitorRequestDtlsDTO.getIdProof());
	            visitorRequestDtls.setIdProofNo(visitorRequestDtlsDTO.getIdproofNo());
	            visitorRequestDtls.setContactNo(visitorRequestDtlsDTO.getContactNo());
	            visitorRequestDtls.setAccessories(visitorRequestDtlsDTO.getAccessories());
	            visitorRequestDtls.setDob(visitorRequestDtlsDTO.getDob());
	           

	            // Set the foreign key relationship
	            visitorRequestDtls.setVisitorRequestMst(visitorRequestMst); // Set the parent object
	            
	            // Save the VisitorRequestDtls entity
	            dtlsRepo.save(visitorRequestDtls);
	        }
	    }
	
	 public VisitorRequestMstDTO updateVehicleDetails(Long id, VisitorRequestMstDTO updatedVisitorRequest) {
	        // Retrieve the existing request by ID
	        VisitorRequestMst existingRequest = repo.findById(id)
	                .orElseThrow(() -> new RuntimeException("Visitor request not found with ID " + id));

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
	 
	 
	 // Following code added on 08-01-25
	 public List<VisitorRequestMst> getVisitorDetailsByVisitorId(String visitorId) {
		 
		 System.out.println("visitor Id:"+visitorId);
		 List<VisitorRequestMst> resp = repo.findByVisitorId(visitorId);
//		 System.out.println("Visitor list rep:"+resp);
		 return resp;
		 
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
	 
	
	
}
