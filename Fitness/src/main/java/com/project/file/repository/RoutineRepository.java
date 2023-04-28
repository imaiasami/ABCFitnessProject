package com.project.file.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.project.file.model.dto.routine.GenerateRoutineForm;
import com.project.file.model.entity.routine.RoutineDefault;
import com.project.file.model.entity.routine.RoutineGenerated;

@Mapper
public interface RoutineRepository {
	// 총 기본 루틴 개수(난이도, 시간, 이름, 언어 순)
	int getDefaultTotal(@Param("diffs") List<Integer> difficulties, @Param("runtimes") List<String> runtimes, @Param("search") String search, @Param("lang") String language);
	
	// 기본 루틴 가져오기(난이도, 시간, 이름, 언어 순)
	List<RoutineDefault> getRoutineDefaults(@Param("diffs") List<Integer> difficulties, @Param("runtimes") List<String> runtimes, @Param("search") String search, @Param("lang") String language, RowBounds rowBounds);
	
	// 루틴 번호로 기본 루틴 가져오기
	RoutineDefault getRoutineDefaultByNo(@Param("rout_no") long rout_d_no, @Param("lang") String language);
	
	
	// 루틴 생성
	int createRoutineGenerated(GenerateRoutineForm rout_g);
	
	// 루틴 번호로 생성 루틴 가져오기
	RoutineGenerated getRoutineGeneratedByRoutNo(long rout_no);
	
	// 회원 번호로 생성 루틴 가져오기
	List<RoutineGenerated> getRoutineGeneratedByMemNo(long mem_no);
}
