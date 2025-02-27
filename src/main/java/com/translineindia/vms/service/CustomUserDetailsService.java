package com.translineindia.vms.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.translineindia.vms.dtos.EmployeeDTO;
import com.translineindia.vms.entity.Employee;
import com.translineindia.vms.entity.Login;
import com.translineindia.vms.entity.employeeTest;
import com.translineindia.vms.repository.EmployeeRepository;
import com.translineindia.vms.security.UserPrincipal;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeRepository empRep;
    
    @Autowired
	private RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	System.out.println("username :"+username);
        if (username == null || username.isEmpty()) {
            throw new UsernameNotFoundException("Username cannot be null or empty");
        }

        String cmpCd = "";
        String idOrEmail = "";

        if (username.contains("::")) {
            String[] parts = username.split("::");
            if (parts.length == 2) {
                cmpCd = parts[0];
                idOrEmail = parts[1];
            } else {
                throw new UsernameNotFoundException("Invalid username format. Expected format: cmpCd::idOrEmail");
            }
        } else {
            throw new UsernameNotFoundException("Invalid username format. Expected '::' delimiter");
        }

        // Attempt to find the user by ID or Email
        Login login = userService.getUserByIdOrEmail(cmpCd, idOrEmail);
        System.out.println("login :"+login);
        if (login != null) {
            return new UserPrincipal(login);
        }

       
//        else if (login == null && username.startsWith("EMP")) {
//        	System.out.println("login ;"+login);
//
//        	  Optional<employeeTest> employeeOptional = empRep.findByEmpId(idOrEmail);
//            
//            System.out.println("employee optional :"+employeeOptional);
//            if (employeeOptional.isPresent()) {
//
//            	employeeTest  empTest = employeeOptional.get();
//            	Login employeeLogin = new Login();
//                employeeLogin.setCmpCd(empTest.getCmpCd());
//                employeeLogin.setEmail(empTest.getEmail());
//                employeeLogin.setUserId(empTest.getEmpId());
//                employeeLogin.setRole("ROLE_EMPLOYEE"); 
//                return new UserPrincipal(employeeLogin);
//            }
//        }
        else if(login==null) {
        	 String apiUrl = "http://localhost:8081/employee?empId=" + idOrEmail + "&cmpCd=" + cmpCd;
             String response = restTemplate.getForObject(apiUrl, String.class);
             System.out.println("API response: " + response);

//             if (response != null && !response.isEmpty()) {
//                 // Parse the response and create a Login object based on employee data
//                 // Assuming the API returns a JSON object with employee details
//                 EmployeeDTO employeeDTO = parseEmployeeApiResponse(response); // You need to implement this method
//                 if (employeeDTO != null) {
//                     Login employeeLogin = new Login();
//                     employeeLogin.setCmpCd(employeeDTO.getCmpCd());
//                     employeeLogin.setEmail(employeeDTO.getEmail());
//                     employeeLogin.setUserId(employeeDTO.getEmpId());
//                     employeeLogin.setRole("ROLE_EMPLOYEE");
//                     return new UserPrincipal(employeeLogin);
//                 }
//             }
             
        }
        
       
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
