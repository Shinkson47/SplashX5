package com.shinkson47.SplashX5.Game.Events.Keys;

import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Game.Events.EventHandler;
import com.shinkson47.SplashX5.Game.Windows.MapSelector;

public class MapSelectorKeyHandler {

	public static void process() {
		switch (EventHandler.key.getKeyChar()) {
		case 'w':
			MapSelector.Scroll(-1);
			break;
			
		case 's':
			MapSelector.Scroll(1);
			break;
		

		case 'a':
			MapSelector.Edit(-1);
			break;
			
		case 'd':
			MapSelector.Edit(1);
			break;
		
		case ' ':
			MapSelector.Select();
			break;
		
		
	}
	
		
	if (EventHandler.key.getKeyCode() == 27) { ClientWindow.SetWindow(Windows.Menu);} 
	}	
}

