package com.joycity.intern.mafia.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.joycity.intern.mafia.user.AuthUser;
import com.joycity.intern.mafia.user.AuthUserService;
import com.joycity.intern.mafia.user.User;
import com.joycity.intern.mafia.user.UserService;
import com.joycity.intern.mafia.util.ResultObject;

@RestController
public class AuthController {
	@Autowired
	AuthUserService authUserService;
	@Autowired
	UserService userService;

	@PostMapping(value = "/login", consumes = "application/json")
	public ResultObject login(@RequestBody User user, HttpSession session, HttpServletResponse response) {
		System.out.println("login user :" + user);
		AuthUser loginUser = authUserService.loadAuthUser(user);

		if (loginUser == null) {
			return ResultObject.builder().setResult("fail").setBody("login fail").build();
		}

		String sessionKey = UUID.randomUUID().toString();
		session.setAttribute(sessionKey, loginUser);

		System.out.println("sesion val : " + session.getAttribute(sessionKey));

		// 필요없어보이기도함
		/*
		 * Cookie setCookie = new Cookie("JSESSION", sessionKey);
		 * setCookie.setMaxAge(-1); response.addCookie(setCookie);
		 */
		ResultObject res = ResultObject.builder().setResult("success").setBody(sessionKey).build();

		System.out.println("response : " + res);

		return res;
	}

	@PostMapping(value = "/signup", consumes = "application/json")
	public ResultObject signUp(@RequestBody User user, HttpSession session) {
		// System.out.println("user : ");
		System.out.println("sighnup user : " + user);
		User savedUser = userService.insert(user);

		if (savedUser == null) {
			return ResultObject.builder().setResult("fail").setBody("signup fail").build();
		}

		AuthUser u = new AuthUser(savedUser);
		String sessionKey = UUID.randomUUID().toString();
		session.setAttribute(sessionKey, u);

		System.out.println("sesion val : " + session.getAttribute(sessionKey));

		ResultObject res = ResultObject.builder().setResult("success").setBody(sessionKey).build();

		System.out.println("response : " + res);

		return res;
	}

}
