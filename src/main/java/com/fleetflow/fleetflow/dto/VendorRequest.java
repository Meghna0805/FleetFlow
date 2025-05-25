/*package com.fleetflow.fleetflow.dto;

import lombok.Data;

@Data
public class VendorRequest {
    private String name;
    private String vendorType; // SUPER_VENDOR or SUB_VENDOR
    private Long parentVendorId; // null if super
}
*/

package com.fleetflow.fleetflow.dto;

import lombok.Data;

@Data
public class VendorRequest {
    private String name;
    private String vendorType; // SUPER_VENDOR or SUB_VENDOR
    private String parentUsername; // null if SUPER_VENDOR
}
