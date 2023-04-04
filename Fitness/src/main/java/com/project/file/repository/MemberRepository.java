package com.project.file.repository;

import org.apache.ibatis.annotations.Mapper;

import com.project.file.model.entity.member.Member;



@Mapper
public interface MemberRepository {
	int joinMember(Member member);

	Member findMemberById(String mail);

}
