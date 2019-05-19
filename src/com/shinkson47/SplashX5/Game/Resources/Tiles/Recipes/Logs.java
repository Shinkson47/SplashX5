package com.shinkson47.SplashX5.Game.Resources.Tiles.Recipes;

import com.shinkson47.SplashX5.Game.Enumerator.ETiles;
import com.shinkson47.SplashX5.Game.Resources.Tiles.TileBase;
import com.shinkson47.SplashX5.Game.Resources.Tiles.TileStack;

public class Logs {
	public static TileStack[] craftingList = {new TileStack(new TileBase(-1,-1, ETiles.Tree,""), 1),null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null};
	public static TileStack output = new TileStack(new TileBase(-1, -1, ETiles.Logs, null), 4);
	public static boolean isShapeless = true;
}
