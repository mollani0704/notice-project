package com.notice.project.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.notice.project.user.User;
import com.notice.project.user.UserRepository;

import lombok.RequiredArgsConstructor;

// 시큐리티 설정에서 loginProcessingUrl("/login")
// login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService{

	private final UserRepository userRepository;
	
	// 시큐리티 session = Authentication(내부 UserDetails)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User userEntity = null;
		
		try {
			userEntity = userRepository.findUserByUsername(username);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException(username);
		}
		
		if(userEntity == null) {
			throw new UsernameNotFoundException(username);
		}
				
		return new PrincipalDetail(userEntity);
	}

}
