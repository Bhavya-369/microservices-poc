package com.suri.microservices.order.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.suri.microservices.order.client.InventoryClient;
import com.suri.microservices.order.dto.OrderRequest;
import com.suri.microservices.order.model.Order;
import com.suri.microservices.order.repo.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	
	private final OrderRepository orderRepo;	
	private final InventoryClient inventoryClient;
	
	public void placeOrder(OrderRequest orderRequest) {
		boolean stockAvailable = inventoryClient.isStockAvailable(orderRequest.skuCode(), orderRequest.quantity());
		if(stockAvailable) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		order.setPrice(orderRequest.price());
		order.setSkuCode(orderRequest.skuCode());
		order.setQuantity(orderRequest.quantity());
		orderRepo.save(order);
		}else {
			throw new RuntimeException("Product with SkuCode "+orderRequest.skuCode()
			+" not in stock");
		}
		
	}

}
