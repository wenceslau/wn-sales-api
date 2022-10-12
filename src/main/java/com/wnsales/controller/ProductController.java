package com.wnsales.controller;

import com.wnsales.model.Product;
import com.wnsales.model.dto.ProductDTO;
import com.wnsales.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/product")
@Tag(name = "Product API - List/Create/Update")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/template")
    @Operation(summary = "Template JSON for resource create")
    public ProductDTO template(){
        return new ProductDTO();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find Product resource by ID")
    public ProductDTO findById(@PathVariable Long id) {
        return ProductDTO.of(productService.findById(id).get());
    }

    @GetMapping()
    @Operation(summary = "Product list pageable")
    public Page<ProductDTO> list(@PageableDefault Pageable pageable) {
        return ProductDTO.of(productService.findAll(pageable));
    }

    @PostMapping
    @Operation(summary = "Save an Product resource")
    public ResponseEntity<ProductDTO> save(@RequestBody @Validated ProductDTO dto) {
        Product product = productService.save(Product.of(dto));

        return new ResponseEntity<>(ProductDTO.of(product), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edit an Product resource")
    public ResponseEntity<ProductDTO> edit(@PathVariable Long id, @RequestBody @Validated ProductDTO dto) {
        Product product = productService.edit(id, Product.of(dto));

        return new ResponseEntity<>(ProductDTO.of(product), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Edit partially Product resource")
    public ResponseEntity<ProductDTO> partialEdit(@PathVariable Long id, @RequestBody ProductDTO dto) {
        Product product = productService.partialEdit(id, Product.of(dto));

        return new ResponseEntity<>(ProductDTO.of(product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete Product resource")
    public void deleteByCode(@PathVariable Long id) {
        productService.delete(id);
    }
}
