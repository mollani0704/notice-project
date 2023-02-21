package com.notice.project.service;

import org.springframework.stereotype.Service;

import com.notice.project.dto.UserReqDto;
import com.notice.project.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
	
	@Override
	public Boolean save(UserReqDto userReqDto) throws Exception {
		
		int result = userRepository.save(userReqDto.toUserEntity());
		
		return result > 0;
	}

}
