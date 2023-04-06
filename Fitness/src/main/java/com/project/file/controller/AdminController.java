package com.project.file.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.file.model.dto.exercise.FullExerciseForm;
import com.project.file.repository.ExerciseRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminController {
	public final ExerciseRepository exerciseMapper;
	
	@GetMapping("/addexercise")
	public String addexercise(Model model) {
		List<FullExerciseForm> exercises = exerciseMapper.getAllExercises();
		for (FullExerciseForm exercise : exercises) {
			exercise.toBrTag();
		}
		model.addAttribute("exercises", exercises);
		return "admin/addexercise";
	}
	
	@GetMapping("/addexercise/write")
	public String addexerciseWrite() {
		return "admin/addexerciseWrite";
	}
	
	@PostMapping("/addexercise/write")
	public String addexerciseWriteDone(@ModelAttribute FullExerciseForm exercise) {
		exerciseMapper.createExercise(exercise);
		return "redirect:/addexercise";
	}
	
	@GetMapping("/addexercise/modify/{ex_no}")
	public String addexerciseModify(@PathVariable long ex_no, Model model) {
		FullExerciseForm exercise = exerciseMapper.getAllExercise(ex_no);
		model.addAttribute("exercise", exercise);
		return "admin/addexerciseModify";
	}
	
	@PostMapping("/addexercise/modify")
	public String addexerciseModifyDone(@ModelAttribute FullExerciseForm exercise) {
		exerciseMapper.updateExercise(exercise);
		return "redirect:/addexercise";
	}
	
	@GetMapping("/addexercise/remove/{ex_no}")
	public String addexerciseRemove(@PathVariable long ex_no) {
		exerciseMapper.deleteExercise(ex_no);
		return "redirect:/addexercise";
	}
}
