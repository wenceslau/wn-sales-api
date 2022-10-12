package com.wnsales.service.impl;

import com.wnsales.model.Product;
import com.wnsales.repository.AccountRepository;
import com.wnsales.repository.ProductRepository;
import com.wnsales.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor //This annotation already inject constructor with autowired
public class ProductServiceImpl extends _DefaultService implements ProductService {

    private final ProductRepository productRepository;

    private final AccountRepository accountRepository;


    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Optional<Product> findById(Long userId) {
        Optional<Product> optionalUser = productRepository.findById(userId);

        if (optionalUser.isEmpty()){
            throw new EmptyResultDataAccessException("Resource not found", 1);
        }

        return optionalUser;
    }

    @Override
    public void delete(Long userId) {
        Optional<Product> optionalProduct = productRepository.findById(userId);

        if (optionalProduct.isPresent()) {
            productRepository.delete(optionalProduct.get());
        }
    }

    @Override
    @Transactional
    public Product save(Product product) {

        if (product.getAccount() == null){
            throw new RuntimeException("Account is required");
        }

        if (accountRepository.existsById(product.getAccount().getId()) == false){
            throw new RuntimeException("Invalid account");
        }

        return productRepository.save(product);
    }

    @Override
    public Product edit(Long userId, Product product) {

        if (product.getAccount() == null){
            throw new RuntimeException("Account is required");
        }

        if (accountRepository.existsById(product.getAccount().getId()) == false) {
            throw new RuntimeException("Invalid account");
        }

        Product target = findById(userId).get();
        BeanUtils.copyProperties(product, target, "id");
        return productRepository.save(target);
    }

    @Override
    public Product partialEdit(Long userId, Product product) {

        if (product.getAccount() != null){
            if (accountRepository.existsById(product.getAccount().getId()) == false){
                throw new RuntimeException("Invalid account");
            }
        }

        Product target = findById(userId).get();

        var ignore = new ArrayList<>(List.of("uid"));
        addPropertyNullToIgnore(product, ignore);

        BeanUtils.copyProperties(product, target, ignore.toArray(String[]::new));

        return productRepository.save(target);
    }

}
