package com.project.file.model.entity.routine;

import java.util.ArrayList;
import java.util.List;

import com.project.file.model.dto.exercise.ExerciseNoSet;

import lombok.Data;

@Data
public class Routine {
	private long rout_no;
	private int diff;
	private String name;
	private List<List<ExerciseNoSet>> step;
	private List<String> equip;
	
	public void setStep(String step) {
		List<List<ExerciseNoSet>> result = new ArrayList<>();
		String[] arr1 = step.replace(" ", "").split("\\/");
		for (String elm1 : arr1) {
			List<ExerciseNoSet> exList = new ArrayList<>();
			String[] arr2 = elm1.split(",");
			for (String elm2 : arr2) {
				String[] arr3 = elm2.split(":");
				exList.add(new ExerciseNoSet(Long.parseLong(arr3[0]), Integer.parseInt(arr3[1])));
			}
			result.add(exList);
		}
		this.step = result;
	}
	
	public void setEquip(String equip) {
		List<String> result = new ArrayList<>();
		String[] arr = equip.replace(" ", "").split(",");
		for (String elm : arr) {
			result.add(elm);
		}
		this.equip = result;
	}
	
	public void changeEquip(List<String> equip) {
		this.equip = equip;
	}
}
