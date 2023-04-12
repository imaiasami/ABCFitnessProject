package com.project.file.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.file.model.entity.exercise.Exercise;
import com.project.file.model.entity.routine.RoutineDefault;
import com.project.file.repository.ExerciseRepository;
import com.project.file.repository.RoutineRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("routine")
@Controller
public class RoutineController {
	
	private final RoutineRepository routineMapper;
	private final ExerciseRepository exerciseMapper;
	
	@GetMapping("default")
	public String defaultRoutine(Model model) {
		List<RoutineDefault> routines = routineMapper.getRoutineDefaultsKo();
		model.addAttribute("routines", routines);
		return "routine/defaultRoutine";
	}

	@GetMapping("default/{rout_no}")
	public String defaultRoutineDetail(@PathVariable long rout_no, Model model) {
		RoutineDefault routine = routineMapper.getRoutineDefaultKo(rout_no);
		model.addAttribute("routine", routine);
		Map<Long, Exercise> exMap = getExMap(routine.getStep());
		model.addAttribute("exMap", exMap);
		return "routine/defaultRoutineDetail";
	}
	
	@GetMapping("themeRoutine")
	public String themeRoutine() {
		return "routine/themeRoutine";
	}

	@GetMapping("themeRoutine/{rout_no}")
	public String themeRoutineDetail(@PathVariable long rout_no) {
		
		return "routine/themeRoutineDetail";
	}
	
	public Map<Long, Exercise> getExMap(List<Map<Long, Integer>> step) {
		Map<Long, Exercise> result = new HashMap<>();
		for (Map<Long, Integer> map : step) {
			for (Long longKey : map.keySet()) {
				Exercise exercise = exerciseMapper.getExerciseKo(longKey);
				result.put(longKey, exercise);
			}
		}
		return result;
	}

}
