package com.kishore.taskproject.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kishore.taskproject.entity.Users;
import com.kishore.taskproject.repository.UserRepositary;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepositary userRepo;

	@Override
	public UserDetails loadUserByUsername(String email)
	        throws UsernameNotFoundException {

	    Users user = userRepo.findByEmail(email)
	            .orElseThrow(() ->
	                    new UsernameNotFoundException(
	                            "User not found"));

	    return User.builder()
	            .username(user.getEmail())
	            .password(user.getPassword())
	            .authorities(user.getRole())
	            .build();
	}
	
	  private Collection<? extends GrantedAuthority> userAuthorization(Set<String> roles){
		  return roles.stream().map(
				  role -> new SimpleGrantedAuthority(role)
				  ).collect(Collectors.toList());
	  }

}
