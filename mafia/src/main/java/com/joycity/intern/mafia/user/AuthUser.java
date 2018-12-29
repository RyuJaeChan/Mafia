package com.joycity.intern.mafia.user;

import lombok.Data;

@Data
public class AuthUser {
	private String userId;
	private Integer roomId;
	private String password;

	public AuthUser() {

	}
	
	public AuthUser(User user) {
		this.userId = user.getId();
		this.password = user.getPassword();
	}

}