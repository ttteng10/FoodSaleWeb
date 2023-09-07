package com.example.demo.repository.mybatis;

import com.example.demo.domain.member.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {

    void save(Member member);

    Optional<Member> findById(@Param("id") String memberId);

    List<Member> findAll();

    void deleteMember();
}
