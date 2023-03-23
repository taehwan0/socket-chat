package com.example.socketchat.application;

import com.example.socketchat.domain.Member;
import com.example.socketchat.domain.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public List<Member> getMembers() {
        return memberRepository.findAll();
    }

    public Member createMember(String name) {
        Member member = Member.create(name);

        memberRepository.save(member);

        return member;
    }

    public Member updateMember(String id, String newName) {
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("NotFoundMember"));

        member.updateName(newName);
        memberRepository.save(member);

        return member;
    }

    public void deleteMember(String id) {
        memberRepository.delete(id);
    }
}
