package com.project.file.model.entity.routine;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Routine {
	private long rout_no;
	private int diff;
	private String name;
	private List<Map<Long, Integer>> step;
	private List<String> equip;
	
	public void setStep(String step) {
		List<Map<Long, Integer>> result = new ArrayList<>();
		String[] arr1 = step.replace(" ", "").split("\\/");
		for (String elm1 : arr1) {
			Map<Long, Integer> exMap = new LinkedHashMap<>();
			String[] arr2 = elm1.split(",");
			for (String elm2 : arr2) {
				String[] arr3 = elm2.split(":");
				exMap.put(Long.parseLong(arr3[0]), Integer.parseInt(arr3[1]));
			}
			result.add(exMap);
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
}
