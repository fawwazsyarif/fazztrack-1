package com.payfazz.hello.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import com.payfazz.hello.model.RoleModel;
import com.payfazz.hello.model.UserModel;
import com.payfazz.hello.repository.UserDB;

public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
	private UserDB userDb;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) {
		UserModel user = userDb.findByUsername(username);
		
		Set<GrantedAuthority>grantedAuthorities = new HashSet<>();
		for (RoleModel role :user.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
			
		}
		
		return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
		
	}
	
}