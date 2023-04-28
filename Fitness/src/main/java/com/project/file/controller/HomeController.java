package com.project.file.controller;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.file.model.entity.exercise.Exercise;
import com.project.file.model.entity.routine.Routine;
import com.project.file.model.entity.routine.RoutineDefault;
import com.project.file.repository.ExerciseRepository;
import com.project.file.repository.RoutineRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HomeController {

	private final ExerciseRepository exerciseMapper;
	private final RoutineRepository routineMapper;

	@GetMapping("/")
	public String home(Model model) {
		RowBounds rowBounds = new RowBounds(0, 9);		// 0번째부터 9개
		List<Exercise> exercises = exerciseMapper.getExercises(null, null, null, null, "ko", rowBounds);
		model.addAttribute("exercises", exercises);
		List<RoutineDefault> routines = routineMapper.getRoutineDefaults(null, null, null, "ko", rowBounds);
		model.addAttribute("routines", routines);
		return "index";
	}

}
