package com.project.file.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.project.file.model.entity.member.Member;
import com.project.file.repository.MemberRepository;
import com.project.file.util.CheckResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("setting")
@Controller
public class UserInformationController {

	private final MemberRepository memberMapper;

	// 개인정보 폼 이동
	@GetMapping("userInformation")
	public String boardList() {
		return "setting/userInformation";
	}

	// 회원정보 수정 페이지 이동
	@GetMapping("update")

	public String update(@SessionAttribute("memberLogin") Member loginMember, Model model) {
		Member findMember = memberMapper.findMemberByMail(loginMember.getMail());
		model.addAttribute("member", findMember);
		log.info("findMember : {}", findMember);
		return "setting/update";
	}

	// 회원정보 수정
	@PostMapping("update")
	public String update(@SessionAttribute("memberLogin") Member loginMember, @ModelAttribute Member member, HttpSession session) {
		log.info("member : {}", member);
		member.setMail(loginMember.getMail());
		log.info("member : {}", member);
		memberMapper.updateMember(member);
		
		 // 세션 정보 업데이트
	    session.setAttribute("memberLogin", member);
	    
		return "redirect:/";
	}

}
