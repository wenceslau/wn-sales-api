package com.wnsales.controller;

import com.wnsales.model.dto.LinkDTO;
import com.wnsales.model.request.ConfirmResponse;
import com.wnsales.model.request.PaymentRequest;
import com.wnsales.model.request.PaymentResponse;
import com.wnsales.model.request.ProductRequest;
import com.wnsales.repository.AccountRepository;
import com.wnsales.repository.ProductRepository;
import com.wnsales.service.PaymentService;
import com.wnsales.util.MemoryStorage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/payment")
@Tag(name = "Account API - Create, buy and confirm payment")
public class PaymentController {

    private final PaymentService paymentService;

    private final ProductRepository productRepository;

    private final AccountRepository accountRepository;

    private final MemoryStorage memoryStorage;

    @GetMapping("/template")
    @Operation(summary = "Template JSON for payment create")
    public PaymentRequest template(){
        PaymentRequest paymentDTO = new PaymentRequest();
        paymentDTO.getProducts().add(new ProductRequest());

        return paymentDTO;
    }

    @PostMapping("/create/{accountId}")
    @Operation(summary = "Create a payment")
    public ResponseEntity<LinkDTO> create(@PathVariable Long accountId, @RequestBody PaymentRequest paymentRequest) {
        LinkDTO linkDTO = paymentService.create(accountId, paymentRequest);

        return new ResponseEntity<>(linkDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Retrieve payment details for pay")
    public PaymentResponse payment(@PathVariable UUID uuid){

        Optional<PaymentRequest> paymentRequest = memoryStorage.find(uuid);

        if (paymentRequest.isEmpty()){
            throw new RuntimeException("Invalid Link");
        }

        return paymentService.payment(paymentRequest.get());
    }

    @PostMapping("/{uuid}")
    @Operation(summary = "Confirm a payment")
    public ResponseEntity<ConfirmResponse> confirm(@PathVariable UUID uuid){
        Optional<PaymentRequest> paymentRequest = memoryStorage.find(uuid);

        if (paymentRequest.isEmpty()){
            throw new RuntimeException("Invalid Link");
        }

        return new ResponseEntity<>(paymentService.confirm(paymentRequest.get()), HttpStatus.OK);
    }

}
