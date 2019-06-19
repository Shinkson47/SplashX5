package com.shinkson47.SplashX5.Game.Windows;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import com.shinkson47.SplashX5.Client.Client;
import com.shinkson47.SplashX5.Client.ClientRenderer;
import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Game.Entities.Player.Player;
import com.shinkson47.SplashX5.Game.Entities.Player.PlayerBase;
import com.shinkson47.SplashX5.Game.Enumerator.Entity;
import com.shinkson47.SplashX5.Game.Enumerator.Gamemode;
import com.shinkson47.SplashX5.Game.Enumerator.Realms;
import com.shinkson47.SplashX5.Game.Resources.ResourceManager;
import com.shinkson47.SplashX5.Game.Resources.SoundManager;
import com.shinkson47.SplashX5.Game.World.Biomes;
import com.shinkson47.SplashX5.Game.World.MapBase;
import com.shinkson47.SplashX5.Game.World.Maps;

public class Game {

	public static int ChunkSize = 10, InitialChunkGenCount = 2, DisplayOffsetX = 0, DisplayOffsetY = 0, TileSize = 36, yoff = 10;
	public static boolean InGame = false;
	public String valuetest = "arse";
	public static MapBase CurrentMap = new MapBase();

	public static void init() {
		if (!InGame) {
			// init
			Maps.GenerateNew();
			SoundManager.PlayGame();
			InGame = true;
			ClientWindow.fullscreen();
			Player.Instantiate(new PlayerBase(Player.PlayerCount, CurrentMap.CharStartX, CurrentMap.CharStartY, Gamemode.SurviveAndThrive, Realms.Overworld));
			Client.PlayerID = Player.PlayerCount - 1;
			Player.UpdateScreenSpawn(Client.PlayerID);
			ClientWindow.setFontSize(20f);
		} else {
			// Return from another menu
		}

	}

	public static void Exit() {
		if (!InGame) {
			// Exit game
			Player.removePlayer(Client.PlayerID);
			Client.PlayerID = 0;
			SoundManager.PlayMenu();
		} else
			// Leave to in game window
			Player.HaltMovement(Client.PlayerID);
	}

	public static void Update() {
		Player.Update(); // Update the player - movement etc
	}

	public static void Mouse(MouseEvent arg0) {
		ClientWindow.window.setTitle(Biomes.GetBiome(getMouseTile(arg0.getPoint().x, false), getMouseTile(arg0.getPoint().x, false)).toString());
	}

	// vertex true = vertical. Supposed to return the tile the mouse is on.
	private static int getMouseTile(int location, boolean vertex) {
		if (vertex) {
			location -= 26;
			location += DisplayOffsetY;
		} else {
			location -= 3;
			location += DisplayOffsetY;
		}
		return location / 64;
	}

	public static boolean selector, lmb, rmb;
	public static int selectorx, selectory;

	public static void Selector(MouseEvent arg0, boolean b) {
		if (Player.players[Client.PlayerID].isMoving) {
			selector = false;
			lmb = false;
			rmb = false;
			return;
		}

		selector = b;
		if (arg0.getButton() == MouseEvent.BUTTON1)
			lmb = b;
		if (arg0.getButton() == MouseEvent.BUTTON3)
			rmb = b;

		if (!lmb && !rmb) {
			selector = false;
			return;
		}

		if (b) {
			// press
			selectorx = getSelectorPosX(arg0);
			selectory = getSelectorPosY(arg0);

			// disable if harvesting a foretile to prevent base tile harvesting
			try {
				if (CurrentMap.TileSet[selectorx][selectory].ForeTile != null)
					selector = false;
			} catch (Exception e) {// There is no fore tile, ignore.

			}

			// Disable mb's if selector is diabled. crucial for foretile harvesting.
			if (lmb)
				Player.Harvest(selectorx, selectory);
			if (rmb)
				Player.Place(selectorx, selectory);

			if (!selector) {
				lmb = false;
				rmb = false;
			}
		} else {
			// release event
		}
	}

	private static int selectorRad = 5;

	private static int getSelectorPosY(MouseEvent arg0) {
		int i = arg0.getPoint().y / (TileSize - yoff) + DisplayOffsetY;
		if (i > Player.players[Client.PlayerID].Y + selectorRad)
			i = Player.players[Client.PlayerID].Y + selectorRad;
		if (i < Player.players[Client.PlayerID].Y - selectorRad)
			i = Player.players[Client.PlayerID].Y - selectorRad;
		return i;
	}

	private static int getSelectorPosX(MouseEvent arg0) {
		int i = arg0.getPoint().x / TileSize + DisplayOffsetX;
		if (i > Player.players[Client.PlayerID].X + selectorRad)
			i = Player.players[Client.PlayerID].X + selectorRad;
		if (i < Player.players[Client.PlayerID].X - selectorRad)
			i = Player.players[Client.PlayerID].X - selectorRad;
		return i;
	}

	public static void scroll(MouseWheelEvent arg0) {

		int direction = 0;
		if (arg0.getPreciseWheelRotation() > 0)
			direction = 1;
		else
			direction = -1;

		Player.players[Client.PlayerID].inventory.scrollHB(direction);
	}

	public static void RenderFrame() {
		Graphics graphics = ClientRenderer.graphics;
		ClientRenderer.FrameUpdated = true;

		// For every tile on screen, in x and y directions
		for (int x = 0; x <= ClientWindow.window.getWidth() / Game.TileSize; x++)
			for (int y = 0; y <= ClientWindow.window.getHeight() / (Game.TileSize - Game.yoff); y++) {

				// Display tile
				try {
					// If there is a tile, draw it
					if (CurrentMap.TileSet[x + Game.DisplayOffsetX][y + Game.DisplayOffsetY] != null)
						graphics.drawImage(ResourceManager.getTexture(CurrentMap.TileSet[x + Game.DisplayOffsetX][y + Game.DisplayOffsetY].Texture), Game.TileSize * x, (Game.TileSize - Game.yoff) * y, null, null);
					else // If not, is the blank tile within the world?
					if (x + Game.DisplayOffsetX > 0 && y + Game.DisplayOffsetY > 0 && x < CurrentMap.WorldBorder && y < CurrentMap.WorldBorder) {
						// Is that chunk generated?
						Boolean mape = Maps.ChunkExists(x + Game.DisplayOffsetX, y + Game.DisplayOffsetY);
						if (!mape)
							// if not, create one. The following loops will draw the rest of the chunk this
							// is generated here in this frame, if it is on screen
							// The current tile will then be drawn on the next frame.
							Maps.GenerateChunk(Maps.getChunkStart(x + Game.DisplayOffsetX), Maps.getChunkStart(y + Game.DisplayOffsetY), CurrentMap.TileSet);
					}
				} catch (Exception e) {
					graphics.drawImage(ResourceManager.getTexture("Tiles/mapnull"), Game.TileSize * x, Game.TileSize * y, null, null);
					// Replace null tile.
				}

				try {
					// are any players on screen?
					for (int i = 0; i <= Player.PlayerCount; i++)
						if (Player.players[i].X == x + Game.DisplayOffsetX)
							if (Player.players[i].Y == y + Game.DisplayOffsetY)
								// Display them
								graphics.drawImage(ResourceManager.getEntityTexture(Entity.Player, Player.players[i].direction), (Player.players[i].X - Game.DisplayOffsetX) * Game.TileSize, (Player.players[i].Y - Game.DisplayOffsetY) * (Game.TileSize - Game.yoff) - Game.TileSize, null, null);
				} catch (Exception e) {
					// No player's exist.
				}

				try {
					// Display if the tile has a fore tile, display it.
					graphics.drawImage(ResourceManager.getTexture(CurrentMap.TileSet[x + Game.DisplayOffsetX][y + Game.DisplayOffsetY].ForeTile.Texture), Game.TileSize * x, (Game.TileSize - Game.yoff) * y - Game.yoff * 2, null, null);

				} catch (Exception e) {
					// There is no foretile.
					// Using a test for null tiles throws a null pointer anyway, so it was removed.
				}

			}

		// Is the selector active?
		if (Game.selector)
			// Display it
			graphics.drawImage(ResourceManager.getTexture("Entities/Player"), (Game.selectorx - Game.DisplayOffsetX) * Game.TileSize, (Game.selectory - Game.DisplayOffsetY) * (Game.TileSize - Game.yoff), null, null);

		// draw hotbar
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fillRoundRect(ClientWindow.window.getWidth() / 2 - Game.TileSize * 10, Game.TileSize - Game.yoff + 2, Game.TileSize * 19, Game.yoff * 3 + Game.TileSize, 30, 30);

		graphics.setColor(Color.white);
		graphics.drawRoundRect(ClientWindow.window.getWidth() / 2 - Game.TileSize * 10, Game.TileSize - Game.yoff + 2, Game.TileSize * 19, Game.yoff * 3 + Game.TileSize, 30, 30);

		graphics.setColor(Color.DARK_GRAY);
		for (int i = 0; i <= Player.players[Client.PlayerID].inventory.HotBar.length; i++)
			graphics.fillRect(ClientWindow.window.getWidth() / 2 - 32 * 10 + 64 * i, Game.TileSize, 36, 53);

		graphics.setColor(Color.cyan);
		graphics.fillRect(ClientWindow.window.getWidth() / 2 - 32 * 10 + 64 * Player.players[Client.PlayerID].inventory.HotBarSI - 1, Game.TileSize - 1, 38, 54);

		graphics.setColor(Color.white);
		for (int i = 0; i <= Player.players[Client.PlayerID].inventory.HotBar.length; i++)
			try {
				graphics.drawImage(ResourceManager.getTexture(Player.players[Client.PlayerID].inventory.HotBar[i].tile.Texture), ClientWindow.window.getWidth() / 2 - 32 * 10 + 64 * i, Game.TileSize, null, null);
				graphics.drawString(String.valueOf(Player.players[Client.PlayerID].inventory.HotBar[i].count), ClientWindow.window.getWidth() / 2 - 32 * 10 + 64 * i, Game.TileSize * 2);
			} catch (Exception e) {
			}
	}

}
