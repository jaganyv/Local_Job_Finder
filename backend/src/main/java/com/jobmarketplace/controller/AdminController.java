package com.jobmarketplace.controller;

import com.jobmarketplace.model.Job;
import com.jobmarketplace.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PutMapping("/jobs/{jobId}/approve")
    public ResponseEntity<Job> approveJob(@PathVariable Long jobId) {
        return ResponseEntity.ok(adminService.approveJob(jobId));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        adminService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/jobs/pending")
    public ResponseEntity<List<Job>> getPendingJobs() {
        return ResponseEntity.ok(adminService.getPendingJobs());
    }

    @GetMapping("/analytics")
    public ResponseEntity<Map<String, Object>> getAnalytics() {
        return ResponseEntity.ok(adminService.getAnalytics());
    }
}

