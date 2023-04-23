package com.project.file.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import com.project.file.interceptor.LoginCheckInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	
		registry.addInterceptor(new LoginCheckInterceptor())
		.order(1)
		.addPathPatterns(
				"/routine/generate/**",
				"/routine/run/**"
		)
		.excludePathPatterns(
				"/",
				"/css/**",
				"/js/**",
				"*.ico"
		);
	}
	
}
