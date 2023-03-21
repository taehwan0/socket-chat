package com.example.socketchat.domain;

import com.example.socketchat.presentation.dto.MessageRequestDto;
import lombok.Getter;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Getter
public class ChatMessage {

    public enum MessageType {
        NOTIFY,
        MESSAGE;
    }

    private final String sender;
    private final String chatRoomId;
    private final String content;
    private final MessageType messageType;

    private ChatMessage(String sender, String chatRoomId, String content, MessageType messageType) {
        this.sender = sender;
        this.chatRoomId = chatRoomId;
        this.content = content;
        this.messageType = messageType;
    }

    public static ChatMessage create(String sender, String chatRoomId, String content, MessageType messageType) {
        return new ChatMessage(sender, chatRoomId, content, messageType);
    }
}
