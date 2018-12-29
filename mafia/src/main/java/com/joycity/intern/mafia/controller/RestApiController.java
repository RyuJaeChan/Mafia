package com.joycity.intern.mafia.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.joycity.intern.mafia.room.GameRoom;
import com.joycity.intern.mafia.room.GameRoomRepository;
import com.joycity.intern.mafia.user.UserService;
import com.joycity.intern.mafia.util.ResultObject;

@RestController
public class RestApiController {
	@Autowired
	UserService userService;
	@Autowired
	GameRoomRepository gameRoomRepository;

	@GetMapping("/socket")
	void sock() {
		gameRoomRepository.connect();
	}

	@GetMapping("/send")
	void send() {
		gameRoomRepository.sendmessage();
	}

	@GetMapping("/test/{sessionKey}")
	void test(HttpSession session, @PathVariable String sessionKey) {
		System.out.println("/test");

		System.out.println("session : " + session.getAttribute(sessionKey));
	}

	@GetMapping("/test2")
	void test2(HttpSession session) {
		System.out.println("/test2");
		session.setAttribute("test1", "TEST VALUE");

		System.out.println("session : " + session.getAttribute("test1"));
		// System.out.println("session : " + session.getValue("test1"));
	}

	@GetMapping("/cookie")
	void test(HttpServletResponse response) {
		System.out.println("/set cookie");

		Cookie setCookie = new Cookie("JSESSION", "123456789000");
		setCookie.setMaxAge(-1);
		response.addCookie(setCookie);
	}

	// 방 목록 조회
	@GetMapping("/rooms")
	public ResultObject getRooms(@RequestHeader(value = "JSESSION", required = true) String sessionKey,
			HttpSession session) {

		System.out.println("/rooms (GET) : [" + sessionKey + "]");
		System.out.println("/rooms (GET) : [" + sessionKey.trim() + "]");

		/*
		 * 세션키가 안가져와지는데... AuthUser user = (AuthUser) session.getAttribute(sessionKey);
		 * System.out.println("User : " + user);
		 */
		List<GameRoom> list = new ArrayList<>(gameRoomRepository.getGameRoomList());
		System.out.println("gameroom list : " + list);

		/*
		 * if(list == null) { return ResultObject.builder() .setResult("fail")
		 * .setBody(list) .build(); }
		 */

		ResultObject obj = ResultObject.builder().setResult("success").setBody(list).build();

		System.out.println("/rooms result object : " + obj);

		return obj;
	}

	// 방 생성
	@PostMapping("/rooms")
	public ResultObject createRoom(@RequestHeader(value = "JSESSION", required = true) String sessionKey,
			HttpSession session) {
		System.out.println("/rooms (POST) : " + sessionKey + " (" + session.getAttribute(sessionKey) + ")");

		/*
		 * AuthUser user = (AuthUser) session.getAttribute(sessionKey.trim());
		 * System.out.println("User : " + user);
		 * 
		 * GameRoom res = gameRoomRepository.createRoom(user.getUserId());
		 */
		GameRoom res = gameRoomRepository.createRoom(sessionKey);
		System.out.println("create room res : " + res);

		if (res == null) {
			return ResultObject.builder().setResult("fail").setBody("create fail...").build();
		}

		ResultObject obj = ResultObject.builder().setResult("success").setBody(res).build();

		System.out.println("result object : " + obj);

		return obj;
	}

	// 방 입장
	@GetMapping("/rooms/{id}")
	public ResultObject joinGameRoom(@PathVariable Integer id,
			@RequestHeader(value = "JSESSION", required = true) String sessionKey) {
		System.out.println("/rooms/{id} (GET) : " + sessionKey);
		GameRoom gameRoom = gameRoomRepository.getGameRoom(id);

		if (gameRoom == null) {
			return ResultObject.builder().setResult("fail").setBody("can't find a room").build();
		}

		return ResultObject.builder().setResult("success").setBody(gameRoom).build();
	}

	@GetMapping("/matching")
	public ResultObject matching(@CookieValue(value = "JSESSION", required = true) String sessionKey) {
		return ResultObject.builder().setResult("success").setBody("matching").build();
	}

}
