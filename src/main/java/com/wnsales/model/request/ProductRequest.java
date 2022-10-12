package com.wnsales.model.request;

import com.wnsales.model.Product;
import com.wnsales.model.dto.AccountDTO;
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
public class ProductRequest {

    private Long id;

    @NotNull
    private Integer amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(id, ((ProductRequest) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
