package com.example.spring_boot_rest_API;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SpringBootRestApiApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("Testing context load...");
	}

}
