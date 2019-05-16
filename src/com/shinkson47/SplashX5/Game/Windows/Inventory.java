package com.shinkson47.SplashX5.Game.Windows;

import java.awt.Color;
import java.awt.Graphics;

import com.shinkson47.SplashX5.Client.Client;
import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Client.ClientRenderer;
import com.shinkson47.SplashX5.Game.Entities.Player.Player;
import com.shinkson47.SplashX5.Game.Enumerator.InventoryAreas;
import com.shinkson47.SplashX5.Game.Resources.ResourceManager;
import com.shinkson47.SplashX5.Game.Resources.Tiles.TileBase;
import com.shinkson47.SplashX5.Game.World.CurrentMap;

public class Inventory {

	public static final int TileScale = 4;
	public static InventoryAreas area = InventoryAreas.Inventory;
	public static TileBase[] Tiles = new TileBase[25565];
	public static int x = 0, y = 0;

	public static void init() {
		area = InventoryAreas.HotBar;
		x = Player.players[Client.PlayerID].inventory.HotBarSI;
	}
	
	public static void Update() {
		
	}
	
	public static void RenderFrame() {
		ClientRenderer.FrameUpdated = true;
		ClientRenderer.CauseBackgroundUpdate = true;
		Graphics graphics = ClientRenderer.graphics;
		
		//Background
		//This will only draw chunks that have been generated.
		//For every tile on screen, in x and y directions
		for (int x = 0; x <= (int) ClientWindow.window.getWidth() / (Game.TileSize / Inventory.TileScale); x++) {
		for (int y = 0; y <= (int) ClientWindow.window.getHeight() / ((Game.TileSize - Game.yoff) / Inventory.TileScale); y++) {
		
		//Display tile
		try {
			//If there is a tile, draw it
			if (CurrentMap.TileSet[x][y] != null) {
					graphics.drawImage(CurrentMap.TileSet[x][y].Texture, (Game.TileSize * x) / Inventory.TileScale,(((Game.TileSize - Game.yoff) / Inventory.TileScale) * y),Game.TileSize / Inventory.TileScale, (Game.TileSize + Game.yoff) / Inventory.TileScale, null,null);
			}
			
			} catch (Exception e) {
					graphics.drawImage(ResourceManager.getTexture("Tiles/mapnull"), (Game.TileSize * x),(Game.TileSize * y),Game.TileSize / Inventory.TileScale, (Game.TileSize + Game.yoff) / Inventory.TileScale,null,null);
			}
		try {
		//Display fore tile.
			graphics.drawImage(CurrentMap.TileSet[x][y].ForeTile.Texture, (Game.TileSize * x) / Inventory.TileScale,(((Game.TileSize - Game.yoff) / Inventory.TileScale) * y) - (Game.yoff * 2 / Inventory.TileScale),Game.TileSize / Inventory.TileScale, (Game.TileSize + Game.yoff) / Inventory.TileScale, null,null);
			} catch  (Exception e) {}
			}
		}
		
		//Backing boxes
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fillRoundRect(ClientWindow.window.getWidth() / 2 - (Game.TileSize * Player.players[Client.PlayerID].inventory.Inventory.length) - Game.TileSize, ClientWindow.window.getHeight() / 2 - (Game.TileSize * Player.players[Client.PlayerID].inventory.Inventory[1].length) - Game.TileSize, (Game.TileSize * 2) * Player.players[Client.PlayerID].inventory.Inventory.length + Game.TileSize, (Game.TileSize * 2) * Player.players[Client.PlayerID].inventory.Inventory[1].length + Game.TileSize + Game.yoff,30,30);	
		graphics.fillRoundRect((ClientWindow.window.getWidth() / 2) - (Game.TileSize * 10), Game.TileSize - Game.yoff + 2, Game.TileSize * 19, (Game.yoff * 3) + Game.TileSize,30,30);	
		
		graphics.setColor(Color.white);
		graphics.drawRoundRect(ClientWindow.window.getWidth() / 2 - (Game.TileSize * Player.players[Client.PlayerID].inventory.Inventory.length) - Game.TileSize, ClientWindow.window.getHeight() / 2 - (Game.TileSize * Player.players[Client.PlayerID].inventory.Inventory[1].length) - Game.TileSize, (Game.TileSize * 2) * Player.players[Client.PlayerID].inventory.Inventory.length + Game.TileSize, (Game.TileSize * 2) * Player.players[Client.PlayerID].inventory.Inventory[1].length + Game.TileSize + Game.yoff,30,30);
		graphics.drawRoundRect((ClientWindow.window.getWidth() / 2) - (Game.TileSize * 10), Game.TileSize - Game.yoff + 2, Game.TileSize * 19, (Game.yoff * 3) + Game.TileSize,30,30);	
		
		graphics.setColor(Color.DARK_GRAY);
		
		//inventory boxes
		for (int x = 0; x <= Player.players[Client.PlayerID].inventory.Inventory.length - 1; x++) {
			for (int y = 0; y <= Player.players[Client.PlayerID].inventory.Inventory[x].length - 1; y++) {
				try {
					graphics.fillRect(ClientWindow.window.getWidth() / 2 - (Game.TileSize * Player.players[Client.PlayerID].inventory.Inventory.length) + ((Game.TileSize * 2) * x), ClientWindow.window.getHeight() / 2 - (Game.TileSize * Player.players[Client.PlayerID].inventory.Inventory[x].length) + ((Game.TileSize * 2) * y), 36, 53);			
				}catch (Exception e) {}
			}	
		}

		
		//Display hotbar boxes
		for (int i = 0; i <= Player.players[Client.PlayerID].inventory.HotBar.length; i++) {
					graphics.fillRect((ClientWindow.window.getWidth() / 2) - (32 * 10) + (64 * i),Game.TileSize, 36, 53);
		}
		
		
		//Display selected inventory item
		graphics.setColor(Color.cyan);
		if (Inventory.area == InventoryAreas.Inventory) {
			graphics.fillRect(ClientWindow.window.getWidth() / 2 - (Game.TileSize * Player.players[Client.PlayerID].inventory.Inventory.length) + ((Game.TileSize * 2) * Inventory.x) - 1, ClientWindow.window.getHeight() / 2 - (Game.TileSize * Player.players[Client.PlayerID].inventory.Inventory[0].length) + ((Game.TileSize * 2) * Inventory.y) - 1, 38, 54);	
		} else {
			graphics.fillRect((ClientWindow.window.getWidth() / 2) - (32 * 10) + (64 * Inventory.x) - 1, Game.TileSize - 1, 38, 54);
		}
		
		
		graphics.setColor(Color.white);	

		
		//inventory images
		for (int x = 0; x <= Player.players[Client.PlayerID].inventory.Inventory.length - 1; x++) {
			for (int y = 0; y <= Player.players[Client.PlayerID].inventory.Inventory[x].length - 1; y++) {
				try {
					graphics.drawImage(Player.players[Client.PlayerID].inventory.Inventory[x][y].tile.Texture, ClientWindow.window.getWidth() / 2 - (Game.TileSize * Player.players[Client.PlayerID].inventory.Inventory.length) + ((Game.TileSize * 2) * x), ClientWindow.window.getHeight() / 2 - (Game.TileSize * Player.players[Client.PlayerID].inventory.Inventory[x].length) + ((Game.TileSize * 2) * y),null, null);
					graphics.drawString(String.valueOf(Player.players[Client.PlayerID].inventory.Inventory[x][y].count), ClientWindow.window.getWidth() / 2 - (Game.TileSize * Player.players[Client.PlayerID].inventory.Inventory.length) + ((Game.TileSize * 2) * x), ClientWindow.window.getHeight() / 2 - (Game.TileSize * Player.players[Client.PlayerID].inventory.Inventory[x].length) + ((Game.TileSize * 2) * y) + Game.TileSize + Game.yoff);		
				}catch (Exception e) {}
			}	
		}
				
		//hotbar images
		for (int i = 0; i <= Player.players[Client.PlayerID].inventory.HotBar.length; i++) {
			try {
			graphics.drawImage(ResourceManager.getTexture("Tiles/" + Player.players[Client.PlayerID].inventory.HotBar[i].tile.tile.toString()), (ClientWindow.window.getWidth() / 2) - (32 * 10) + (64 * i),Game.TileSize, null, null);
			graphics.drawString(String.valueOf(Player.players[Client.PlayerID].inventory.HotBar[i].count), (ClientWindow.window.getWidth() / 2) - (32 * 10) + (64 * i), Game.TileSize * 2);
			} catch (Exception e) {}
		}
		
		//Display inmotion item
		try {
		graphics.drawImage(Player.players[Client.PlayerID].inventory.InMotion.tile.Texture, 100, Game.TileSize,null, null);
		} catch (Exception e) {}	
		
		}
}