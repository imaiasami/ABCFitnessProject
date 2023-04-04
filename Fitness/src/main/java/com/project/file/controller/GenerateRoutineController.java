package com.project.file.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GenerateRoutineController {

	@GetMapping("/routine/generate")
	public String generateRoutine() {
		return "generateRoutine/generateRoutine";
	}

}
