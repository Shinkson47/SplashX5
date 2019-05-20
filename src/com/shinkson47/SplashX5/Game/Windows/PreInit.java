package com.shinkson47.SplashX5.Game.Windows;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import com.shinkson47.SplashX5.Client.Client;
import com.shinkson47.SplashX5.Client.ClientRenderer;
import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Client.Logger;
import com.shinkson47.SplashX5.Game.Enumerator.ClientState;
import com.shinkson47.SplashX5.Game.Enumerator.LogState;
import com.shinkson47.SplashX5.Game.Resources.ResourceManager;

public class PreInit {

	private static long InitStartTime = System.currentTimeMillis();
	private static int framecount = 0;
	public static void init() {
		//Code ran before the client thread is loaded
		if (Client.state != ClientState.PreInit) {
			Logger.log("Trying to preinitialise, but the client is already initialised!", PreInit.class, LogState.Warn);
			return;
		}

		
		Logger.log("PreInitialising Client", PreInit.class, LogState.Info);

		ClientWindow.init(); //window is set before the client is loaded so the user can be shown that the client is loading.
		
		
		while (framecount < 40) {
			ClientRenderer.Update();
			framecount++;
		}
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

	
	public static void RenderFrame() {
		ClientRenderer.FrameUpdated = true;
		Graphics graphics = ClientRenderer.graphics;
		
		graphics.setColor(Color.black);
		graphics.fillRect(0,0,ClientWindow.width, ClientWindow.height);
		
		Image java = ResourceManager.getTexture("UI/Java");
		graphics.drawImage(java, (ClientWindow.window.getWidth() / 2) - (java.getWidth(null) / 2), (ClientWindow.window.getHeight() / 2) - (java.getHeight(null) / 2), null, null);
		
		graphics.setColor(Color.white);

		graphics.drawString("Splash X5 is powered by", (ClientWindow.window.getWidth() / 2) - (java.getWidth(null) / 2) + 25, (ClientWindow.window.getHeight() / 2) - (java.getHeight(null) / 2));
		
	}
	

}
