package com.kishore.taskproject.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthResponse {

	private String token;
	private String tokenType="Bearer";
	private String refreshToken;
	
	public JwtAuthResponse(String token,String refreshToken) {
		this.token=token;
		this.refreshToken=refreshToken;
	}

}
