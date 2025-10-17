package com.tvseries.TvSeriesManagementSystemBackend.service.Impl;

import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SmsService {

    private final String ACCOUNT_SID = "";
    private final String AUTH_TOKEN = "";

    public void sendSms(String to, String body) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(new PhoneNumber(to),new PhoneNumber("0743694971"),body).create();

        log.info("Sms sent SID {}", message.getSid());
    }
}
