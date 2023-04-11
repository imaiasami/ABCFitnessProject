package com.project.file.controller;

import java.util.List;

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
		List<Exercise> exercises = exerciseMapper.getExercisesKo();
		model.addAttribute("exercises", exercises);
		return "index";
	}

}
