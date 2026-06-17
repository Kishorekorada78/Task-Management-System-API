package com.kishore.taskproject.payload;

import lombok.Data;

@Data
public class LogoutRequest {
	private String refreshToken;

}
