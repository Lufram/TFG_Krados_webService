package com.edix.krados;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql({"/category.sql"})
class KradosApplicationTests {

	@Test

	void contextLoads() {
	}

}
