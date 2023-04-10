package com.project.file.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.file.model.entity.exercise.Exercise;
import com.project.file.repository.ExerciseRepository;
import com.project.file.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ExerciseController {
	
	private final ExerciseRepository exerciseMapper;
	
	@GetMapping("/exercise")
	public String exercise(Model model) {
		List<Exercise> exercises = exerciseMapper.getExercisesKo();
		model.addAttribute("exercises", exercises);
		return "exercise/exercise";
	}

	@GetMapping("/exercise/{ex_no}")
	public String exerciseDetail(@PathVariable long ex_no) {
		return "exercise/exerciseDetail";
	}

}
