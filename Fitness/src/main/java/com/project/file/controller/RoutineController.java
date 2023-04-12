package com.project.file.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.file.model.entity.exercise.Exercise;
import com.project.file.model.entity.routine.RoutineDefault;
import com.project.file.repository.ExerciseRepository;
import com.project.file.repository.RoutineRepository;
import com.project.file.util.PageNavigator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("routine")
@Controller
public class RoutineController {

	private final RoutineRepository routineMapper;
	private final ExerciseRepository exerciseMapper;

	// 페이징 처리 상수값
	final int countPerPage = 9; // 페이지 당 글의 수
	final int pagePerGroup = 5; // 페이지 그룹 당 표시할 페이지 수

	@GetMapping("default")
	public String defaultRoutine(Model model, @RequestParam(defaultValue = "1") int page) {

		int total = routineMapper.getTotal();

		// 페이징 처리를 위한 네비게이터 객체 생성
		PageNavigator navigator = new PageNavigator(countPerPage, pagePerGroup, page, total);
		model.addAttribute("navigator", navigator);
		RowBounds rowBounds = new RowBounds(navigator.getStartRecord(), navigator.getCountPerPage());
		
		List<RoutineDefault> routines = routineMapper.getRoutineDefaultsKo(rowBounds);
		model.addAttribute("routines", routines);
		return "routine/defaultRoutine";
	}

	@GetMapping("default/{rout_no}")
	public String defaultRoutineDetail(@PathVariable long rout_no, Model model) {
		RoutineDefault routine = routineMapper.getRoutineDefaultKo(rout_no);
		model.addAttribute("routine", routine);
		Map<Long, Exercise> exMap = getExMap(routine.getStep());
		model.addAttribute("exMap", exMap);
		return "routine/defaultRoutineDetail";
	}

	@GetMapping("themeRoutine")
	public String themeRoutine() {
		return "routine/themeRoutine";
	}

	@GetMapping("themeRoutine/{rout_no}")
	public String themeRoutineDetail(@PathVariable long rout_no) {

		return "routine/themeRoutineDetail";
	}

	public Map<Long, Exercise> getExMap(List<Map<Long, Integer>> step) {
		Map<Long, Exercise> result = new HashMap<>();
		for (Map<Long, Integer> map : step) {
			for (Long longKey : map.keySet()) {
				Exercise exercise = exerciseMapper.getExerciseKo(longKey);
				result.put(longKey, exercise);
			}
		}
		return result;
	}

}
