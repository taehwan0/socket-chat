package com.example.socketchat.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

@Getter
public class ChatRoom {

    private final String id;
    private final String name;
    private final Set<WebSocketSession> sessions;

    private ChatRoom(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.sessions = new HashSet<>();
    }

    public static ChatRoom create(String name) {
        return new ChatRoom(name);
    }

    public ChatRoom join(WebSocketSession session) {
        this.sessions.add(session);
        return this;
    }

    public ChatRoom out(WebSocketSession session) {
        this.sessions.remove(session);
        return this;
    }
}
