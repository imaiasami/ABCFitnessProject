package com.project.file.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.project.file.model.dto.exercise.FullExerciseForm;
import com.project.file.model.entity.exercise.Exercise;

@Mapper
public interface ExerciseRepository {
	// 운동 CRUD
	int createExercise(FullExerciseForm exercise);
	List<FullExerciseForm> getAllExercises();
	FullExerciseForm getAllExercise(long ex_no);
	int updateExercise(FullExerciseForm exercise);
	int deleteExercise(long ex_no);
	
	// 총 운동 개수(근육, 난이도, 장비, 이름, 언어 순)
	int getTotal(@Param("muscs") List<String> muscles, @Param("diffs") List<Integer> difficulties, @Param("equips") List<String> equipments, @Param("search") String search, @Param("lang") String language);
	
	// 운동 정보 가져오기(근육, 난이도, 장비, 이름, 언어 순)
	List<Exercise> getExercises(@Param("muscs") List<String> muscles, @Param("diffs") List<Integer> difficulties, @Param("equips") List<String> equipments, @Param("search") String search, @Param("lang") String language, RowBounds rowBounds);
	
	// 근육별 운동 가져오기(언어별 정보, 페이징 생략)
	List<Exercise> getExercisesByMuscle(@Param("musc") String muscle, @Param("diffs") List<Integer> difficulties, @Param("equips") List<String> equipments, @Param("lang") String language);
	
	// 운동 번호로 운동 정보 가져오기
	Exercise getExerciseByNo(@Param("ex_no") long ex_no, @Param("lang") String language);
	
	// 장비 이름 가져오기(EQUIPMENT 테이블)
	List<String> getEquipNames(@Param("equips") List<String> equip_ids, @Param("lang") String language);
}
