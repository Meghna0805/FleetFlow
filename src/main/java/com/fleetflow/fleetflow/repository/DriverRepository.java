package com.fleetflow.fleetflow.repository;

import com.fleetflow.fleetflow.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> findByCabId(Long cabId);
    List<Driver> findByCabVendorId(Long vendorId);

}
