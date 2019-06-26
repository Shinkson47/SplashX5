package com.shinkson47.SplashX5.Game.Windows;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseWheelEvent;

import com.shinkson47.SplashX5.Client.Client;
import com.shinkson47.SplashX5.Client.ClientRenderer;
import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Game.Entities.Player.Player;
import com.shinkson47.SplashX5.Game.Enumerator.InventoryAreas;
import com.shinkson47.SplashX5.Game.Resources.ResourceManager;
import com.shinkson47.SplashX5.Game.Resources.SoundManager;
import com.shinkson47.SplashX5.Game.Resources.Tiles.Crafting;
import com.shinkson47.SplashX5.Game.Resources.Tiles.TileStack;

public class CraftingBench {
	public static int selectory = 0, selectorx = 0;
	private static int InventoryRow = 1;
	public static InventoryAreas area = InventoryAreas.Inventory;
	public static TileStack[][] CraftingMatrix = new TileStack[4][4];
	public static TileStack InMotion;
	public static boolean IsPicked = false;

	public static void init() {
		// area = InventoryAreas.HotBar;
	}

	public static void Update() {

	}

	// Interaction shit
	public static void scroll(MouseWheelEvent arg0) {
		if (area == InventoryAreas.Inventory)
			if (arg0.getPreciseWheelRotation() < 0) {
				if (InventoryRow > 0)
					InventoryRow--;
			} else if (InventoryRow < Player.players[Client.PlayerID].inventory.Inventory[0].length - 1)
				InventoryRow++;
	}

	public static void lift() {
		SoundManager.PlayMisc("/Runtime/ConfirmAlert", true);
		switch (area) {
		case Armor:
			break;
		case CraftingGrid:
			InMotion = CraftingMatrix[selectorx][selectory];
			CraftingMatrix[selectorx][selectory] = null;
			IsPicked = true;
			break;
		case HotBar:
			InMotion = Player.players[Client.PlayerID].inventory.HotBar[selectorx];
			Player.players[Client.PlayerID].inventory.HotBar[selectorx] = null;
			IsPicked = true;
			break;
		case Inventory:
			InMotion = Player.players[Client.PlayerID].inventory.Inventory[selectorx][selectory];
			Player.players[Client.PlayerID].inventory.Inventory[selectorx][selectory] = null;
			IsPicked = true;
			break;
		default:
			break;
		}

		try {
			if (InMotion == null)
				IsPicked = false;
			else
				IsPicked = true;
		} catch (Exception e) {
			IsPicked = false;
		}
	}

	TileStack temp;

	public static void drop() {
		SoundManager.PlayMisc("/Runtime/Confirm", true);
		switch (area) {
		case Armor:
			break;
		case CraftingGrid:
			try {
				if (CraftingMatrix[selectorx][selectory] != null) {
					// Tile is not empty!
					if (CraftingMatrix[selectorx][selectory].tile.tile == InMotion.tile.tile)
						if (CraftingMatrix[selectorx][selectory].count + InMotion.count > 64) {
							int diff = 64 - CraftingMatrix[selectorx][selectory].count;
							CraftingMatrix[selectorx][selectory].count = 64;
							InMotion.count -= diff;
							if (InMotion.count <= 0)
								IsPicked = false;
							Crafting.craft(CraftingMatrix, Client.PlayerID);
							return;
						} else {
							CraftingMatrix[selectorx][selectory].count += InMotion.count;
							InMotion = null;
							IsPicked = false;
							Crafting.craft(CraftingMatrix, Client.PlayerID);
							return;
						}
					TileStack temp = CraftingMatrix[selectorx][selectory];
					CraftingMatrix[selectorx][selectory] = InMotion;
					InMotion = temp;
					Crafting.craft(CraftingMatrix, Client.PlayerID);
					return;
				}
			} catch (Exception e) {
			}

			CraftingMatrix[selectorx][selectory] = InMotion;
			InMotion = null;
			IsPicked = false;
			Crafting.craft(CraftingMatrix, Client.PlayerID);
			break;
		case HotBar:
			try {
				if (Player.players[Client.PlayerID].inventory.HotBar[selectorx] != null) {
					// Tile is not empty!
					if (Player.players[Client.PlayerID].inventory.HotBar[selectorx].tile.tile == InMotion.tile.tile) {
						if (Player.players[Client.PlayerID].inventory.HotBar[selectorx].count + InMotion.count > 64) {
							int diff = 64 - Player.players[Client.PlayerID].inventory.HotBar[selectorx].count;
							Player.players[Client.PlayerID].inventory.HotBar[selectorx].count = 64;
							InMotion.count -= diff;
							InMotion = null;
							IsPicked = false;
							return;
						} else {
							Player.players[Client.PlayerID].inventory.HotBar[selectorx].count += InMotion.count;
							InMotion = null;
							IsPicked = false;
							return;
						}
					} else {
						TileStack temp = Player.players[Client.PlayerID].inventory.HotBar[selectorx];
						Player.players[Client.PlayerID].inventory.HotBar[selectorx] = InMotion;
						InMotion = temp;
						return;
					}
				} else {
					Player.players[Client.PlayerID].inventory.HotBar[selectorx] = InMotion;
					InMotion = null;
					IsPicked = false;
					return;
				}

			} catch (Exception e) {
			}

			break;
		case Inventory:
			try {
				if (Player.players[Client.PlayerID].inventory.Inventory[selectorx][InventoryRow] != null) {
					// Tile is not empty!
					if (Player.players[Client.PlayerID].inventory.Inventory[selectorx][InventoryRow].tile.tile == InMotion.tile.tile)
						if (Player.players[Client.PlayerID].inventory.Inventory[selectorx][InventoryRow].count + InMotion.count > 64) {
							int diff = 64 - Player.players[Client.PlayerID].inventory.Inventory[selectorx][InventoryRow].count;
							Player.players[Client.PlayerID].inventory.Inventory[selectorx][InventoryRow].count = 64;
							InMotion.count -= diff;
							if (InMotion.count <= 0)
								IsPicked = false;
							return;
						} else {
							Player.players[Client.PlayerID].inventory.Inventory[selectorx][InventoryRow].count += InMotion.count;
							InMotion = null;
							IsPicked = false;
							return;
						}
					TileStack temp = Player.players[Client.PlayerID].inventory.Inventory[selectorx][InventoryRow];
					Player.players[Client.PlayerID].inventory.Inventory[selectorx][InventoryRow] = InMotion;
					InMotion = temp;
					return;
				}
			} catch (Exception e) {
			}

			Player.players[Client.PlayerID].inventory.Inventory[selectorx][InventoryRow] = InMotion;
			InMotion = null;
			IsPicked = false;
			break;
		default:
			break;
		}

		try {
			if (InMotion == null)
				IsPicked = false;
			else
				IsPicked = true;
		} catch (Exception e) {
			IsPicked = false;
		}
	}

	public static void Single(int x, int y) {
		if (InMotion == null && IsPicked)
			IsPicked = false;
		if (InMotion != null)
			if (InMotion.count <= 0) {
				InMotion = null;
				IsPicked = false;
			}

		switch (area) {
		case Armor:
			break;
		case CraftingGrid:
			if (InMotion != null && CraftingMatrix[x][y] != null)
				if (InMotion.tile.tile != CraftingMatrix[x][y].tile.tile)
					return;
			if (CraftingMatrix[x][y] != null && InMotion != null)
				if (CraftingMatrix[x][y].count > InMotion.count) {
					InMotion.count++;
					CraftingMatrix[x][y].count--;
				} else {
					InMotion.count--;
					CraftingMatrix[x][y].count++;
				}

			if (CraftingMatrix[x][y] == null && InMotion != null) {
				CraftingMatrix[x][y] = new TileStack(InMotion.tile, 1);
				InMotion.count--;
			}

			if (CraftingMatrix[x][y] != null && InMotion == null) {
				InMotion = new TileStack(CraftingMatrix[x][y].tile, 1);
				CraftingMatrix[x][y].count--;
			}
			try {
				if (CraftingMatrix[x][y].count <= 0)
					CraftingMatrix[x][y] = null;
			} catch (Exception e) {
				CraftingMatrix[x][y] = null;
			}
			Crafting.craft(CraftingMatrix, Client.PlayerID);
			break;
		case HotBar:
			if (InMotion != null && Player.players[Client.PlayerID].inventory.HotBar[x] != null)
				if (InMotion.tile.tile != Player.players[Client.PlayerID].inventory.HotBar[x].tile.tile)
					return;
			if (Player.players[Client.PlayerID].inventory.HotBar[x] != null && InMotion != null)
				if (Player.players[Client.PlayerID].inventory.HotBar[x].count > InMotion.count) {
					InMotion.count++;
					Player.players[Client.PlayerID].inventory.HotBar[x].count--;
				} else {
					InMotion.count--;
					Player.players[Client.PlayerID].inventory.HotBar[x].count++;
				}

			if (Player.players[Client.PlayerID].inventory.HotBar[x] == null && InMotion != null) {
				Player.players[Client.PlayerID].inventory.HotBar[x] = new TileStack(InMotion.tile, 1);
				InMotion.count--;
			}

			if (Player.players[Client.PlayerID].inventory.HotBar[x] != null && InMotion == null) {
				InMotion = new TileStack(Player.players[Client.PlayerID].inventory.HotBar[x].tile, 1);
				Player.players[Client.PlayerID].inventory.HotBar[x].count--;
			}
			try {
				if (Player.players[Client.PlayerID].inventory.HotBar[x].count <= 0) {
					IsPicked = false;
					Player.players[Client.PlayerID].inventory.HotBar[x] = null;
				}
			} catch (Exception e) {
				IsPicked = false;
				Player.players[Client.PlayerID].inventory.HotBar[x] = null;
			}
			break;
		case Inventory:
			if (InMotion != null && Player.players[Client.PlayerID].inventory.Inventory[x][InventoryRow] != null)
				if (InMotion.tile.tile != Player.players[Client.PlayerID].inventory.Inventory[x][InventoryRow].tile.tile)
					return;

			if (Player.players[Client.PlayerID].inventory.Inventory[x][InventoryRow] != null && InMotion != null)
				if (Player.players[Client.PlayerID].inventory.Inventory[x][InventoryRow].count > InMotion.count) {
					InMotion.count++;
					Player.players[Client.PlayerID].inventory.Inventory[x][InventoryRow].count--;
				} else {
					InMotion.count--;
					Player.players[Client.PlayerID].inventory.Inventory[x][InventoryRow].count++;
				}

			if (Player.players[Client.PlayerID].inventory.Inventory[x][InventoryRow] == null && InMotion != null) {
				Player.players[Client.PlayerID].inventory.Inventory[x][InventoryRow] = new TileStack(InMotion.tile, 1);
				InMotion.count--;
			}

			if (Player.players[Client.PlayerID].inventory.Inventory[x][InventoryRow] != null && InMotion == null) {
				InMotion = new TileStack(Player.players[Client.PlayerID].inventory.Inventory[x][InventoryRow].tile, 1);
				Player.players[Client.PlayerID].inventory.Inventory[x][InventoryRow].count--;
			}
			try {
				if (Player.players[Client.PlayerID].inventory.Inventory[x][InventoryRow].count <= 0) {
					IsPicked = false;
					Player.players[Client.PlayerID].inventory.Inventory[x][InventoryRow] = null;
				}
			} catch (Exception e) {
				IsPicked = false;
				Player.players[Client.PlayerID].inventory.Inventory[x][InventoryRow] = null;
			}
			break;
		default:
			break;
		}

		try {
			if (InMotion.count <= 0) {
				IsPicked = false;
				InMotion = null;
			}
		} catch (Exception e) {
			IsPicked = false;
			InMotion = null;
		}
		if (InMotion != null)
			IsPicked = true;
		else
			IsPicked = false;
	}

	public static void RenderFrame() {
		Graphics graphics = ClientRenderer.graphics;
		ClientRenderer.FrameUpdated = true;

		// Background
		// This will only draw chunks that have been generated.
		// For every tile on screen, in x and y directions
		for (int x = 0; x <= ClientWindow.window.getWidth() / (Game.TileSize / Inventory.TileScale); x++)
			for (int y = 0; y <= ClientWindow.window.getHeight() / ((Game.TileSize - Game.yoff) / Inventory.TileScale); y++) {

				// Display tile
				try {
					// If there is a tile, draw it
					if (Game.CurrentMap.TileSet[x][y] != null)
						graphics.drawImage(ResourceManager.getTexture(Game.CurrentMap.TileSet[x][y].Texture), Game.TileSize * x / Inventory.TileScale, (Game.TileSize - Game.yoff) / Inventory.TileScale * y, Game.TileSize / Inventory.TileScale, (Game.TileSize + Game.yoff) / Inventory.TileScale, null, null);

				} catch (Exception e) {
					graphics.drawImage(ResourceManager.getTexture("Tiles/mapnull"), Game.TileSize * x, Game.TileSize * y, Game.TileSize / Inventory.TileScale, (Game.TileSize + Game.yoff) / Inventory.TileScale, null, null);
				}
				try {
					// Display fore tile.
					graphics.drawImage(ResourceManager.getTexture(Game.CurrentMap.TileSet[x][y].ForeTile.Texture), Game.TileSize * x / Inventory.TileScale, (Game.TileSize - Game.yoff) / Inventory.TileScale * y - Game.yoff * 2 / Inventory.TileScale, Game.TileSize / Inventory.TileScale, (Game.TileSize + Game.yoff) / Inventory.TileScale, null, null);
				} catch (Exception e) {
				}
			}

		// Backing boxes
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.drawImage(ResourceManager.getTexture("UI/Hotbar"), ClientWindow.window.getWidth() / 2 - Game.TileSize * 10, Game.TileSize * 3, null, null);
		graphics.drawImage(ResourceManager.getTexture("UI/Hotbar"), ClientWindow.window.getWidth() / 2 - Game.TileSize * 10, 0, null, null);
		graphics.drawImage(ResourceManager.getTexture("CraftingMatrix"), ClientWindow.window.getWidth() / 2 - CraftingMatrix.length * Game.TileSize, Game.TileSize * 6, null, null);

		graphics.drawString("Crafting", ClientWindow.window.getWidth() / 2 - Game.TileSize * 10, Game.TileSize * 7 - 10);
		graphics.drawString("Inventory row " + (InventoryRow + 1) + "", ClientWindow.window.getWidth() / 2 - Game.TileSize * 10, Game.TileSize * 4 - 15);

		graphics.setColor(Color.DARK_GRAY);
		// crafting boxes
		for (int i = 0; i <= Player.players[Client.PlayerID].inventory.Inventory.length; i++)
			graphics.fillRect(ClientWindow.window.getWidth() / 2 - 32 * 10 + 64 * i, Game.TileSize * 4, 36, 53);

		// Display hotbar boxes
		for (int i = 0; i <= Player.players[Client.PlayerID].inventory.HotBar.length; i++)
			graphics.fillRect(ClientWindow.window.getWidth() / 2 - 32 * 10 + 64 * i, Game.TileSize, 36, 53);

		// Crafting boxes
		for (int x = 0; x <= CraftingMatrix.length - 1; x++)
			for (int y = 0; y <= CraftingMatrix[x].length - 1; y++)
				graphics.fillRect(ClientWindow.window.getWidth() / 2 - CraftingMatrix.length * Game.TileSize + Game.TileSize * 2 * x + Game.TileSize / 2, Game.TileSize * 2 * y + Game.TileSize * 6 + Game.yoff + 5, 36, 53);

		// Display selected item
		graphics.setColor(Color.cyan);
		switch (area) {
		case Inventory:
			graphics.fillRect(ClientWindow.window.getWidth() / 2 - 32 * 10 + 64 * selectorx - 1, (Game.TileSize + Game.yoff) * 3 + 5, 38, 54);
			break;
		case CraftingGrid:
			graphics.fillRect(ClientWindow.window.getWidth() / 2 - CraftingMatrix.length * Game.TileSize + Game.yoff + 8 + Game.TileSize * 2 * selectorx, Game.TileSize * 6 + Game.yoff + 5 + Game.TileSize * 2 * selectory, 36, 53);
			break;
		case HotBar:
			graphics.fillRect(ClientWindow.window.getWidth() / 2 - 32 * 10 + 64 * selectorx - 1, Game.TileSize - 1, 38, 54);
			break;
		default:
			break;
		}

		graphics.setColor(Color.white);
		// Inventory images
		for (int i = 0; i <= Player.players[Client.PlayerID].inventory.Inventory.length - 1; i++)
			try {
				graphics.drawImage(ResourceManager.getTexture(Player.players[Client.PlayerID].inventory.Inventory[i][InventoryRow].tile.Texture), ClientWindow.window.getWidth() / 2 - 32 * 10 + 64 * i, Game.TileSize * 4, null, null);
				graphics.drawString(String.valueOf(Player.players[Client.PlayerID].inventory.Inventory[i][InventoryRow].count), ClientWindow.window.getWidth() / 2 - 32 * 10 + 64 * i, Game.TileSize * 5);
			} catch (Exception e) {
			}

		// hotbar images
		for (int i = 0; i <= Player.players[Client.PlayerID].inventory.HotBar.length - 1; i++)
			try {
				graphics.drawImage(ResourceManager.getTexture(Player.players[Client.PlayerID].inventory.HotBar[i].tile.Texture), ClientWindow.window.getWidth() / 2 - 32 * 10 + 64 * i, Game.TileSize, null, null);
				graphics.drawString(String.valueOf(Player.players[Client.PlayerID].inventory.HotBar[i].count), ClientWindow.window.getWidth() / 2 - 32 * 10 + 64 * i, Game.TileSize * 2);
			} catch (Exception e) {
			}

		// Crafting Images
		for (int x = 0; x <= CraftingMatrix.length - 1; x++)
			for (int y = 0; y <= CraftingMatrix[x].length - 1; y++)
				try {
					graphics.drawImage(ResourceManager.getTexture(CraftingMatrix[x][y].tile.Texture), ClientWindow.window.getWidth() / 2 - CraftingMatrix.length * Game.TileSize + Game.TileSize * 2 * x + Game.TileSize / 2, Game.TileSize * 2 * y + Game.TileSize * 6 + Game.yoff + 5, null, null);
					graphics.drawString(String.valueOf(CraftingMatrix[x][y].count), ClientWindow.window.getWidth() / 2 - CraftingMatrix.length * Game.TileSize + Game.TileSize * 2 * x + Game.TileSize / 2, Game.TileSize * 2 * y + Game.TileSize * 7 + Game.yoff + 5);
				} catch (Exception e) {
				}

		graphics.drawImage(ResourceManager.getTexture("UI/ControllerButtons/L1"), ClientWindow.window.getWidth() / 2 - Game.TileSize * 10 + Game.yoff, Game.TileSize * 4, null, null);
		graphics.drawImage(ResourceManager.getTexture("UI/ControllerButtons/R1"), ClientWindow.window.getWidth() / 2 + Game.TileSize * 8 + Game.yoff, Game.TileSize * 4, null, null);

		// Display inmotion item
		// Don't place more drawing commands below this!
		try {

			if (InMotion == null)
				return;

			graphics.setColor(Color.DARK_GRAY);
			graphics.fillRoundRect(100 - Game.TileSize, Game.yoff, Game.TileSize * 3, (Game.TileSize + Game.yoff) * 2, 30, 30);

			graphics.setColor(Color.white);
			graphics.drawRoundRect(100 - Game.TileSize, Game.yoff, Game.TileSize * 3, (Game.TileSize + Game.yoff) * 2, 30, 30);

			graphics.drawImage(ResourceManager.getTexture(InMotion.tile.Texture), 100, Game.TileSize, null, null);

			graphics.drawString(String.valueOf(InMotion.count), 100, Game.TileSize * 2 + Game.yoff);

		} catch (Exception e) {
		}

	}

}
