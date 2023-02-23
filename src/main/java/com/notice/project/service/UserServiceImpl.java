package com.notice.project.service;

import org.springframework.stereotype.Service;

import com.notice.project.dto.SignUpReqDto;
import com.notice.project.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
	
	@Override
	public Boolean save(SignUpReqDto signUpReqDto) throws Exception {
		
		int result = userRepository.save(signUpReqDto.toUserEntity());
		
		return result > 0;
	}

}
