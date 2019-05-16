package com.shinkson47.SplashX5.Game.Events.Keys;

import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Game.Events.EventHandler;
import com.shinkson47.SplashX5.Game.Windows.TileViewer;

public class TileViewerKeyHandler {
	public static void process() {
		switch (EventHandler.key.getKeyChar()) {
		case 'w':
			
			break;
		case 'a':
			TileViewer.prevTile();
			break;
		case 's':
			
			break;
		case 'd':
			TileViewer.nextTile();
			break;
			
		
	}
		
		if (EventHandler.key.getKeyCode() == 27) { ClientWindow.SetWindow(Windows.Extras);} 
	}
}
