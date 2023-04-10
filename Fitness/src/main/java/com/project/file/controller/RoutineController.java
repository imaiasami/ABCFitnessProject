package com.project.file.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.file.repository.RoutineRepository;

@Controller
@RequestMapping("routine")
public class RoutineController {
	
//	private final RoutineRepository routineMapper;
	
	@GetMapping("default")
	public String defaultRoutine() {
		return "routine/defaultRoutine";
	}

	@GetMapping("default/{rout_d_no}")
	public String defaultRoutineDetail(@PathVariable long rout_d_no) {
		return "routine/defaultRoutineDetail";
	}
	
	@GetMapping("themeRoutine")
	public String themeRoutine() {
		return "routine/themeRoutine";
	}

	@GetMapping("themeRoutine/{rout_t_no}")
	public String themeRoutineDetail(@PathVariable long rout_t_no) {
		return "routine/themeRoutineDetail";
	}

}
