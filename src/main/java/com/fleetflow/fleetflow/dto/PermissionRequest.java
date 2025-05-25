package com.fleetflow.fleetflow.dto;

import lombok.Data;

@Data
public class PermissionRequest {
    private Long superVendorId;
    private Long subVendorId;
    private String accessType;  // e.g., "VIEW_RIDES", "CREATE_VENDOR"
}
