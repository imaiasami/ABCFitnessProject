package com.project.file.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.project.file.model.entity.member.Member;

@Mapper
public interface MemberRepository {
	int joinMember(Member member);

	int deleteMember(Member member);

	Member findMemberByMail(String mail);

	Member findMemberById(String mem_id);

	List<Member> findAllMembers();

	void updateMember(Member member);

	int countByEmail(String email);
	
	int countByMemberId(String mem_id);
}
