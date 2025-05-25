package com.fleetflow.fleetflow.service;

import com.fleetflow.fleetflow.dto.PermissionRequest;
import com.fleetflow.fleetflow.entity.Permission;
import com.fleetflow.fleetflow.entity.Vendor;
import com.fleetflow.fleetflow.repository.PermissionRepository;
import com.fleetflow.fleetflow.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private VendorRepository vendorRepository;

    public Permission assignPermission(PermissionRequest request) {
        Vendor superVendor = vendorRepository.findById(request.getSuperVendorId())
                .orElseThrow(() -> new RuntimeException("Super vendor not found"));

        Vendor subVendor = vendorRepository.findById(request.getSubVendorId())
                .orElseThrow(() -> new RuntimeException("Sub vendor not found"));

        Permission permission = Permission.builder()
                .accessType(request.getAccessType())
                .superVendor(superVendor)
                .subVendor(subVendor)
                .build();

        return permissionRepository.save(permission);
    }

    public List<Permission> getPermissionsBySuperVendor(Long superVendorId) {
        return permissionRepository.findBySuperVendorId(superVendorId);
    }

    public List<Permission> getPermissionsBySubVendor(Long subVendorId) {
        return permissionRepository.findBySubVendorId(subVendorId);
    }
}
