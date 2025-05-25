package com.fleetflow.fleetflow.controller;

import com.fleetflow.fleetflow.dto.CabRequest;
import com.fleetflow.fleetflow.entity.Cab;
import com.fleetflow.fleetflow.entity.Vendor;
import com.fleetflow.fleetflow.repository.CabRepository;
import com.fleetflow.fleetflow.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cabs")
@RequiredArgsConstructor
public class CabController {

    private final CabRepository cabRepository;
    private final VendorRepository vendorRepository;
    private static final Logger logger = LoggerFactory.getLogger(CabController.class);

    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_VENDOR', 'ROLE_SUB_VENDOR')")
    @PostMapping
    public ResponseEntity<?> createCab(@RequestBody CabRequest request) {
        logger.info("Received request to create cab for vendorUsername={}", request.getVendorUsername());

        if (cabRepository.existsByRegistrationNumber(request.getRegistrationNumber())) {
            logger.warn("Cab with registration number {} already exists", request.getRegistrationNumber());
            return ResponseEntity.badRequest().body("Cab with this registration number already exists");
        }

        Vendor vendor = vendorRepository.findByName(request.getVendorUsername())
                .orElseThrow(() -> new RuntimeException("Vendor not found: " + request.getVendorUsername()));

        Cab cab = Cab.builder()
                .registrationNumber(request.getRegistrationNumber())
                .capacity(request.getCapacity())
                .fuelType(request.getFuelType())
                .vendor(vendor)
                .build();

        Cab savedCab = cabRepository.save(cab);
        logger.info("Cab created with ID: {}", savedCab.getId());

        return ResponseEntity.ok().body("Cab created with ID: " + savedCab.getId());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_VENDOR', 'ROLE_SUB_VENDOR')")
    @GetMapping("/vendor/{username}")
    public ResponseEntity<?> getCabsByVendorUsername(@PathVariable String username) {
        List<Cab> cabs = cabRepository.findByVendorName(username);
        return ResponseEntity.ok(cabs);
    }

    @GetMapping("/test-auth")
    public ResponseEntity<?> testAuthorities() {
        System.out.println("👮 Authorities: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return ResponseEntity.ok("Check console for authorities");
    }

    @PreAuthorize("hasAuthority('ROLE_SUPER_VENDOR')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCabById(@PathVariable Long id) {
        return cabRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Cacheable(value = "cabCount", key = "'fixedKey'")
    @PreAuthorize("hasAuthority('ROLE_SUPER_VENDOR')")
    @GetMapping("/active-inactive-count")
    public ResponseEntity<?> getActiveInactiveCount() {
        logger.info("Fetching active/inactive cab count...");

        List<Cab> allCabs = cabRepository.findAll();

        long active = allCabs.stream().filter(Cab::isActive).count();
        long inactive = allCabs.size() - active;

        Map<String, Long> countMap = new HashMap<>();
        countMap.put("active", active);
        countMap.put("inactive", inactive);

        logger.info("Active Cabs: {}, Inactive Cabs: {}", active, inactive);
        return ResponseEntity.ok(countMap);
    }
}
