package com.jobmarketplace.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "saved_jobs", uniqueConstraints = @UniqueConstraint(columnNames = {"seeker_id", "job_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavedJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saved_job_id")
    private Long savedJobId;

    @NotNull
    @Column(name = "seeker_id", nullable = false)
    private Long seekerId;

    @NotNull
    @Column(name = "job_id", nullable = false)
    private Long jobId;

    @Column(name = "saved_at")
    private LocalDateTime savedAt = LocalDateTime.now();
}

