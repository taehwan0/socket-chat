package com.example.socketchat.domain.repository;

import com.example.socketchat.domain.ChatRoom;
import org.springframework.stereotype.Repository;

@Repository
public class ChatRoomRepository extends BasicRepository<ChatRoom> {
    @Override
    public void save(ChatRoom chatRoom) {
        items.put(chatRoom.getId(), chatRoom);
    }
}
