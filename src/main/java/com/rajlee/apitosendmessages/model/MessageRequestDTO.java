package com.rajlee.apitosendmessages.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequestDTO {
    private List<User> users;
    private String messageBody;
}