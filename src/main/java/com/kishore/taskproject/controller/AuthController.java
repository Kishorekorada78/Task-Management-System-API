package com.kishore.taskproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kishore.taskproject.payload.JwtAuthResponse;
import com.kishore.taskproject.payload.LoginDTO;
import com.kishore.taskproject.payload.UserDTO;
import com.kishore.taskproject.security.JwtTokenProvider;
import com.kishore.taskproject.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
   // post store the user in DB
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@PostMapping("/register")
	public ResponseEntity createUser(@RequestBody UserDTO userdto) {
		 System.out.println(userdto);
		return new ResponseEntity<>(userService.createUser(userdto),HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> loginUser(@RequestBody LoginDTO loginDto){
		Authentication authentication=
				authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
			System.out.println(authentication);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token= jwtTokenProvider.generateToken(authentication);    // get the toekn

		return ResponseEntity.ok(new JwtAuthResponse(token));
	}
	
}
