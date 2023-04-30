package com.project.file.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.file.model.entity.exercise.Exercise;
import com.project.file.model.entity.member.Member;
import com.project.file.model.entity.routine.Routine;
import com.project.file.model.entity.routine.RoutineGenerated;
import com.project.file.repository.MemberRepository;
import com.project.file.repository.RoutineRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("{lang}/profile")
@Controller
public class ProfileController {

	private final MemberRepository memberMapper;
	private final RoutineRepository routineMapper;

	// 비회원일 시 로그인 페이지로, 회원일 시 자신의 프로필 페이지로 이동
	@GetMapping("")
	public String goProfile(@PathVariable String lang, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Member memberLogin = (Member) session.getAttribute("memberLogin");
		if (memberLogin == null)
			return "redirect:/" + lang + "/login?redirectURL=profile";
		else
			return "redirect:/" + lang + "/profile/" + memberLogin.getMem_id();
	}

	@GetMapping("{mem_id}")
	public String profileHome(@PathVariable("mem_id") String mem_id, @PathVariable String lang, Model model) {
		// 주소의 {mem_id}와 세션의 mem_id를 비교하여 동일할 경우에 프로필 편집 활성화
		Member memberProfile = memberMapper.findMemberById(mem_id);
		model.addAttribute("memberProfile", memberProfile);

		List<Exercise> bookmarked = memberMapper.getBookmarkList(memberProfile.getMem_no(), lang);
		model.addAttribute("bookmarks", bookmarked);

		List<Routine> routineBookmarked = memberMapper.getRoutineBookmarkList(memberProfile.getMem_no(), lang);
		model.addAttribute("routineBookmarks", routineBookmarked);
		
		List<RoutineGenerated> generateRoutines = routineMapper.getRoutineGeneratedByMemNo(memberProfile.getMem_no());
		model.addAttribute("generateRoutines", generateRoutines);

		return "profile/profile";
	}

}
