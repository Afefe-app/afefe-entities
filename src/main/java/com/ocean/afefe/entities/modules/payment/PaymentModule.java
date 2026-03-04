package com.ocean.afefe.entities.modules.payment;

import com.ocean.afefe.entities.modules.payment.service.ExchangeRateDomainServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(value = {
        ExchangeRateDomainServiceImpl.class
})
@Configuration
public class PaymentModule {
}
