package com.shinkson47.SplashX5;

import com.shinkson47.SplashX5.Client.Client;
import com.shinkson47.SplashX5.Client.ClientHandler;
import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Client.Logger;
import com.shinkson47.SplashX5.Game.Enumerator.LogState;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Game.Windows.PreInit;

public class main {
	
	public static void main(String[] args){
		Logger.log("======================================", PreInit.class, LogState.Info);
		Logger.log("", PreInit.class, LogState.Info);
		Logger.log(" Splash X5 is loading client " + Client.ClientVersion, PreInit.class, LogState.Info);
		Logger.log("", PreInit.class, LogState.Info);
		Logger.log("======================================", PreInit.class, LogState.Info);
		
		ClientWindow.SetWindow(Windows.PreInit);
		ClientHandler.StartClient();
		}
}
