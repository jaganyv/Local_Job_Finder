package com.jobmarketplace.repository;

import com.jobmarketplace.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findBySeekerId(Long seekerId);
    List<Application> findByJobId(Long jobId);
    Optional<Application> findByJobIdAndSeekerId(Long jobId, Long seekerId);
    boolean existsByJobIdAndSeekerId(Long jobId, Long seekerId);
}

