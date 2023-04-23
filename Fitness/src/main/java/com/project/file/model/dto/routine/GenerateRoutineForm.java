package com.project.file.model.dto.routine;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class GenerateRoutineForm {
	private long rout_no;
	private long mem_no;
	private String name;
	private String step;
	private String equip;
	private int diff;
	private int days;
	private boolean cardio;
	private LocalDateTime time;
}
