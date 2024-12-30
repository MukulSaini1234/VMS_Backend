package com.transline.vms.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transline.vms.dtos.VisitorLoginDTO;
import com.transline.vms.service.VisitorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/visitors")
public class VisitorController {
	
	@Autowired
	private VisitorService VisitorService;
	

	@PostMapping
	public ResponseEntity<VisitorLoginDTO> createUser(@RequestBody @Valid VisitorLoginDTO newVisitorDTO){		
		return ResponseEntity.status(HttpStatus.CREATED).body(VisitorService.createUser(newVisitorDTO));
	}
		
}