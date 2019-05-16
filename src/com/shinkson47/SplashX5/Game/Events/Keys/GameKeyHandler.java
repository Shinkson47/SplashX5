package com.shinkson47.SplashX5.Game.Events.Keys;

import com.shinkson47.SplashX5.Client.Client;
import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Game.Entities.Player.Player;
import com.shinkson47.SplashX5.Game.Enumerator.Direction;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Game.Events.EventHandler;
import com.shinkson47.SplashX5.Game.Windows.Game;

public class GameKeyHandler {

	public static void process() {
		switch (EventHandler.key.getKeyChar()) {
		case 'e':
			ClientWindow.SetWindow(Windows.Inventory);
			Game.Exit();
			break;
		case 'w':
			Player.StartMove(Direction.N, Client.PlayerID);
			break;
		case 'a':
			Player.StartMove(Direction.W, Client.PlayerID);
			break;
		case 's':
			Player.StartMove(Direction.S, Client.PlayerID);
			break;
		case 'd':
			Player.StartMove(Direction.E, Client.PlayerID);
			break;
		default:
			break;
	}
		
		if (EventHandler.key.getKeyCode() == 27) { Game.InGame = false; Game.Exit(); ClientWindow.SetWindow(Windows.Menu); } 
	}
	
	public static void released() {
		switch (EventHandler.Rkey.getKeyChar()) {
		case 'w':
			Player.StopMove(Direction.N, Client.PlayerID);
			break;
		case 'a':
			Player.StopMove(Direction.W, Client.PlayerID);
			break;
		case 's':
			Player.StopMove(Direction.S, Client.PlayerID);
			break;
		case 'd':
			Player.StopMove(Direction.E, Client.PlayerID);
			break;
	}	
	}
	
}
