package com.shinkson47.SplashX5.Game.Server.chat;

import com.shinkson47.SplashX5.Game.Entities.Player.Player;
import com.shinkson47.SplashX5.Game.Enumerator.ETiles;
import com.shinkson47.SplashX5.Game.Resources.Tiles.TileBase;
import com.shinkson47.SplashX5.Game.Resources.Tiles.TileStack;

public class Chat {
	public static MessageBase[] log = new MessageBase[25565];
	public static int messagecount = 0;
	
	public static void newMessage(String message, int id) {
		String sub = message.substring(0,1);
		if (sub.equals("/")) {
			addMessage(ParseCommand(new MessageBase(message, id)), id);
		} else {
			addMessage(message, id);
		}
	}
	
	private static void addMessage(String message, int id) {
		log[messagecount] = new MessageBase(message, id);
		messagecount++;	
	}

	private static String ParseCommand(MessageBase messageBase) {
		//split the command
		String args[] = messageBase.Message.split(" ");
		
		//switch to command
		try {
		switch (args[0]) {
		case "/give":
			if (args.length < 2) {
				return "'give' requires at least one argument";
			}
			
			try {
				if (args.length < 4) {
					Player.players[messageBase.PlayerID].inventory.collect(new TileStack(new TileBase(-1,-1,ETiles.valueOf(args[1]),""), Integer.parseInt(args[2])));
					return "given player " + messageBase.PlayerID + " " + args[2] + " of " + args[1];
				} else {
					Player.players[Integer.parseInt(args[3])].inventory.collect(new TileStack(new TileBase(-1,-1,ETiles.valueOf(args[1]),""), Integer.parseInt(args[2])));
					return "given player " + args[3] + " " + args[2] + " of " + args[1];
				}
			} catch (Exception e) {	
				return "'give' failed to parse with these arguments, " + messageBase.Message;
			}
			
		default:
			return "No such command, " + args[0];
		}
		
		} catch (Exception e) {
			return "'give' failed to parse with these arguments, " + messageBase.Message;
		}
}
}
