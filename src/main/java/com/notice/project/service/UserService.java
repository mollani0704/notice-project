package com.notice.project.service;

import java.util.List;

import com.notice.project.dto.SignUpReqDto;
import com.notice.project.user.User;

public interface UserService {
	public Boolean save(SignUpReqDto signUpReqDto) throws Exception;
	
	public Boolean validUsername(String username) throws Exception;
	
	public List<User> getUserList() throws Exception;
}
