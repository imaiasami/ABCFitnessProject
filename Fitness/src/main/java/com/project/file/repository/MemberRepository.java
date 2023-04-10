package com.project.file.repository;

import org.apache.ibatis.annotations.Mapper;

import com.project.file.model.entity.member.Member;



@Mapper
public interface MemberRepository {
	int joinMember(Member member);

	Member findMemberByMail(String mail);
	Member findMemberById(String mem_id);

}
