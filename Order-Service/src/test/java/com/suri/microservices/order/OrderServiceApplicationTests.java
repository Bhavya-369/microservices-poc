package com.suri.microservices.order;

import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.suri.microservices.order.stubs.InventoryClientStub;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureWireMock(port = 0)
class OrderServiceApplicationTests {
	
    @ServiceConnection
    static MySQLContainer mysql = new MySQLContainer("mysql:8.0")
            .withDatabaseName("orderdb")    
            .withUsername("root")            
            .withPassword("root");          

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }
    
    static {
    	mysql.start();
    }

    @Test
    void shouldSubmitOrder() {
        String requestBody = """
            {
              "skuCode": "Copper-Jug",
              "price": 1500,
              "quantity": 290
            }
            """;
        
        InventoryClientStub.stubInventoryCall("Copper-Jug", 290);

        String response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/order")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .asString();

        assertThat(response, Matchers.is("Order placed Successfully !!"));
    }
}
