package com.edix.krados;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
class KradosApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	@Sql({"/category.sql"})
	void addCategories(){
	}

	@Test
	@Sql({"/product.sql"})
	void addProducts(){
	}
}
