package com.shinkson47.SplashX5.Game.Server.chat;

public class MessageBase {
	public String Message = "";
	public long Time = 0L;
	public int PlayerID = -1;
	
	public MessageBase(String message, int id) {
		Message = message;
		Time = System.currentTimeMillis();
		PlayerID = id;
	}
	
}
