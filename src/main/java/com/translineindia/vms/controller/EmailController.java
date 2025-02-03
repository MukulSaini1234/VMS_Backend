package com.translineindia.vms.controller;


import com.translineindia.vms.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import javax.mail.MessagingException;
@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "*",allowedHeaders = "*", allowCredentials = "false")
public class EmailController { 

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-email")
    public String sendTestEmail() {
        try {
        	System.out.println("test A");
            emailService.sendEmail("akanksha.kataria12@gmail.com", "Test Subject", "<h1>This is a test email</h1>");
            return "Email sent successfully!";
        } catch (Exception e) {
            return "Failed to send email: " + e.getMessage();
        }
    }
}
