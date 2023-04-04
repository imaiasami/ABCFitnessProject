package com.project.file.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.project.file.model.entity.member.Member;
import com.project.file.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Controller
public class MemberController {
	private final MemberRepository memberMapper;
	
	// 회원가입 폼 이동
		@GetMapping("join")
		public String joinMemberForm(Model model) {
			model.addAttribute("memberJoin", new Member());
			return "login/join";
		}

		// 회원가입
		@PostMapping("join")
		public String joinMember(@ModelAttribute("memberJoin") Member member) {
			memberMapper.joinMember(member);
			return "redirect:/";
		}

		// 로그인 폼으로 이동
		@GetMapping("login")
		public String loginMemberForm(Model model) {
			model.addAttribute("memberLogin", new Member());
			return "login/login";
		}

		// 로그인하기
		@PostMapping("login")
		public String loginMember(@Validated @ModelAttribute("memberLogin") Member memberLogin, 
				BindingResult result, HttpServletRequest request) {
		// 로그인 폼 유효성 검사
		if (result.hasErrors()) {
		return "login/login";
		}

		// 패스워드 비교
		Member findMember = memberMapper.findMemberById(memberLogin.getMail());
		if (findMember == null || !findMember.getPassword().equals(memberLogin.getPassword())) {
		result.reject("loginError", "아이디 또는 패스워드가 다릅니다.");
		return "login/login";
		}
		// 로그인 성공 처리
		HttpSession session = request.getSession();
		session.setAttribute("memberLogin", findMember);
		
		return "redirect:/";
			
		}
		
		
		//비밀번호 찾기폼으로 이동
		@GetMapping("password")
		public String searchPasswordForm(Model model) {
			model.addAttribute("memberPassword", new Member());
			return "login/findpassword";
		}
		
		//비밀번호 찾기
		@PostMapping("password")
		public String searchPassword(@ModelAttribute("memberPassword")Member memberPassword) {
			
			return  "login/login";
		}
		
}
