package com.project.file.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.file.model.entity.member.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("profile")
@Controller
public class ProfileController {
	
	// 비회원일 시 로그인 페이지로, 회원일 시 자신의 프로필 페이지로 이동
	@GetMapping("")
	public String goProfile(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Member memberLogin = (Member) session.getAttribute("memberLogin");
		if (memberLogin == null) return "redirect:/login?redirectURL=profile";
		else return "redirect:/profile/" + memberLogin.getMem_id();
	}
	
	@GetMapping("{mem_id}")
	public String profileHome(@PathVariable("mem_id") String mem_id) {
		// 주소의 {mem_id}와 세션의 mem_id를 비교하여 동일할 경우에 프로필 편집 활성화
		
		return "profile/profile";
	}

}
