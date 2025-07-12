package com.suri.microservices.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suri.microservices.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
