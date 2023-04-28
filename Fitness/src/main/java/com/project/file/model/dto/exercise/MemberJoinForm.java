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
	private int year;
	private int month;
	private int day;
	private String gender;
	private String phone;

	public Member toMember() {
		Member member = new Member();
		member.setMail(this.mail);
		member.setPassword(this.password);
		member.setMem_id(this.mem_id);
		member.setName(this.name);
		member.setBirth(LocalDate.of(this.year, this.month, this.day));
		member.setGender(this.gender);
		member.setPhone(this.phone);
		return member;
	}

}
