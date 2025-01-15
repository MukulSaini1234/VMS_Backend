package com.translineindia.vms.config;

import org.springframework.http.HttpHeaders;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.translineindia.vms.security.JwtAuthenticationEntyPoint;
import com.translineindia.vms.security.JwtAuthenticationFilter;
import com.translineindia.vms.security.JwtHelper;
import com.translineindia.vms.service.CustomUserDetailsService;
import com.translineindia.vms.service.UserService;

//import com.translineindia.vms.security.JwtAuthenticationFilter;
//import com.translineindia.vms.security.User;

@Configuration
@EnableWebSecurity


public class SecurityConfig {
		
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	private JwtAuthenticationEntyPoint jwtAuthenticationEntyPoint;
	
	@Autowired
	private ApplicationContext applicationContext;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	SecurityFilterChain filterChain= http
            .csrf(csrf->csrf.disable()) // Disable CSRF for stateless APIs            
            .authorizeHttpRequests(auth -> auth        		
                .requestMatchers("/auth/**","/public/**").permitAll() // Allow public access to authentication endpoints   
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .anyRequest().authenticated() // Secure all other endpoints                                
            )  
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless sessions
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter
//            .exceptionHandling(e->e.authenticationEntryPoint(jwtAuthenticationEntyPoint))
            .cors(customizer->customizer.configurationSource(request->corsConfiguration()))
            .build(); 
    	
    	filterChain.getFilters().forEach(item->System.out.println(item));
    	
    	return filterChain;
    }
	
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    
//    @Bean
//    public AuthenticationProvider authenticationProvider()  {
//    	DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
//    	UserDetailsService userDetailsService=applicationContext.getBean(UserDetailsService.class);
//    	provider.setUserDetailsService(userDetailsService);
//    	provider.setPasswordEncoder(passwordEncoder());
//        return provider;
//    }
    
    

//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//        UserDetails user = User.withUsername("user")
//            .password(passwordEncoder.encode("password"))
//            .roles("USER")
//            .build();
//
//        UserDetails admin = User.withUsername("admin")
//            .password(passwordEncoder.encode("admin"))
//            .roles("ADMIN")
//            .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }
    
    
    public CorsConfiguration corsConfiguration() {
		String[] allowedMethods= {
				HttpMethod.GET.name(),
				HttpMethod.POST.name(),
				HttpMethod.PUT.name(),
				HttpMethod.DELETE.name(),
				HttpMethod.OPTIONS.name(),
				HttpMethod.PATCH.name()
		};
		
		String[] allowedHeaders= {
				HttpHeaders.AUTHORIZATION,
				HttpHeaders.CONTENT_TYPE,
				HttpHeaders.ACCEPT
		};
		String[] allowedOrigins= {"*"};
		
		CorsConfiguration cors=new CorsConfiguration();
//		cors.setAllowedOrigins(Arrays.asList("*"));
//		cors.setAllowedMethods(Arrays.asList("*"));
//		cors.setAllowedHeaders(Arrays.asList("*"));
		cors.setAllowedOrigins(Arrays.asList(allowedOrigins));
		cors.setAllowedMethods(Arrays.asList(allowedMethods));					
		cors.setAllowedHeaders(Arrays.asList(allowedHeaders));
		return cors;
	}
    
   
}
