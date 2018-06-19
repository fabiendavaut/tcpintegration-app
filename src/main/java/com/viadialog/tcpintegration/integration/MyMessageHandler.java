package com.viadialog.tcpintegration.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Component
public class MyMessageHandler implements MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(MyMessageHandler.class);


    @Override
    public void handleMessage(Message<?> message) throws MessagingException {

        logger.debug(message.toString());

    }

}
