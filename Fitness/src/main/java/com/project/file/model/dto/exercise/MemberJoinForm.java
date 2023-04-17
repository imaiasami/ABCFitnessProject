package com.project.file.model.dto.exercise;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.project.file.model.entity.member.Member;

import lombok.Data;

@Data
public class MemberJoinForm {

	private String mail;
	private String password;
	private String mem_id;
	private String name;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birth;
	private String gender;
	private String phone1;
	private String phone2;
	private String phone3;

	public Member toMember() {
		Member member = new Member();
		member.setMail(this.mail);
		member.setPassword(this.password);
		member.setMem_id(this.mem_id);
		member.setName(this.name);
		member.setBirth(this.birth);
		member.setGender(this.gender);
		member.setPhone(this.phone1 + "-" + this.phone2 + "-" + this.phone3);
		return member;
	}

}
