package com.joycity.intern.mafia.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
//@Entity(name = "mafia_user")
@Entity(name = "user")
public class User {

	@Id
	@Column(name = "id", length = 16)
	private String id;

	@Column(name = "password", length = 32)
	private String password;

}
