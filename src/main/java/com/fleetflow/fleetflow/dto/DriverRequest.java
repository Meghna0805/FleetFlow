package com.fleetflow.fleetflow.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverRequest {

    @NotBlank(message = "Driver name is required")
    private String name;

    @NotBlank(message = "Contact number is required")
    private String contact;

    @NotNull(message = "Cab ID is required")
    private Long cabId;

    @NotBlank(message = "License number is required")
    private String licenseNumber;

    @NotNull(message = "License expiry date is required")
    private Date licenseExpiry;

    @NotBlank(message = "RC number is required")
    private String rcNumber;

    @NotNull(message = "RC expiry date is required")
    private Date rcExpiry;
}
