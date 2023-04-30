package com.project.file.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.project.file.model.entity.exercise.Equipment;
import com.project.file.model.entity.exercise.Exercise;
import com.project.file.model.entity.member.Bookmark;
import com.project.file.model.entity.member.Member;
import com.project.file.model.entity.member.RoutineBookmark;
import com.project.file.model.entity.routine.Routine;

@Mapper
public interface MemberRepository {
	int joinMember(Member member);

	int deleteMember(Member member);

	Member findMemberByMail(String mail);

	Member findMemberById(String mem_id);

	List<Member> findAllMembers();

	int updateMember(Member member);
	
	int updateMemberConditions(Member member);
	
	int updateMemberLanguage(Member member);

	// 이메일 중복 확인
	int countByEmail(String email);

	// 사용자 이름 중복 확인
	int countByMemberId(String mem_id);

	// 운동 즐겨찾기
	int insertBookmark(Bookmark bookmark);

	Bookmark getBookmark(Bookmark bookmark);

	int deleteBookmark(Bookmark bookmark);

	List<Exercise> getBookmarkList(@Param("mem_no") long mem_no, @Param("lang") String language);

	// 루틴 즐겨찾기
	int insertRoutineBookmark(RoutineBookmark routineBookmark);

	RoutineBookmark getRoutineBookmark(RoutineBookmark routineBookmark);

	int deleteRoutineBookmark(RoutineBookmark routineBookmark);

	List<Routine> getRoutineBookmarkList(@Param("mem_no") long mem_no, @Param("lang") String language);

	// 운동기구
	int insertEquipment(Equipment equipment);

	Equipment getEquipment(Equipment equipment);

	List<String> getEquipmentList(long mem_no);
	
	int deleteEquipment(Equipment equipment);
	
	int deleteAllEquipments(long mem_no);

}
