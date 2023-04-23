package com.project.file.model.dto.member;

import com.project.file.model.entity.member.Member;

import lombok.Data;

@Data
public class MemberLoginForm {
	
	private String mail;
	private String password;
	private String saveId;


}
