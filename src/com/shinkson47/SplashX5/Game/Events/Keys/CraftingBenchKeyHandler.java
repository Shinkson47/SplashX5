package com.shinkson47.SplashX5.Game.Events.Keys;

import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Game.Events.EventHandler;

public class CraftingBenchKeyHandler {

	public static void process() {
		if (EventHandler.key.getKeyChar() == 'e') {
		ClientWindow.SetWindow(Windows.Game);}
	}
	
}
