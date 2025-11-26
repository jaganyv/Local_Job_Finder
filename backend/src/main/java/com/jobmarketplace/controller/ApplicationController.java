package com.jobmarketplace.controller;

import com.jobmarketplace.model.Application;
import com.jobmarketplace.service.ApplicationService;
import com.jobmarketplace.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
@CrossOrigin(origins = "http://localhost:3000")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private AuthService authService;

    private Long getCurrentUserId(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return authService.getUserIdByEmail(userDetails.getUsername());
    }

    @PostMapping("/apply/{jobId}")
    public ResponseEntity<Application> applyForJob(@PathVariable Long jobId, Authentication authentication) {
        Long seekerId = getCurrentUserId(authentication);
        return ResponseEntity.ok(applicationService.applyForJob(jobId, seekerId));
    }

    @GetMapping("/my-applications")
    public ResponseEntity<List<Application>> getMyApplications(Authentication authentication) {
        Long seekerId = getCurrentUserId(authentication);
        return ResponseEntity.ok(applicationService.getApplicationsBySeeker(seekerId));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<Application>> getApplicationsByJob(@PathVariable Long jobId, Authentication authentication) {
        Long employerId = getCurrentUserId(authentication);
        return ResponseEntity.ok(applicationService.getApplicationsByJob(jobId, employerId));
    }

    @PutMapping("/{applicationId}/accept")
    public ResponseEntity<Application> acceptApplication(@PathVariable Long applicationId, Authentication authentication) {
        Long employerId = getCurrentUserId(authentication);
        return ResponseEntity.ok(applicationService.acceptApplication(applicationId, employerId));
    }

    @PutMapping("/{applicationId}/reject")
    public ResponseEntity<Application> rejectApplication(@PathVariable Long applicationId, Authentication authentication) {
        Long employerId = getCurrentUserId(authentication);
        return ResponseEntity.ok(applicationService.rejectApplication(applicationId, employerId));
    }
}

