package com.notice.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsernameValidRespDto {
	private int user_code;
	private String user_name;
	private String user_id;
	private String user_roles;
}
