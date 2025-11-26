package com.jobmarketplace.service;

import com.jobmarketplace.model.Application;
import com.jobmarketplace.model.Job;
import com.jobmarketplace.model.User;
import com.jobmarketplace.repository.ApplicationRepository;
import com.jobmarketplace.repository.JobRepository;
import com.jobmarketplace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    public Application applyForJob(Long jobId, Long seekerId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (!job.getIsActive() || !job.getIsApproved()) {
            throw new RuntimeException("Job is not available");
        }

        if (applicationRepository.existsByJobIdAndSeekerId(jobId, seekerId)) {
            throw new RuntimeException("You have already applied for this job");
        }

        Application application = new Application();
        application.setJobId(jobId);
        application.setSeekerId(seekerId);
        application.setStatus(Application.ApplicationStatus.PENDING);

        return applicationRepository.save(application);
    }

    public List<Application> getApplicationsBySeeker(Long seekerId) {
        return applicationRepository.findBySeekerId(seekerId);
    }

    public List<Application> getApplicationsByJob(Long jobId, Long employerId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (!job.getEmployerId().equals(employerId)) {
            throw new RuntimeException("You can only view applications for your own jobs");
        }

        return applicationRepository.findByJobId(jobId);
    }

    public Application acceptApplication(Long applicationId, Long employerId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        Job job = jobRepository.findById(application.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (!job.getEmployerId().equals(employerId)) {
            throw new RuntimeException("You can only accept applications for your own jobs");
        }

        application.setStatus(Application.ApplicationStatus.ACCEPTED);
        application = applicationRepository.save(application);

        // Send WhatsApp notification
        User seeker = userRepository.findById(application.getSeekerId())
                .orElseThrow(() -> new RuntimeException("Seeker not found"));
        
        notificationService.sendJobAcceptanceNotification(seeker, job);

        return application;
    }

    public Application rejectApplication(Long applicationId, Long employerId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        Job job = jobRepository.findById(application.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (!job.getEmployerId().equals(employerId)) {
            throw new RuntimeException("You can only reject applications for your own jobs");
        }

        application.setStatus(Application.ApplicationStatus.REJECTED);
        return applicationRepository.save(application);
    }
}

