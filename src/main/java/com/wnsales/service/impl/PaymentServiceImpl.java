package com.wnsales.service.impl;

import com.wnsales.model.Account;
import com.wnsales.model.Product;
import com.wnsales.model.dto.LinkDTO;
import com.wnsales.model.request.*;
import com.wnsales.repository.AccountRepository;
import com.wnsales.repository.ProductRepository;
import com.wnsales.service.PaymentService;
import com.wnsales.util.MemoryStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;

    private final MemoryStorage memoryStorage;

    @Override
    public LinkDTO create(Long accountId, PaymentRequest paymentRequest) {

        if (accountRepository.existsById(accountId) == false){
            throw new RuntimeException("Account invalid");
        }

        for (ProductRequest productRequest : paymentRequest.getProducts()) {
            Optional<Product> product = productRepository.findById(productRequest.getId());

            if (product.isEmpty()){
                throw new RuntimeException("Product invalid, id: " + productRequest.getId());
            }

            if (product.get().getAccount().getId().equals(accountId) == false){
                throw new RuntimeException("Account is not owner for this product, id: " + productRequest.getId());
            }
        }

        paymentRequest.setLinkId(UUID.randomUUID());
        paymentRequest.setAccountId(accountId);

        //Simple Memory storage :)
        memoryStorage.add(paymentRequest);

        return new LinkDTO("http://localhost:4200/payment/"+paymentRequest.getLinkId());
    }

    public PaymentResponse payment(PaymentRequest paymentRequest){

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setAccountId(paymentRequest.getAccountId());
        BigDecimal totalPayment = BigDecimal.ZERO;

        for (ProductRequest productRequest : paymentRequest.getProducts()) {
            Optional<Product> optionalProduct = productRepository.findById(productRequest.getId());
            if (optionalProduct.isEmpty()){
                throw new RuntimeException("Invalid product, id" + productRequest.getId());
            }
            Product product = optionalProduct.get();
            totalPayment = totalPayment.add(product.getPrice().multiply(new BigDecimal(productRequest.getAmount())));
            paymentResponse.setIban(product.getAccount().getIban());
            paymentResponse.setAccountName(product.getAccount().getAccountName());
            paymentResponse.getProducts().add(new ProductResponse(product,productRequest.getAmount()));
        }
        paymentResponse.setTotalPayment(totalPayment);

        return paymentResponse;
    }

    @Override
    public ConfirmResponse confirm(PaymentRequest paymentRequest) {
        memoryStorage.remove(paymentRequest);
        Optional<Account> optionalAccount = accountRepository.findById(paymentRequest.getAccountId());

        if (optionalAccount.isEmpty()){
            throw new RuntimeException("Account not found");
        }

        return new ConfirmResponse("Payment executed to IBAN: "+ optionalAccount.get().getIban());
    }
}
