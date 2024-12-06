package com.sockets.appchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor    // Genera automáticamente un constructor que acepta un argumento para cada campo de la clase.
public class ChatMessage {
// Modelo del SMS
    private String message;
    private String user;

}

