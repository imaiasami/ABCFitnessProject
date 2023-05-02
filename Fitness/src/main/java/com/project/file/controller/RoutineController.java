package com.project.file.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.project.file.model.dto.exercise.ExerciseNoSet;
import com.project.file.model.entity.member.Member;
import com.project.file.model.entity.member.RoutineBookmark;
import com.project.file.model.entity.routine.RoutineDefault;
import com.project.file.model.entity.routine.RoutineGenerated;
import com.project.file.repository.ExerciseRepository;
import com.project.file.repository.MemberRepository;
import com.project.file.repository.RoutineRepository;
import com.project.file.util.PageNavigator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("{lang}/routine")
@Controller
public class RoutineController {

	private final RoutineRepository routineMapper;
	private final ExerciseRepository exerciseMapper;
	private final MemberRepository memberMapper;

	// 페이징 처리 상수값
	final int countPerPage = 12; // 페이지 당 글의 수
	final int pagePerGroup = 5; // 페이지 그룹 당 표시할 페이지 수

	// 루틴 페이지 이동 시 자동으로 기본 루틴으로 연결
	@GetMapping("default")
	public String defaultRoutine(@RequestParam(defaultValue = "1") int page, @PathVariable String lang, Model model) {
		int total = routineMapper.getDefaultTotal(null, null, null, lang);

		// 페이징 처리를 위한 네비게이터 객체 생성
		PageNavigator navigator = new PageNavigator(countPerPage, pagePerGroup, page, total);
		model.addAttribute("navigator", navigator);
		RowBounds rowBounds = new RowBounds(navigator.getStartRecord(), navigator.getCountPerPage());
		
		List<RoutineDefault> routines = routineMapper.getRoutineDefaults(null, null, null, lang, rowBounds);
		model.addAttribute("routines", routines);
		return "routine/defaultRoutine";
	}

	// 기본 루틴 상세
	@GetMapping("default/{rout_no}")
	public String defaultRoutineDetail(@PathVariable long rout_no, @SessionAttribute(name = "memberLogin", required = false) Member loginMember, @PathVariable String lang, Model model) {
		RoutineDefault routine = routineMapper.getRoutineDefaultByNo(rout_no, lang);
		setStep(routine.getStep(), lang);
		routine.changeEquip(exerciseMapper.getEquipNames(routine.getEquip(), lang));
		model.addAttribute("routine", routine);

		if (loginMember != null) { // 로그인한 사용자일 경우 북마크 목록 추가
			RoutineBookmark routineBookmark = new RoutineBookmark(loginMember.getMem_no(), rout_no);
			RoutineBookmark routineBookmarks = memberMapper.getRoutineBookmark(routineBookmark);
			model.addAttribute("bookmarked", routineBookmarks);

		}

		return "routine/defaultRoutineDetail";
	}

	// 테마별 루틴(미구현)
	@GetMapping("theme")
	public String themeRoutine() {
		return "routine/themeRoutine";
	}

	// 테마별 루틴 상세(미구현)
	@GetMapping("theme/{rout_no}")
	public String themeRoutineDetail(@PathVariable long rout_no) {
		return "routine/themeRoutineDetail";
	}

	// 루틴 재생 페이지 이동
	@GetMapping("run/{type}/{rout_no}/{day}")
	public String runRoutine(@PathVariable String type, @PathVariable long rout_no, @PathVariable int day, @PathVariable String lang, Model model) {
		if (type.equals("d")) { // 기본 루틴
			RoutineDefault routine = routineMapper.getRoutineDefaultByNo(rout_no, lang);
			setStep(routine.getStep(), lang);
			model.addAttribute("routine", routine);
		} else if (type.equals("t")) { // 테마별 루틴(미구현)
			
		} else if (type.equals("g")) { // 생성된 루틴
			RoutineGenerated routine = routineMapper.getRoutineGeneratedByRoutNo(rout_no);
			setStep(routine.getStep(), lang);
			model.addAttribute("routine", routine);
		}
		return "routine/playRoutine";
	}
	
	// 루틴 재생 완료
	@PostMapping("run/{type}/{rout_no}/{day}")
	public String completeRoutine(@PathVariable String type, @PathVariable long rout_no, @PathVariable int day, @RequestParam long time, @PathVariable String lang, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Member loginMember = (Member) session.getAttribute("memberLogin");
		int next = 1;
		
		// mem_date 테이블에 오늘 수행한 루틴 정보 저장
		if (type.equals("d")) { // 기본 루틴
			RoutineDefault routine = routineMapper.getRoutineDefaultByNo(rout_no, lang);
			if (day < routine.getStep().size()) next = day + 1;
		} else if (type.equals("t")) { // 테마별 루틴(미구현)
			
		} else if (type.equals("g")) { // 생성된 루틴
			RoutineGenerated routine = routineMapper.getRoutineGeneratedByRoutNo(rout_no);
			if (day < routine.getStep().size()) next = day + 1;
		}
		
		// member 테이블에 다음에 수행할 루틴 정보 저장
		loginMember.setRecent(type + "/" + rout_no + "/" + next);
		if (memberMapper.updateMemberRecent(loginMember) > 0) session.setAttribute("memberLogin", loginMember);
		
		return "redirect:/" + lang + "/profile/" + loginMember.getMem_id();
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
