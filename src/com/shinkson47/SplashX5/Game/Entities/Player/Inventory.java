package com.shinkson47.SplashX5.Game.Entities.Player;

import com.shinkson47.SplashX5.Game.Enumerator.InventoryAreas;
import com.shinkson47.SplashX5.Game.Resources.SoundManager;
import com.shinkson47.SplashX5.Game.Resources.Tiles.TileBase;
import com.shinkson47.SplashX5.Game.Resources.Tiles.TileStack;

public class Inventory {
	public boolean IsPicked = false;
	public TileStack InMotion;
	
	public TileStack HotBar[] = new TileStack[9];
	public TileStack Inventory[][] = new TileStack[9][4];
	public TileStack CraftingGrid[][] = new TileStack[4][4];

	public int HotBarSI = 0;
	
	public void lift(InventoryAreas area, int indexx, int indexy) {
		SoundManager.PlayMisc("/Runtime/ConfirmAlert", true);
		switch (area) {
		case Armor:
			break;
		case CraftingGrid:
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
	
	public void drop(InventoryAreas area, int indexx, int indexy) {
		SoundManager.PlayMisc("/Runtime/Confirm", true);
		switch (area) {
		case Armor:
			break;
		case CraftingGrid:
			break;
		case HotBar:
			try {
				if (HotBar[indexx] != null) {
					//Tile is not empty!
					if (HotBar[indexx] != null) {
						//Tile is not empty!
						if (HotBar[indexx].tile.tile == InMotion.tile.tile) {
							if ((HotBar[indexx].count + InMotion.count) > 64) {
								int diff = 64 - HotBar[indexx].count;
								HotBar[indexx].count = 64;
								InMotion.count -= diff;
								return;
							} else {
								HotBar[indexx].count += InMotion.count;
								InMotion = null;
								IsPicked = false;
								return;	
							}
						}
					TileStack temp = HotBar[indexx];
					HotBar[indexx] = InMotion;
					InMotion = temp;	
					return;
				}}
			} catch (Exception e) {}
			
			HotBar[indexx] = InMotion;
			InMotion = null;
			IsPicked = false;		
			
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
		if (IsPicked) { return; }
		switch (area) {
		case Armor:
			break;
		case CraftingGrid:
			break;
		case HotBar:
			if (HotBar[indexx].count % 2 == 0) {
				InMotion = new TileStack(new TileBase(-1,-1,HotBar[indexx].tile.tile,""), HotBar[indexx].count);
				InMotion.count /= 2;
				HotBar[indexx].count /= 2;
				IsPicked = true;	
			} else {
				InMotion = new TileStack(new TileBase(-1,-1,HotBar[indexx].tile.tile,""), HotBar[indexx].count);
				InMotion.count /= 2;
				HotBar[indexx].count /= 2;
				HotBar[indexx].count ++;
				IsPicked = true;	
			}
			break;
		case Inventory:
			if (Inventory[indexx][indexy].count % 2 == 0) {
				InMotion = new TileStack(new TileBase(-1,-1,Inventory[indexx][indexy].tile.tile,""), Inventory[indexx][indexy].count);
				InMotion.count /= 2;
				Inventory[indexx][indexy].count /= 2;
				IsPicked = true;	
			} else {
				InMotion = new TileStack(new TileBase(-1,-1,Inventory[indexx][indexy].tile.tile,""), Inventory[indexx][indexy].count);
				InMotion.count /= 2;
				Inventory[indexx][indexy].count /= 2;
				Inventory[indexx][indexy].count ++;
				IsPicked = true;	
			}
			break;
		default:
			break;
		}
		
		try {
		if (InMotion == null) { IsPicked = false; } else { IsPicked = true; }
		} catch (Exception e) { IsPicked = false; }
		
	}
	
	public void dump(InventoryAreas area, int indexx, int indexy) {
		
	}
	
	public void collect(TileBase tile) { //TODO stack max
		
		for (int x = 0; x <= HotBar.length - 1; x++) {
			try {	
				
			if (HotBar[x].tile.tile == tile.tile) { 
				if (HotBar[x].add()) {return;}
			}
			
			} catch (Exception e) {
				HotBar[x] = new TileStack(tile,1);
				return;
			} 
		}
		
		for (int x = 0; x <= HotBar.length - 1; x++) {
			try {	
			//If item is on hot bar, add it to stack
			if (HotBar[x].tile == null) { HotBar[x].add(); return;}
			} catch (Exception e) {
				HotBar[x] = new TileStack(tile,1);
				return;
			}
		}
		
		//if the item is in the inventory, add it
		boolean space = false;
		int spacex = -1 , spacey = -1;
		for (int x = 0; x <= Inventory.length - 1; x++) {
			for (int y = 0; y <= Inventory[x].length - 1; y++){
				try {
				if (Inventory[x][y].tile.tile == tile.tile) { Inventory[x][y].add(); return;}
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
			Inventory[spacex][spacey] = new TileStack(tile,1);
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
