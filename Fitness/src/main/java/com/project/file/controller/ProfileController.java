package com.project.file.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("profile")
@Controller
public class ProfileController {
	
	@GetMapping("/{mem_id}")
	public String profileHome(@PathVariable("mem_id") String mem_id) {
		return "profile/profile";
	}

}
