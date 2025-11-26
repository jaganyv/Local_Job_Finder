package com.jobmarketplace.service;

import com.jobmarketplace.model.SavedJob;
import com.jobmarketplace.repository.SavedJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavedJobService {

    @Autowired
    private SavedJobRepository savedJobRepository;

    public SavedJob saveJob(Long seekerId, Long jobId) {
        if (savedJobRepository.existsBySeekerIdAndJobId(seekerId, jobId)) {
            throw new RuntimeException("Job already saved");
        }

        SavedJob savedJob = new SavedJob();
        savedJob.setSeekerId(seekerId);
        savedJob.setJobId(jobId);

        return savedJobRepository.save(savedJob);
    }

    public List<SavedJob> getSavedJobs(Long seekerId) {
        return savedJobRepository.findBySeekerId(seekerId);
    }

    public void unsaveJob(Long seekerId, Long jobId) {
        savedJobRepository.deleteBySeekerIdAndJobId(seekerId, jobId);
    }
}

