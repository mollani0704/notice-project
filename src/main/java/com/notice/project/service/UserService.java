package com.notice.project.service;

import com.notice.project.dto.UserReqDto;

public interface UserService {
	public Boolean save(UserReqDto userReqDto) throws Exception;
}
