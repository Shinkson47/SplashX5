package com.shinkson47.SplashX5.Game.Windows;

import java.awt.Color;
import java.awt.Graphics;

import com.shinkson47.SplashX5.Client.Client;
import com.shinkson47.SplashX5.Client.ClientRenderer;
import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Client.Logger;
import com.shinkson47.SplashX5.Game.Enumerator.ClientState;
import com.shinkson47.SplashX5.Game.Enumerator.LogState;
import com.shinkson47.SplashX5.Game.Events.ControllerHandler;
import com.shinkson47.SplashX5.Game.Events.EventHandler;
import com.shinkson47.SplashX5.Game.Resources.SoundManager;
import com.shinkson47.SplashX5.Game.Resources.Tiles.Crafting;
import com.shinkson47.SplashX5.Game.Resources.Tiles.Tiles;
import com.shinkson47.SplashX5.Game.World.Maps;

public class Init {

	private static long InitStartTime = System.currentTimeMillis();	
	public static void init() {
		//Code ran when the client is starting, including the code to start the client. Most will go here.
				if (Client.state != ClientState.Init) {
					Logger.log("Trying to initialise, but the client is not in a valid state!", Init.class, LogState.Warn);
					return;
				}
				Logger.log("Initialising Client", Init.class, LogState.Info);
				
				DisplayLoad(39);
				EventHandler.init();
				ControllerHandler.init();

				DisplayLoad(45);
				Tiles.init();
				Crafting.init();
				
				DisplayLoad(51);
				Maps.init();
				
				DisplayLoad(57);
				SoundManager.init();
				
				DisplayLoad(63);
				Logger.log("Done! This stage took " + (System.currentTimeMillis() - InitStartTime) + "Ms", Init.class, LogState.Info);
				InitStartTime = System.currentTimeMillis();
	}
	
	private static void DisplayLoad(int percent) {
		Client.LoadPercent = percent;
		ClientRenderer.Update();
		}
	
	public static void Update() {
		
	}
	
	public static void RenderFrame() {
		Graphics graphics = ClientRenderer.graphics;
		ClientRenderer.FrameUpdated = true;
		graphics.drawImage(com.shinkson47.SplashX5.Game.Resources.ResourceManager.getTexture("/UI/Overlay"), 0,0,null,null);
		
		graphics.setColor(Color.gray);
		graphics.fillRect((ClientWindow.window.getWidth() / 2) - 125 ,ClientWindow.window.getHeight() / 2 - 50, 250, 25);
		
		graphics.setColor(Color.red);
		graphics.fillRect((ClientWindow.window.getWidth() / 2) - 125 ,ClientWindow.window.getHeight() / 2 - 50, (100 * Client.LoadPercent) / 250, 25);
		
		graphics.setColor(Color.white);
		graphics.drawRect((ClientWindow.window.getWidth() / 2) - 125  ,ClientWindow.window.getHeight() / 2 - 50, 250, 25);
	
		
	}

}

