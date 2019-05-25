package com.shinkson47.SplashX5.Game.Resources.Tiles;

import java.io.Serializable;

public class TileStack implements Serializable {
	public TileBase tile;
	public int count;
	
	public TileStack(TileBase Tile, int Count) {
		tile = Tile;
		count = Count;
	}
	
	public int add(int i){
		if (count + i > 64) {
			int initcount = count;
			count += (64 - count);		
			return Math.negateExact((64 - i) - initcount);
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
