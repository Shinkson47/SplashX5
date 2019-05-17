package com.shinkson47.SplashX5.Game.Resources.Tiles;

import java.lang.reflect.Field;

import com.shinkson47.SplashX5.Client.Logger;
import com.shinkson47.SplashX5.Game.Entities.Player.Player;
import com.shinkson47.SplashX5.Game.Enumerator.LogState;
import com.shinkson47.SplashX5.Game.Enumerator.Recipies;

public class Crafting {
	public static int ShapelessRecipeCount = 0;
	public static ShapelessRecipeBase[] ShapelessDictionary = new ShapelessRecipeBase[25565];
	
	public static void init() {
		for (Recipies recipe : Recipies.values()) {
			try {
				//See if a definition for this tile exists
				Class<?> recipeclass = Class.forName("com.shinkson47.SplashX5.Game.Resources.Tiles.Recipes." + recipe.toString()); 
				RegisterNew(recipeclass);
			} catch (Exception e) {
				//if not Warn and skip
				Logger.log("Ignoring enum for " + recipe.toString() + ", as there is no recipe definition." , Tiles.class, LogState.Warn);
				continue;				
			}
			
		}			
		Logger.log("Initalised " + (ShapelessRecipeCount) + " recipes into the crafting dictionary.", Tiles.class, LogState.Info);
		}
	
	
	private static void RegisterNew(Class<?> recipeclass) {
		try {
			Field IsShapeless = recipeclass.getField("isShapeless");
			boolean Type = IsShapeless.getBoolean(IsShapeless);
			if (Type) {
				//Shapeless
				ShapelessDictionary[ShapelessRecipeCount] = new ShapelessRecipeBase(recipeclass);
				ShapelessRecipeCount++;
			} else {
				//Shaped
		
			}
		} catch (Exception e) {}
		
	}


	public static void craft(TileStack[][] craftingGrid, int playerID) {
		if (GetShapedRecipe(craftingGrid) != null) {
		
			return;
		}
		
		ShapelessRecipeBase sbase = GetShapelessRecipe(craftingGrid);
		if (sbase != null) {
			Player.players[playerID].inventory.collect(sbase.output.tile);
			for (int x = 0; x <= craftingGrid.length - 1; x++) {
				for (int y = 0; y <= craftingGrid[x].length - 1; y++) {
					try {
						if (craftingGrid[x][y] == null) {continue;}
					} catch (Exception e) { continue;}
					
					if (sbase.MatchesItem(craftingGrid[x][y])) {
						craftingGrid[x][y].count -= sbase.getRequiredCount(craftingGrid[x][y]);
						if (craftingGrid[x][y].count <= 0) {craftingGrid[x][y] = null;}
					}
					}
				}	
			return;
		}
		
		
	}

	private static ShapelessRecipeBase GetShapelessRecipe(TileStack[][] craftingGrid) {
		
		
		for (ShapelessRecipeBase base : ShapelessDictionary) {
			
			boolean match = true;
			for (int x = 0; x <= craftingGrid.length - 1; x++) {
				for (int y = 0; y <= craftingGrid[x].length - 1; y++) {
					try {
						if (craftingGrid[x][y] == null) {continue;}
					} catch (Exception e) { continue;}
					
					if (!base.MatchesItem(craftingGrid[x][y])) {match = false;}
				}	
				if (match) {
					return base;
				}
			}
			

		}
		return null;
	}

	private static ShapedRecipeBase GetShapedRecipe(TileStack[][] craftingGrid) {
		
		return null;
	}

}
