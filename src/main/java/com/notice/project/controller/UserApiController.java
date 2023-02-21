package com.notice.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.notice.project.dto.UserReqDto;
import com.notice.project.service.UserService;
import com.notice.project.user.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserApiController {
	
	private final UserService userService;
	
	@PostMapping("/auth/signup")
	public ResponseEntity<?> signup(UserReqDto userReqDto) {
		
		try {
			boolean result = userService.save(userReqDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.badRequest().body("회원가입이 안되었습니다");
		}
		
		return ResponseEntity.ok().body("회원가입이 완료되었습니다.");
	}
	
}
