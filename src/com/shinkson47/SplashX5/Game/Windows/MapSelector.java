package com.shinkson47.SplashX5.Game.Windows;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JOptionPane;

import com.shinkson47.SplashX5.Client.ClientRenderer;
import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Game.Resources.ResourceManager;

public class MapSelector {

	public static int SelectedIndex = 0;


	public static void init() {
		ClientWindow.setFontSize(20f);
	}
	
	public static void Update() {

	}
	
	public static void Scroll(int i) {
		if (i < 0 && SelectedIndex > 0) { SelectedIndex--; }
		if (i > 0 && SelectedIndex < 5) { SelectedIndex++; }
	}

	public static void Select() {
		switch (SelectedIndex) {
		case 0:
			try {
				String i = JOptionPane.showInputDialog("Custom seed [long]");
				long num = Long.parseLong(i);
				Game.CurrentMap.Seed = num;
			} catch (Exception e) { 
				JOptionPane.showMessageDialog(ClientWindow.window,"That seed is not valid");
			}
			break;
		case 1:
			try {
				String i = JOptionPane.showInputDialog("How many chunks will be generated at first? [int]");
				int num =  Integer.parseInt(i);
				Game.InitialChunkGenCount = num;
			} catch (Exception e) { 
				JOptionPane.showMessageDialog(ClientWindow.window,"That chunk count is not valid");
			}
			break;
		case 2:
			try {
				String i = JOptionPane.showInputDialog("World border [int]");
				int num =  Integer.parseInt(i);
				
				if (num < 10 || num > 25565) { throw new Exception("invalid border"); }
				Game.CurrentMap.WorldBorder = num;
			} catch (Exception e) { 
				JOptionPane.showMessageDialog(ClientWindow.window,"World border is not valid");
			}
			break;
		case 3:
			try {
				String i = JOptionPane.showInputDialog("Terrain noise scale (-1 to 1) [float]");
				float num = Float.parseFloat(i);
				
				if (num > 1f || num < -1f) { throw new Exception("invalid noise scale"); }
				Game.CurrentMap.NScale = num;
			} catch (Exception e) { 
				JOptionPane.showMessageDialog(ClientWindow.window,"Invalid noise scale");
			}
			break;
		case 4:
			try {
				String i = JOptionPane.showInputDialog("Biome noise scale (1 / 100) [float]");
				int num = Integer.parseInt(i);
				
				if (num > 1f || num < -1f) { throw new Exception("invalid noise scale"); }
				Game.CurrentMap.BScale = num;
			} catch (Exception e) { 
				JOptionPane.showMessageDialog(ClientWindow.window,"Invalid noise scale");
			}
			break;
		case 5:
			ClientWindow.SetWindow(Windows.GameLoad);
			break;
		}
			
	}

	public static void Edit(int i) {
		switch (SelectedIndex) {

		case 0:
			if (i > 0) {
				Game.CurrentMap.Seed = System.currentTimeMillis();
			} else {
				Game.CurrentMap.Seed = System.nanoTime();
			}
			break;
			
		case 1:
			if (i < 0 && Game.InitialChunkGenCount > 1 ) { Game.InitialChunkGenCount--;}
			if (i > 0 && Game.InitialChunkGenCount < 100 ) { Game.InitialChunkGenCount++;}
			break;

		case 2:
			if (i < 0 && Game.CurrentMap.WorldBorder > 100 ) {Game.CurrentMap.WorldBorder -= 100;}
			if (i > 0 && Game.CurrentMap.WorldBorder < 25465 ) { Game.CurrentMap.WorldBorder += 100;}
			
			if (Game.CurrentMap.BScale > Game.CurrentMap.WorldBorder) {Game.CurrentMap.WorldBorder = Game.CurrentMap.BScale;}
			break;

		case 3:
			if (i < 0 && Game.CurrentMap.NScale > -1f ) { Game.CurrentMap.NScale -= 0.001;}
			if (i > 0 && Game.CurrentMap.NScale < 1f ) { Game.CurrentMap.NScale += 0.001;}
			break;

		case 4:
			if (i < 0 && Game.CurrentMap.BScale > 10) { Game.CurrentMap.BScale--;}
			if (i > 0 && Game.CurrentMap.BScale < Game.CurrentMap.WorldBorder ) { Game.CurrentMap.BScale++;}
			
			break;		
		}
		
	}
	public static void RenderFrame() {
		Graphics graphics = ClientRenderer.graphics;
		ClientRenderer.FrameUpdated = true;
		
		graphics.drawImage(ResourceManager.getTexture("/UI/OverlayBlur"), 0,0,null,null);
		graphics.drawImage(ResourceManager.getTexture("/UI/NewWorldTitle"), (ClientWindow.window.getWidth() / 2) - 150, 40,null,null);
		graphics.drawImage(ResourceManager.getTexture("/UI/MenuFooter"), (ClientWindow.window.getWidth() / 2) - 150, ClientWindow.window.getHeight() - 40,null,null);	
		
		graphics.setColor(Color.white);
		graphics.drawString("Seed", (ClientWindow.window.getWidth() / 2) - 200, (ClientWindow.window.getHeight() / 2) - 100);
		graphics.drawString(String.valueOf(Game.CurrentMap.Seed), (ClientWindow.window.getWidth() / 2) + 200, (ClientWindow.window.getHeight() / 2) - 100);
		
		graphics.drawString("Initial chunk count", (ClientWindow.window.getWidth() / 2) - 200, (ClientWindow.window.getHeight() / 2) - 80);
		graphics.drawString(String.valueOf(Game.InitialChunkGenCount), (ClientWindow.window.getWidth() / 2) + 200, (ClientWindow.window.getHeight() / 2) - 80);
		
		graphics.drawString("World size", (ClientWindow.window.getWidth() / 2) - 200, (ClientWindow.window.getHeight() / 2) - 60);
		graphics.drawString(String.valueOf(Game.CurrentMap.WorldBorder), (ClientWindow.window.getWidth() / 2) + 200, (ClientWindow.window.getHeight() / 2) - 60);
		
		graphics.drawString("Terrain Noise scale", (ClientWindow.window.getWidth() / 2) - 200, (ClientWindow.window.getHeight() / 2) - 40);
		graphics.drawString(String.valueOf(Game.CurrentMap.NScale), (ClientWindow.window.getWidth() / 2) + 200, (ClientWindow.window.getHeight() / 2) - 40);
		
		graphics.drawString("Biome scale", (ClientWindow.window.getWidth() / 2) - 200, (ClientWindow.window.getHeight() / 2) - 20);
		graphics.drawString(String.valueOf(Game.CurrentMap.BScale), (ClientWindow.window.getWidth() / 2) + 200, (ClientWindow.window.getHeight() / 2) - 20);
		
		graphics.drawString("→", (ClientWindow.window.getWidth() / 2) - 250, (MapSelector.SelectedIndex * 20) + (ClientWindow.window.getHeight() / 2) - 100);
		
		graphics.drawRoundRect((ClientWindow.window.getWidth() / 2) - 260, (ClientWindow.window.getHeight() / 2) - 130, 750, 150, 10, 10);
		
		graphics.setColor(Color.cyan);	
		graphics.drawString("Generate!", (ClientWindow.window.getWidth() / 2) - 200, ClientWindow.window.getHeight() / 2);
	}
	
	
}
