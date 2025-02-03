package com.translineindia.vms.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class sendNotifications {

    // This will run every 5 seconds
    @Scheduled(fixedRate = 10000)
    public void runTask() {
        System.out.println("Scheduled task is running at: " + System.currentTimeMillis());
    }
}
