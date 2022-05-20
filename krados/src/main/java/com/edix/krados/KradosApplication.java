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

	public static void main(String[] args) {
		SpringApplication.run(KradosApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	CommandLineRunner run(UserServiceImp userService, CategoryRepository categoryRepository, ProductRepository productRepository) {
		return args -> {
		};
	}
}
