package com.project.file.model.entity.routine;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RoutineGenerated extends Routine  {
	private long mem_no;
	private int days;
	private boolean cardio;
	private LocalDateTime time;
	
	public boolean getCardio() {
		return this.cardio;
	}
}
