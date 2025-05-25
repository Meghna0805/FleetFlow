/*package com.fleetflow.fleetflow.service;

import com.fleetflow.fleetflow.dto.VendorRequest;
import com.fleetflow.fleetflow.entity.Vendor;
import com.fleetflow.fleetflow.entity.VendorType;
import com.fleetflow.fleetflow.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    public Vendor createVendor(VendorRequest request) {
        Vendor vendor = new Vendor();
        vendor.setName(request.getName());
        vendor.setVendorType(VendorType.valueOf(request.getVendorType()));

        if (request.getParentVendorId() != null) {
            Vendor parent = vendorRepository.findById(request.getParentVendorId())
                    .orElseThrow(() -> new RuntimeException("Parent vendor not found"));
            vendor.setParentVendor(parent);
        }

        return vendorRepository.save(vendor);
    }
    // VendorService.java
    public List<Vendor> getSubVendorsBySuperId(Long superVendorId) {
        return vendorRepository.findByParentVendorId(superVendorId);
    }

}
*/

package com.fleetflow.fleetflow.service;

import com.fleetflow.fleetflow.dto.VendorRequest;
import com.fleetflow.fleetflow.entity.Vendor;
import com.fleetflow.fleetflow.entity.VendorType;
import com.fleetflow.fleetflow.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    // ✅ Create vendor with parentUsername instead of ID
    public Vendor createVendor(VendorRequest request) {
        Vendor vendor = new Vendor();
        vendor.setName(request.getName());
        vendor.setVendorType(VendorType.valueOf(request.getVendorType()));

        if (request.getParentUsername() != null && !request.getParentUsername().isEmpty()) {
            Vendor parent = vendorRepository.findByName(request.getParentUsername())
                    .orElseThrow(() -> new RuntimeException("Parent vendor not found"));
            vendor.setParentVendor(parent);
        }

        return vendorRepository.save(vendor);
    }

    // ✅ Fetch sub-vendors by parent username
    public List<Vendor> getSubVendorsBySuperUsername(String username) {
        Vendor parent = vendorRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("Parent vendor not found"));
        return vendorRepository.findByParentVendorId(parent.getId());
    }
}
