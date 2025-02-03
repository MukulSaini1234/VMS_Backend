package com.translineindia.vms.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        if (login != null) {
            return new UserPrincipal(login);
        }
//        else if(login == null && username)

        // If login is not found and the username starts with "75", check for employee details
        else if (login == null && username.startsWith("EMP")) {
//            Optional<Employee> employeeOptional = empRep.findByCmpCdAndEmpId(cmpCd, idOrEmail);
        	  Optional<employeeTest> employeeOptional = empRep.findByEmpId(idOrEmail);
            
            System.out.println("employee optional :"+employeeOptional);
            if (employeeOptional.isPresent()) {
//                Employee employee = employeeOptional.get();
            	employeeTest  empTest = employeeOptional.get();
            	
                // Create a new Login object to wrap the employee's details
                Login employeeLogin = new Login();
                employeeLogin.setCmpCd(empTest.getCmpCd());
                employeeLogin.setEmail(empTest.getEmail());
                employeeLogin.setUserId(empTest.getEmpId());
                employeeLogin.setRole("ROLE_EMPLOYEE"); // Assuming employee role is predefined

                return new UserPrincipal(employeeLogin);
            }
        }
        
       
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
