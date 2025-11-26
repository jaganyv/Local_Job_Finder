package com.jobmarketplace.dto;

import com.jobmarketplace.model.Job;
import lombok.Data;

@Data
public class JobFilterRequest {
    private Job.JobType jobType;
    private Double minSalary;
    private Double maxSalary;
    private Double locationLat;
    private Double locationLon;
    private Double radius; // in kilometers
}

