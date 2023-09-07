package com.example.demo.domain.member;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MemberRepository {

    private static final Map<String,Member> store = new HashMap<>();

    public Member save(Member member){
        store.put(member.getId(),member);
        return member;
    }

    public Member findById(String memberId){
        return store.get(memberId);
    }

    public void deleteMember(String memberId){
        store.remove(memberId);
    }

    public void clearStore() {
        store.clear();
    }
}
