package com.project.file.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.file.model.dto.exercise.FullExerciseForm;
import com.project.file.model.entity.exercise.Exercise;

@Mapper
public interface ExerciseRepository {
	List<Exercise> getExercisesKo();
	List<Exercise> getExercisesJp();
	List<Exercise> getExercisesEn();
	
	Exercise getExerciseKo(long ex_no);
	
	int createExercise(FullExerciseForm exercise);
	List<FullExerciseForm> getAllExercises();
	FullExerciseForm getAllExercise(long ex_no);
	int updateExercise(FullExerciseForm exercise);
	int deleteExercise(long ex_no);
}
