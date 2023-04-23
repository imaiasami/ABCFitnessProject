package com.project.file.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.file.model.dto.routine.GenerateRoutineConditions;
import com.project.file.model.dto.routine.GenerateRoutineForm;
import com.project.file.model.entity.exercise.Exercise;
import com.project.file.model.entity.member.Member;
import com.project.file.model.entity.routine.RoutineGenerated;
import com.project.file.repository.ExerciseRepository;
import com.project.file.repository.RoutineRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("routine")
@Controller
public class GenerateRoutineController {
	public final ExerciseRepository exerciseMapper;
	public final RoutineRepository routineMapper;

	// 루틴 생성 페이지로 이동
	@GetMapping("generate")
	public String selectConditions(Model model) {
		return "generateRoutine/generateRoutine";
	}
	
	// 루틴 생성 로직
	@PostMapping("generate")
	public String generateRoutine(@ModelAttribute GenerateRoutineConditions conditions, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Member memberLogin = (Member) session.getAttribute("memberLogin");
		
		if (conditions.getSkill() < 1) conditions.setSkill(1);
		if (conditions.getDays() < 1) conditions.setDays(1);
		
		Random rd = new Random();
		String step = "";
		List<List<String>> weeklyMuscle = getWeeklyMuscle(conditions.getDays());
		for (int i = 0; i < weeklyMuscle.size(); i++) {
			if (i != 0) step += " / ";
			for (int j = 0; j < weeklyMuscle.get(i).size(); j++) {
				if (j != 0) step += ", ";
				// 조건에 맞는 운동 가져오기
				List<Exercise> exercises = exerciseMapper.getExercisesByMuscle(weeklyMuscle.get(i).get(j), getDifficulty(conditions.getSkill()), conditions.getEquip(), "ko");
				if (exercises.size() == 0) exercises = exerciseMapper.getExercisesByMuscle(weeklyMuscle.get(i).get(j), getDifficulty(2), conditions.getEquip(), "ko");
				Exercise exercise = exercises.get(rd.nextInt(exercises.size()));
				step += exercise.getEx_no() + ":3";
				// 체중 감량이 목적일 경우, 각 일정 마지막에 유산소 운동 넣기
				if (conditions.getCardio() && j == weeklyMuscle.get(i).size() - 1) {
					List<Exercise> cardios = exerciseMapper.getExercisesByMuscle(null, new ArrayList<Integer>(Arrays.asList(6)), conditions.getEquip(), "ko");
					Exercise cardio = cardios.get(rd.nextInt(cardios.size()));
					step += ", " + cardio.getEx_no() + ":3";
				}
			}
		}
		
		GenerateRoutineForm rout_g = new GenerateRoutineForm();
		rout_g.setMem_no(memberLogin.getMem_no());
		rout_g.setName("Personal Routine");
		rout_g.setStep(step);
		rout_g.setEquip(conditions.getEquip().toString().replace("[cali]", "").replace("cali, ", "").replaceAll("[\\[\\]]", ""));
		rout_g.setDiff(conditions.getSkill());
		rout_g.setDays(conditions.getDays());
		rout_g.setCardio(conditions.getCardio());
		routineMapper.createRoutineGenerated(rout_g);
		
		return "redirect:/routine/generate/" + rout_g.getRout_no();
	}
	
	// 루틴 생성 완료 페이지로 이동
	@GetMapping("generate/{rout_no}")
	public String defaultRoutineDetail(@PathVariable long rout_no, Model model) {
		RoutineGenerated routine = routineMapper.getRoutineGeneratedByRoutNo(rout_no);
		log.info("routine: {}", routine);
		model.addAttribute("routine", routine);
		Map<Long, Exercise> exMap = getExMap(routine.getStep());
		model.addAttribute("exMap", exMap);
		return "generateRoutine/generateRoutineDetail";
	}
	
	// 운동 난이도 지정
	public List<Integer> getDifficulty(int skill) {
		switch (skill) {
		default : 
		case 1 : return new ArrayList<Integer>(Arrays.asList(1, 2));
		case 2 : return new ArrayList<Integer>(Arrays.asList(2 ,3, 4));
		case 3 : return new ArrayList<Integer>(Arrays.asList(4, 5));
		}
	}
	
	// 일자별 운동 부위 지정
	public List<List<String>> getWeeklyMuscle(int days) {
		List<List<String>> result = new ArrayList<>();
		switch (days) {
		case 2 : 
			result.add(new ArrayList<String>(Arrays.asList("chest", "back", "biceps", "triceps", "leg", "shoulder", "core")));
		default : 
		case 1 : 
			result.add(new ArrayList<String>(Arrays.asList("chest", "back", "biceps", "triceps", "leg", "shoulder", "core")));
			break;
		case 3 : 
			result.add(new ArrayList<String>(Arrays.asList("back", "triceps", "core", "back", "triceps", "core", "back", "triceps", "core")));
			result.add(new ArrayList<String>(Arrays.asList("chest", "biceps", "core", "chest", "biceps", "core", "chest", "biceps", "core")));
			result.add(new ArrayList<String>(Arrays.asList("leg", "shoulder", "core", "leg", "shoulder", "core", "leg", "shoulder", "core")));
			break;
		case 4 : 
			result.add(new ArrayList<String>(Arrays.asList("chest", "back", "biceps", "core", "chest", "back", "biceps", "core")));
			result.add(new ArrayList<String>(Arrays.asList("leg", "shoulder", "triceps", "core", "leg", "shoulder", "triceps", "core")));
			result.add(new ArrayList<String>(Arrays.asList("chest", "back", "biceps", "core", "chest", "back", "biceps", "core")));
			result.add(new ArrayList<String>(Arrays.asList("leg", "shoulder", "triceps", "core", "leg", "shoulder", "triceps", "core")));
			break;
		case 5 : 
			result.add(new ArrayList<String>(Arrays.asList("chest", "back", "biceps", "core", "chest", "back", "biceps", "core")));
			result.add(new ArrayList<String>(Arrays.asList("leg", "shoulder", "core", "leg", "shoulder", "core", "leg", "shoulder", "core")));
			result.add(new ArrayList<String>(Arrays.asList("back", "triceps", "core", "back", "triceps", "core", "back", "triceps", "core")));
			result.add(new ArrayList<String>(Arrays.asList("leg", "shoulder", "triceps", "core", "leg", "shoulder", "triceps", "core")));
			result.add(new ArrayList<String>(Arrays.asList("chest", "biceps", "core", "chest", "biceps", "core", "chest", "biceps", "core")));
			break;
		case 6 : 
			result.add(new ArrayList<String>(Arrays.asList("back", "triceps", "core", "back", "triceps", "core", "back", "triceps", "core")));
			result.add(new ArrayList<String>(Arrays.asList("chest", "biceps", "core", "chest", "biceps", "core", "chest", "biceps", "core")));
			result.add(new ArrayList<String>(Arrays.asList("leg", "shoulder", "core", "leg", "shoulder", "core", "leg", "shoulder", "core")));
			result.add(new ArrayList<String>(Arrays.asList("back", "triceps", "core", "back", "triceps", "core", "back", "triceps", "core")));
			result.add(new ArrayList<String>(Arrays.asList("chest", "biceps", "core", "chest", "biceps", "core", "chest", "biceps", "core")));
			result.add(new ArrayList<String>(Arrays.asList("leg", "shoulder", "core", "leg", "shoulder", "core", "leg", "shoulder", "core")));
			break;
		case 7 : 
			result.add(new ArrayList<String>(Arrays.asList("leg", "core", "leg", "core", "leg", "core")));
			result.add(new ArrayList<String>(Arrays.asList("back", "triceps", "core", "back", "triceps", "core", "back", "triceps", "core")));
			result.add(new ArrayList<String>(Arrays.asList("chest", "biceps", "core", "chest", "biceps", "core", "chest", "biceps", "core")));
			result.add(new ArrayList<String>(Arrays.asList("leg", "shoulder", "core", "leg", "shoulder", "core", "leg", "shoulder", "core")));
			result.add(new ArrayList<String>(Arrays.asList("chest", "biceps", "core", "chest", "biceps", "core", "chest", "biceps", "core")));
			result.add(new ArrayList<String>(Arrays.asList("shoulder", "core", "shoulder", "core", "shoulder", "core")));
			result.add(new ArrayList<String>(Arrays.asList("back", "triceps", "core", "back", "triceps", "core", "back", "triceps", "core")));
			break;
		}
		return result;
	}
	
	// 루틴에 저장된 운동 번호를 기반으로 각 운동 상세 불러오기
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
