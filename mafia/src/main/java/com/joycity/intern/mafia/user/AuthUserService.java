package com.joycity.intern.mafia.user;

import org.springframework.stereotype.Service;

@Service
public class AuthUserService {
	private UserRepository userRepository;

	public AuthUserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public AuthUser loadAuthUser(User reqUser) {
		User user = userRepository.findById(reqUser.getId());
		
		if(user == null) {	//등록되지 않은 사용자
			return null;
		}
		
		if(user.getPassword().equals(reqUser.getPassword()) == false) {
			return null;
		}
		
		return new AuthUser(user);
	}

}