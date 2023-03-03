package com.notice.project.user;

import java.sql.Timestamp;

import com.notice.project.dto.UsernameValidRespDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
	
	private int user_code;
	private String user_name;
	private String user_id;
	private String user_email;
	private String user_password;
	private String user_roles;
	private String user_provider;
	private String user_address;
	private int user_gender;
	private Timestamp create_date;
	private Timestamp update_date;
	
	public UsernameValidRespDto toValidDto() {
		return UsernameValidRespDto.builder()
					.user_code(user_code)
					.user_name(user_name)
					.user_id(user_id)
					.user_roles(user_roles)
					.build();
	}
}
