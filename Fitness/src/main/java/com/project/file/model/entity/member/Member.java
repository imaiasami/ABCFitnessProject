package com.project.file.model.entity.member;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Member {
	private long mem_no;
	private  String mail;
	private String password;
	private String mem_id;
	private String language;
	private  int status;
	private String name;
	private Date birth;
	private String gender;
	private String phone;
	private String image;
	private String intro;
	private double height;
	private int skill;
	private int days;
	private int cardio;



public Member(long mem_no, String mail, String mem_id, String password, String name, Date birth, String gender) {
		this.mem_no = mem_no;
		this.mail = mail;
		this.mem_id = mem_id;
		this.password = password;
		this.name = name;
		this.birth = birth;
		this.gender = gender;
	}
}
