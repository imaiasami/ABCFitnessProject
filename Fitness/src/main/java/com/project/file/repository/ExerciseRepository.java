package com.project.file.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.project.file.model.dto.exercise.FullExerciseForm;
import com.project.file.model.entity.exercise.Exercise;

@Mapper
public interface ExerciseRepository {
	// 전체 운동 목록 가져오기
	List<Exercise> getExercisesKo(RowBounds rowBounds);
	List<Exercise> getExercisesJp();
	List<Exercise> getExercisesEn();
	
	// 개별 운동 정보 가져오기
	Exercise getExerciseKo(long ex_no);
	Exercise getExerciseJp(long ex_no);
	Exercise getExerciseEn(long ex_no);
	
	// 운동 CRUD
	int createExercise(FullExerciseForm exercise);
	List<FullExerciseForm> getAllExercises();
	FullExerciseForm getAllExercise(long ex_no);
	int updateExercise(FullExerciseForm exercise);
	int deleteExercise(long ex_no);
	
	// 총 운동 개수
	int getTotal();
}
