package com.project.file.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.project.file.model.entity.member.Member;
import com.project.file.repository.MemberRepository;

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

//	@PostMapping("information")
//	public String myInformation() {
//		return "redirect:/";
//	}

}
