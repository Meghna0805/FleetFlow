
package com.fleetflow.fleetflow.service;

import com.fleetflow.fleetflow.dto.SignupRequest;
import com.fleetflow.fleetflow.entity.Role;
import com.fleetflow.fleetflow.entity.User;
import com.fleetflow.fleetflow.entity.Vendor;
import com.fleetflow.fleetflow.entity.VendorType;
import com.fleetflow.fleetflow.repository.RoleRepository;
import com.fleetflow.fleetflow.repository.UserRepository;
import com.fleetflow.fleetflow.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(SignupRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Set<Role> roles = new HashSet<>();
        for (String roleName : request.getRoles()) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            roles.add(role);
        }
        user.setRoles(roles);


        User savedUser = userRepository.save(user);


        for (Role role : roles) {
            if (role.getName().equals("SUPER_VENDOR") || role.getName().equals("SUB_VENDOR")) {
                Vendor vendor = new Vendor();
                vendor.setName(savedUser.getUsername());
                vendor.setVendorType(VendorType.valueOf(role.getName()));
                vendor.setParentVendor(null);
                vendorRepository.save(vendor);
            }
        }

        return savedUser;
    }
}
