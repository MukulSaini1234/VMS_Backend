package com.translineindia.vms.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.translineindia.vms.dtos.AppointmentDTO;
import com.translineindia.vms.entity.AppointmentEntity;
import com.translineindia.vms.repository.AppointmentRepo;

import lombok.Data;

@Service
@Data
public class AppointmentService {
	
	@Autowired
	private AppointmentRepo repo;
	
	public AppointmentDTO RequestAppointment(AppointmentDTO newAppointmentDTO) {
		AppointmentEntity newRequest = new AppointmentEntity();
		newRequest.setEmpid(newAppointmentDTO.getEmpId());
	    newRequest.setEmpName(newAppointmentDTO.getEmpName());
	    newRequest.setEmpEmail(newAppointmentDTO.getEmpEmail());
	    newRequest.setPurpose(newAppointmentDTO.getPurpose());
	    newRequest.setForDays(newAppointmentDTO.getForDays());
	    newRequest.setFromDate(newAppointmentDTO.getFromDate());
	    newRequest.setVisitorAdd(newAppointmentDTO.getVisitorAdd());
	    newRequest.setIdProof(newAppointmentDTO.getIdProof());
	    newRequest.setFileData(newAppointmentDTO.getFileData());
	    newRequest.setVisName(newAppointmentDTO.getVisName());
	    newRequest.setVisDob(newAppointmentDTO.getVisDob());
	    newRequest.setVisNum(newAppointmentDTO.getVisNum());
	    newRequest.setVisAccessories(newAppointmentDTO.getVisAccessories());
	    newRequest.setVisPhoto(newAppointmentDTO.getVisPhoto());
	    newRequest.setVisVehicle(newAppointmentDTO.getVisVehicle());
	    newRequest.setVisDrivername(newAppointmentDTO.getVisDrivername());
	    newRequest.setVisVehicleno(newAppointmentDTO.getVisVehicleno());
	    newRequest.setVisVehicleType(newAppointmentDTO.getVisVehicleType());
	    newRequest.setVisDriverDlNo(newAppointmentDTO.getVisDriverDlNo());
	    newRequest.setVisDriverDlUpto(newAppointmentDTO.getVisDriverDlUpto());
	    
	    AppointmentDTO responseDTO = new AppointmentDTO();
	    
	    AppointmentEntity savedRequest = repo.save(newRequest);
	    BeanUtils.copyProperties(savedRequest, responseDTO);
	    
	    return responseDTO;
	}
}
