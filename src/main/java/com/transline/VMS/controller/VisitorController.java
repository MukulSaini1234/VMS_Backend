package com.transline.VMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.transline.VMS.DTO.VisitorLoginDTO;
import com.transline.VMS.services.*;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

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