package com.example.socketchat.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;

@Getter
public class ChatRoom {

    private final String id;
    private String name;
    private final Set<Member> members;

    private ChatRoom(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.members = new HashSet<>();
    }

    public static ChatRoom create(String name) {
        return new ChatRoom(name);
    }

    public ChatRoom join(Member member) {
        this.members.add(member);
        return this;
    }

    public ChatRoom out(Member member) {
        this.members.remove(member);
        return this;
    }

    public ChatRoom updateName(String newName) {
        this.name = newName;
        return this;
    }
}
