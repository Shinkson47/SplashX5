package com.shinkson47.SplashX5.Game.Entities.Player;

import com.shinkson47.SplashX5.Client.Client;
import com.shinkson47.SplashX5.Game.Enumerator.InventoryAreas;
import com.shinkson47.SplashX5.Game.Resources.SoundManager;
import com.shinkson47.SplashX5.Game.Resources.Tiles.Crafting;
import com.shinkson47.SplashX5.Game.Resources.Tiles.TileStack;

public class Inventory {
	public boolean IsPicked = false;
	public TileStack InMotion;
	
	public TileStack HotBar[] = new TileStack[9];
	public TileStack Inventory[][] = new TileStack[9][4];
	public TileStack CraftingGrid[][] = new TileStack[2][2];
	public int HotBarSI = 0;
	
	public void lift(InventoryAreas area, int indexx, int indexy) {
		SoundManager.PlayMisc("/Runtime/ConfirmAlert", true);
		switch (area) {
		case Armor:
			break;
		case CraftingGrid:
			InMotion = CraftingGrid[indexx][indexy];
			CraftingGrid[indexx][indexy] = null;
			IsPicked = true;
			break;
		case HotBar:
			InMotion = HotBar[indexx];
			HotBar[indexx] = null;
			IsPicked = true;
			break;
		case Inventory:
			InMotion = Inventory[indexx][indexy];
			Inventory[indexx][indexy] = null;
			IsPicked = true;
			break;
		default:
			break;
		}
		
		try {
		if (InMotion == null) { IsPicked = false; } else { IsPicked = true; }
		} catch (Exception e) { IsPicked = false; }
		
		
	
	}
	
	TileStack temp; 
	public void drop(InventoryAreas area, int indexx, int indexy) {
		SoundManager.PlayMisc("/Runtime/Confirm", true);
		switch (area) {
		case Armor:
			break;
		case CraftingGrid:
			if (CraftingGrid[indexx][indexy] != null) {
			//Tile is not empty!
				if (CraftingGrid[indexx][indexy].tile.tile == InMotion.tile.tile) {
					if ((CraftingGrid[indexx][indexy].count + InMotion.count) > 64) {
						int diff = 64 - CraftingGrid[indexx][indexy].count;
						CraftingGrid[indexx][indexy].count = 64;
						InMotion.count -= diff;
					}}
					} else {
						CraftingGrid[indexx][indexy]= new TileStack(InMotion.tile, InMotion.count);
						InMotion = null;
						IsPicked = false;
						
					}
					
					

			Crafting.craft(CraftingGrid, Client.PlayerID);
			return;	
//			temp = CraftingGrid[indexx][indexy];
//			CraftingGrid[indexx][indexy] = InMotion;
//			InMotion = temp;
		case HotBar:
			try {
				if (HotBar[indexx] != null) {
						//Tile is not empty!
						if (HotBar[indexx].tile.tile == InMotion.tile.tile) {
							if ((HotBar[indexx].count + InMotion.count) > 64) {
								int diff = 64 - HotBar[indexx].count;
								HotBar[indexx].count = 64;
								InMotion.count -= diff;
								InMotion = null;
								IsPicked = false;
								return;
							} else {
								HotBar[indexx].count += InMotion.count;
								InMotion = null;
								IsPicked = false;
								return;	
							}

				} else {
					TileStack temp = HotBar[indexx];
					HotBar[indexx] = InMotion;
					InMotion = temp;
					return;
				}} else {
					HotBar[indexx] = InMotion;
					InMotion = null;
					IsPicked = false;		
					return;
				}
				
			} catch (Exception e) {}


			
			break;
		case Inventory:
			try {
				if (Inventory[indexx][indexy] != null) {
					//Tile is not empty!
					if (Inventory[indexx][indexy].tile.tile == InMotion.tile.tile) {
						if ((Inventory[indexx][indexy].count + InMotion.count) > 64) {
							int diff = 64 - Inventory[indexx][indexy].count;
							Inventory[indexx][indexy].count = 64;
							InMotion.count -= diff;
							if (InMotion.count <= 0) {  IsPicked = false;}
							return;
						} else {
							Inventory[indexx][indexy].count += InMotion.count;
							InMotion = null;
							IsPicked = false;
							return;	
						}
					}
					TileStack temp = Inventory[indexx][indexy];
					Inventory[indexx][indexy] = InMotion;
					InMotion = temp;	
					return;
				}
			} catch (Exception e) {}
			
			Inventory[indexx][indexy] = InMotion;
			InMotion = null;
			IsPicked = false;		
			break;
		default:
			break;
		}

		try {
		if (InMotion == null) { IsPicked = false; } else { IsPicked = true; }
		} catch (Exception e) { IsPicked = false; }
	}	

	public void cut(InventoryAreas area, int indexx, int indexy) {
		switch (area) {
		case Armor:
			break;
		case CraftingGrid:
			//Place half into stack
			if (InMotion != null && CraftingGrid[indexx][indexy] != null) {
				CraftingGrid[indexx][indexy].count += (InMotion.count /2);
				int prevcount = InMotion.count;
				InMotion.count /= 2;
				if (prevcount % 2 != 0) {
					CraftingGrid[indexx][indexy].count++;
				}
			}
			
			//Take half into null
			if (InMotion == null && CraftingGrid[indexx][indexy] != null) {
				InMotion = new TileStack(CraftingGrid[indexx][indexy].tile, CraftingGrid[indexx][indexy].count);
				int prevcount = InMotion.count;
				InMotion.count /= 2;
				CraftingGrid[indexx][indexy].count /= 2;
				
				if (prevcount % 2 != 0) {
					CraftingGrid[indexx][indexy].count++;
				}
			}
			
			//Place half into null
			if (InMotion != null && CraftingGrid[indexx][indexy] == null) {
			
				CraftingGrid[indexx][indexy] = new TileStack(InMotion.tile, InMotion.count);
				int prevcount = InMotion.count;
				InMotion.count /= 2;
				CraftingGrid[indexx][indexy].count /= 2;
				
				if (prevcount % 2 != 0) {
					CraftingGrid[indexx][indexy].count++;
				}
			}
			break;
		case HotBar:
			//Place half into stack
			if (InMotion != null && HotBar[indexx] != null) {
				HotBar[indexx].count += (InMotion.count /2);
				int prevcount = InMotion.count;
				InMotion.count /= 2;
				if (prevcount % 2 != 0) {
					HotBar[indexx].count++;
				}
			}
			
			//Take half into null
			if (InMotion == null && HotBar[indexx] != null) {
				InMotion = new TileStack(HotBar[indexx].tile, HotBar[indexx].count);
				int prevcount = InMotion.count;
				InMotion.count /= 2;
				HotBar[indexx].count /= 2;
				
				if (prevcount % 2 != 0) {
					HotBar[indexx].count++;
				}
			}
			
			//Place half into null
			if (InMotion != null && HotBar[indexx] == null) {
			
				HotBar[indexx] = new TileStack(InMotion.tile, InMotion.count);
				int prevcount = InMotion.count;
				InMotion.count /= 2;
				HotBar[indexx].count /= 2;
				
				if (prevcount % 2 != 0) {
					HotBar[indexx].count++;
				}
			}
			break;
		case Inventory:
			//Place half into stack
			if (InMotion != null && Inventory[indexx][indexy] != null) {
				Inventory[indexx][indexy].count += (InMotion.count /2);
				int prevcount = InMotion.count;
				InMotion.count /= 2;
				if (prevcount % 2 != 0) {
					Inventory[indexx][indexy].count++;
				}
			}
			
			//Take half into null
			if (InMotion == null && Inventory[indexx][indexy] != null) {
				InMotion = new TileStack(Inventory[indexx][indexy].tile, Inventory[indexx][indexy].count);
				int prevcount = InMotion.count;
				InMotion.count /= 2;
				Inventory[indexx][indexy].count /= 2;
				
				if (prevcount % 2 != 0) {
					Inventory[indexx][indexy].count++;
				}
			}
			
			//Place half into null
			if (InMotion != null && Inventory[indexx][indexy] == null) {
			
				Inventory[indexx][indexy] = new TileStack(InMotion.tile, InMotion.count);
				int prevcount = InMotion.count;
				InMotion.count /= 2;
				Inventory[indexx][indexy].count /= 2;
				
				if (prevcount % 2 != 0) {
					Inventory[indexx][indexy].count++;
				}
			}
			break;
		default:
			break;
		}
		
		
		try {
		//Empty split check
		if (InMotion.count == 0) {IsPicked = false; InMotion = null;}
			
		if (InMotion == null) { IsPicked = false; } else { IsPicked = true; }
		} catch (Exception e) { IsPicked = false; }
	}
	
	public void dump(InventoryAreas area, int indexx, int indexy) {
		
	}
	
	public void collect(TileStack Tile) { //TODO stack max
		
		for (int x = 0; x <= HotBar.length - 1; x++) {
			try {	
				
			if (HotBar[x].tile.tile == Tile.tile.tile) { 
				if (HotBar[x].add(Tile.count)) {return;}
			}
			
			} catch (Exception e) {
				HotBar[x] = new TileStack(Tile.tile,Tile.count);
				return;
			} 
		}
		
		for (int x = 0; x <= HotBar.length - 1; x++) {
			try {	
			//If item is on hot bar, add it to stack
			if (HotBar[x].tile == null) { HotBar[x].add(Tile.count); return;}
			} catch (Exception e) {
				HotBar[x] = new TileStack(Tile.tile,Tile.count);
				return;
			}
		}
		
		//if the item is in the inventory, add it
		boolean space = false;
		int spacex = -1 , spacey = -1;
		for (int x = 0; x <= Inventory.length - 1; x++) {
			for (int y = 0; y <= Inventory[x].length - 1; y++){
				try {
				if (Inventory[x][y].tile.tile == Tile.tile.tile) { Inventory[x][y].add(Tile.count); return;}
				} catch (Exception e) {
					if (!space) {
					spacex = x;
					spacey = y;
					space = true;}
				}
			}	
		}
		
		//else, add it to a space
		if (space) {
			Inventory[spacex][spacey] = new TileStack(Tile.tile, Tile.count);
			return;
			}
		//TODO else add it to the void inventory
		}
	
	public void remove(InventoryAreas area, int indexx, int indexy, int quantity) {
		switch (area) {
		case Armor:
			break;
		case CraftingGrid:
			break;
		case HotBar:
			HotBar[indexx].remove(quantity);
			break;
		case Inventory:
			break;
		default:
			break;
		}
	}

	public void scrollHB(int direction) {
		if (direction > 0 && HotBarSI < HotBar.length) { HotBarSI++;}
		if (direction < 0 && HotBarSI > 0) { HotBarSI--;}
	}

	
	
}
