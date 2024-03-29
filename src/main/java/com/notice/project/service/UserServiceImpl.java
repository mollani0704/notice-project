package com.notice.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.notice.project.dto.SignUpReqDto;
import com.notice.project.dto.UsernameValidRespDto;
import com.notice.project.user.User;
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

	@Override
	public Boolean validUsername(String username) throws Exception {
		
		User user = userRepository.findUserByUsername(username);
		
		return user == null;
	}

	@Override
	public List<User> getUserList() throws Exception {
		
		List<User> users = new ArrayList<User>();
		
		userRepository.getUserList().forEach(user -> {
			users.add(user);
		});
		
		return users;
	}

}
