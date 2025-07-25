package com.suri.microservices.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;
import org.hamcrest.Matchers;
import io.restassured.RestAssured;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	@ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}
	
	static {
		mongoDBContainer.start();
	}

	@Test
	void shouldCreateProduct() {
		
		String requestBody = """
				{
					  "name":"KeyChain",
					  "description":"silver",
					  "price":"200"
				}
				""";
		
		RestAssured.given()
							.contentType("application/json")
							.body(requestBody)
							.when()
							.post("/api/product")
							.then()
							.statusCode(201)
							.body("id", Matchers.notNullValue())
							.body("name",Matchers.equalTo("KeyChain"))
							.body("description", Matchers.equalTo("silver"))
							.body("price", Matchers.equalTo(200));
	
	}

}
