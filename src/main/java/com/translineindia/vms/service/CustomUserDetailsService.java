package com.translineindia.vms.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.translineindia.vms.entity.Login;
import com.translineindia.vms.security.UserPrincipal;

@Service
public class CustomUserDetailsService implements UserDetailsService {
		
		@Autowired
		private UserService userService;
	
	   @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		   //username= VS$cmpCd$visitorId   || VS$cmpCd$emailId		  
		   System.out.println(username);		   			   
		   String cmpCd="";
		   String idOrEmail="";			   
//		   if(username.contains("::") && !username.contains("VS")) {
		   
		   if(username.contains("::")) {
			   System.out.println("username1: "+username);
			   cmpCd=username.split("::")[0];
			   idOrEmail=username.split("::")[1];
		   }
//		   if(username.contains("VS")) {
//			   System.out.println("username2:"+username);
//			   cmpCd = username.split("::")[1];
//			   idOrEmail = username.split("::")[2];
//		   }
		   System.out.println("cmpCd :"+cmpCd);
		   System.out.println("id :"+idOrEmail);
		   
		   
		   Login login=userService.getVisitorByIdOrEmail(cmpCd, idOrEmail);
		   if(login!=null) {
			   return new UserPrincipal(login);
		   }else {
			   throw new UsernameNotFoundException("User Not Found");
		   }		   
//		   else if (username.startsWith("AD::")) { // Handle Admin user // or else condition other than visitor
//			   // tables are different but this class is same so need to check for different user type....
//	            username = username.substring(4);
//	            String cmpCd = "";
//	            String idOrEmail = "";
//	            
//	            if (username.contains("::")) {
//	                cmpCd = username.split("::")[0];
//	                idOrEmail = username.split("::")[1];
//	            }
//	            System.out.println("username :"+username);
//	            Login visitor=userService.getVisitorByIdOrEmail(cmpCd, idOrEmail); //make different table , dofferent entity and therefore service
//	            if (visitor != null) {
//	            	return new UserPrincipal(visitor); // Custom UserDetails for Admin
//	            } else {
//	                throw new UsernameNotFoundException("Admin Not Found");
//	            }
//	        }
//		   throw new UsernameNotFoundException("User not found");
		   
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
