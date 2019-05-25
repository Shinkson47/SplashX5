package com.shinkson47.SplashX5.Game.Entities.Player;

import java.io.Serializable;

import com.shinkson47.SplashX5.Client.Client;
import com.shinkson47.SplashX5.Game.Enumerator.InventoryAreas;
import com.shinkson47.SplashX5.Game.Resources.SoundManager;
import com.shinkson47.SplashX5.Game.Resources.Tiles.Crafting;
import com.shinkson47.SplashX5.Game.Resources.Tiles.TileStack;

public class Inventory implements Serializable {
	private static final long serialVersionUID = -5109869976417644020L;
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
			try {
				if (CraftingGrid[indexx][indexy] != null) {
					//Tile is not empty!
					if (CraftingGrid[indexx][indexy].tile.tile == InMotion.tile.tile) {
						if ((CraftingGrid[indexx][indexy].count + InMotion.count) > 64) {
							int diff = 64 - CraftingGrid[indexx][indexy].count;
							CraftingGrid[indexx][indexy].count = 64;
							InMotion.count -= diff;
							if (InMotion.count <= 0) {  IsPicked = false;}
							Crafting.craft(CraftingGrid, Client.PlayerID);
							return;
						} else {
							CraftingGrid[indexx][indexy].count += InMotion.count;
							InMotion = null;
							IsPicked = false;
							Crafting.craft(CraftingGrid, Client.PlayerID);
							return;	
						}
					}
					TileStack temp = CraftingGrid[indexx][indexy];
					CraftingGrid[indexx][indexy] = InMotion;
					InMotion = temp;	
					Crafting.craft(CraftingGrid, Client.PlayerID);
					return;
				}
			} catch (Exception e) {}
			
			CraftingGrid[indexx][indexy] = InMotion;
			InMotion = null;
			IsPicked = false;		
			Crafting.craft(CraftingGrid, Client.PlayerID);
			break;
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
			if (CraftingGrid[indexx][indexy] == null && InMotion == null) { return; } try {if (InMotion.tile.tile != CraftingGrid[indexx][indexy].tile.tile) { return; }} catch (Exception e) {} //one is null, continue
			
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
			if (HotBar[indexx] == null && InMotion == null) { return; } try {if (InMotion.tile.tile != HotBar[indexx].tile.tile) { return; }} catch (Exception e) {} //one is null, continue
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
			if (Inventory[indexx][indexy] == null && InMotion == null) { return; } try {if (InMotion.tile.tile != Inventory[indexx][indexy].tile.tile) { return; }} catch (Exception e) {} //one is null, continue
			
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
		
		//It if's on the hot bar, add it to the stack
		for (int x = 0; x <= HotBar.length -1; x++) {
			try {	
				if (HotBar[x] == null) { continue; }
			if (HotBar[x].tile.tile == Tile.tile.tile) {
				int num = HotBar[x].add(Tile.count); 
				if (num <= 0) {return;} else {Tile.count = num;}
				//if not done, continue
			}
			
			} catch (Exception e) {
				HotBar[x] = new TileStack(Tile.tile,Tile.count);
				return;
			} 
		}
		
		//Create new tilestack on the hot bar, if there's room
		for (int x = 0; x <= HotBar.length -1; x++) {
			if (HotBar[x] == null) {HotBar[x] = new TileStack(Tile.tile, Tile.count); return;}
		}
		
		//if the item is in the inventory, add it
		boolean space = false;
		int spacex = -1 , spacey = -1;
		for (int x = 0; x <= Inventory.length - 1; x++) {
			for (int y = 0; y <= Inventory[x].length - 1; y++){
				try { 
				if (Inventory[x][y].tile.tile == Tile.tile.tile) { 
					int num = Inventory[x][y].add(Tile.count); 
					if (num <= 0) {
						return;
					} //else continue
				}
				
				} catch (Exception e) {
					if (!space) {
					spacex = x;
					spacey = y;
					space = true;}
				}
			}	
		}
		
		//If there are no more items with room, add it to an empty space
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

	public void Single(InventoryAreas area, int x, int y) {
			if (InMotion == null && IsPicked) {IsPicked = false;}
			if (InMotion != null) {
				if (InMotion.count <= 0){
					InMotion = null;
					IsPicked = false;
					}
				}
		
			switch (area) {
			case Armor:
				break;
			case CraftingGrid:
				if (InMotion != null && CraftingGrid[x][y] != null) {if (InMotion.tile.tile != CraftingGrid[x][y].tile.tile) {return;} }
				if (CraftingGrid[x][y] != null && InMotion != null) {
					if (CraftingGrid[x][y].count > InMotion.count) {
						InMotion.count++;
						CraftingGrid[x][y].count--;
					} else {
						InMotion.count--;
						CraftingGrid[x][y].count++;
					}
				}
				
				if (CraftingGrid[x][y] == null && InMotion != null) {
					CraftingGrid[x][y] = new TileStack(InMotion.tile, 1);
					InMotion.count--;
				}
				
				if (CraftingGrid[x][y] != null && InMotion == null) {
					InMotion = new TileStack(CraftingGrid[x][y].tile, 1);
					CraftingGrid[x][y].count--;
				}
				try { if (CraftingGrid[x][y].count <= 0) {CraftingGrid[x][y] = null;} } catch (Exception e) {CraftingGrid[x][y] = null;}
				Crafting.craft(CraftingGrid, Client.PlayerID);
				break;
			case HotBar:
				if (InMotion != null && HotBar[x] != null) {if (InMotion.tile.tile != HotBar[x].tile.tile) {return;} }
				if (HotBar[x] != null && InMotion != null) {
					if (HotBar[x].count > InMotion.count) {
						InMotion.count++;
						HotBar[x].count--;
					} else {
						InMotion.count--;
						HotBar[x].count++;
					}
				}
				
				if (HotBar[x] == null && InMotion != null) {
					HotBar[x] = new TileStack(InMotion.tile, 1);
					InMotion.count--;
				}
				
				if (HotBar[x] != null && InMotion == null) {
					InMotion = new TileStack(HotBar[x].tile, 1);
					HotBar[x].count--;
				}
				try { if (HotBar[x].count <= 0) {IsPicked = false; HotBar[x] = null;} } catch (Exception e) {IsPicked = false; HotBar[x] = null;}
				break;
			case Inventory:
				if (InMotion != null && Inventory[x][y] != null) {if (InMotion.tile.tile != Inventory[x][y].tile.tile) {return;} }
				
				if (Inventory[x][y] != null && InMotion != null) {
					if (Inventory[x][y].count > InMotion.count) {
						InMotion.count++;
						Inventory[x][y].count--;
					} else {
						InMotion.count--;
						Inventory[x][y].count++;
					}
				}
				
				if (Inventory[x][y] == null && InMotion != null) {
					Inventory[x][y] = new TileStack(InMotion.tile, 1);
					InMotion.count--;
				}
				
				if (Inventory[x][y] != null && InMotion == null) {
					InMotion = new TileStack(Inventory[x][y].tile, 1);
					Inventory[x][y].count--;
				}
				try { if (Inventory[x][y].count <= 0) {IsPicked = false; Inventory[x][y] = null;} } catch (Exception e) {IsPicked = false; Inventory[x][y] = null;}
				break;
			default:
				break;
			}
			
			
			try { if (InMotion.count <= 0) {IsPicked = false; InMotion = null;} } catch (Exception e) {IsPicked = false; InMotion = null;}
			if (InMotion != null) {IsPicked = true;} else {IsPicked = false;}
	}

	
	
}
