package com.shinkson47.SplashX5.Game.Resources.Tiles.Recipes;

import com.shinkson47.SplashX5.Game.Enumerator.ETiles;
import com.shinkson47.SplashX5.Game.Resources.Tiles.TileBase;
import com.shinkson47.SplashX5.Game.Resources.Tiles.TileStack;

public class Tree {
	public static TileStack[][] craftingMatrix = {
			{new TileStack(new TileBase(-1, -1, ETiles.CraftingBench, null), 1),new TileStack(new TileBase(-1, -1, ETiles.CraftingBench, null), 1),null,null},
			{null,null,null,null}
	};
	public static TileStack output = new TileStack(new TileBase(-1, -1, ETiles.Tree, null), 40);
	public static boolean isShapeless = false;
}
