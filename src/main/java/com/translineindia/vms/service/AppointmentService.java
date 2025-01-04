package com.translineindia.vms.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.translineindia.vms.dtos.AppointmentDTO;
import com.translineindia.vms.dtos.VehicleDetailsDTO;
import com.translineindia.vms.entity.AppointmentEntity;
import com.translineindia.vms.exception.ConflictException;
import com.translineindia.vms.repository.AppointmentRepo;

import lombok.Data;

@Service
@Data
public class AppointmentService {
	
	@Autowired
	private AppointmentRepo repo;
	
	public AppointmentDTO RequestAppointment(AppointmentDTO newAppointmentDTO) throws IOException {
		AppointmentEntity newRequest = new AppointmentEntity();
		VehicleDetailsDTO vehicleDetails = new VehicleDetailsDTO();
		newRequest.setEmpid(newAppointmentDTO.getEmpId());
		newRequest.setEmpName(newAppointmentDTO.getEmpName());
		newRequest.setEmpEmail(newAppointmentDTO.getEmpEmail());
		newRequest.setPurpose(newAppointmentDTO.getPurpose());
		newRequest.setForDays(newAppointmentDTO.getForDays());
		newRequest.setFromDate(newAppointmentDTO.getFromDate());
		newRequest.setVisitorAdd(newAppointmentDTO.getVisitorAdd());
		newRequest.setIdProof(newAppointmentDTO.getIdProof());
//		    newRequest.setFileData(newAppointmentDTO.getFileData().getBytes());
		newRequest.setVisName(newAppointmentDTO.getVisName());
		newRequest.setVisDob(newAppointmentDTO.getVisDob());
		newRequest.setVisNum(newAppointmentDTO.getVisNum());
		newRequest.setVisAccessories(newAppointmentDTO.getVisAccessories());

		
		newRequest.setVisDrivername(
			    Optional.ofNullable(newAppointmentDTO)
			            .map(AppointmentDTO::getVehicleDetails)
			            .map(VehicleDetailsDTO::getVisDrivername)
			            .orElse(null) 
			);

			newRequest.setVisVehicleno(
			    Optional.ofNullable(newAppointmentDTO)
			            .map(AppointmentDTO::getVehicleDetails)
			            .map(VehicleDetailsDTO::getVisVehicleno)
			            .orElse(null) 
			);

			newRequest.setVisVehicleType(
			    Optional.ofNullable(newAppointmentDTO)
			            .map(AppointmentDTO::getVehicleDetails)
			            .map(VehicleDetailsDTO::getVisVehicleType)
			            .orElse(null)
			);

			newRequest.setVisDriverDlNo(
			    Optional.ofNullable(newAppointmentDTO)
			            .map(AppointmentDTO::getVehicleDetails)
			            .map(VehicleDetailsDTO::getVisDriverDlNo)
			            .orElse(null) 
			);

	
		
		newRequest.setVisDriverDlUpto(
			    Optional.ofNullable(newAppointmentDTO) 
			            .map(AppointmentDTO::getVehicleDetails) 
			            .map(VehicleDetailsDTO::getVisDriverDlUpto) 
			            .orElse(null)
			);

        AppointmentDTO responseDTO = new AppointmentDTO();
		System.out.println("responseDto: "+responseDTO);
		AppointmentEntity savedRequest = repo.save(newRequest);
		BeanUtils.copyProperties(savedRequest, responseDTO);	    
		return responseDTO;
	}
}
