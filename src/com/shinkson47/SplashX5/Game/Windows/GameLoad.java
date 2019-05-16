package com.shinkson47.SplashX5.Game.Windows;

import java.awt.Color;
import java.awt.Graphics;

import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Client.ClientRenderer;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Game.Resources.ResourceManager;
import com.shinkson47.SplashX5.Game.World.Maps;
import com.shinkson47.SplashX5.Game.World.Voroni;

public class GameLoad {

	public static int LoadPercent = 0;

	public static void init() {
		//Server.StartServer();
		//Client.Connect():
		ClientWindow.setFontSize(20f);
		Maps.GenerateNew();
		ClientWindow.SetWindow(Windows.Game);
	}

	public static void Update() {
		
	}	
	
	public static void RenderFrame() {
		Graphics graphics = ClientRenderer.graphics;
		ClientRenderer.FrameUpdated = true;
		graphics.drawImage(com.shinkson47.SplashX5.Game.Resources.ResourceManager.getTexture("/UI/Overlay"), 0,0,null,null);
		graphics.drawImage(ResourceManager.getTexture("/UI/NewWorldTitle"), (ClientWindow.window.getWidth() / 2) - 150, 40,null,null);
		graphics.drawImage(ResourceManager.getTexture("/UI/MenuFooter"), (ClientWindow.window.getWidth() / 2) - 150, ClientWindow.window.getHeight() - 40,null,null);	
		
		
		graphics.setColor(Color.white);
		
		graphics.drawRoundRect((ClientWindow.window.getWidth() / 2) - 170, (ClientWindow.window.getHeight() / 2) - 130, 360, 150, 10, 10);
		
		if (!Voroni.generated) { 
			graphics.drawString("Generating Biomes",ClientWindow.window.getWidth() / 2 - 150,ClientWindow.window.getHeight() / 2 - 80);
			return;
		} else {
			graphics.drawString("Generating Chunks",ClientWindow.window.getWidth() / 2 - 150,ClientWindow.window.getHeight() / 2 - 80);
		}
		graphics.setColor(Color.gray);
		graphics.fillRect((ClientWindow.window.getWidth() / 2) - 125 ,ClientWindow.window.getHeight() / 2 - 50, 250, 25);
		
		graphics.setColor(Color.red);
		graphics.fillRect((ClientWindow.window.getWidth() / 2) - 125 ,ClientWindow.window.getHeight() / 2 - 50, (100 * GameLoad.LoadPercent) / 250, 25);
		
		graphics.setColor(Color.white);
		graphics.drawRect((ClientWindow.window.getWidth() / 2) - 125  ,ClientWindow.window.getHeight() / 2 - 50, 250, 25);
	}
}
