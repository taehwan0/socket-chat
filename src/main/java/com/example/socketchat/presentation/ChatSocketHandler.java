package com.example.socketchat.presentation;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
public class ChatSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> members = new HashSet<>();

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws IOException {
        log.info("{}: {}", session.getId(), message.getPayload());
        for(WebSocketSession member: members) {
            member.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("connection established: {}", session.getId());
        members.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("connection closed: {}", session.getId());
        members.remove(session);
    }
}
