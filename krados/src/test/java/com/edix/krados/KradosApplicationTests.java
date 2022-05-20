package com.edix.krados;

import com.edix.krados.controller.UserController;
import com.edix.krados.form.RegisterForm;
import com.edix.krados.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class KradosApplicationTests {

	@InjectMocks
	private KradosApplication app;
	@Autowired
	public WebApplicationContext webApplicationContext;
	@Autowired
	public UserController userController;

	@Test
	void contextLoads() {
	}

	@Test
	@Sql({"/category.sql"})
	void addCategories(){
	}

	@Test
	@Sql({"/roles.sql"})
	void addRoles(){
	}

	@Test
	@Sql({"/product.sql"})
	void addProducts(){
	}

}
