package com.mynotes.api.pojo;

import lombok.Getter;

@Getter
public class AuthenticateRequest {
	private String email;
	private String password;
}
