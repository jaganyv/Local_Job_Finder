package com.jobmarketplace.controller;

import com.jobmarketplace.model.SavedJob;
import com.jobmarketplace.service.AuthService;
import com.jobmarketplace.service.SavedJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/saved-jobs")
@CrossOrigin(origins = "http://localhost:3000")
public class SavedJobController {

    @Autowired
    private SavedJobService savedJobService;

    @Autowired
    private AuthService authService;

    private Long getCurrentUserId(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return authService.getUserIdByEmail(userDetails.getUsername());
    }

    @PostMapping("/{jobId}")
    public ResponseEntity<SavedJob> saveJob(@PathVariable Long jobId, Authentication authentication) {
        Long seekerId = getCurrentUserId(authentication);
        return ResponseEntity.ok(savedJobService.saveJob(seekerId, jobId));
    }

    @GetMapping("/my-saved")
    public ResponseEntity<List<SavedJob>> getMySavedJobs(Authentication authentication) {
        Long seekerId = getCurrentUserId(authentication);
        return ResponseEntity.ok(savedJobService.getSavedJobs(seekerId));
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<Void> unsaveJob(@PathVariable Long jobId, Authentication authentication) {
        Long seekerId = getCurrentUserId(authentication);
        savedJobService.unsaveJob(seekerId, jobId);
        return ResponseEntity.ok().build();
    }
}

