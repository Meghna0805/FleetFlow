package com.fleetflow.fleetflow.dto;

import com.fleetflow.fleetflow.entity.FuelType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CabRequest {
    private String registrationNumber;
    private Integer capacity;
    private FuelType fuelType;
    private String vendorUsername; // ✅ Replaced Long vendorId with this
}
