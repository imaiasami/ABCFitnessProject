package com.project.file.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.file.model.dto.exercise.FullExerciseForm;
import com.project.file.repository.ExerciseRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("{lang}/character")
@Controller
public class CharacterController {
	public final ExerciseRepository exerciseMapper;
	
	
	
	@GetMapping("/zoro")
	public String addcharacterzoro(@PathVariable String lang) {
		return "character/zoro";
	}
	
	@GetMapping("/luffy")
	public String addcharacterLuffy(@PathVariable String lang) {
		return "character/luffy";
	}
	
	
	@GetMapping("/saitama")
	public String addcharacterSaitama(@PathVariable String lang) {
		return "character/saitama";
	}
	
	
	@GetMapping("/tanjiro")
	public String addcharactertanjiro(@PathVariable String lang) {
		return "character/tanjiro";
	}
	
	
	
	@GetMapping("/levi")
	public String addcharacterlevi(@PathVariable String lang) {
		return "character/levi";
	}
	
	@GetMapping("/manjiro")
	public String addcharactermanjiro(@PathVariable String lang) {
		return "character/manjiro";
	}
}
