package com.joycity.intern.mafia.room;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.joycity.intern.mafia.util.Message;
import com.joycity.intern.mafia.util.MessageType;
import com.joycity.intern.mafia.util.SocketComponent;

import lombok.Data;

@Component
@Data
public class GameRoomRepository {
	private ConcurrentHashMap<Integer, GameRoom> gameRoomList = new ConcurrentHashMap<>();

	private SocketComponent GameServerSocket = new SocketComponent(3302) {
		@Override
		public void receiveMessage(byte[] data) {
			Message m = new Message(data);
			System.out.println("message : " + m);

			if (m.getType() == MessageType.JOIN) {// JOIN_ACK
				System.out.println("recv JOIN ACK");
				GameRoom gameRoom = gameRoomList.get(m.getRoomId());
				if (gameRoom == null) {
					return;
				}
				gameRoom.setNumOfPlayer(gameRoom.getNumOfPlayer() + 1);
				System.out.println("room list : " + gameRoomList);
			} else if (m.getType() == MessageType.QUIT) {
				System.out.println("recv QUIT");
				GameRoom gameRoom = gameRoomList.get(m.getRoomId());
				if (gameRoom == null) {
					return;
				}
				gameRoom.setNumOfPlayer(gameRoom.getNumOfPlayer() - 1);
				if (gameRoom.getNumOfPlayer() == 0) {

					gameRoomList.remove(m.getRoomId());
				}
				System.out.println("room list : " + gameRoomList);
			}
		}
	};

	public void addGameRoom(Integer id, GameRoom gameRoom) {
		gameRoomList.put(id, gameRoom);
	}

	public void delGameRoom(Integer id) {
		gameRoomList.remove(id);
	}

	public Collection<GameRoom> getGameRoomList() {
		return gameRoomList.values();
	}

	public GameRoom getGameRoom(Integer id) {
		return gameRoomList.get(id);
	}

	// 정원 초과 - null
	// 해당 방이 없어도 null
	public GameRoom joinGameRoom(Integer id, String userId) {
		byte[] res = GameServerSocket.sendAndReceive(new Message(MessageType.JOIN_REQ, id, userId, "test").toBytes());
		Message m = new Message(res);

		if (m.getText().equals("fail")) {
			return null;
		}

		GameRoom gameRoom = gameRoomList.get(id);
		if (gameRoomList.get(id).getNumOfPlayer() > 4) {
			return null;
		}

		return gameRoom;
	}

	public GameRoom createRoom(String userId) {
		byte[] res = GameServerSocket.sendAndReceive(new Message(MessageType.CREATE, 0, userId, "test").toBytes());
		Message m = new Message(res);

		System.out.println("response message : " + m);

		if (m.getType() != 2) {
			return null;
		}

		GameRoom gameRoom = new GameRoom();
		gameRoom.setId(m.getRoomId());
		gameRoom.setNumOfPlayer(0);
		// gameRoom.setUrl("10.255.252.84");
		gameRoom.setUrl("192.168.245.1");
		gameRoom.setPort(3302);

		gameRoomList.put(m.getRoomId(), gameRoom);

		System.out.println("room list : " + gameRoomList);

		return gameRoom;
	}

	public void connect() {
		GameServerSocket.connectRequest();
	}

	public void sendmessage() {
		GameServerSocket.sendMessage(new Message(1, 0, "test Serv", "test message!").toBytes());
	}

}
