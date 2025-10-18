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
    private final String AUTH_TOKEN = "b49a958bfca1a4d45b524ffa27fe348c";
    private final String ACCOUNT_PHONE_NUMBER = "+94 74 369 4971";

    public void sendSms(String to, String body) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(new PhoneNumber(to),new PhoneNumber(ACCOUNT_PHONE_NUMBER),body).create();

        log.info("Sms sent SID {}", message.getSid());
    }
}
