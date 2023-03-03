package com.notice.project.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.notice.project.dto.CMRespDto;
import com.notice.project.dto.SignUpReqDto;
import com.notice.project.service.UserService;
import com.notice.project.user.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserApiController {
	
	private final UserService userService;
	
	@PostMapping("/api/auth/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody SignUpReqDto signUpReqDto) {
		
		boolean result = false;
		
		try {
			result = userService.save(signUpReqDto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("회원가입이 안되었습니다");
		}
		
		return ResponseEntity.ok().body(new CMRespDto<>(200, "회원가입이 완료되었습니다.", result));
	}
	
	@GetMapping("/api/auth/signup/validation/username")
	public ResponseEntity<?> checkUsername(String username) {
		
		boolean result = false;
		
		try {
			result = userService.validUsername(username);
		} catch (Exception e) {
			
			e.printStackTrace();
			return ResponseEntity.badRequest().body("아아디 validation check가 되지 않았습니다.");
		}
		
		return ResponseEntity.ok().body(new CMRespDto<>(200, "validation check가 완료되었습니다.", result));
	}
	
}
