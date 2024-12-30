package com.translineindia.vms.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.translineindia.vms.dtos.VisitorLoginDTO;
import com.translineindia.vms.entity.Visitor;
import com.translineindia.vms.repository.VisitorRepository;



@Service
public class VisitorService {
	@Autowired
	private VisitorRepository visitorRepo;
	
	public VisitorLoginDTO createUser(VisitorLoginDTO newVisitorDTO) {
		Visitor visitor=new Visitor();
		visitor.setEmail(newVisitorDTO.getEmail());
		visitor.setName(newVisitorDTO.getName());
		visitor.setPassword(newVisitorDTO.getPassword());
		visitor.setUsername(generateUsername());
		visitor.setCmpCd(newVisitorDTO.getCmpCd());
		visitor.setOffCd(newVisitorDTO.getOffCd());
//		visitor.setPhoneNo(Long.parseLong(newVisitorDTO.getPhoneNo();
		Visitor savedVisitor=visitorRepo.save(visitor);
//		VisitorLoginDTO userDTO=new VisitorLoginDTO();
//		userDTO.setId(savedUser.getId());
//		userDTO.setName(savedUser.getName());
//		userDTO.setEmail(savedUser.getEmail());
//		return userDTO;
		return newVisitorDTO;
	}
	
	
	public String generateUsername() {
        String maxSerialStr = visitorRepo.findMaxSerialNumber();
        int newSerial = (maxSerialStr != null) ? Integer.parseInt(maxSerialStr) + 1 : 1;
        return "VS" + String.format("%04d", newSerial); // Pad with leading zeros if needed
    }
}
