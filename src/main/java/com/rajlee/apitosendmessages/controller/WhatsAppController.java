package com.rajlee.apitosendmessages.controller;

import com.rajlee.apitosendmessages.model.MessageRequestDTO;
import com.rajlee.apitosendmessages.service.WhatsAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WhatsAppController {

    @Autowired
    private WhatsAppService whatsAppService;

    @PostMapping("/api/whatsapp/send-messages")
    public ResponseEntity<String> sendMessages(@RequestBody MessageRequestDTO messageRequestDTO) {
        try {
            whatsAppService.sendMessagesToUsers(messageRequestDTO);
            return ResponseEntity.ok("Messages sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send messages: " + e.getMessage());
        }
    }
}
