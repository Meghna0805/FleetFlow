package com.fleetflow.fleetflow;

import com.fleetflow.fleetflow.entity.Role;
import com.fleetflow.fleetflow.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching; // ✅ Add this
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching // ✅ Enable Spring's caching mechanism
public class FleetFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(FleetFlowApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.count() == 0) {
				roleRepository.save(new Role("SUPER_VENDOR"));
				roleRepository.save(new Role("SUB_VENDOR"));
			}
		};
	}


}
