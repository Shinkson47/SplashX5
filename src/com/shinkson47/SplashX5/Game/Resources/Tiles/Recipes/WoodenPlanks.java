package com.shinkson47.SplashX5.Game.Resources.Tiles.Recipes;

import com.shinkson47.SplashX5.Game.Enumerator.ETiles;
import com.shinkson47.SplashX5.Game.Resources.Tiles.TileBase;
import com.shinkson47.SplashX5.Game.Resources.Tiles.TileStack;

public class WoodenPlanks {
	public static TileStack[][] craftingMatrix = {
			{new TileStack(new TileBase(-1, -1, ETiles.Logs, null), 1),new TileStack(new TileBase(-1, -1, ETiles.Logs, null), 1),null,null},
			{new TileStack(new TileBase(-1, -1, ETiles.Logs, null), 1),new TileStack(new TileBase(-1, -1, ETiles.Logs, null), 1),null,null}
	};
	public static TileStack output = new TileStack(new TileBase(-1, -1, ETiles.WoodenPlanks, null), 1);
	public static boolean isShapeless = false;
}
