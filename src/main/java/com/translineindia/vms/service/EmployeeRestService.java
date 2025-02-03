package com.translineindia.vms.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.translineindia.vms.dtos.EmployeeDTO;

@Service
public class EmployeeRestService {

//    @Value("${external.api.url}")  // Inject the API base URL from application.properties
    private String apiUrl;

    private final RestTemplate restTemplate;

    public EmployeeRestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public EmployeeDTO fetchEmployeeDetails(String empId, String cmpCd) {
//        String url = apiUrl + "/employee?username=" + username;
//        return restTemplate.getForObject(url, EmployeeDTO.class);
    	String apiUrl = "http://localhost:8081/employee?empId="+empId+"&cmpCd="+cmpCd;
    	return restTemplate.getForObject(apiUrl, EmployeeDTO.class);
    }
    
    
}

