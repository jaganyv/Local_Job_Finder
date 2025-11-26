package com.jobmarketplace.service;

import com.jobmarketplace.dto.JobFilterRequest;
import com.jobmarketplace.dto.JobRequest;
import com.jobmarketplace.model.Job;
import com.jobmarketplace.model.User;
import com.jobmarketplace.repository.JobRepository;
import com.jobmarketplace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    public Job postJob(Long employerId, JobRequest request) {
        User employer = userRepository.findById(employerId)
                .orElseThrow(() -> new RuntimeException("Employer not found"));

        if (employer.getRole() != User.UserRole.EMPLOYER && employer.getRole() != User.UserRole.ADMIN) {
            throw new RuntimeException("Only employers can post jobs");
        }

        Job job = new Job();
        job.setEmployerId(employerId);
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setSalary(request.getSalary());
        job.setJobType(request.getJobType());
        job.setLocationLat(request.getLocationLat());
        job.setLocationLon(request.getLocationLon());
        job.setIsActive(true);
        job.setIsApproved(employer.getRole() == User.UserRole.ADMIN); // Auto-approve for admins

        return jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findByIsActiveTrueAndIsApprovedTrue();
    }

    public List<Job> getNearbyJobs(double lat, double lon, double radius) {
        return jobRepository.findNearbyJobs(lat, lon, radius);
    }

    public List<Job> filterJobs(JobFilterRequest filter) {
        List<Job> jobs;

        if (filter.getLocationLat() != null && filter.getLocationLon() != null && filter.getRadius() != null) {
            jobs = getNearbyJobs(filter.getLocationLat(), filter.getLocationLon(), filter.getRadius());
        } else {
            jobs = getAllJobs();
        }

        return jobs.stream()
                .filter(job -> filter.getJobType() == null || job.getJobType() == filter.getJobType())
                .filter(job -> filter.getMinSalary() == null || (job.getSalary() != null && job.getSalary() >= filter.getMinSalary()))
                .filter(job -> filter.getMaxSalary() == null || (job.getSalary() != null && job.getSalary() <= filter.getMaxSalary()))
                .collect(Collectors.toList());
    }

    public Job getJobById(Long jobId) {
        return jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
    }

    public Job updateJob(Long jobId, Long employerId, JobRequest request) {
        Job job = getJobById(jobId);

        if (!job.getEmployerId().equals(employerId)) {
            throw new RuntimeException("You can only edit your own jobs");
        }

        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setSalary(request.getSalary());
        job.setJobType(request.getJobType());
        job.setLocationLat(request.getLocationLat());
        job.setLocationLon(request.getLocationLon());

        return jobRepository.save(job);
    }

    public void closeJob(Long jobId, Long employerId) {
        Job job = getJobById(jobId);

        if (!job.getEmployerId().equals(employerId)) {
            throw new RuntimeException("You can only close your own jobs");
        }

        job.setIsActive(false);
        jobRepository.save(job);
    }

    public List<Job> getJobsByEmployer(Long employerId) {
        return jobRepository.findByEmployerId(employerId);
    }
}

