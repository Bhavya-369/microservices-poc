package com.suri.microservices.inventory;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class InventoryServiceApplicationTests {
	
	private static final Logger log = LoggerFactory.getLogger(InventoryServiceApplicationTests.class);


    @LocalServerPort
    private int port;

    @ServiceConnection
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("inventorydb")
            .withUsername("root")
            .withPassword("root");
    
    static {
    	mysql.start();
    }

//    @DynamicPropertySource
//    static void overrideProps(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", mysql::getJdbcUrl);
//        registry.add("spring.datasource.username", mysql::getUsername);
//        registry.add("spring.datasource.password", mysql::getPassword);
//        registry.add("spring.flyway.enabled", () -> true);
//    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void shouldCheckInventory() {
        var response = RestAssured.given()
                .when()
                .get("/api/inventory?skuCode=Radio&quantity=100")
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(Boolean.class);
        
        log.info("Inventory available: {}", response);

        assertTrue(response); // ✅ works if data preloaded via Flyway
    }
}
