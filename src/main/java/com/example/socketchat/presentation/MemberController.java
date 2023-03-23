package com.example.socketchat.presentation;

import com.example.socketchat.application.MemberService;
import com.example.socketchat.domain.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("")
    public ResponseEntity<List<Member>> getMembers() {
        return ResponseEntity.ok(memberService.getMembers());
    }

    @PostMapping("")
    public ResponseEntity<Member> createMember(@RequestParam("name") String name) {
        return ResponseEntity.ok(memberService.createMember(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(
        @PathVariable("id") String id,
        @RequestParam("name") String name) {
        return ResponseEntity.ok(memberService.updateMember(id, name));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable("id") String id) {
        memberService.deleteMember(id);
        return ResponseEntity.ok().build();
    }


}
