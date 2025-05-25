package com.fleetflow.fleetflow.repository;

import com.fleetflow.fleetflow.entity.Cab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CabRepository extends JpaRepository<Cab, Long> {
    boolean existsByRegistrationNumber(String registrationNumber);

    List<Cab> findByVendorId(Long vendorId);

    List<Cab> findByVendorName(String name);
}
