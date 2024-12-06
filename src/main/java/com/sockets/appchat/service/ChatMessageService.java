package com.sockets.appchat.service;

import com.sockets.appchat.dto.ChatMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatMessageService {

    private final ConcurrentHashMap<String, List<ChatMessage>> pendingMessages = new ConcurrentHashMap<>();
    private final SimpMessagingTemplate messagingTemplate;

    public ChatMessageService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void addMessage(String roomId, ChatMessage message) {
        // Guarda el mensaje en la lista de mensajes pendientes.
        pendingMessages.computeIfAbsent(roomId, k -> new ArrayList<>()).add(message);
    }

    public void sendPendingMessages(String roomId) {
        // Envía los mensajes pendientes al cliente cuando se reconecta.
        List<ChatMessage> messages = pendingMessages.remove(roomId);
        if (messages != null) {
            messages.forEach(message ->
                    messagingTemplate.convertAndSend("/topic/" + roomId, message)
            );
        }
    }
}
