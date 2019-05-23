	package com.shinkson47.SplashX5.Game.Entities.Player;

import java.awt.event.MouseEvent;
import java.lang.reflect.Method;

import com.shinkson47.SplashX5.Client.Client;
import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Client.Logger;
import com.shinkson47.SplashX5.Game.Enumerator.Direction;
import com.shinkson47.SplashX5.Game.Enumerator.Entity;
import com.shinkson47.SplashX5.Game.Enumerator.EntitySounds;
import com.shinkson47.SplashX5.Game.Enumerator.InventoryAreas;
import com.shinkson47.SplashX5.Game.Enumerator.LogState;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Game.Resources.SoundManager;
import com.shinkson47.SplashX5.Game.Resources.Tiles.TileBase;
import com.shinkson47.SplashX5.Game.Resources.Tiles.TileStack;
import com.shinkson47.SplashX5.Game.Windows.Game;
import com.shinkson47.SplashX5.Game.World.CurrentMap;

public class Player {
	public static PlayerBase players[] = new PlayerBase[255];
	public static int PlayerCount = 0;

	public static void  Instantiate(PlayerBase player) {
		if (player.exists) {
			//wut
		} else {
			//Instantiate new
			players[PlayerCount] = player;
			players[PlayerCount].exists = true;
			PlayerCount++;
		}
	}
	
	 public static void removePlayer(int index) { 
		 if (players == null || index < 0 || index >= players.length) { return; } 

		 	// Create another array of size one less 
		 	PlayerBase[] anotherArray = new PlayerBase[players.length - 1]; 

		 	// Copy the elements except the index 
		 	// from original array to the other array 
		 	for (int i = 0, k = 0; i < players.length; i++) { 

		 		if (i == index) { continue; } 

		 		anotherArray[k++] = players[i]; } 

		 		players = anotherArray;
		 		PlayerCount--;
} 

	public static PlayerBase getPlayer(int id) {
		return players[id];
	}
	
	@SuppressWarnings("incomplete-switch")
	public static void StartMove(Direction d, int id) {
			switch (d) {
			case E:
				players[id].E = true;
				break;
			case N:
				players[id].N = true;
				break;
			case S:
				players[id].S = true;
				break;
			case W:
				players[id].W = true;
				break;
		}
			players[id].isMoving = true;
	}
	
	@SuppressWarnings("incomplete-switch")
	public static void StopMove(Direction d, int id) {
		switch (d) {
		case E:
			players[id].E = false;
			break;
		case N:
			players[id].N = false;
			break;
		case S:
			players[id].S = false;
			break;
		case W:
			players[id].W = false;
			break;
		}
		
		if (!players[id].E && !players[id].N && !players[id].S && !players[id].W) {
			//players[id].isMoving = false;
		}	
	}


	private static void UpdateMovement() {
	try {
		if (players[Client.PlayerID].isMoving && Game.selector) { Game.selector = false; }
		
		for (int i = 0; i <= Player.PlayerCount; i++) {//For every player
			if (players[i].isMoving) {//That is moving
		
			//Double check they should be moving
			if (!players[i].E && !players[i].N && !players[i].S && !players[i].W) {
				players[i].isMoving = false;
				return;
			}
			
			/*
			 * Step now?
			 * 
			 * This line controls the speed of each player independently, and determines weather or not each player moves on this tick.
			 */
			if (players[i].MoveTimer < players[i].Speed + players[i].SpeedMod) {players[i].MoveTimer++; continue;} else {players[i].MoveTimer = 0;}
			
			//Get next location
			int nextx = players[i].X, nexty = players[i].Y;
			
			if (players[i].N) {nexty--;}
			if (players[i].S) {nexty++;}
			
			//Can the player go there?
			if (BoundCheck(nextx, nexty)) {
				//go there
				players[i].X = nextx;
				players[i].Y = nexty;
				UpdateScreenPosition(nextx, nexty);
			}
		
			if (players[i].E) {nextx++;}
			if (players[i].W) {nextx--;}
			
			//Can the player go there?
			if (BoundCheck(nextx, nexty)) {
				//go there
				players[i].X = nextx;
				players[i].Y = nexty;
				UpdateScreenPosition(nextx, nexty);
				SoundManager.PlayEntity(Entity.Player, EntitySounds.Walk);
				Game.Selector(new MouseEvent(ClientWindow.window, 0, System.currentTimeMillis(), -1,-1,-1, 1, false), false);
			} else {
				//If the player can't go there, then there may be a tile that they can interact with.
				//Is there a foretile?
				if (CurrentMap.TileSet[nextx][nexty] != null) { //Null check, just prevents null pointers in the next if statement
					if (CurrentMap.TileSet[nextx][nexty].ForeTile != null) {
						//There is a foretile, does it cause an event?
						if (CurrentMap.TileSet[nextx][nexty].ForeTile.CausesEvent) {
							TileEvent(CurrentMap.TileSet[nextx][nexty].ForeTile); //Parse tile event
						}
					}	
				}
			}
			}
			
			//Set player speed, after they've moved to a new tile.
			players[i].SpeedMod = CurrentMap.TileSet[players[i].X][players[i].Y].SpeedReduction;
			
			if (players[i].N && players[i].E) { players[i].direction = Direction.NE; continue;}
			if (players[i].N && players[i].W) { players[i].direction = Direction.NW; continue;}
			if (players[i].S && players[i].E) { players[i].direction = Direction.SE; continue;}
			if (players[i].S && players[i].W) { players[i].direction = Direction.SW; continue;}
			
			if (players[i].N) { players[i].direction = Direction.N; continue;}
			if (players[i].E) { players[i].direction = Direction.E; continue;}
			if (players[i].S) { players[i].direction = Direction.S; continue;}
			if (players[i].W) { players[i].direction = Direction.W; continue;}

		}
		
	} catch (Exception e) {}
	
}

	private static void TileEvent(TileBase tileBase) {
		Windows initWindow = ClientWindow.GetWindow();
		
		try {
			Class<?> TileClass = Class.forName("com.shinkson47.SplashX5.Game.Resources.Tiles.tiles." + tileBase.tile.toString());
			Method event = TileClass.getMethod("event");
			event.invoke(null);
		} catch (Exception e) {
			Logger.log(e.getMessage(), Player.class, LogState.Error);
			e.printStackTrace();
		} //There is no class, no event, or the event threw an exception.
		
		if (ClientWindow.GetWindow() != initWindow) {HaltMovement(Client.PlayerID);}
	}

	public static void UpdateScreenSpawn(int id) {
		Game.DisplayOffsetX = Player.players[id].X - ((ClientWindow.window.getWidth() / Game.TileSize) / 2);
		Game.DisplayOffsetY = Player.players[id].Y - ((ClientWindow.window.getHeight() / (Game.TileSize + Game.yoff) / 2));
		
		if (Game.DisplayOffsetX < 0) { Game.DisplayOffsetX = 0; }
		if (Game.DisplayOffsetY < 0) { Game.DisplayOffsetY = -1; }
	}
	
	private static void UpdateScreenPosition(int nextx, int nexty) {
		//Scroll
		if (nextx == (ClientWindow.window.getWidth() / Game.TileSize) + Game.DisplayOffsetX - 5) { Game.DisplayOffsetX++; }
		if (nextx == Game.DisplayOffsetX + 5) { Game.DisplayOffsetX--; }
		if (nexty == Game.DisplayOffsetY + 5) { Game.DisplayOffsetY--; }
		if (nexty == (ClientWindow.window.getHeight() / (Game.TileSize - Game.yoff)) + Game.DisplayOffsetY - 5) { Game.DisplayOffsetY++; }
		
		//Test for player off screen
		if (Player.players[Client.PlayerID].X < Game.DisplayOffsetX) { UpdateScreenSpawn(Client.PlayerID);}
		if (Player.players[Client.PlayerID].X > ClientWindow.window.getWidth() / Game.TileSize + Game.DisplayOffsetX) {UpdateScreenSpawn(Client.PlayerID); }
		if (Player.players[Client.PlayerID].Y < Game.DisplayOffsetY) {UpdateScreenSpawn(Client.PlayerID); }
		if (Player.players[Client.PlayerID].Y > ClientWindow.window.getWidth() / (Game.TileSize - Game.yoff) + Game.DisplayOffsetY) {UpdateScreenSpawn(Client.PlayerID); }
		
	}


	public static boolean BoundCheck(int x, int y) {

		//If there's a fore tile, return false.
		try {
			if (CurrentMap.TileSet[x][y].ForeTile != null) {
				return false;
			}
		} catch (Exception e) {}
		
		//Get background value
		try {
			return CurrentMap.TileSet[x][y].Walkable;
		} catch (Exception e) {}
		
		//Just in case..
		return false;
	}


	public static void HaltMovement(int i) {
		players[i].isMoving = false;
		players[i].N = false;
		players[i].S = false;
		players[i].E = false;
		players[i].W = false;
	}


	public static void Update() {
		UpdateMovement();
	}
	
	public static void Harvest(int x, int y) {
		if (players[Client.PlayerID].isMoving) {return;}

	
		try {
			//Foretile
			try {
				if (CurrentMap.TileSet[x][y].ForeTile == null || CurrentMap.TileSet[x][y].ForeTile.Texture == null) {
					throw new NullPointerException();
				} else {
					//Foretile
					if (CurrentMap.TileSet[x][y].ForeTile.IsHarvestable) {
						if (x == Player.players[Client.PlayerID].X && y == Player.players[Client.PlayerID].Y) {return;}
						players[Client.PlayerID].inventory.collect(new TileStack(CurrentMap.TileSet[x][y].ForeTile, 1));
						CurrentMap.TileSet[x][y].ForeTile = null;
					}
					return;
				}
				
				
			} catch (Exception e) {
				//Empty foretiles cause np's
			}
			
			
			//Base tile
		if (CurrentMap.TileSet[x][y].IsHarvestable) {
			if (x == Player.players[Client.PlayerID].X && y == Player.players[Client.PlayerID].Y) {return;}
			players[Client.PlayerID].inventory.collect(new TileStack(CurrentMap.TileSet[x][y], 1));
			CurrentMap.TileSet[x][y] = null;
		}
	} catch (Exception e) {}
	}

	public static void Place(int x, int y) {
		if (players[Client.PlayerID].isMoving) {return;} //Don't place if in motion
		
		if (x == Player.players[Client.PlayerID].X && y == Player.players[Client.PlayerID].Y) {return;} //Don't place on the player
		
		try {
			if (CurrentMap.TileSet[x][y] != null) {
				
			if (CurrentMap.TileSet[x][y].ForeTile == null) {
			//Foretile
				if (Player.players[Client.PlayerID].inventory.HotBar[Player.players[Client.PlayerID].inventory.HotBarSI].count <= 0) {Player.players[Client.PlayerID].inventory.HotBar[Player.players[Client.PlayerID].inventory.HotBarSI] = null; return;} //Tilestack exsists but is empty, remove it and return.
				if (!Player.players[Client.PlayerID].inventory.HotBar[Player.players[Client.PlayerID].inventory.HotBarSI].tile.SupportsForeTile) {return;}

				CurrentMap.TileSet[x][y].ForeTile = new TileBase(x,y,players[Client.PlayerID].inventory.HotBar[players[Client.PlayerID].inventory.HotBarSI].tile.tile,"");
				CurrentMap.TileSet[x][y].ForeTile.XPos = x;
				CurrentMap.TileSet[x][y].ForeTile.YPos = y;
				
				players[Client.PlayerID].inventory.remove(InventoryAreas.HotBar,players[Client.PlayerID].inventory.HotBarSI,0,1);
			}	
			return;
			
			} else {
			//Basetile
			if (Player.players[Client.PlayerID].inventory.HotBar[Player.players[Client.PlayerID].inventory.HotBarSI].count <= 0) {Player.players[Client.PlayerID].inventory.HotBar[Player.players[Client.PlayerID].inventory.HotBarSI] = null; return;}
			if (!Player.players[Client.PlayerID].inventory.HotBar[Player.players[Client.PlayerID].inventory.HotBarSI].tile.SupportsBaseTile) {return;}
			
			CurrentMap.TileSet[x][y] = new TileBase(x,y,players[Client.PlayerID].inventory.HotBar[players[Client.PlayerID].inventory.HotBarSI].tile.tile,"");
			CurrentMap.TileSet[x][y].XPos = x;
			CurrentMap.TileSet[x][y].YPos = y;
			
			players[Client.PlayerID].inventory.remove(InventoryAreas.HotBar,players[Client.PlayerID].inventory.HotBarSI,0,1);
			}} catch (Exception e) {}
	}	
}