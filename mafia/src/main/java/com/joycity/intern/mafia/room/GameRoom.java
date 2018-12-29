package com.joycity.intern.mafia.room;

import lombok.Data;

@Data
public class GameRoom {
	private Integer id;
	private String url;
	private Integer port;
	private Integer numOfPlayer = 0;
}
