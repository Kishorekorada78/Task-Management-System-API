package com.kishore.taskproject.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {
		
		// get the token from the header
		 String token=getToken(request);
		 
		// check the either valid or not
		 if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
			 String email=jwtTokenProvider.getEmailFromToken(token);
			 UserDetails userDetails=customUserDetailsService.loadUserByUsername(email);
			 UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken( userDetails,null,userDetails.getAuthorities());
			 SecurityContextHolder.getContext().setAuthentication(auth);
		 }
		 filterChain.doFilter(request, response);
		// if valid load  the user and set authentication
		
	}
	private String getToken(HttpServletRequest request) {
		String token=request.getHeader("Authorization");
		if(StringUtils.hasText(token) && token.startsWith("Bearer")) {
			return token.substring(7,token.length());
		}
		return null;
	}

}
