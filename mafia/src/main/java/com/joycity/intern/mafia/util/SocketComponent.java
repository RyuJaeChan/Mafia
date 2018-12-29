package com.joycity.intern.mafia.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/*
 * 
 * CREATE SCHEMA `mafia` DEFAULT CHARACTER SET utf8 ;

 * 
 * 
 * */

public abstract class SocketComponent {
	private int port;

	private Socket socket;
	private Socket pollingSocket;

	private InputStream pollingIn;
	private OutputStream pollingOut;

	private OutputStream out;
	private InputStream in;

	public SocketComponent(int port) {
		System.out.println("[" + port + "] SocketComponent Init");
		this.port = port;
		connectRequest();
	}

	// 게임 서버가 접속할때까지 대기
	private Thread connectThread = new Thread(() -> {
		System.out.println("waiting gameserver connect...");
		try {
			socket = new Socket("127.0.0.1", port);
			pollingSocket = new Socket("127.0.0.1", port);

			out = socket.getOutputStream();
			in = socket.getInputStream();

			pollingIn = pollingSocket.getInputStream();
			pollingOut = pollingSocket.getOutputStream();

			this.pollingThread.start();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("[" + port + "] socket connect fail...");
		}
	});

	public void connectRequest() {
		connectThread.start();
	}

	public void sendMessage(byte[] data) {
		try {
			out.write(data);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 게임서버에서 전송하는 정보를 수신 후 처리
	private Thread pollingThread = new Thread(() -> {
		try {
			pollingOut.write(new Message(MessageType.POLLING, 0, "web", "polling req").toBytes());
			pollingOut.flush();

			byte[] data = new byte[152];
			while (pollingIn.read(data) != 0) {
				System.out.println("[" + port + "] polling recv data in pollingThread");
				receiveMessage(data);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	});

	public byte[] sendAndReceive(byte[] data) {
		byte[] recvData = new byte[152];
		try {
			out.write(data);
			out.flush();

			in.read(recvData);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return recvData;
	}

	abstract public void receiveMessage(byte[] message);

}
