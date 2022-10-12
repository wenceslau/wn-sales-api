package com.wnsales.model;

import com.wnsales.model.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Account account;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(id, ((Product) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    //Builder
    private Product(ProductDTO dto) {
        BeanUtils.copyProperties(dto, this);
        if (dto.getAccountId() != null){
            this.account = new Account();
            this.account.setId(dto.getAccountId());
        }
    }

    public static Product of(ProductDTO source){
        if (source == null)
            return null;
        return new Product(source);
    }

    public static Set<Product> of(Set<ProductDTO> source){
        return source.stream().map(Product::new).collect(Collectors.toSet());
    }


}
