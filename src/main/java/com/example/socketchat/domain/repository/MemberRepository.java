package com.example.socketchat.domain.repository;

import com.example.socketchat.domain.Member;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository extends BasicRepository<Member> {

    @Override
    public void save(Member member) {
        String memberId = member.getId();
        if (items.containsKey(memberId)) {
            items.replace(memberId, member);
        } else {
            items.put(memberId, member);
        }
    }
}
