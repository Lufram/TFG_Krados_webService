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
	CommandLineRunner run(UserServiceImp userService, CategoryRepository categoryRepository, ProductRepository productRepository) {
		return args -> {
//
//			try (FileReader fr = new FileReader(FILE_NAME);
//				 BufferedReader br = new BufferedReader(fr);) {
//				String data = br.readLine();
//				while(data != null){
//					System.out.println(data);
//					Category c = new Category();
//					c.setName(data);
//					categoryRepository.save(c);
//					data = br.readLine();
//				}
//				System.out.println("Fichero leido correctamente");
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//			try (FileReader fr = new FileReader(FILE_NAME_PRODUCT);
//				 BufferedReader br = new BufferedReader(fr);) {
//
//				String[] data = null;
//
//				String row;
//
//				while((row = br.readLine()) != null){
//					data = row.split("\\t");
//						System.out.println("Info= " + data[0]);
//						System.out.println("Name= " + data[1]);
//						System.out.println("Price= " + data[2]);
//					Product p = new Product();
//					p.setInfo(data[0]);
//					p.setName(data[1]);
//					p.setuPrice(Double.parseDouble(data[2]));
//					productRepository.save(p);
////					Category c = categoryRepository.findById(Long.parseLong(data[3]) ).orElse(null);
////					if(c == null) {
////						System.out.println("No existe esta categoria");
////					}else {
////						p.setCategory(c);
////						System.out.println(p.toString());
////						categoryRepository.save(c);
////					}
//
//				}
//				System.out.println("Fichero leido correctamente");
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}

//			userService.saveRole(new Role(null, "ROLE_USER"));
//			userService.saveRole(new Role(null, "ROLE_MANAGER"));
//			userService.saveRole(new Role(null, "ROLE_ADMIN"));
//			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));
//
//			userService.saveUser(new User(null, "John", "John@mailito.es", "1234",null, new ArrayList<>()));
//			userService.saveUser(new User(null, "Will", "Will@mailito.es", "1234", new ArrayList<>()));
//			userService.saveUser(new User(null, "Jim", "Jim@mailito.es", "1234", new ArrayList<>()));
//			userService.saveUser(new User(null, "Arnold", "Arnold@mailito.es", "1234", new ArrayList<>()));
//			userService.saveUser(new User(null, "admin", "admin", "1234", new ArrayList<>()));

//			userService.addRoleToUser("John@mailito.es","ROLE_USER");
//			userService.addRoleToUser("Will@mailito.es","ROLE_USER");
//			userService.addRoleToUser("Jim@mailito.es","ROLE_MANAGER");
//			userService.addRoleToUser("Arnold@mailito.es","ROLE_USER");
//
//			userService.addRoleToUser("admin","ROLE_USER");
//			userService.addRoleToUser("admin","ROLE_MANAGER");
//			userService.addRoleToUser("admin","ROLE_ADMIN");
//			userService.addRoleToUser("admin","ROLE_SUPER_ADMIN");

		};
	}
}
