package com.wnsales.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PaymentRequest {

    @JsonIgnore
    private UUID linkId;
    @JsonIgnore
    private Long accountId;

    private List<ProductRequest> products = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentRequest that = (PaymentRequest) o;

        return Objects.equals(linkId, that.linkId);
    }

    @Override
    public int hashCode() {
        return linkId != null ? linkId.hashCode() : 0;
    }
}
