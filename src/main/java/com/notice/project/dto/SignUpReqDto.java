package com.notice.project.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.notice.project.user.User;

import lombok.Data;

@Data
public class SignUpReqDto {
	@NotBlank
	@Pattern(regexp = "^[가-힇]*$", message = "한글만 입력 가능합니다.")
	private String name;
	
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
	@NotBlank
	@Email
	private String email;
	
	public User toUserEntity() {
		return User.builder()
				.user_name(name)
				.user_id(username)
				.user_password(new BCryptPasswordEncoder().encode(password))
				.user_email(email)
				.user_roles("User")
				.build();
	}
}
