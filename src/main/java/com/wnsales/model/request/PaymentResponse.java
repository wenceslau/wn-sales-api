package com.wnsales.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wnsales.model.dto.ProductDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PaymentResponse {

    private Long accountId;
    private String accountName;
    private String iban;
    private List<ProductResponse> products = new ArrayList<>();
    private BigDecimal totalPayment;

}
