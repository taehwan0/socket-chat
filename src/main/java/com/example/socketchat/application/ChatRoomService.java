package com.example.socketchat.application;

import com.example.socketchat.domain.ChatRoom;
import com.example.socketchat.domain.Member;
import com.example.socketchat.domain.repository.ChatRoomRepository;
import com.example.socketchat.domain.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    public List<ChatRoom> getChatRooms() {
        return chatRoomRepository.findAll();
    }

    public ChatRoom getChatRoom(String chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
            .orElseThrow(() -> new RuntimeException("NotFoundChatRoom"));
    }

    public ChatRoom createChatRoom(String name) {
        ChatRoom chatRoom = ChatRoom.create(name);

        chatRoomRepository.save(chatRoom);

        return chatRoom;
    }

    public ChatRoom updateChatRoom(String chatRoomId, String newName) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
            .orElseThrow(() -> new RuntimeException("NotFoundChatRoom"));

        return chatRoom.updateName(newName);
    }

    public ChatRoom join(String chatRoomId, String memberId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
            .orElseThrow(() -> new RuntimeException("NotFoundChatRoom"));

        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new RuntimeException("NotFoundMember"));

        return chatRoom.join(member);
    }

    public ChatRoom out(String chatRoomId, String memberId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
            .orElseThrow(() -> new RuntimeException("NotFoundChatRoom"));

        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new RuntimeException("NotFoundMember"));

        return chatRoom.out(member);
    }

    public void deleteChatRoom(String chatRoomId) {
        chatRoomRepository.delete(chatRoomId);
    }
}
