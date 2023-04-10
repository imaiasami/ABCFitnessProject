package com.project.file.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoutineController {
	@GetMapping("/routine/default")
	public String defaultRoutine() {
		return "routine/defaultRoutine";
	}

	@GetMapping("/routine/defaultRoutineDetail")
	public String defaultRoutineDetail() {
		return "routine/defaultRoutineDetail";
	}
	
	@GetMapping("routine/themeRoutine")
	public String themeRoutine() {
		return "routine/themeRoutine";
	}

	@GetMapping("routine/themeRoutineDetail")
	public String themeRoutineDetail() {
		return "routine/themeRoutineDetail";
	}

}
