package com.shinkson47.SplashX5.Game.Resources.Tiles;

public class TileStack {
	public TileBase tile;
	public int count;
	
	public TileStack(TileBase Tile, int Count) {
		tile = Tile;
		count = Count;
	}
	
	public boolean add(){
		if (count < 64) {
			count++;
			return true;
		} else {
			return false;
		}
	}
	
	public void remove(int quantity){
		if (count > 0) {
			count -= quantity;
		}
		
		if (count == 0) {
			tile = null;
		}
	}
	
}
