package com.shinkson47.SplashX5.Game.Windows;

import com.shinkson47.SplashX5.Client.Client;
import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Client.Logger;
import com.shinkson47.SplashX5.Game.Enumerator.ClientState;
import com.shinkson47.SplashX5.Game.Enumerator.LogState;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Client.ClientRenderer;

public class PostInit {
	private static long InitStartTime = System.currentTimeMillis();	
	public static void init() {
		//Code ran once the client has started
		if (Client.state != ClientState.PostInit) {
			Logger.log("Trying to postinitialise, but the client is not in a valid state!", PostInit.class, LogState.Warn);
			return;
		}
		Logger.log("PostInitialising Client", PostInit.class, LogState.Info);
		DisplayLoad(100);
		
		//Post Init scripts

				
		//Finished post init
		Logger.log("Done! This stage took " + (System.currentTimeMillis() - InitStartTime) + "Ms", PostInit.class, LogState.Info);
		InitStartTime = System.currentTimeMillis();
		Client.state = ClientState.Running;
		ClientWindow.SetWindow(Windows.Menu);

	}
	
	private static void DisplayLoad(int percent) {
		Client.LoadPercent = percent;
		ClientRenderer.Update();
		}
	
	public static void Update() {
	}
	
	public static void RenderFrame() {
	}

}
