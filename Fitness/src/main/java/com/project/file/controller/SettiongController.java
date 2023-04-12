package com.project.file.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("settings")
public class SettiongController {
	
	//회원탈퇴 페이지 이동
 @GetMapping("withdrawal")
 	public String withdrawal() {
	 return "setting/withdrawal";
 }
 


}
