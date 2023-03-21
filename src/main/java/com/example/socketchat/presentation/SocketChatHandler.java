package com.example.socketchat.presentation;

import com.example.socketchat.application.SocketChatService;
import com.example.socketchat.presentation.dto.MessageRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@RequiredArgsConstructor
@Component
public class SocketChatHandler extends TextWebSocketHandler {

    private final SocketChatService socketChatService;
    private final ObjectMapper objectMapper;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();
        MessageRequestDto messageRequestDto = objectMapper.readValue(payload, MessageRequestDto.class);

        socketChatService.postMessage(session, messageRequestDto);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        socketChatService.joinRoom(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws IOException {
        socketChatService.outRoom(session);
    }
}
