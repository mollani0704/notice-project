package com.notice.project.service;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.notice.project.user.User;
import com.notice.project.user.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	
	private final UserRepository userRepository;
	
	public OAuth2User loadUser(OAuth2UserRequest auth2UserRequest) {
		
		String provider = null;
		
		OAuth2User oAuth2User = super.loadUser(auth2UserRequest);

		//ClientRegistration - OAuth2 제공자에 등록된 Client의 정보를 나타내는 클래스이다. 
		ClientRegistration clientRegistration = auth2UserRequest.getClientRegistration();
		
		Map<String, Object> attributes = oAuth2User.getAttributes();
		
		log.error(">>>>>>>>> ClientRegistration : {} ", clientRegistration);
		log.error(">>>>>>> oAuth2User : {}", attributes);
		
		provider = clientRegistration.getClientName();
		
		User user = getOauth2User(provider, attributes);
		
		return new PrincipalDetail(user, attributes);
		
	}
	
	private User getOauth2User(String provider, Map<String, Object> attributes) throws OAuth2AuthenticationException {
		
		User user = null;
		String id = null;
		
		String oauth2_id = null;
		
		Map<String, Object> response = null;
		
		if(provider.equalsIgnoreCase("google")) {
			response = attributes;
			id = (String) response.get("sub");
		} else {
			throw new OAuth2AuthenticationException("provider Error!");
		}
		
		oauth2_id = provider + "_" + id;
		
		try {
			user = userRepository.findOAuth2UserByUsername(oauth2_id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new OAuth2AuthenticationException("DATABASE Error!");
		}
		
		if(user == null) {
			user = User.builder()
					.user_name((String) response.get("name"))
					.user_email((String) response.get("email"))
					.user_id(oauth2_id)
					.oauth2_id(oauth2_id)
					.user_password(new BCryptPasswordEncoder().encode(oauth2_id))
					.user_roles("ROLE_USER")
					.user_provider(provider)
					.build();
			
			try {
				userRepository.save(user);
				user = userRepository.findOAuth2UserByUsername(oauth2_id);
			} catch (Exception e) {
				e.printStackTrace();
				throw new OAuth2AuthenticationException("DATABASE Error!");
			}
		}
		
		return user;
	}
	
}
