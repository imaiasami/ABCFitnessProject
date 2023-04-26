package com.project.file.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.project.file.model.entity.exercise.Equipment;
import com.project.file.model.entity.exercise.Exercise;
import com.project.file.model.entity.member.Bookmark;
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
	
	// 즐겨찾기
		int insertBookmark(Bookmark bookmark);

		Bookmark getBookmark(Bookmark bookmark);

		int deleteBookmark(Bookmark bookmark);

		List<Exercise> getBookmarkList(long mem_no);
		
		
		//운동기구
		
				int insertEquipment(Equipment equipment);
				
				int deleteEquipment(Equipment equipment);
				
				Equipment getEquipment(Equipment equipment);
				
				List<String> getEquipmentList (long mem_no);
				
			

	
}
