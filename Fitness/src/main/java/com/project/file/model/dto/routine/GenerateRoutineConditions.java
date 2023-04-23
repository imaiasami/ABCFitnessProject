package com.project.file.model.dto.routine;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class GenerateRoutineConditions {
	private boolean cardio;
	private int skill;
	private List<String> equip;
	private int days;
	private boolean dumbbell;
	private boolean pullup;
	private boolean band;
	private boolean cycle;
	private boolean treadmill;
	
	public GenerateRoutineConditions() {
		this.equip = new ArrayList<>();
		this.equip.add("cali");
	}
	
	// boolean의 getter는 자동 생성되지 않음
	public boolean getCardio() {
		return this.cardio;
	}

	public void setDumbbell(boolean dumbbell) {
		this.dumbbell = dumbbell;
		if (dumbbell) {
			this.equip.add("dumbbell");
		}
	}

	public void setPullup(boolean pullup) {
		this.pullup = pullup;
		if (pullup) {
			this.equip.add("pullup");
		}
	}

	public void setBand(boolean band) {
		this.band = band;
		if (band) {
			this.equip.add("band");
		}
	}

	public void setCycle(boolean cycle) {
		this.cycle = cycle;
		if (cycle) {
			this.equip.add("cycle");
		}
	}

	public void setTreadmill(boolean treadmill) {
		this.treadmill = treadmill;
		if (treadmill) {
			this.equip.add("treadmill");
		}
	}
}
