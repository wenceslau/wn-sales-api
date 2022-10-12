package com.wnsales.service.impl;

import com.wnsales.model.Product;
import com.wnsales.repository.ProductRepository;
import com.wnsales.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class ProductServiceImplTest {


    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private AccountService accountService;

    @Test
    void givenAccount_whenFindAll_thenShouldBeFound() {
        Product usr1 = new Product();
        usr1.setName("Account1");

        PageRequest pageRequest = PageRequest.of(1, 10);
        Page<Product> pageAccount = new PageImpl<>(List.of(usr1), pageRequest, 1);

        BDDMockito.given(productRepository.findAll(pageRequest)).willReturn(pageAccount);

        assertThat(pageAccount.getTotalElements()).isGreaterThan(0);

    }

    @Test
    void givenAccount_whenFindById_thenShouldBeFound() {

        Product usr1 = new Product();
        usr1.setName("Account1");
        Optional<Product> optionalAccount = Optional.of(usr1);

        BDDMockito.given(productRepository.findById(1L)).willReturn(optionalAccount);

        assertThat(optionalAccount.isPresent()).isTrue();
    }

    @Test
    void givenAccount_whenSave_thenShouldBeCreate() {
        Product product = new Product();
        product.setName("AccountA");
        product.setId(1L);

        BDDMockito.given(productRepository.save(BDDMockito.any(Product.class))).willReturn(product);

        assertThat(product.getId()).isEqualTo(1L);
    }

    @Test
    void givenAccount_whenSave_thenShouldBeEdit() {
        Product product = new Product();
        product.setName("product");
        product.setId(1L);

        BDDMockito.given(productRepository.save(BDDMockito.any(Product.class))).willReturn(product);

        assertThat(product.getName()).isEqualTo("product");
    }

}