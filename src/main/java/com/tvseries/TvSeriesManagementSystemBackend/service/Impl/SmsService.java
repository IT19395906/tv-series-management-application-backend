package com.tvseries.TvSeriesManagementSystemBackend.service.Impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SmsService {

    private final String ACCOUNT_SID = "";

    @Value("${twilio.authToken}")
    private String AUTH_TOKEN;

    @Value("${twilio.accountPhoneNumber}")
    private String ACCOUNT_PHONE_NUMBER;

    public void sendSms(String to, String body) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(new PhoneNumber(to),new PhoneNumber(ACCOUNT_PHONE_NUMBER),body).create();

        log.info("Sms sent SID {}", message.getSid());
    }
}
