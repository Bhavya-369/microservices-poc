# SpringBoot3 Microservices POC

Product Service - created REST API endpoints - GET , POST
                - Integration test using test containers
                - used mongoDB for database layer
                
Order Service   - created REST API endpoints -  POST
                - Integration test using test containers
                - used mysql for database layer
                - inter service communication through open feign client 

Inventory Service   - created REST API endpoints -  GET
                    - Integration test using test containers and wiremock for mocking HTTP services
                    - used mysql for database layer
