package com.project.file.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.project.file.model.entity.exercise.Equipment;
import com.project.file.model.entity.exercise.Exercise;
import com.project.file.model.entity.member.Bookmark;
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
	public String boardList(Model model, @SessionAttribute(name = "memberLogin", required = false) Member loginMember) {
	    
	    
	    List<String> equipmented = memberMapper.getEquipmentList(loginMember.getMem_no());
	    model.addAttribute("equipmented", equipmented);
	    return "setting/userInformation";
	}

	@PostMapping("userInformation")
	public ResponseEntity<?> equipment(Model model,
			@SessionAttribute(name = "memberLogin", required = false) Member loginMember, String equip_id) {
		if (loginMember == null) { // 로그인 안한 사용자
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
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

	public String update(@SessionAttribute("memberLogin") Member loginMember, Model model) {
		Member findMember = memberMapper.findMemberByMail(loginMember.getMail());
		model.addAttribute("member", findMember);
		log.info("findMember : {}", findMember);
		return "setting/update";
	}

	// 회원정보 수정
	@PostMapping("update")
	public String update(@SessionAttribute("memberLogin") Member loginMember, @ModelAttribute Member member,
			HttpSession session) {
		log.info("member : {}", member);
		member.setMail(loginMember.getMail());
		log.info("member : {}", member);
		memberMapper.updateMember(member);

		// 세션 정보 업데이트
		session.setAttribute("memberLogin", member);

		return "redirect:/";
	}

}
