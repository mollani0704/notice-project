package com.notice.project.dto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.notice.project.user.Role;
import com.notice.project.user.User;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserReqDto {
	
	private String username;
	private String password;
	private String email;
	
	public User toUserEntity() {
		return User.builder()
				.user_name(username)
				.user_password(new BCryptPasswordEncoder().encode(password))
				.user_email(email)
				.user_roles(Role.User)
				.build();
	}
}
