package com.project.file.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.project.file.model.entity.exercise.Equipment;
import com.project.file.model.entity.member.Member;
import com.project.file.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("{lang}/setting")
@Controller
public class SettingController {

	private final MemberRepository memberMapper;

	// 개인정보 폼 이동
	@GetMapping("userInformation")
	public String boardList(Model model, @SessionAttribute(name = "memberLogin", required = false) Member loginMember, @PathVariable String lang) {
	    List<String> equipmented = memberMapper.getEquipmentList(loginMember.getMem_no());
	    model.addAttribute("equipmented", equipmented);
	    return "setting/userInformation";
	}

	@PostMapping("userInformation")
	public ResponseEntity<?> equipment(@PathVariable String lang, Model model,
			@SessionAttribute(name = "memberLogin", required = false) Member loginMember, String equip_id) {
		if (loginMember == null) { // 로그인 안 한 사용자
			String message = "You need to log on.";
			if (lang.equals("ko")) message = "로그인이 필요합니다.";
			else if (lang.equals("jp")) message = "ログインが必要です。";
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
		} else { // 로그인한 사용자
			try {
				Equipment equipmentForm = new Equipment(loginMember.getMem_no(), equip_id);
				Equipment equipment = memberMapper.getEquipment(equipmentForm);
				if (equipment == null || !equipment.getEquip_id().equals(equip_id)) {
					log.info("equipmentinsert : {}", equipmentForm);
					memberMapper.insertEquipment(equipmentForm);
					
				} else {
					log.info("equipmentdeleteForm : {}", equipmentForm);
					memberMapper.deleteEquipment(equipmentForm);
				}
				Map<String, Object> resultMap = new HashMap<>();
				resultMap.put("status", "success");
				return ResponseEntity.ok().body(resultMap);
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류가 발생했습니다.");
			}
		}
	}

	// 회원정보 수정 페이지 이동
	@GetMapping("update")
	public String update(@SessionAttribute("memberLogin") Member loginMember, @PathVariable String lang, Model model) {
		Member findMember = memberMapper.findMemberByMail(loginMember.getMail());
		model.addAttribute("member", findMember);
		log.info("findMember : {}", findMember);
		return "setting/update";
	}

	// 회원정보 수정
	@PostMapping("update")
	public String update(@SessionAttribute("memberLogin") Member loginMember, @ModelAttribute Member member,
			@PathVariable String lang, HttpSession session) {
		log.info("member : {}", member);
		member.setMail(loginMember.getMail());
		log.info("member : {}", member);
		memberMapper.updateMember(member);

		// 세션 정보 업데이트
		session.setAttribute("memberLogin", member);

		return "redirect:/" + lang + "/setting/userInformation";
	}

	// 회원탈퇴 페이지 이동
	@GetMapping("withdrawal")
	public String withdrawalForm() {
		return "setting/withdrawal";
	}

	// 회원탈퇴 처리
	@PostMapping("withdrawal")
	public String withdrawal(@RequestParam(required = false) String password, @PathVariable String lang, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		Member member = (Member) session.getAttribute("memberLogin");
		if (member.getPassword().equals(password)) {
			memberMapper.deleteMember(member);
			return "setting/withdrawal";
		} else {
			String message = "Wrong password.";
			if (lang.equals("ko")) message = "비밀번호가 알맞지 않습니다.";
			else if (lang.equals("jp")) message = "パスワードが間違います。";
			model.addAttribute("error", message);
			return "setting/userInformation";
		}
	}

}
