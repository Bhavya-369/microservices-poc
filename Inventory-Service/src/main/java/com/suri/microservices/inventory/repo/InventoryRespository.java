package com.suri.microservices.inventory.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suri.microservices.inventory.model.Inventory;

public interface InventoryRespository  extends JpaRepository<Inventory, Long>{

	boolean existsBySkuCodeAndQuantityGreaterThanEqual(String skuCode, Integer quantity);

}
