package com.project.file.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.file.model.dto.exercise.ExerciseNoSet;
import com.project.file.model.entity.exercise.Exercise;
import com.project.file.model.entity.member.Member;
import com.project.file.model.entity.routine.Routine;
import com.project.file.model.entity.routine.RoutineDefault;
import com.project.file.model.entity.routine.RoutineGenerated;
import com.project.file.repository.ExerciseRepository;
import com.project.file.repository.MemberRepository;
import com.project.file.repository.RoutineRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("{lang}/profile")
@Controller
public class ProfileController {

	private final MemberRepository memberMapper;
	private final RoutineRepository routineMapper;
	private final ExerciseRepository exerciseMapper;

	// 비회원일 시 로그인 페이지로, 회원일 시 자신의 프로필 페이지로 이동
	@GetMapping("")
	public String goProfile(@PathVariable String lang, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Member memberLogin = (Member) session.getAttribute("memberLogin");
		if (memberLogin == null) return "redirect:/" + lang + "/login?redirectURL=profile";
		else return "redirect:/" + lang + "/profile/" + memberLogin.getMem_id();
	}

	@GetMapping("{mem_id}")
	public String profileHome(@PathVariable("mem_id") String mem_id, @PathVariable String lang, Model model) {
		Member memberProfile = memberMapper.findMemberById(mem_id);
		model.addAttribute("memberProfile", memberProfile);
		
		String recent = memberProfile.getRecent();
		model.addAttribute("recent", recent);
		
		if (recent != null) {
			String[] recents = recent.split("\\/");
			if (recents[0].equals("d")) { // 기본 루틴
				RoutineDefault recentRoutine = routineMapper.getRoutineDefaultByNo(Long.parseLong(recents[1]), lang);
				setStep(recentRoutine.getStep(), lang);
				model.addAttribute("recentRoutine", recentRoutine);
			} else if (recents[0].equals("t")) { // 테마별 루틴(미구현)
				
			} else if (recents[0].equals("g")) { // 생성된 루틴
				RoutineGenerated recentRoutine = routineMapper.getRoutineGeneratedByRoutNo(Long.parseLong(recents[1]));
				setStep(recentRoutine.getStep(), lang);
				model.addAttribute("recentRoutine", recentRoutine);
			}
		}
		

		List<Exercise> bookmarked = memberMapper.getBookmarkList(memberProfile.getMem_no(), lang);
		model.addAttribute("bookmarks", bookmarked);

		List<Routine> routineBookmarked = memberMapper.getRoutineBookmarkList(memberProfile.getMem_no(), lang);
		model.addAttribute("routineBookmarks", routineBookmarked);
		
		List<RoutineGenerated> generateRoutines = routineMapper.getRoutineGeneratedByMemNo(memberProfile.getMem_no());
		model.addAttribute("generateRoutines", generateRoutines);

		return "profile/profile";
	}
	
	// 루틴에 저장된 운동 번호를 기반으로 각 운동 상세 불러오기
	public void setStep(List<List<ExerciseNoSet>> step, String lang) {
		for (List<ExerciseNoSet> daily : step) {
			for (ExerciseNoSet ex : daily) {
				ex.setInfo(exerciseMapper.getExerciseByNo(ex.getEx_no(), lang));
			}
		}
	}

}
