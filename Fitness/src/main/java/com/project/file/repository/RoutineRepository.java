package com.project.file.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.project.file.model.entity.routine.RoutineDefault;

@Mapper
public interface RoutineRepository {
	// 전체 기본 루틴 가져오기
	List<RoutineDefault> getRoutineDefaultsKo(RowBounds rowBounds);
	List<RoutineDefault> getRoutineDefaultsJp();
	List<RoutineDefault> getRoutineDefaultsEn();
	
	// 개별 기본 루틴 가져오기
	RoutineDefault getRoutineDefaultKo(long rout_no);
	RoutineDefault getRoutineDefaultJp(long rout_no);
	RoutineDefault getRoutineDefaultEn(long rout_no);
	
	// 총 루틴 개수
	int getTotal();
}
