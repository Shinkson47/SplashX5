package com.shinkson47.SplashX5.Game.Resources.Tiles;

public class TileStack {
	public TileBase tile;
	public int count;
	
	public TileStack(TileBase Tile, int Count) {
		tile = Tile;
		count = Count;
	}
	
	public int add(int i){
		if (count + i > 64) {
			return Math.negateExact((64 - i) - count);
		} else {
			count += i;
			return 0;
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
