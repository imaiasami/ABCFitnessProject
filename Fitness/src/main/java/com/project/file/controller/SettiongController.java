package com.project.file.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.file.model.entity.member.Member;
import com.project.file.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("setting")
public class SettiongController {
	
	private final MemberRepository membermapper;
	
	//회원탈퇴 페이지 이동
 @GetMapping("withdrawal")
 	public String withdrawalForm() {
	 return "setting/withdrawal";
 }
 
 @PostMapping("withdrawal")
 	public String withdrawal(@RequestParam(required = false) String password, HttpServletRequest request, Model model) {
	 HttpSession session = request.getSession();
	 Member member = (Member) session.getAttribute("memberLogin");
	 if(member.getPassword().equals(password)) {
		 membermapper.deleteMember(member);
		 return "setting/withdrawal";
	 }else {
		 model.addAttribute("error", "비밀번호가 알맞지 않습니다.");
		 return "setting/userInformation";
	 }
	}
	 
 }


