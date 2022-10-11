package com.wnsales.service;

import com.wnsales.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ProductService {

    Optional<Product> findById(Long productId);

    Page<Product> findAll(Pageable pageable);

    void delete(Long productId);

    Product save(Product product);

    Product edit(Long productId, Product product);

    Product partialEdit(Long productId, Product product);
}
