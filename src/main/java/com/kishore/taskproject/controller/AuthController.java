package com.kishore.taskproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kishore.taskproject.entity.RefreshToken;
import com.kishore.taskproject.entity.Users;
import com.kishore.taskproject.payload.JwtAuthResponse;
import com.kishore.taskproject.payload.LoginDTO;
import com.kishore.taskproject.payload.LogoutRequest;
import com.kishore.taskproject.payload.RefreshTokenRequest;
import com.kishore.taskproject.payload.UserDTO;
import com.kishore.taskproject.repository.UserRepositary;
import com.kishore.taskproject.security.JwtTokenProvider;
import com.kishore.taskproject.service.RefreshTokenService;
import com.kishore.taskproject.service.UserService;

import jakarta.validation.Valid;

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
	
	@Autowired
	private RefreshTokenService refreshTokenService;
	
	@Autowired
	private UserRepositary userRepo;
	
	@PostMapping("/register")
	public ResponseEntity createUser(@Valid @RequestBody UserDTO userdto) {
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
		
		String token =
		        jwtTokenProvider.generateToken(authentication);

		Users user =
		        userRepo.findByEmail(
		                loginDto.getEmail())
		                .orElseThrow();

		RefreshToken refreshToken =
		        refreshTokenService
		                .createRefreshToken(user);

		return ResponseEntity.ok(
		        new JwtAuthResponse(
		                token,
		                refreshToken.getToken()));
	}
	
	@PostMapping("/refresh-token")
	public ResponseEntity<JwtAuthResponse> refreshToken(
	        @RequestBody RefreshTokenRequest request){

	    RefreshToken refreshToken =
	            refreshTokenService.findByToken(
	                    request.getRefreshToken());

	    refreshTokenService.verifyExpiration(
	            refreshToken);

	    String token =
	            jwtTokenProvider.generateTokenFromEmail(
	                    refreshToken.getUser().getEmail());

	    return ResponseEntity.ok(
	            new JwtAuthResponse(
	                    token,
	                    refreshToken.getToken()));
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logout(
	        @RequestBody LogoutRequest request){

	    refreshTokenService.deleteByToken(
	            request.getRefreshToken());

	    return ResponseEntity.ok(
	            "Logged out successfully");
	}
}
