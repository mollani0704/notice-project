package com.notice.project.service;

import com.notice.project.dto.SignUpReqDto;

public interface UserService {
	public Boolean save(SignUpReqDto signUpReqDto) throws Exception;
}
