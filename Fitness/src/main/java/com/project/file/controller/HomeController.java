package com.project.file.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.project.file.model.entity.exercise.Exercise;
import com.project.file.model.entity.member.Member;
import com.project.file.model.entity.routine.RoutineDefault;
import com.project.file.repository.ExerciseRepository;
import com.project.file.repository.RoutineRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HomeController {

	private final ExerciseRepository exerciseMapper;
	private final RoutineRepository routineMapper;
	
	// 언어 설정
	@GetMapping("/")
	public String checkLanguage(@SessionAttribute(name = "memberLogin", required = false) Member memberLogin, HttpServletRequest request) {
		if (memberLogin == null || memberLogin.getLanguage() == null) {
			String lang = request.getLocale().getLanguage();
			if (lang.equals("ja")) lang = "jp";
			return "redirect:/" + lang + "/";
		} else {
			return "redirect:/" + memberLogin.getLanguage() + "/";
		}
	}

	// 메인 페이지
	@GetMapping("{lang}/")
	public String home(@PathVariable String lang, Model model) {
		RowBounds rowBounds = new RowBounds(0, 9);		// 0번째부터 9개
		List<Exercise> exercises = exerciseMapper.getExercises(null, null, null, null, lang, rowBounds);
		model.addAttribute("exercises", exercises);
		List<RoutineDefault> routines = routineMapper.getRoutineDefaults(null, null, null, lang, rowBounds);
		model.addAttribute("routines", routines);
		return "index";
	}

}
