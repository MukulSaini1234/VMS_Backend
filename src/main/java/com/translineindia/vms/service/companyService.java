package com.translineindia.vms.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import com.translineindia.vms.entity.CmpMst;
import org.springframework.stereotype.Service;

import com.translineindia.vms.dtos.VisitorLoginDTO;
import com.translineindia.vms.dtos.companyDTO;
import com.translineindia.vms.entity.Visitor;
import com.translineindia.vms.repository.VisitorRepository;
import com.translineindia.vms.repository.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepo;
	
//	public String  getCmpName(String cmpCd) {
//		
//	    String cmpName = companyRepo.findByCmpCd(cmpCd);
//		
//	    return 	cmpName;
//	}
	
	public Map<String,String> getCmpList() {
		CmpMst cmpMst=new CmpMst();
		List<Object[]> cmpList = companyRepo.findCompanyWithOptionId();
	
		return cmpList.stream().collect(Collectors.toMap(obj ->(String) obj[1], obj->(String) obj[0]));
	}
	
	
}
