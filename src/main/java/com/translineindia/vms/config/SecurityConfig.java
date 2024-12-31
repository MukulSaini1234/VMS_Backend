package com.translineindia.vms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//import com.translineindia.vms.security.JwtAuthenticationFilter;
//import com.translineindia.vms.security.User;

@Configuration
public class SecurityConfig {
		
//	@Autowired
//	private JwtAuthenticationFilter jwtAuthenticationFilter;
//	
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .csrf().disable() // Disable CSRF for stateless APIs
//            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless sessions
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/auth/**","/public/**").permitAll() // Allow public access to authentication endpoints
//                .anyRequest().authenticated() // Secure all other endpoints
//            )
//            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter
//        
//        return http.build();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

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

    
}
