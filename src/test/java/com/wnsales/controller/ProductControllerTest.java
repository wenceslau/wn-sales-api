package com.wnsales.controller;

import com.wnsales.model.Product;
import com.wnsales.model.dto.ProductDTO;
import com.wnsales.service.ProductService;
import com.wnsales.util.Utils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    @Test
    void givenProduct_whenFindById_ThenReturnJsonElement() throws Exception {
        Product prd1 = new Product();
        prd1.setName("Product1");
        Optional<Product> optionalProduct = Optional.of(prd1);

        BDDMockito.given(productService.findById(1L)).willReturn(optionalProduct);

        mvc.perform(MockMvcRequestBuilders.get("/product/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Product1"));
    }

    @Test
    void givenProducts_whenList_ThenReturnPageJsonArray() throws Exception {
        Product prd1 = new Product();
        prd1.setName("Product1");

        PageRequest pageRequest = PageRequest.of(1, 10);
        Page<Product> pageProduct = new PageImpl<>(List.of(prd1), pageRequest, 1);

        BDDMockito.given(productService.findAll(pageRequest)).willReturn(pageProduct);

        mvc.perform(MockMvcRequestBuilders.get("/product?page=1&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void givenProduct_WhenSave_ThenReturnStatusOk() throws Exception {
        ProductDTO prdDTO = new ProductDTO();
        prdDTO.setName("Product1");
        prdDTO.setPrice(BigDecimal.TEN);
        prdDTO.setAccountId(1L);

        Product prdResp = new Product();
        prdResp.setId(1L);
        prdResp.setName("Product1");
        prdResp.setPrice(BigDecimal.TEN);

        BDDMockito.given(productService.save(Product.of(prdDTO))).willReturn(prdResp);

        mvc.perform(MockMvcRequestBuilders
                        .post("/product")
                        .content(Utils.objectToJson(prdDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));

    }

    @Test
    void givenProduct_WhenSaveWithWrongIban_ThenReturnStatusNoOk() throws Exception {
        Product prdReq = new Product();
        prdReq.setName("Product1");

        Product prdResp = new Product();
        prdResp.setId(1L);
        prdResp.setName("Product1");

        BDDMockito.given(productService.save(prdReq)).willReturn(prdResp);

        mvc.perform(MockMvcRequestBuilders
                        .post("/product")
                        .content(Utils.objectToJson(prdReq))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void givenProduct_WhenEdit_thenReturnStatusOk() throws Exception {

        ProductDTO prdDTO = new ProductDTO();
        prdDTO.setName("ProductChanged");
        prdDTO.setPrice(BigDecimal.TEN);
        prdDTO.setAccountId(1L);

        Product prdResp = new Product();
        prdResp.setId(1L);
        prdResp.setName("ProductChanged");
        prdResp.setPrice(BigDecimal.TEN);

        BDDMockito.given(productService.edit(1L,Product.of(prdDTO))).willReturn(prdResp);

        mvc.perform(MockMvcRequestBuilders
                        .put("/product/1")
                        .content(Utils.objectToJson(prdDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("ProductChanged"));
    }

    @Test
    void givenProduct_WhenPartialEdit_thenReturnStatusOk() throws Exception {

        Product prdReq = new Product();
        prdReq.setName("ProductChanged");

        Product prdResp = new Product();
        prdResp.setId(1L);
        prdResp.setName("ProductChanged");
        prdResp.setPrice(BigDecimal.TEN);

        BDDMockito.given(productService.partialEdit(1L,prdReq)).willReturn(prdResp);

        mvc.perform(MockMvcRequestBuilders
                        .patch("/product/1")
                        .content(Utils.objectToJson(prdReq))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(BigDecimal.TEN));

    }
}