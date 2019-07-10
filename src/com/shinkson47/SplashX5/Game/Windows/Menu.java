package com.shinkson47.SplashX5.Game.Windows;

import java.awt.Color;
import java.awt.Graphics;

import com.shinkson47.SplashX5.Client.Client;
import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Client.ClientRenderer;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Game.Resources.ResourceManager;

public class Menu{
	public static int SelectedIndex = 0;
	public static float fontsize = 20f;
	
	public static void Update() {	
		
	}

	public static void init() {
		ClientWindow.DefaultSize();
		ClientWindow.setFontSize(fontsize);
	}

	public static void Scroll(int i) {
		if (i < 0 && SelectedIndex > 0) { SelectedIndex--; }
		if (i > 0 && SelectedIndex < 2) { SelectedIndex++; }
	}

	public static void Select() { 
		switch (SelectedIndex) {
		case 0:
			ClientWindow.SetWindow(Windows.MapSelector);
			break;
		case 1:
			ClientWindow.SetWindow(Windows.Extras);
			break;
		case 2:
			Client.HaltClient(-1);
			break;
	}}
		
	public static void RenderFrame() {
			Graphics graphics = ClientRenderer.graphics;
			ClientRenderer.FrameUpdated = true;
			graphics.drawImage(com.shinkson47.SplashX5.Game.Resources.ResourceManager.getTexture("/UI/Overlay"), 0,0,null,null);
			graphics.drawImage(ResourceManager.getTexture("/UI/MainMenuTitle"), (ClientWindow.window.getWidth() / 2) - 150, 40,null,null);
			graphics.drawImage(ResourceManager.getTexture("/UI/MenuFooter"), (ClientWindow.window.getWidth() / 2) - 150, ClientWindow.window.getHeight() - 40,null,null);	
			
			
			graphics.setColor(Color.white);
			graphics.drawString("Play", (int) ((int) (ClientWindow.window.getWidth() / 2) - 100), (ClientWindow.window.getHeight() / 2) - 80);
			graphics.drawString("Extras", (int) ((int) (ClientWindow.window.getWidth() / 2) - 100), (ClientWindow.window.getHeight() / 2) - 60);
			graphics.drawString("Exit", (int) ((int) (ClientWindow.window.getWidth() / 2) - 100), (ClientWindow.window.getHeight() / 2) - 40);
			
			graphics.drawRoundRect((ClientWindow.window.getWidth() / 2) - 150, (ClientWindow.window.getHeight() / 2) - 130, 300, 130, 10, 10);
			

			graphics.drawString("→", (int) ((int) (ClientWindow.window.getWidth() / 2) - 140), (Menu.SelectedIndex * 20) + (ClientWindow.window.getHeight() / 2) - 80);
	}
	
}
