package com.jobmarketplace.dto;

import com.jobmarketplace.model.Job;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JobRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private Double salary;

    @NotNull
    private Job.JobType jobType;

    private Double locationLat;
    private Double locationLon;
}

