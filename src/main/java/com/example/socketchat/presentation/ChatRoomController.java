package com.example.socketchat.presentation;

import com.example.socketchat.application.ChatRoomService;
import com.example.socketchat.domain.ChatRoom;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/chatroom")
@RestController
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("")
    public ResponseEntity<List<ChatRoom>> getChatRooms() {
        return ResponseEntity.ok(chatRoomService.getChatRooms());
    }

    @GetMapping("/{chatRoomId}")
    public ResponseEntity<ChatRoom> getChatRoom(@PathVariable String chatRoomId) {
        return ResponseEntity.ok(chatRoomService.getChatRoom(chatRoomId));
    }

    @PostMapping("")
    public ResponseEntity<ChatRoom> createChatRoom(@RequestParam("name") String name) {
        return ResponseEntity.ok(chatRoomService.createChatRoom(name));
    }

    @PutMapping("/{chatRoomId}/join")
    public ResponseEntity<ChatRoom> joinChatRoom(
        @PathVariable String chatRoomId,
        @RequestParam("memberId") String memberId) {
        return ResponseEntity.ok(chatRoomService.join(chatRoomId, memberId));
    }

    @PutMapping("/{chatRoomId}/out")
    public ResponseEntity<ChatRoom> outChatRoom(
        @PathVariable String chatRoomId,
        @RequestParam("memberId") String memberId) {
        return ResponseEntity.ok(chatRoomService.out(chatRoomId, memberId));
    }

    @PutMapping("/{chatRoomId}")
    public ResponseEntity<ChatRoom> updateChatRoom(
        @PathVariable String chatRoomId,
        @RequestParam("name") String newName) {
        return ResponseEntity.ok(chatRoomService.updateChatRoom(chatRoomId, newName));
    }

    @DeleteMapping("/{chatRoomId}")
    public ResponseEntity<Void> deleteChatRoom(@PathVariable String chatRoomId) {
        chatRoomService.deleteChatRoom(chatRoomId);

        return ResponseEntity.ok().build();
    }

}
