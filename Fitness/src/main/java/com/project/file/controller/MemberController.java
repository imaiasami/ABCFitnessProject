
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.project.file.model.dto.exercise.MemberJoinForm;
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
		model.addAttribute("memberJoin", new MemberJoinForm());
		return "login/join";
	}

	// 회원가입
	@PostMapping("join")
	public String joinMember(@ModelAttribute("memberJoin") MemberJoinForm member) {
		memberMapper.joinMember(member.toMember());
		return "redirect:/";
	}

	// 로그인 폼으로 이동
	@GetMapping("login")
	public String loginMemberForm(@SessionAttribute(name = "memberLogin", required = false) Member loginMember, Model model) {
		if (loginMember == null) {
			model.addAttribute("memberLogin", new Member());
			return "login/login";
		} else {
			return "redirect:/";
		}
	}

	// 로그인하기
	@PostMapping("login")
	public String loginMember(@Validated @ModelAttribute("memberLogin") Member memberLogin, BindingResult result,
			HttpServletRequest request, @RequestParam String redirectURL) {
		// 로그인 폼 유효성 검사
		if (result.hasErrors()) {
			return "login/login";
		}

		// 로그인 처리 로직
		Member findMember = memberMapper.findMemberByMail(memberLogin.getMail());
		if (findMember == null || !findMember.getPassword().equals(memberLogin.getPassword())) {
			result.reject("loginError", "아이디 또는 패스워드가 다릅니다.");
			return "login/login";
		} else {
			// 로그인 성공 처리
			HttpSession session = request.getSession();
			session.setAttribute("memberLogin", findMember);
		}
		return "redirect:" + redirectURL;
	}

	// 로그아웃
	@GetMapping("logout")
	public String logoutMember(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("memberLogin", null);
		return "redirect:/";
	}

	// 비밀번호 찾기폼으로 이동
	@GetMapping("password")
	public String searchPasswordForm(Model model) {
		model.addAttribute("memberPassword", new Member());
		return "login/findPassword";
	}

	// 비밀번호 찾기
	@PostMapping("password")
	public String searchPassword(@ModelAttribute("memberPassword") Member memberPassword) {

		return "login/login";
	}

}
