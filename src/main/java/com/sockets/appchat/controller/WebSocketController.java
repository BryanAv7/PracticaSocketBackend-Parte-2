package com.sockets.appchat.controller;

import com.sockets.appchat.dto.ChatMessage;
import com.sockets.appchat.service.ChatMessageService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {   // Manejar los mensajes entrantes de WebSocket y transmitirlos a los clientes conectados.


    private final ChatMessageService chatMessageService;

    public WebSocketController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @MessageMapping("/chat/{roomId}")
    //Decimos que cuando llegue el mensaje se redireccione al
    //topic
    @SendTo("/topic/{roomId}") //Es el canal del envio de los mensajes.
    public ChatMessage chat(@DestinationVariable String roomId, ChatMessage message) {  // Recibe un mensaje (ChatMessage) enviado al servidor desde un cliente.
        System.out.println("Mensaje recibido: " + message.getMessage());
        // Guardar mensaje en la cola si es necesario
        chatMessageService.addMessage(roomId, message);

        return message;
    }

    @MessageMapping("/chat/reconnect/{roomId}")
    public void reconnect(@DestinationVariable String roomId) {
        // Envía los mensajes pendientes al cliente reconectado.
        System.out.println("Reconectando a la sala: " + roomId);
        chatMessageService.sendPendingMessages(roomId);
    }

}
