package com.fleetflow.fleetflow.repository;

import com.fleetflow.fleetflow.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    List<Vendor> findByParentVendorId(Long parentId);

    // ✅ Needed for parentUsername logic
    Optional<Vendor> findByName(String name);
}
