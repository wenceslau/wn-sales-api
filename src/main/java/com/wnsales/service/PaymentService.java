package com.wnsales.service;

import com.wnsales.model.dto.LinkDTO;
import com.wnsales.model.request.ConfirmResponse;
import com.wnsales.model.request.PaymentRequest;
import com.wnsales.model.request.PaymentResponse;

public interface PaymentService {

    LinkDTO create(Long accoundId, PaymentRequest paymentRequest);

    PaymentResponse payment(PaymentRequest paymentRequest);

    ConfirmResponse confirm(PaymentRequest paymentRequest);

}
