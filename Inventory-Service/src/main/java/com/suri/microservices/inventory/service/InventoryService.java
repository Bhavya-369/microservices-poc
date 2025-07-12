package com.suri.microservices.inventory.service;

import org.springframework.stereotype.Service;

import com.suri.microservices.inventory.repo.InventoryRespository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {
	
	private final InventoryRespository inventoryRepo;
	
	public boolean isStockAvailable(String skuCode, Integer quantity) {
		return inventoryRepo.existsBySkuCodeAndQuantityGreaterThanEqual(skuCode,quantity);
	}

}
