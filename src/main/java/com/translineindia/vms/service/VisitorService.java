package com.translineindia.vms.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.example.demo.excep.ResourceNotFoundException;
import com.translineindia.vms.dtos.VisitorLoginDTO;
import com.translineindia.vms.entity.Visitor;
import com.translineindia.vms.repository.VisitorRepository;

import com.translineindia.vms.exception.*;

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
		VisitorLoginDTO visitorDTO=new VisitorLoginDTO();
//		if(visitorRepo.findByEmail(visitor.getEmail())==null)
		if (visitorRepo.findByEmail(visitor.getEmail()).isEmpty())
		{
		Visitor savedVisitor=visitorRepo.save(visitor);
		visitorDTO.setId(savedVisitor.getId());
		visitorDTO.setName(savedVisitor.getName());
		visitorDTO.setEmail(savedVisitor.getEmail());
		return visitorDTO;
		}else {
			throw new AlreadyExistsException(newVisitorDTO.getName());
		}
		
		
	}
	
	
	public String generateUsername() {
        String maxSerialStr = visitorRepo.findMaxSerialNumber();
        int newSerial = (maxSerialStr != null) ? Integer.parseInt(maxSerialStr) + 1 : 1;
        return "VS" + String.format("%04d", newSerial); // Pad with leading zeros if needed
    }
}
