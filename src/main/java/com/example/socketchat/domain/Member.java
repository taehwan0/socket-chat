package com.example.socketchat.domain;

import java.util.UUID;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

@Getter
public class Member {

    private final String id;
    private final String name;
    private final WebSocketSession session;

    private Member(String name, WebSocketSession session) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.session = session;
    }

    public static Member create(String name, WebSocketSession session) {
        return new Member(name, session);
    }
}
