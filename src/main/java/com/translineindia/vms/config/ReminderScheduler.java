//package com.translineindia.vms.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import com.translineindia.vms.service.*;
//
//import java.time.LocalTime;
//
//@Component
//public class ReminderScheduler {
//
//    @Autowired
//    private EmailService emailService;
//
//    private static final String VISITOR_EMAIL = "visitor@example.com";
//    private static final String SECURITY_TEAM_EMAIL = "security@example.com";
//    private static final String HOST_EMAIL = "host@example.com";
//
//    // This method will run every day at 5:00 PM
//    @Scheduled(cron = "0 0 17 * * ?")
//    public void checkAndSendReminders() {
//        LocalTime currentTime = LocalTime.now();
//
//        if (currentTime.isAfter(LocalTime.of(17, 0))) {
//            sendReminderEmails();
//        }
//    }
//
//    private void sendReminderEmails() {
//        String subject = "Reminder: Upcoming Event";
//        String body = "This is a reminder about the event later today. Please ensure you're ready.";
//
//        // Send reminder to the visitor, security team, and host
//        emailService.sendEmail(VISITOR_EMAIL, subject, body);
//        emailService.sendEmail(SECURITY_TEAM_EMAIL, subject, body);
//        emailService.sendEmail(HOST_EMAIL, subject, body);
//
//        System.out.println("Reminder emails sent.");
//    }
//}
