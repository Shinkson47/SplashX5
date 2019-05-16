package com.shinkson47.SplashX5.Game.Windows;

import com.shinkson47.SplashX5.Client.Client;
import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Client.Logger;
import com.shinkson47.SplashX5.Client.ClientRenderer;
import com.shinkson47.SplashX5.Game.Enumerator.ClientState;
import com.shinkson47.SplashX5.Game.Enumerator.LogState;
import com.shinkson47.SplashX5.Game.Resources.ResourceManager;

public class PreInit {

	private static long InitStartTime = System.currentTimeMillis();
	public static void init() {
		//Code ran before the client thread is loaded
		if (Client.state != ClientState.PreInit) {
			Logger.log("Trying to preinitialise, but the client is already initialised!", PreInit.class, LogState.Warn);
			return;
		}
	
		Logger.log("PreInitialising Client", PreInit.class, LogState.Info);

		ClientWindow.init(); //window is set before the client is loaded so the user can be shown that the client is loading.
		
		DisplayLoad(11);
		

		DisplayLoad(22);
		ResourceManager.init(); //Resources are needed for everything.
		
		DisplayLoad(33);
		Logger.log("Done! This stage took " + (System.currentTimeMillis() - InitStartTime) + "Ms", PreInit.class, LogState.Info);
		InitStartTime = System.currentTimeMillis();
	}
	
	private static void DisplayLoad(int percent) {
		Client.LoadPercent = percent;
		ClientRenderer.Update();
		}

	public static void Update() {
	}
	
	public static void RenderFrame() {
		ClientRenderer.FrameUpdated = true;
	}
	

}
