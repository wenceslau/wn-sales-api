package com.wnsales.controller;

import com.wnsales.model.dto.LinkDTO;
import com.wnsales.model.request.*;
import com.wnsales.repository.AccountRepository;
import com.wnsales.repository.ProductRepository;
import com.wnsales.service.PaymentService;
import com.wnsales.util.MemoryStorage;
import com.wnsales.util.Utils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@RunWith(SpringRunner.class)
@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private MemoryStorage memoryStorage;


    @Test
    void givenPayment_whenCreate_theReturnStatusOk() throws Exception {
        ProductRequest prodRq = new ProductRequest();
        prodRq.setId(1L);
        prodRq.setAmount(2);
        PaymentRequest pymReq = new PaymentRequest();
        pymReq.getProducts().add(prodRq);

        LinkDTO linkDTO = new LinkDTO();
        linkDTO.setLink("http://localhost:8083/payment/"+ UUID.randomUUID());

        BDDMockito.given(paymentService.create(1L, pymReq)).willReturn(linkDTO);

        mvc.perform(MockMvcRequestBuilders.post("/payment/create/1")
                        .content(Utils.objectToJson(pymReq))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void givenPayment_whenPayment_theReturnStatusOk() throws Exception {

        ProductRequest prodRq = new ProductRequest();
        prodRq.setId(1L);
        prodRq.setAmount(2);
        PaymentRequest pymReq = new PaymentRequest();
        pymReq.getProducts().add(prodRq);

        PaymentResponse payResp = new PaymentResponse();
        payResp.setIban("PT50003800015623391998072");
        payResp.setAccountId(1L);
        ProductResponse presp = new ProductResponse();
        presp.setId(prodRq.getId());
        presp.setAmount(prodRq.getAmount());
        payResp.getProducts().add(presp);
        payResp.setTotalPayment(BigDecimal.TEN);

        PaymentRequest payReq = new PaymentRequest();
        payReq.getProducts().add(prodRq);

        UUID uuid = UUID.randomUUID();
        Optional<PaymentRequest> optionalPaymentRequest = Optional.of(payReq);

        BDDMockito.given(paymentService.payment(pymReq)).willReturn(payResp);
        BDDMockito.given(memoryStorage.find(uuid)).willReturn(optionalPaymentRequest);

        mvc.perform(MockMvcRequestBuilders.get("/payment/"+uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPayment").value(BigDecimal.TEN));
    }

    @Test
    void givenPayment_whenPaymentUUidWrond_theReturnStatusBad() throws Exception {

        ProductRequest prodRq = new ProductRequest();
        prodRq.setId(1L);
        prodRq.setAmount(2);
        PaymentRequest pymReq = new PaymentRequest();
        pymReq.getProducts().add(prodRq);

        PaymentResponse payResp = new PaymentResponse();
        payResp.setIban("PT50003800015623391998072");
        payResp.setAccountId(1L);
        ProductResponse presp = new ProductResponse();
        presp.setId(prodRq.getId());
        presp.setAmount(prodRq.getAmount());
        payResp.getProducts().add(presp);
        payResp.setTotalPayment(BigDecimal.TEN);

        PaymentRequest payReq = new PaymentRequest();
        payReq.getProducts().add(prodRq);

        UUID uuid = UUID.randomUUID();

        BDDMockito.given(paymentService.payment(pymReq)).willReturn(payResp);

        mvc.perform(MockMvcRequestBuilders.get("/payment/"+uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void givenPayment_whenConfirm_theReturnStatusOk() throws Exception {

        UUID uuid = UUID.randomUUID();
        ConfirmResponse confirmResponse = new ConfirmResponse();
        confirmResponse.setMessage("Payment executed to IBAN: PT50001800035623391902079");

        Optional<PaymentRequest> optionalPaymentRequest = Optional.of(Mockito.mock(PaymentRequest.class));

        BDDMockito.given(paymentService.confirm(Mockito.mock(PaymentRequest.class))).willReturn(confirmResponse);
        BDDMockito.given(memoryStorage.find(uuid)).willReturn(optionalPaymentRequest);

        mvc.perform(MockMvcRequestBuilders.post("/payment/"+uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}