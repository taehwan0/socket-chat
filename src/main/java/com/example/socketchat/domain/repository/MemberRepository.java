package com.example.socketchat.domain.repository;

import com.example.socketchat.domain.Member;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository extends BasicRepository<Member> {
    @Override
    public void save(Member member) {
        String sessionId = member.getSession().getId();
        items.put(sessionId, member);
    }
}
