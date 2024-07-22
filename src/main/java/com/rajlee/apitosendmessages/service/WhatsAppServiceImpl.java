package com.rajlee.apitosendmessages.service;

import com.rajlee.apitosendmessages.model.MessageRequestDTO;
import com.rajlee.apitosendmessages.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class WhatsAppServiceImpl implements WhatsAppService {

    @Value("${whatsapp.api.url}")
    private String apiUrl;

    @Value("${whatsapp.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void sendMessagesToUsers(MessageRequestDTO messageRequestDTO) {
        List<User> users = messageRequestDTO.getUsers();
        String messageBody = messageRequestDTO.getMessageBody();

        for (User user : users) {
            String phoneNumber = user.getPhoneNumber();
            String name = user.getName();
            String formattedMessage = "Hello " + name + ", " + messageBody;
            String jsonPayload = createJsonPayload(phoneNumber, formattedMessage);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);

            try {
                ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

                if (response.getStatusCode() == HttpStatus.OK) {
                    System.out.println("Message sent successfully to " + phoneNumber);
                } else {
                    System.err.println("Failed to send message to " + phoneNumber + ". Status code: " + response.getStatusCode());
                    System.err.println("Response body: " + response.getBody());
                }
            } catch (HttpClientErrorException.Unauthorized ex) {
                System.err.println("Unauthorized: Check your API key or permissions for WhatsApp API");
                ex.printStackTrace();

            } catch (Exception e) {
                System.err.println("Failed to send message to " + phoneNumber + ": " + e.getMessage());
                e.printStackTrace();

            }
        }
    }

    private String createJsonPayload(String phoneNumber, String messageBody) {
        return "{\"recipient\":{\"phone_number\":\"whatsapp:" + phoneNumber + "\"},\"message\":{\"text\":{\"body\":\"" + messageBody + "\"}}}";
    }
}
