package com.project.file.model.dto.exercise;

import com.project.file.model.entity.exercise.Exercise;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ExerciseNoSet {
	private long ex_no;
	private int set;
	private Exercise info;

	public ExerciseNoSet(long ex_no, int set) {
		this.ex_no = ex_no;
		this.set = set;
	}
}
