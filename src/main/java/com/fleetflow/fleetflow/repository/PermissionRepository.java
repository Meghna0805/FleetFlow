package com.fleetflow.fleetflow.repository;

import com.fleetflow.fleetflow.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findBySuperVendorId(Long superVendorId);
    List<Permission> findBySubVendorId(Long subVendorId);
}
