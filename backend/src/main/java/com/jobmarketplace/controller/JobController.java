package com.jobmarketplace.controller;

import com.jobmarketplace.dto.JobFilterRequest;
import com.jobmarketplace.dto.JobRequest;
import com.jobmarketplace.model.Job;
import com.jobmarketplace.service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@CrossOrigin(origins = "http://localhost:3000")
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private com.jobmarketplace.service.AuthService authService;

    private Long getCurrentUserId(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return authService.getUserIdByEmail(userDetails.getUsername());
    }

    @PostMapping("/post")
    public ResponseEntity<Job> postJob(@Valid @RequestBody JobRequest request, Authentication authentication) {
        Long employerId = getCurrentUserId(authentication);
        return ResponseEntity.ok(jobService.postJob(employerId, request));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<Job>> getNearbyJobs(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(defaultValue = "10") double radius) {
        return ResponseEntity.ok(jobService.getNearbyJobs(lat, lon, radius));
    }

    @PostMapping("/filter")
    public ResponseEntity<List<Job>> filterJobs(@RequestBody JobFilterRequest filter) {
        return ResponseEntity.ok(jobService.filterJobs(filter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Long id, @Valid @RequestBody JobRequest request, Authentication authentication) {
        Long employerId = getCurrentUserId(authentication);
        return ResponseEntity.ok(jobService.updateJob(id, employerId, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> closeJob(@PathVariable Long id, Authentication authentication) {
        Long employerId = getCurrentUserId(authentication);
        jobService.closeJob(id, employerId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my-jobs")
    public ResponseEntity<List<Job>> getMyJobs(Authentication authentication) {
        Long employerId = getCurrentUserId(authentication);
        return ResponseEntity.ok(jobService.getJobsByEmployer(employerId));
    }
}

