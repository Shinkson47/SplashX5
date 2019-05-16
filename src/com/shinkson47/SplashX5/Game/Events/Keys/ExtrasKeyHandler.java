package com.shinkson47.SplashX5.Game.Events.Keys;

import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Game.Events.EventHandler;
import com.shinkson47.SplashX5.Game.Windows.Extras;
import com.shinkson47.SplashX5.Interfaces.IKeyHandler;

public class ExtrasKeyHandler implements IKeyHandler{
	
	public static int y;

	public static void process() {
		switch (EventHandler.key.getKeyChar()) {
		case 'w':
			Extras.Scroll(-1);
			break;
		case 's':
			Extras.Scroll(1);
			break;
			
		case ' ':
			Extras.Select();
			break;			
	}
		
		if (EventHandler.key.getKeyCode() == 27) { ClientWindow.SetWindow(Windows.Menu);} 
	}
	
}
