package com.rajlee.apitosendmessages.service;

import com.rajlee.apitosendmessages.model.MessageRequestDTO;

public interface WhatsAppService {
    void sendMessagesToUsers(MessageRequestDTO messageRequestDTO);
}