package com.wnsales.model.dto;

import com.wnsales.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {

    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Long accountId;

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
        BeanUtils.copyProperties(model, this, "account");
        this.account = AccountDTO.of(model.getAccount());
        if (model.getAccount() != null) {
            this.accountId = model.getAccount().getId();
        }
    }

    public static ProductDTO of(Product source){
        if (source == null)
            return null;
        return new ProductDTO(source);
    }

    public static Set<ProductDTO> of(Set<Product> source){
        return source.stream().map(ProductDTO::new).collect(Collectors.toSet());
    }

    public static Page<ProductDTO> of(Page<Product> source){
        return source.map(ProductDTO::new);
    }

}
