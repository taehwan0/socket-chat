package com.example.socketchat.application;

import com.example.socketchat.domain.ChatMessage;
import com.example.socketchat.domain.ChatMessage.MessageType;
import com.example.socketchat.domain.ChatRoom;
import com.example.socketchat.domain.Member;
import com.example.socketchat.domain.repository.ChatRoomRepository;
import com.example.socketchat.domain.repository.MemberRepository;
import com.example.socketchat.presentation.dto.MessageRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@RequiredArgsConstructor
@Service
public class SocketChatService {

    private final ObjectMapper objectMapper;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    public void postMessage(WebSocketSession session, MessageRequestDto messageRequestDto) throws IOException {
        // 메세지 보내는 로직
        // 아직 메세지 저장은 안해요~
        String roomId = messageRequestDto.getRoomId();
        String content = messageRequestDto.getContent();

        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
            .orElseThrow(() -> new RuntimeException("NotFoundChatRoom"));

        String memberId = session.getHandshakeHeaders().getFirst("member_id");
        Member sender = memberRepository.findById(memberId)
            .orElseThrow(() -> new RuntimeException("NotFoundMember"));

        ChatMessage chatMessage = ChatMessage.create(sender.getName(), chatRoom.getName(), content, MessageType.MESSAGE);
        String messageAsString = objectMapper.writeValueAsString(chatMessage);
        TextMessage message = new TextMessage(messageAsString);

        chatRoom.getMembers().forEach(member -> {
            member.getSessions().forEach(s -> {
                try {
                    s.sendMessage(message);
                } catch (IOException e) {
                    log.error("message error: {}", s);
                }
            });
        });


    }

    public void joinRoom(WebSocketSession session) {
        // 해당 유저에 session 추가하는 로직
        Member member = validateSocketSession(session);

        member.addSession(session);
    }

    public void outRoom(WebSocketSession session) {
        // 해당 유저에 session 제거하는 로직
        Member member = validateSocketSession(session);

        member.removeSession(session);
    }

    private Member validateSocketSession(WebSocketSession session) {
        HttpHeaders httpHeaders = session.getHandshakeHeaders();
        String memberId = httpHeaders.getFirst("member_id");

        if (memberId == null) {
            throw new RuntimeException("NeedMemberId");
        }

        return memberRepository.findById(memberId)
            .orElseThrow(() -> new RuntimeException("NotFoundMember"));
    }
}
