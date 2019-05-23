package com.shinkson47.SplashX5.Game.Windows;

import java.awt.Color;
import java.awt.Graphics;

import com.shinkson47.SplashX5.Client.ClientRenderer;
import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Game.Resources.ResourceManager;
import com.shinkson47.SplashX5.Game.Resources.Tiles.TileBase;
import com.shinkson47.SplashX5.Game.Resources.Tiles.Tiles;

public class TileViewer {
	private static TileBase CurrentTile; 
	private static int index = 0;
	
	public static void init() {
		index = 0;
		setTile();
	}
	


	public static void Update() {
	}
	
	public static void nextTile() {
		if (index < Tiles.TileCount - 1) {
			index++;
		}
		setTile();
	}
	
	public static void  prevTile() {
		if (index > 0) {
			index--;
		}
		setTile();
	}
	
	public static void setTile() {
		CurrentTile = Tiles.TileDictionary[index];
	}
	
	
	
	
	
	public static void RenderFrame() {
		ClientRenderer.FrameUpdated = true;
		Graphics graphics = ClientRenderer.graphics;
		
		graphics.drawImage(ResourceManager.getTexture("/UI/OverlayBlur"), 0,0,null,null);
		graphics.drawImage(ResourceManager.getTexture("/UI/ExtrasTitle"), (ClientWindow.window.getWidth() / 2) - 150, 40,null,null);
		graphics.drawImage(ResourceManager.getTexture("/UI/MenuFooter"), (ClientWindow.window.getWidth() / 2) - 150, ClientWindow.window.getHeight() - 40,null,null);	
	
		graphics.setColor(Color.white);
		graphics.drawString((Tiles.TileCount - 1) + " tiles loaded", (ClientWindow.window.getWidth() / 2) - 120, ClientWindow.window.getHeight() / 2 - 180);
		
		graphics.drawImage(CurrentTile.Texture, (ClientWindow.window.getWidth() / 2) - (Game.TileSize / 2), ClientWindow.window.getHeight() / 2 - (Game.TileSize - Game.yoff) - 90,null,null);

		graphics.drawString("Resouce name: " + CurrentTile.tile.toString(), (ClientWindow.window.getWidth() / 2) - 120, ClientWindow.window.getHeight() / 2 + 10);
		graphics.drawString("Friendly name: " + CurrentTile.FriendlyName,  (ClientWindow.window.getWidth() / 2) - 120, ClientWindow.window.getHeight() / 2 + 30);
		graphics.drawString("This tile causes damage: "+ CurrentTile.CausesDamage,  (ClientWindow.window.getWidth() / 2) - 120, ClientWindow.window.getHeight() / 2 + 50);
		graphics.drawString("Damage multiplyer: "+ CurrentTile.DamageMultiplyer,  (ClientWindow.window.getWidth() / 2) - 120, ClientWindow.window.getHeight() / 2 + 70);
		graphics.drawString("This tile causes an event: "+ CurrentTile.CausesEvent,  (ClientWindow.window.getWidth() / 2) - 120, ClientWindow.window.getHeight() / 2 + 90);
		graphics.drawString("Entity speed reduction: "+ CurrentTile.SpeedReduction,  (ClientWindow.window.getWidth() / 2) - 120, ClientWindow.window.getHeight() / 2 + 110);
		graphics.drawString("This tile is a fluid: "+ CurrentTile.IsFluid,  (ClientWindow.window.getWidth() / 2) - 120, ClientWindow.window.getHeight() / 2 + 130);
		graphics.drawString("This tile can be harvested: "+ CurrentTile.IsHarvestable,  (ClientWindow.window.getWidth() / 2) - 120, ClientWindow.window.getHeight() / 2 + 150);
		graphics.drawString("This tile can be walked on: "+ CurrentTile.Walkable,  (ClientWindow.window.getWidth() / 2) - 120, ClientWindow.window.getHeight() / 2 + 170);
		
		if (index > 0) {
			graphics.drawString("←", (int) ((int) (ClientWindow.window.getWidth() / 2) - 60), (ClientWindow.window.getHeight() / 2) - 90);
		}
		
		if (index < Tiles.TileCount - 1) {
			graphics.drawString("→", (int) ((int) (ClientWindow.window.getWidth() / 2) + 40), (ClientWindow.window.getHeight() / 2) - 90);
		}
		
		
	}
	
}
