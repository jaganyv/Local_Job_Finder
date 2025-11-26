package com.jobmarketplace.repository;

import com.jobmarketplace.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByEmployerId(Long employerId);
    List<Job> findByIsActiveTrueAndIsApprovedTrue();
    
    @Query(value = "SELECT *, (6371 * acos(cos(radians(:lat)) * cos(radians(location_lat)) * " +
            "cos(radians(location_lon) - radians(:lon)) + sin(radians(:lat)) * " +
            "sin(radians(location_lat)))) AS distance FROM jobs " +
            "WHERE is_active = true AND is_approved = true " +
            "HAVING distance < :radius ORDER BY distance", nativeQuery = true)
    List<Job> findNearbyJobs(@Param("lat") double lat, @Param("lon") double lon, @Param("radius") double radius);
}

