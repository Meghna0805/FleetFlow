package com.fleetflow.fleetflow.controller;

import com.fleetflow.fleetflow.dto.DriverRequest;
import com.fleetflow.fleetflow.entity.Cab;
import com.fleetflow.fleetflow.entity.Driver;
import com.fleetflow.fleetflow.repository.CabRepository;
import com.fleetflow.fleetflow.repository.DriverRepository;
import com.fleetflow.fleetflow.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverRepository driverRepository;
    private final CabRepository cabRepository;
    private final DriverService driverService;

    private static final Logger logger = LoggerFactory.getLogger(DriverController.class);

    // ✅ Create Driver
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_VENDOR', 'ROLE_SUB_VENDOR')")
    @PostMapping
    public ResponseEntity<?> createDriver(@Valid @RequestBody DriverRequest request) {
        logger.info("Creating driver for cabId={}", request.getCabId());

        Cab cab = cabRepository.findById(request.getCabId()).orElse(null);
        if (cab == null) {
            logger.warn("Cab not found for ID: {}", request.getCabId());
            return ResponseEntity.badRequest().body("Cab not found for ID: " + request.getCabId());
        }

        Driver driver = Driver.builder()
                .name(request.getName())
                .contact(request.getContact())
                .licenseNumber(request.getLicenseNumber())
                .licenseExpiry(request.getLicenseExpiry())
                .rcNumber(request.getRcNumber())
                .rcExpiry(request.getRcExpiry())
                .cab(cab)
                .available(true)
                .build();

        Driver savedDriver = driverRepository.save(driver);
        logger.info("Driver created with ID: {}", savedDriver.getId());

        return ResponseEntity.ok("Driver created with ID: " + savedDriver.getId());
    }

    // ✅ Get All Drivers (Used by View My Drivers)
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_VENDOR', 'ROLE_SUB_VENDOR')")
    @GetMapping
    public ResponseEntity<List<Driver>> getAllDrivers() {
        logger.info("Fetching all drivers...");
        return ResponseEntity.ok(driverRepository.findAll());
    }

    // ✅ Get Drivers With Expired License/RC
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_VENDOR', 'ROLE_SUB_VENDOR')")
    @GetMapping("/expired-docs")
    public ResponseEntity<List<Driver>> getDriversWithExpiredDocs() {
        logger.info("Fetching drivers with expired documents...");
        return ResponseEntity.ok(driverService.getExpiredDocumentDrivers());
    }

    // ✅ Dashboard: Driver Availability Count
    @Cacheable("driverAvailability")
    @PreAuthorize("hasAuthority('ROLE_SUPER_VENDOR')")
    @GetMapping("/availability-count")
    public ResponseEntity<?> getAvailabilityCount() {
        logger.info("Fetching available/unavailable driver count...");

        List<Driver> drivers = driverRepository.findAll();
        long available = drivers.stream().filter(Driver::isAvailable).count();
        long unavailable = drivers.size() - available;

        Map<String, Long> response = new HashMap<>();
        response.put("available", available);
        response.put("unavailable", unavailable);

        logger.info("Available: {}, Unavailable: {}", available, unavailable);
        return ResponseEntity.ok(response);
    }

    // ✅ Dashboard: Flagged Expired License Drivers
    @PreAuthorize("hasAuthority('ROLE_SUPER_VENDOR')")
    @GetMapping("/flagged-license")
    public ResponseEntity<?> getDriversWithExpiredLicense() {
        logger.info("Fetching drivers with expired license...");

        List<Driver> allDrivers = driverRepository.findAll();
        List<Driver> flaggedDrivers = allDrivers.stream()
                .filter(d -> d.getLicenseExpiry() != null && d.getLicenseExpiry().before(new Date()))
                .toList();

        logger.info("Flagged drivers count: {}", flaggedDrivers.size());
        return ResponseEntity.ok(flaggedDrivers);
    }
}
