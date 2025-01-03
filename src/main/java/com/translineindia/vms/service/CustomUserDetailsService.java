package com.translineindia.vms.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.translineindia.vms.entity.Visitor;
import com.translineindia.vms.security.VisitorLogin;

@Service
public class CustomUserDetailsService implements UserDetailsService {
		
		@Autowired
		private VisitorService visitorService;
		
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // Fetch user from database or other source
//        // For example:
//        if ("admin".equals(username)) {
//            return new CustomUserDetails("admin", "password", true, List.of());
//        }
//        throw new UsernameNotFoundException("User not found");
//    }
	
	   @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		   //username= VS$cmpCd$visitorId   || VS$cmpCd$emailId		  
		   System.out.println(username);
		   if(username.startsWith("VS::")) {
			   username=username.substring(4);
			   String cmpCd="";
			   String idOrEmail="";			   
			   if(username.contains("::")) {
				   cmpCd=username.split("::")[0];
				   idOrEmail=username.split("::")[1];
			   }
			   Visitor visitor=visitorService.getVisitorByIdOrEmail(cmpCd, idOrEmail);
			   if(visitor!=null) {
				   return new VisitorLogin(visitor);
			   }else {
				   throw new UsernameNotFoundException("Visitor Not Found");
			   }
		   }
		   throw new UsernameNotFoundException("User not found");
		   
//	        // Replace this with a database lookup for Visitor
//	        if (!username.equals("test@visitor.com")) {
//	            throw new UsernameNotFoundException("User not found");
//	        }
//
//	        return new User("test@visitor.com", "$2a$10$7oE5COVBxVZoMciKQHo5mevFGIv8X3Qe1Zb.Ga/dCSTBV24w8F5AC", Collections.emptyList());
//	        // Password above is "password" hashed using BCrypt
//	        
//	        
	    }
	
	
}
