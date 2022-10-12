package com.wnsales.model.request;

import com.wnsales.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor

public class ProductResponse {

    private Long id;

    private String name;

    private BigDecimal price;
    @NotNull
    private Integer amount;

    public ProductResponse(Product product, Integer amount) {
        this.amount = amount;
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(id, ((ProductResponse) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
