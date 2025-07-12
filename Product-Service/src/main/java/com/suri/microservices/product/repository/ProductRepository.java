package com.suri.microservices.product.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.suri.microservices.product.model.Product;

public interface ProductRepository extends MongoRepository<Product,String>{}
