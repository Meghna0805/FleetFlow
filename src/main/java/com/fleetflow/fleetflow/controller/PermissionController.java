package com.fleetflow.fleetflow.controller;

import com.fleetflow.fleetflow.dto.PermissionRequest;
import com.fleetflow.fleetflow.entity.Permission;
import com.fleetflow.fleetflow.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    // ✅ Assign permission (delegation)
    @PostMapping("/assign")
    public Permission assignPermission(@RequestBody PermissionRequest request) {
        return permissionService.assignPermission(request);
    }

    // ✅ Get all permissions delegated by a SuperVendor
    @GetMapping("/super/{superVendorId}")
    public List<Permission> getBySuperVendor(@PathVariable Long superVendorId) {
        return permissionService.getPermissionsBySuperVendor(superVendorId);
    }

    // ✅ Get all permissions granted to a SubVendor
    @GetMapping("/sub/{subVendorId}")
    public List<Permission> getBySubVendor(@PathVariable Long subVendorId) {
        return permissionService.getPermissionsBySubVendor(subVendorId);
    }
}
