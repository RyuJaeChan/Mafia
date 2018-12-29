package com.joycity.intern.mafia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.joycity.intern.mafia.room.GameRoomRepository;

@Controller
public class DashBoardController {

	@Autowired
	GameRoomRepository gameRoomRepository;

	@Autowired
	private SimpMessagingTemplate smt;

	@GetMapping("/dashboard")
	public String getDashBoard() {
		return "dashboard";
	}

	@GetMapping("/sendsocket")
	public void test() {
		System.out.println("/sendsocket");
		// gameRoomRepository.createGameRoom();
	}

	// client에서 보낸 메시지를 처리한다.
	// config에서 setApplicationDestinationPrefixes를 통해 등록한 url/send 이런식으로 클라이언트에서
	// 보낸다.
	// 메시지 응답은 /sub/gameroom/{roomid}" 이런식으로 하면 된다.
	@MessageMapping("/message")
	public void sendMessage(String message) {
		System.out.println("/message");

		smt.convertAndSend("/sub/dashboard", message);
	}

}
