package com.project.file.controller;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.project.file.model.entity.exercise.Exercise;
import com.project.file.model.entity.member.Bookmark;
import com.project.file.model.entity.member.Member;
import com.project.file.repository.ExerciseRepository;
import com.project.file.repository.MemberRepository;
import com.project.file.util.PageNavigator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ExerciseController {
	
	private final ExerciseRepository exerciseMapper;
	private final MemberRepository memberMapper;

	// 페이징 처리 상수값
	final int countPerPage = 9; // 페이지 당 글의 수
	final int pagePerGroup = 5; // 페이지 그룹 당 표시할 페이지 수

	@GetMapping("/exercise")
	public String exercise(Model model, @RequestParam(defaultValue = "1") int page) {
		
		int total = exerciseMapper.getTotal();
		
		// 페이징 처리를 위한 네비게이터 객체 생성
		PageNavigator navigator = new PageNavigator(countPerPage, pagePerGroup, page, total);
		model.addAttribute("navigator", navigator);
		RowBounds rowBounds = new RowBounds(navigator.getStartRecord(), navigator.getCountPerPage());
		
		List<Exercise> exercises = exerciseMapper.getExercisesKo(rowBounds);
		model.addAttribute("exercises", exercises);
		return "exercise/exercise";
	}

	@GetMapping("/exercise/{ex_no}")
	public String exerciseDetail(@PathVariable long ex_no, Model model,
			@SessionAttribute(name = "memberLogin", required = false) Member loginMember)  {
		Exercise exercise = exerciseMapper.getExerciseKo(ex_no);
		exercise.toBrTag();
		model.addAttribute("exercise", exercise);
		
		if (loginMember != null) { // 로그인한 사용자일 경우 북마크 목록 추가
			Bookmark bookmark = new Bookmark(loginMember.getMem_no(), ex_no);
			Bookmark bookmarks = memberMapper.getBookmark(bookmark);
			model.addAttribute("bookmarked", bookmarks);
			
		}
		
		
		return "exercise/exerciseDetail";
	}

}
