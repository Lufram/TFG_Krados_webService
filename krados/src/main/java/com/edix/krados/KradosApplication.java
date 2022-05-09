package com.edix.krados;

import com.edix.krados.model.Role;
import com.edix.krados.model.User;
import com.edix.krados.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class KradosApplication {

	public static void main(String[] args) {
		SpringApplication.run(KradosApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {

			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new User(null, "John", "John@mailito.es", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Will", "Will@mailito.es", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Jim", "Jim@mailito.es", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Arnold", "Arnold@mailito.es", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "admin", "admin", "1234", new ArrayList<>()));

			userService.addRoleToUser("John","ROLE_USER");
			userService.addRoleToUser("Will","ROLE_USER");
			userService.addRoleToUser("Jim","ROLE_MANAGER");
			userService.addRoleToUser("Arnold","ROLE_USER");

			userService.addRoleToUser("admin","ROLE_USER");
			userService.addRoleToUser("admin","ROLE_MANAGER");
			userService.addRoleToUser("admin","ROLE_ADMIN");
			userService.addRoleToUser("admin","ROLE_SUPER_ADMIN");
		};
	}
}
