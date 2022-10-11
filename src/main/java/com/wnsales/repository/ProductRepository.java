package com.wnsales.repository;

import com.wnsales.model.Account;
import com.wnsales.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
