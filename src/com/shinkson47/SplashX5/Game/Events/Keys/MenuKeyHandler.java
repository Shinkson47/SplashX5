package com.shinkson47.SplashX5.Game.Events.Keys;

import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Game.Events.EventHandler;
import com.shinkson47.SplashX5.Game.Windows.Menu;
import com.shinkson47.SplashX5.Interfaces.IKeyHandler;

public class MenuKeyHandler implements IKeyHandler{

	public static void process() {
		switch (EventHandler.key.getKeyChar()) {
		case 'w':
			Menu.Scroll(-1);
			break;
			
		case 's':
			Menu.Scroll(1);
			break;
		
		case ' ':
			Menu.Select();
			break;
			
		case '#':
			ClientWindow.SetWindow(Windows.TileDev);
			break;
		
		
	}
		
	}
	
}
