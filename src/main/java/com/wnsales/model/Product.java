package com.wnsales.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.wnsales.model.dto.ProductDTO;
import com.wnsales.model.dto.UserDTO;
import lombok.*;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ACCOUNT_ID")
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
    private Product(ProductDTO model) {
        BeanUtils.copyProperties(model, this);
        this.account = Account.of(model.getAccount());
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
