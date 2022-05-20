package com.edix.krados;

import com.edix.krados.controller.UserController;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
class KradosApplicationRoles {

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
	@Sql({"/roles.sql"})
	void addRoles(){
	}
}
