package com.edix.krados;

import com.edix.krados.model.Category;
import com.edix.krados.model.Product;
import com.edix.krados.model.Role;
import com.edix.krados.model.User;
import com.edix.krados.repository.CategoryRepository;
import com.edix.krados.repository.ProductRepository;
import com.edix.krados.service.UserService;
import com.edix.krados.service.UserServiceImp;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@SpringBootApplication
public class KradosApplication {

	public static final String FILE_NAME = "./BBDDData/CategoryData.txt";

	public static final String FILE_NAME_PRODUCT= "./BBDDData/ProductData.txt";
	public static void main(String[] args) {
		SpringApplication.run(KradosApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	CommandLineRunner run(UserServiceImp userService) {
		return args -> {

//			userService.saveRole(new Role(null, "ROLE_USER"));
//			userService.saveRole(new Role(null, "ROLE_MANAGER"));
//			userService.saveRole(new Role(null, "ROLE_ADMIN"));
//			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));
//
//			userService.saveUser(new User(null, "John", "John@mailito.es", "1234",null, new ArrayList<>()));
//			userService.saveUser(new User(null, "Will", "Will@mailito.es", "1234", new ArrayList<>()));
//			userService.saveUser(new User(null, "Jim", "Jim@mailito.es", "1234", new ArrayList<>()));
//			userService.saveUser(new User(null, "Arnold", "Arnold@mailito.es", "1234", new ArrayList<>()));
//			userService.saveUser(new User(null, "fulano", "fulano@mailito.es", "1234", new ArrayList<>()));
//
//			userService.addRoleToUser("John@mailito.es","ROLE_USER");
//			userService.addRoleToUser("Will@mailito.es","ROLE_USER");
//			userService.addRoleToUser("Jim@mailito.es","ROLE_MANAGER");
//			userService.addRoleToUser("fulano@mailito.es","ROLE_USER");
//
//			userService.addRoleToUser("admin","ROLE_USER");
//			userService.addRoleToUser("admin","ROLE_MANAGER");
//			userService.addRoleToUser("admin","ROLE_ADMIN");
//			userService.addRoleToUser("admin","ROLE_SUPER_ADMIN");

		};
	}
}
