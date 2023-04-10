package com.project.file.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoutineController {
	@GetMapping("/defaultRoutine")
	public String defaultRoutine() {
		return "routine/defaultRoutine";
	}

	@GetMapping("/defaultRoutineDetail")
	public String defaultRoutineDetail() {
		return "routine/defaultRoutineDetail";
	}
	
	@GetMapping("/themeRoutine")
	public String themeRoutine() {
		return "routine/themeRoutine";
	}

	@GetMapping("/themeRoutineDetail")
	public String themeRoutineDetail() {
		return "routine/themeRoutineDetail";
	}

}
