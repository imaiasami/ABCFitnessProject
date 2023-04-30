package com.project.file.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		HttpSession session = request.getSession();
		String requestURI = request.getRequestURI();
		String lang = requestURI.substring(0, 3);
		
		if (session == null || session.getAttribute("memberLogin") == null) {
			response.sendRedirect(lang + "/login?redirectURL=" + requestURI);
			return false;
		}
		return true;
	}
}










