package com.shinkson47.SplashX5.Game.Events.Keys;

import com.shinkson47.SplashX5.Client.Client;
import com.shinkson47.SplashX5.Game.Entities.Player.Player;
import com.shinkson47.SplashX5.Game.Entities.Player.PlayerBase;
import com.shinkson47.SplashX5.Game.Enumerator.Gamemode;
import com.shinkson47.SplashX5.Game.Enumerator.Realms;
import com.shinkson47.SplashX5.Game.Events.EventHandler;
import com.shinkson47.SplashX5.Game.Windows.Game;
import com.shinkson47.SplashX5.Interfaces.IKeyHandler;

public class DebugKeyHandler implements IKeyHandler{

	public static void process() {
		switch (EventHandler.key.getKeyChar()) {	
		case '@':
			//ClientWindow.IgnoreHiddenWindow = true;
			//ClientWindow.window.setVisible(false);
			//ClientCommandline.run();
			//ClientWindow.window.setVisible(true);
			break;
		case '+':
			if (Client.PlayerID < Player.players.length) {Client.PlayerID++;}
			if (Player.players[Client.PlayerID] == null) {Player.Instantiate(new PlayerBase(Client.PlayerID, Game.CurrentMap.CharStartX, Game.CurrentMap.CharStartY, Gamemode.SurviveAndThrive, Realms.Overworld));}
			break;
		case '-':
			if (Client.PlayerID > 0) {Client.PlayerID--;}
			break;			
	}
	}
	
}
