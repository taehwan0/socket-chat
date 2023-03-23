package com.example.socketchat.domain;

import java.util.UUID;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

@Getter
public class Member {

    private final String id;
    private String name;
    private WebSocketSession session;

    private Member(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public static Member create(String name) {
        return new Member(name);
    }

    public Member updateName(String name) {
        this.name = name;
        return this;
    }
}
