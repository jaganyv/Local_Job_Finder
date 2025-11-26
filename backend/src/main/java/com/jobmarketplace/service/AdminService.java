package com.jobmarketplace.service;

import com.jobmarketplace.model.Job;
import com.jobmarketplace.repository.JobRepository;
import com.jobmarketplace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    public Job approveJob(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        job.setIsApproved(true);
        return jobRepository.save(job);
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(userId);
    }

    public List<Job> getPendingJobs() {
        return jobRepository.findAll().stream()
                .filter(job -> !job.getIsApproved() && job.getIsActive())
                .toList();
    }

    public Map<String, Object> getAnalytics() {
        Map<String, Object> analytics = new HashMap<>();
        
        long totalUsers = userRepository.count();
        long totalJobs = jobRepository.count();
        long activeJobs = jobRepository.findByIsActiveTrueAndIsApprovedTrue().size();
        long pendingJobs = getPendingJobs().size();
        
        Map<String, Long> usersByRole = new HashMap<>();
        userRepository.findAll().forEach(user -> {
            String role = user.getRole().name();
            usersByRole.put(role, usersByRole.getOrDefault(role, 0L) + 1);
        });

        analytics.put("totalUsers", totalUsers);
        analytics.put("totalJobs", totalJobs);
        analytics.put("activeJobs", activeJobs);
        analytics.put("pendingJobs", pendingJobs);
        analytics.put("usersByRole", usersByRole);

        return analytics;
    }
}

