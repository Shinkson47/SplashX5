package com.shinkson47.SplashX5.Game.Events.Keys;

import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Client.ClientCommandline;
import com.shinkson47.SplashX5.Game.Events.EventHandler;
import com.shinkson47.SplashX5.Game.Windows.Game;
import com.shinkson47.SplashX5.Interfaces.IKeyHandler;

public class DebugKeyHandler implements IKeyHandler{
	
	public static int y;

	public static void process() {
		switch (EventHandler.key.getKeyChar()) {
		case '@':
			ClientWindow.IgnoreHiddenWindow = true;
			ClientWindow.window.setVisible(false);
			ClientCommandline.Run();
			ClientWindow.window.setVisible(true);
			break;
		case '+':
			y++;
			System.out.println(Game.TileSize);
			break;
		
		case '-':
			y--;
			System.out.println(Game.TileSize);
			break;
		
			
	}
	}
	
}
