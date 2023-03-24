package com.example.socketchat.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

@Getter
public class Member {

    private final String id;
    private String name;
    private Set<WebSocketSession> sessions;

    private Member(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.sessions = new HashSet<>();
    }

    public static Member create(String name) {
        return new Member(name);
    }

    public Member updateName(String name) {
        this.name = name;
        return this;
    }

    public Member addSession(WebSocketSession session) {
        this.sessions.add(session);
        return this;
    }

    public Member removeSession(WebSocketSession session) {
        this.sessions.remove(session);
        return this;
    }
}
