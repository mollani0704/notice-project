package com.notice.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.notice.project.dto.CMRespDto;
import com.notice.project.dto.SignUpReqDto;
import com.notice.project.handler.CustomValidationApiException;
import com.notice.project.handler.aop.annotation.Log;
import com.notice.project.handler.aop.annotation.Timer;
import com.notice.project.handler.aop.annotation.ValidCheck;
import com.notice.project.service.PrincipalDetail;
import com.notice.project.service.UserService;
import com.notice.project.user.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserApiController {
	
	private final UserService userService;
	
	@Log
	@Timer
//	@ValidCheck
	@PostMapping("/api/auth/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody SignUpReqDto signUpReqDto, BindingResult bindingResult ) {
		
//		if(bindingResult.hasErrors()) {
//			Map<String, String> errorMessage = new HashMap<String, String>();
//			
//			bindingResult.getFieldErrors().forEach(error -> {
//				errorMessage.put(error.getField(), error.getDefaultMessage());
//			});
//			
//			throw new CustomValidationApiException("유효성 검사", errorMessage);
//		}
		
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
	
	@GetMapping("/api/auth/principal")
	public ResponseEntity<?> getPrincipal(@AuthenticationPrincipal PrincipalDetail principalDetail) {
		if(principalDetail == null) {
			return ResponseEntity.badRequest().body(new CMRespDto<>(1, "principal is null", null));
		}
		
		return ResponseEntity.ok(new CMRespDto<>(1, "success load", principalDetail.getUser()));
	}
	
	@GetMapping("/api/users")
	public ResponseEntity<?> getUserList(Model model) {
		
		List<User> data = null;
		
		try {
			data = userService.getUserList();
			model.addAttribute("userList", data);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "Database failed", null));
		}
		
		return ResponseEntity.ok().body(new CMRespDto<>(1, "Database success", data));
	}
}
