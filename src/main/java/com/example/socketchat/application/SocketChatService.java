package com.example.socketchat.application;

import com.example.socketchat.domain.ChatMessage;
import com.example.socketchat.domain.ChatMessage.MessageType;
import com.example.socketchat.domain.ChatRoom;
import com.example.socketchat.domain.Member;
import com.example.socketchat.domain.repository.MemberRepository;
import com.example.socketchat.presentation.dto.MessageRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Random;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@RequiredArgsConstructor
@Service
public class SocketChatService {

    private static final String[] firstNames = {"빨간색", "주황색", "노란색", "초록색", "파란색", "남색", "보라색"};
    private static final String[] lastNames = {"강아지", "고앙이", "코끼리", "말", "하마", "쥐", "새"};

    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;

    private ChatRoom room;

    @PostConstruct
    public void init() {
        this.room = ChatRoom.create("room");
    }

    public void postMessage(WebSocketSession session, MessageRequestDto messageRequestDto) throws IOException {
        Member member = memberRepository.findById(session.getId())
            .orElseThrow(() -> new RuntimeException("NotFoundMember"));

        String content = messageRequestDto.getContent();
        ChatMessage message = ChatMessage.create(member.getName(), this.room.getId(), content, MessageType.MESSAGE);
        TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(message));

        for (WebSocketSession roomSession : this.room.getSessions()) {
            roomSession.sendMessage(textMessage);
        }
    }

    public void joinRoom(WebSocketSession session) throws IOException {
        ChatRoom room = this.room.join(session);

        String randomNickname = createRandomNickname();
        Member member = Member.create(randomNickname, session);

        memberRepository.save(member);

        log.info("JOIN room[{}]: {}[{}]", room.getName(), member.getName(), session.getId());

        String content = member.getName() + "님이 참여했습니다.";
        ChatMessage message = ChatMessage.create("NOTIFY", this.room.getId(), content, MessageType.NOTIFY);
        TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(message));

        for (WebSocketSession roomSession : this.room.getSessions()) {
            roomSession.sendMessage(textMessage);
        }
    }

    private String createRandomNickname() {
        Random random = new Random();
        int headIndex = random.nextInt(7);
        int tailIndex = random.nextInt(7);

        return firstNames[headIndex] + " " + lastNames[tailIndex];
    }

    public void outRoom(WebSocketSession session) throws IOException {
        ChatRoom room = this.room.out(session);
        Member member = memberRepository.delete(session.getId())
            .orElseThrow(() -> new RuntimeException("NotFoundMember"));

        log.info("OUT room[{}]: {}[{}]", room.getName(), member.getName(), session.getId());

        String content = member.getName() + "님이 나갔습니다.";
        ChatMessage message = ChatMessage.create("NOTIFY", this.room.getId(), content, MessageType.NOTIFY);
        TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(message));

        for (WebSocketSession roomSession : this.room.getSessions()) {
            roomSession.sendMessage(textMessage);
        }
    }
}
