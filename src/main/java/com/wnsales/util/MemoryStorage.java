package com.wnsales.util;

import com.wnsales.model.request.PaymentRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class MemoryStorage {
    private static Set<PaymentRequest> links = new HashSet<>();

    public Optional<PaymentRequest> find(UUID uuid){
        Optional<PaymentRequest> paymentRequest = links.stream()
                .filter(x-> x.getLinkId().equals(uuid))
                .findFirst();
        return paymentRequest;
    }

    public void add(PaymentRequest paymentRequest){
        links.add(paymentRequest);
    }

    public void remove(PaymentRequest paymentRequest) {
        links.remove(paymentRequest);
    }
}
