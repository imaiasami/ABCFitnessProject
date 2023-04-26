
package com.project.file.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.project.file.model.dto.exercise.MemberJoinForm;
import com.project.file.model.entity.member.Bookmark;
import com.project.file.model.entity.member.Member;
import com.project.file.model.entity.member.RoutineBookmark;
import com.project.file.repository.MemberRepository;
import com.project.file.util.CheckResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Controller
public class MemberController {
	private final MemberRepository memberMapper;

	// 회원가입 폼 이동
	@GetMapping("join")
	public String joinMemberForm(Model model) {
		model.addAttribute("memberJoin", new MemberJoinForm());
		return "login/join";
	}

	// 회원가입
	@PostMapping("join")
	public String joinMember(@ModelAttribute("memberJoin") MemberJoinForm member) {
		memberMapper.joinMember(member.toMember());
		return "redirect:/";
	}

	// 로그인 폼으로 이동
	@GetMapping("login")
	public String loginMemberForm(@SessionAttribute(name = "memberLogin", required = false) Member loginMember,
			Model model) {
		if (loginMember == null) {
			model.addAttribute("memberLogin", new Member());
			return "login/login";
		} else {
			return "redirect:/";
		}
	}

	// 로그인하기
	@PostMapping("login")
	public String loginMember(@Validated @ModelAttribute("memberLogin") Member memberLogin, BindingResult result,
			HttpServletRequest request, @RequestParam String redirectURL) {
		// 로그인 폼 유효성 검사
		if (result.hasErrors()) {
			return "login/login";
		}

		// 로그인 처리 로직
		Member findMember = memberMapper.findMemberByMail(memberLogin.getMail());
		if (findMember == null || !findMember.getPassword().equals(memberLogin.getPassword())) {
			result.reject("loginError", "아이디 또는 패스워드가 다릅니다.");
			return "login/login";
		} else if (findMember.getStatus() == 1) {
			result.reject("loginError", "비활성화계정입니다.");
			return "login/login";

		} else {
			// 로그인 성공 처리
			HttpSession session = request.getSession();
			session.setAttribute("memberLogin", findMember);
		}
		return "redirect:" + redirectURL;
	}

	// 로그아웃
	@GetMapping("logout")
	public String logoutMember(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("memberLogin", null);
		return "redirect:/";
	}

	// 비밀번호 찾기폼으로 이동
	@GetMapping("password")
	public String searchPasswordForm(Model model) {
		model.addAttribute("memberPassword", new Member());
		return "login/findPassword";
	}

	// 비밀번호 찾기
	@PostMapping("password")
	public String searchPassword(@ModelAttribute("memberPassword") Member memberPassword) {

		return "login/login";
	}

	// 이메일 중복 확인
	@ResponseBody
	@PostMapping("check-email")
	public CheckResponse checkEmail(@RequestParam String email) {
		boolean isDuplicate = isEmailDuplicate(email);
		return new CheckResponse(isDuplicate);
	}

	// 이메일 중복 확인
	private boolean isEmailDuplicate(String email) {
		int count = memberMapper.countByEmail(email);
		return count > 0;
	}

	// 사용자 이름 중복 확인
	@ResponseBody
	@PostMapping("check-member_id")
	public CheckResponse checkMemberId(@RequestParam String mem_id) {
		boolean isDuplicate = isMemberDuplicate(mem_id);
		return new CheckResponse(isDuplicate);
	}

	// 사용자 이름 중복 확인
	private boolean isMemberDuplicate(String mem_id) {
		int count = memberMapper.countByMemberId(mem_id);
		return count > 0;
	}

	// 북마크 추가/삭제
	@PostMapping("/exercise/{ex_no}")
	@ResponseBody
	public ResponseEntity<?> bookmark(@SessionAttribute(name = "memberLogin", required = false) Member loginMember,
			@PathVariable("ex_no") long ex_no) {
		if (loginMember == null) { // 로그인 안한 사용자
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		} else { // 로그인한 사용자
			try {
				Bookmark bookmarkForm = new Bookmark(loginMember.getMem_no(), ex_no);
				Bookmark bookmark = memberMapper.getBookmark(bookmarkForm);
				if (bookmark != null && bookmark.getEx_no() == ex_no) { // 북마크 되어 있는 경우
					log.info("bookmarks : {}", bookmark);
					memberMapper.deleteBookmark(bookmarkForm);
				} else {// 북마크추가
					log.info("bookmarked : {}", bookmarkForm);
					memberMapper.insertBookmark(bookmarkForm);
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
	
	// 루틴 북마크 추가/삭제
	@PostMapping("/default/{rout_no}")
	@ResponseBody
	public ResponseEntity<?> routineBookmark(@SessionAttribute(name = "memberLogin", required = false) Member loginMember,
			@PathVariable("rout_no") long rout_no) {
		if (loginMember == null) { // 로그인 안한 사용자
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		} else { // 로그인한 사용자
			try {
				RoutineBookmark routineBookmarkForm = new RoutineBookmark(loginMember.getMem_no(), rout_no);
				RoutineBookmark routineBookmark = memberMapper.getRoutineBookmark(routineBookmarkForm);
				if (routineBookmark != null && routineBookmark.getRout_d_no() == rout_no) { // 북마크 되어 있는 경우
					log.info("routineBookmarks : {}", routineBookmark);
					memberMapper.deleteRoutineBookmark(routineBookmarkForm);
				} else {// 루틴 북마크 추가
					log.info("routineBookmarked : {}", routineBookmarkForm);
					memberMapper.insertRoutineBookmark(routineBookmarkForm);
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

}
