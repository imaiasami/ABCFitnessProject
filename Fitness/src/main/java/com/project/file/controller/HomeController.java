package com.project.file.controller;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.file.model.entity.exercise.Exercise;
import com.project.file.repository.ExerciseRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HomeController {

	private final ExerciseRepository exerciseMapper;

	@GetMapping("/")
	public String home(Model model) {
		RowBounds rowBounds = new RowBounds(0, 9);		// 0번째부터 9개
		List<Exercise> exercises = exerciseMapper.getExercisesKo(rowBounds);
		model.addAttribute("exercises", exercises);
		return "index";
	}

}
