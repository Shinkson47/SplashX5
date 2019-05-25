package com.shinkson47.SplashX5.Game.Windows;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseWheelEvent;

import com.shinkson47.SplashX5.Client.Client;
import com.shinkson47.SplashX5.Client.ClientRenderer;
import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Game.Entities.Player.Player;
import com.shinkson47.SplashX5.Game.Enumerator.InventoryAreas;
import com.shinkson47.SplashX5.Game.Resources.ResourceManager;
import com.shinkson47.SplashX5.Game.Resources.Tiles.TileStack;

public class CraftingBench {
	private static int selectorx = 0;
	private static int selectory = 0;
	private static int InventoryRow = 1;
	private static InventoryAreas area = InventoryAreas.Inventory;
	private static TileStack[][] CraftingMatrix = new TileStack[4][4];
	
	public static void init() {
		//area = InventoryAreas.HotBar;
	}
	
	public static void Update() {
		
	}
	
	public static void scroll(MouseWheelEvent arg0) {
			if (area == InventoryAreas.Inventory) {
				if (arg0.getPreciseWheelRotation() < 0) {
					if (InventoryRow > 0) {
						InventoryRow--;
					}
				} else {
					if (InventoryRow < Player.players[Client.PlayerID].inventory.Inventory[0].length -1) {
						InventoryRow++;
					}
				}
				
				
			}
	}

	
	
	public static void RenderFrame() {
		Graphics graphics = ClientRenderer.graphics;
		ClientRenderer.FrameUpdated = true;
		
		//Background
		//This will only draw chunks that have been generated.
		//For every tile on screen, in x and y directions
		for (int x = 0; x <= (int) ClientWindow.window.getWidth() / (Game.TileSize / Inventory.TileScale); x++) {
		for (int y = 0; y <= (int) ClientWindow.window.getHeight() / ((Game.TileSize - Game.yoff) / Inventory.TileScale); y++) {
		
		//Display tile
		try {
			//If there is a tile, draw it
			if (Game.CurrentMap.TileSet[x][y] != null) {
					graphics.drawImage(ResourceManager.getTexture(Game.CurrentMap.TileSet[x][y].Texture), (Game.TileSize * x) / Inventory.TileScale,(((Game.TileSize - Game.yoff) / Inventory.TileScale) * y),Game.TileSize / Inventory.TileScale, (Game.TileSize + Game.yoff) / Inventory.TileScale, null,null);
			}
			
			} catch (Exception e) {
					graphics.drawImage(ResourceManager.getTexture("Tiles/mapnull"), (Game.TileSize * x),(Game.TileSize * y),Game.TileSize / Inventory.TileScale, (Game.TileSize + Game.yoff) / Inventory.TileScale,null,null);
			}
		try {
		//Display fore tile.
			graphics.drawImage(ResourceManager.getTexture(Game.CurrentMap.TileSet[x][y].ForeTile.Texture), (Game.TileSize * x) / Inventory.TileScale,(((Game.TileSize - Game.yoff) / Inventory.TileScale) * y) - (Game.yoff * 2 / Inventory.TileScale),Game.TileSize / Inventory.TileScale, (Game.TileSize + Game.yoff) / Inventory.TileScale, null,null);
			} catch  (Exception e) {}
			}
		}
		
		//Backing boxes
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fillRoundRect((ClientWindow.window.getWidth() / 2) - (Game.TileSize * 10), Game.TileSize * 4 - Game.yoff + 2, Game.TileSize * 19, (Game.yoff * 3) + Game.TileSize,30,30);	
		graphics.fillRoundRect((ClientWindow.window.getWidth() / 2) - (Game.TileSize * 10), Game.TileSize - Game.yoff + 2, Game.TileSize * 19, (Game.yoff * 3) + Game.TileSize,30,30);	
		graphics.fillRoundRect((ClientWindow.window.getWidth() / 2) - (CraftingMatrix.length * Game.TileSize), Game.TileSize * 6, ((CraftingMatrix.length * 2) * Game.TileSize),((CraftingMatrix[0].length * 2) * Game.TileSize + Game.yoff),30,30);	
		
				
		graphics.setColor(Color.white);
		graphics.drawRoundRect((ClientWindow.window.getWidth() / 2) - (Game.TileSize * 10), Game.TileSize * 4 - Game.yoff + 2, Game.TileSize * 19, (Game.yoff * 3) + Game.TileSize,30,30);
		graphics.drawRoundRect((ClientWindow.window.getWidth() / 2) - (Game.TileSize * 10), Game.TileSize - Game.yoff + 2, Game.TileSize * 19, (Game.yoff * 3) + Game.TileSize,30,30);	
		graphics.drawRoundRect((ClientWindow.window.getWidth() / 2) - (CraftingMatrix.length * Game.TileSize), Game.TileSize * 6, ((CraftingMatrix.length * 2) * Game.TileSize),((CraftingMatrix[0].length * 2) * Game.TileSize + Game.yoff),30,30);
		
		graphics.drawString("Crafting", ClientWindow.window.getWidth() / 2 - (Game.TileSize * Player.players[Client.PlayerID].inventory.Inventory.length) - (Game.TileSize * (Player.players[Client.PlayerID].inventory.CraftingGrid[0].length * 2) + (Game.TileSize * 3)) , ClientWindow.window.getHeight() / 2 - (Game.TileSize * Player.players[Client.PlayerID].inventory.Inventory[1].length) - Game.TileSize - 10);
		graphics.drawString("Inventory", (ClientWindow.window.getWidth() / 2) - (Game.TileSize * 10) , Game.TileSize * 4 -10);
		
		graphics.setColor(Color.DARK_GRAY);
		//crafting boxes
		for (int i = 0; i <= Player.players[Client.PlayerID].inventory.Inventory.length; i++) {
					graphics.fillRect((ClientWindow.window.getWidth() / 2) - (32 * 10) + (64 * i),Game.TileSize * 4, 36, 53);
		}
		
		//Display hotbar boxes
		for (int i = 0; i <= Player.players[Client.PlayerID].inventory.HotBar.length; i++) {
					graphics.fillRect((ClientWindow.window.getWidth() / 2) - (32 * 10) + (64 * i),Game.TileSize, 36, 53);
		}
		
		//Crafting boxes
		for (int x = 0; x <= CraftingMatrix.length; x++) {
			for (int y = 0; y <= CraftingMatrix[x].length - 1; y++) {
			graphics.fillRect((ClientWindow.window.getWidth() / 2) - (CraftingMatrix.length * Game.TileSize) + ((Game.TileSize * 2) * x) + (Game.TileSize / 2), ((Game.TileSize) * 2) * y + (Game.TileSize * 6) + Game.yoff + 5, 36, 53);
		}
		}
				
		//Display selected inventory item
		graphics.setColor(Color.cyan);
		switch (Inventory.area) {
		case Inventory:
			graphics.fillRect(ClientWindow.window.getWidth() / 2 - (Game.TileSize * Player.players[Client.PlayerID].inventory.Inventory.length) + ((Game.TileSize * 2) * Inventory.x) - 1, ClientWindow.window.getHeight() / 2 - (Game.TileSize * Player.players[Client.PlayerID].inventory.Inventory[0].length) + ((Game.TileSize * 2) * Inventory.y) - 1, 38, 54);
			break;
		case CraftingGrid:
			graphics.fillRect(ClientWindow.window.getWidth() / 2 - (Game.TileSize * Player.players[Client.PlayerID].inventory.Inventory.length) - (Game.TileSize * (Player.players[Client.PlayerID].inventory.CraftingGrid[0].length * 2) + (Game.TileSize * 2)) + ((Game.TileSize * 2) * selectorx), ClientWindow.window.getHeight() / 2 - (Game.TileSize * Player.players[Client.PlayerID].inventory.Inventory[selectorx].length) + ((Game.TileSize * 2) * selectory), 36, 53);
			break;
		case HotBar:
			graphics.fillRect((ClientWindow.window.getWidth() / 2) - (32 * 10) + (64 * Inventory.x) - 1, Game.TileSize - 1, 38, 54);
			break;
		default:
			break;
		}
		
	graphics.setColor(Color.white);	
		//Inventory images
		for (int i = 0; i <= Player.players[Client.PlayerID].inventory.Inventory.length; i++) {
			try {
			graphics.drawImage((Image) ResourceManager.getTexture((Player.players[Client.PlayerID].inventory.Inventory[i][InventoryRow].tile.Texture)), (ClientWindow.window.getWidth() / 2) - (32 * 10) + (64 * i),Game.TileSize * 4, null, null);
			graphics.drawString(String.valueOf(Player.players[Client.PlayerID].inventory.Inventory[i][InventoryRow].count), (ClientWindow.window.getWidth() / 2) - (32 * 10) + (64 * i), Game.TileSize * 6);
			} catch (Exception e) {}
		}	
				
		//hotbar images
		for (int i = 0; i <= Player.players[Client.PlayerID].inventory.HotBar.length; i++) {
			try {
			graphics.drawImage(ResourceManager.getTexture(Player.players[Client.PlayerID].inventory.HotBar[i].tile.Texture), (ClientWindow.window.getWidth() / 2) - (32 * 10) + (64 * i),Game.TileSize, null, null);
			graphics.drawString(String.valueOf(Player.players[Client.PlayerID].inventory.HotBar[i].count), (ClientWindow.window.getWidth() / 2) - (32 * 10) + (64 * i), Game.TileSize * 2);
			} catch (Exception e) {}
		}
		
		graphics.drawImage(ResourceManager.getTexture("UI/ControllerButtons/L1"), (ClientWindow.window.getWidth() / 2) - (Game.TileSize * 10) + Game.yoff, Game.TileSize * 4, null, null);
		graphics.drawImage(ResourceManager.getTexture("UI/ControllerButtons/R1"), (ClientWindow.window.getWidth() / 2) + (Game.TileSize * 8) + Game.yoff , Game.TileSize * 4, null, null);
	
			
	
		
		//Display inmotion item
		//Don't place more drawing commands below this!
		try {
			
		if (Player.players[Client.PlayerID].inventory.InMotion == null) {return;}
		
		graphics.setColor(Color.DARK_GRAY);
		graphics.fillRoundRect(100 - Game.TileSize, Game.yoff, Game.TileSize * 3, (Game.TileSize + Game.yoff) * 2,30,30);
		
		graphics.setColor(Color.white);
		graphics.drawRoundRect(100 - Game.TileSize, Game.yoff, Game.TileSize * 3, (Game.TileSize + Game.yoff) * 2,30,30);
			
		graphics.drawImage(ResourceManager.getTexture(Player.players[Client.PlayerID].inventory.InMotion.tile.Texture), 100, Game.TileSize,null, null);
		
		graphics.drawString(String.valueOf(Player.players[Client.PlayerID].inventory.InMotion.count), 100, (Game.TileSize * 2) + Game.yoff);		
		
		} catch (Exception e) {}	
		
		}


	}

