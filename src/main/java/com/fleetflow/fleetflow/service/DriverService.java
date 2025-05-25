package com.fleetflow.fleetflow.service;

import com.fleetflow.fleetflow.entity.Driver;
import com.fleetflow.fleetflow.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;

    public List<Driver> getExpiredDocumentDrivers() {
        Date today = new Date();

        return driverRepository.findAll()
                .stream()
                .filter(driver ->
                        (driver.getLicenseExpiry() != null && driver.getLicenseExpiry().before(today)) ||
                                (driver.getRcExpiry() != null && driver.getRcExpiry().before(today))
                )
                .collect(Collectors.toList());
    }
}
