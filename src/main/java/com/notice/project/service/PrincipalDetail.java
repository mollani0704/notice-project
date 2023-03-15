package com.notice.project.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.notice.project.user.User;

import lombok.Data;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인을 진행이 완료가 되면 시큐리티 session을 만들어준다. 
// 이 session들어갈 수 있는 객체 오브젝틑 Authentication 타입 객체여야 한다.
// Authentication 안에 User 정보가 있어야 됨.
// User오브젝트 타입 => UserDetails 타입 객체

// Security Session => Authentication => UserDetails(PrincipalDetail)

@Data
public class PrincipalDetail implements UserDetails, OAuth2User{

	private static final long serialVersionUID = 1L;
	
	private User user;
	private Map<String, Object> attribute;
	
	public PrincipalDetail(User user) {
		this.user = user;
	}
	
	public PrincipalDetail(User user, Map<String, Object> attribute) {
		this.user =  user;
		this.attribute = attribute;
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collector = new ArrayList<GrantedAuthority>();
		
		collector.add(() -> {
			return "ROLE_" + user.getUser_roles();
		});
		
		return collector;
	}

	@Override
	public String getPassword() {
		
		return user.getUser_password();
	}

	@Override
	public String getUsername() {
		
		return user.getUser_id();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return user.getUser_name();
	}

}
