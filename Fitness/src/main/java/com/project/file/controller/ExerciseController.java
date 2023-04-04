package com.project.file.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExerciseController {
	@GetMapping("/exercise")
	public String exercise() {
		return "exercise/exercise";
	}

	@GetMapping("/exerciseDetail")
	public String exerciseDetail() {
		return "exercise/exerciseDetail";
	}

}
