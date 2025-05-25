/*package com.fleetflow.fleetflow.controller;

import com.fleetflow.fleetflow.dto.VendorRequest;
import com.fleetflow.fleetflow.entity.Vendor;
import com.fleetflow.fleetflow.repository.CabRepository;
import com.fleetflow.fleetflow.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    @Autowired
    private VendorService vendorService;
    @Autowired
    private CabRepository cabRepository;


    // ✅ Create a vendor (Super/Sub)
    @PreAuthorize("hasAuthority('ROLE_SUPER_VENDOR')")


    @PostMapping("/create-sub-vendor")
    public Vendor createVendor(@RequestBody VendorRequest request) {
        return vendorService.createVendor(request);
    }

    // ✅ List sub-vendors of a Super Vendor
    @PreAuthorize("hasAuthority('ROLE_SUPER_VENDOR')")


    @GetMapping("/sub-vendors/{superVendorId}")
    public List<Vendor> getSubVendors(@PathVariable Long superVendorId) {
        return vendorService.getSubVendorsBySuperId(superVendorId);
    }
}
*/

package com.fleetflow.fleetflow.controller;

import com.fleetflow.fleetflow.dto.VendorRequest;
import com.fleetflow.fleetflow.entity.Vendor;
import com.fleetflow.fleetflow.repository.CabRepository;
import com.fleetflow.fleetflow.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private CabRepository cabRepository;


    @PreAuthorize("hasAuthority('ROLE_SUPER_VENDOR')")
    @PostMapping("/create-sub-vendor")
    public Vendor createVendor(@RequestBody VendorRequest request) {
        return vendorService.createVendor(request);
    }


    @PreAuthorize("hasAuthority('ROLE_SUPER_VENDOR')")
    @GetMapping("/sub-vendors/{superUsername}")
    public List<Vendor> getSubVendors(@PathVariable String superUsername) {
        return vendorService.getSubVendorsBySuperUsername(superUsername);
    }
}
