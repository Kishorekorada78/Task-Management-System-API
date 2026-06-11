package com.kishore.taskproject.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JwtAuthResponse {

	private String token;
	private String tokenType="Bearer";
	
	public JwtAuthResponse(String token) {
		this.token=token;
	}

}
