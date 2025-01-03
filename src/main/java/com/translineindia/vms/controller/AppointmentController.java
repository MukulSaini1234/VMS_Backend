package com.translineindia.vms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.translineindia.vms.dtos.AppointmentDTO;
import com.translineindia.vms.entity.AppointmentEntity;
import com.translineindia.vms.service.AppointmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AppointmentController {
	
	@Autowired
	private AppointmentService appointmentService;

	@PostMapping("/appointment")
	public ResponseEntity<AppointmentDTO> Appointment(@RequestBody @Valid AppointmentDTO dto){
		AppointmentDTO responseDto = appointmentService.RequestAppointment(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
	}
}
