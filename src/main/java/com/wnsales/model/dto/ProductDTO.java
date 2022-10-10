package com.wnsales.model.dto;

import com.wnsales.model.Product;
import com.wnsales.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {

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
    private AccountDTO account;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(id, ((ProductDTO) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    //Builder
    private ProductDTO(Product model) {
        BeanUtils.copyProperties(model, this);
        this.account = AccountDTO.of(model.getAccount());
    }

    public static ProductDTO of(Product source){
        if (source == null)
            return null;
        return new ProductDTO(source);
    }

    public static Set<ProductDTO> of(Set<Product> source){
        return source.stream().map(ProductDTO::new).collect(Collectors.toSet());
    }

}
