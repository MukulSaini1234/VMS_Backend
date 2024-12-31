package com.translineindia.vms.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.translineindia.vms.dtos.VisitorLoginDTO;
import com.translineindia.vms.dtos.companyDTO;
import com.translineindia.vms.service.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/company")
@CrossOrigin(origins = {"*"},allowCredentials = "false")
public class CompanyController {

	@Autowired
	private companyService companyService;
	
//	@GetMapping
//	public ResponseEntity<String> checkCmpCd(@RequestBody companyDTO cmpDTO){		
//		return ResponseEntity.status(HttpStatus.CREATED).body(companyService.getCmpName(cmpDTO.getCmpCd()));
//	}
	
	@GetMapping("/getCmpList")
	public ResponseEntity<Map<String,String>> getCmpList(){
		
		return ResponseEntity.status(HttpStatus.OK).body(companyService.getCmpList());
		
	}
	
}
