package com.shinkson47.SplashX5.Game.Windows;

import java.awt.Color;
import java.awt.Graphics;

import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Client.ClientRenderer;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Game.Resources.ResourceManager;

public class Extras {
	//Game modifyers
	//settings
	//credits
	
	public static int SelectedIndex;

	public static void init() {
		
	}
	
	public static void Update() {
		
	}

	public static void Scroll(int i) {
		if (i > 0 && SelectedIndex > 2) { return; }
		if (i < 0 && SelectedIndex < 0) { return; }
		
		if (i == 1) { SelectedIndex++; }
		if (i == -1) { SelectedIndex--; }
	}

	public static void Select() {
	 switch (SelectedIndex) {
	 case 0: //credit
		 //Lewis
		 //Me
		 
		 //Devils workshop - tile textures
		 //Textures image
		 
		 //Sound
		 
		 //Logos
		 //Copywrite
		 //version
		 
		 break;
	
	 case 1: //Mods
		 //Windows
		 //Maps
		 //Blocks
		 //Entities
		 break;
	 case 2: //Maps
		 

	 case 3: //tile viewer
		 ClientWindow.SetWindow(Windows.TileViewer);
		 break;
	 case 4: //settings
		 ClientWindow.SetWindow(Windows.Settings);
		 break;
		 
	 }
	}
	
	public static void RenderFrame() {
		Graphics graphics = ClientRenderer.graphics;
		ClientRenderer.FrameUpdated = true;
		
		graphics.drawImage(ResourceManager.getTexture("/UI/OverlayBlur"), 0,0,null,null);
		graphics.drawImage(ResourceManager.getTexture("/UI/ExtrasTitle"), (ClientWindow.window.getWidth() / 2) - 150, 40,null,null);
		graphics.drawImage(ResourceManager.getTexture("/UI/MenuFooter"), (ClientWindow.window.getWidth() / 2) - 150, ClientWindow.window.getHeight() - 40,null,null);	
	
		graphics.setColor(Color.white);
		graphics.drawString("Credit", (int) ((int) (ClientWindow.window.getWidth() / 2) - 100), (ClientWindow.window.getHeight() / 2) - 100);
		graphics.drawString("Modifications", (int) ((int) (ClientWindow.window.getWidth() / 2) - 100), (ClientWindow.window.getHeight() / 2) - 80);
		graphics.drawString("Maps", (int) ((int) (ClientWindow.window.getWidth() / 2) - 100), (ClientWindow.window.getHeight() / 2) - 60);
		graphics.drawString("Tile viewer", (int) ((int) (ClientWindow.window.getWidth() / 2) - 100), (ClientWindow.window.getHeight() / 2) - 40);
		graphics.drawString("Settings", (int) ((int) (ClientWindow.window.getWidth() / 2) - 100), (ClientWindow.window.getHeight() / 2) - 20);
		
		
		graphics.drawRoundRect((ClientWindow.window.getWidth() / 2) - 150, (ClientWindow.window.getHeight() / 2) - 130, 300, 130, 10, 10);
		
		graphics.drawString("→", (int) ((int) (ClientWindow.window.getWidth() / 2) - 140), (Extras.SelectedIndex * 20) + (ClientWindow.window.getHeight() / 2) - 80);
	}
	
	
}
