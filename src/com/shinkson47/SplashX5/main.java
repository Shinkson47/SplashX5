package com.shinkson47.SplashX5;

import com.shinkson47.SplashX5.Client.ClientHandler;
import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;

public class main {
	
	public static void main(String[] args){
		ClientWindow.SetWindow(Windows.PreInit);
		ClientHandler.StartClient();
		}
	
}
