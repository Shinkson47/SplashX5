package com.shinkson47.SplashX5.Game.Resources.Tiles;

import java.lang.reflect.Field;

import com.shinkson47.SplashX5.Client.Logger;
import com.shinkson47.SplashX5.Game.Enumerator.LogState;

public class ShapelessRecipeBase {
	public TileStack[] craftingList;
	public TileStack output;
	
	ShapelessRecipeBase(Class<?> rclass) {
		
		try {
			
		Object obj = rclass.newInstance();        				//get recipe
		Field matrixField = rclass.getField("craftingList");	//Get matrix field from recipe
		Object[] matrix = (Object[])matrixField.get(obj);	//Import data as object array
		craftingList = (TileStack[])matrix;					//Store crafting matrix in this recipe
	
			output = (TileStack) rclass.getField("output").get(obj);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | InstantiationException e) {
			Logger.log("Nulling recipe for " + rclass.getSimpleName() + " as it is not valid!", ShapelessRecipeBase.class, LogState.Error);
		}
	}
	
	public boolean MatchesItem(TileStack stack) {
		boolean match = true;
		for (TileStack test : craftingList) {
			
			try {if (test == null) {continue;}} catch (Exception e) {continue;}
			
			if (test.tile.tile == stack.tile.tile && test.count <= stack.count) {} else {match = false;}
		}
		
		return match;
	}

	public int getRequiredCount(TileStack tileStack) {
		int count = 0;
		for (TileStack test : craftingList) {
			try {if (test == null) {continue;}} catch (Exception e) {continue;}
			
			if (test.tile.tile == tileStack.tile.tile) {
				count += test.count;
			}
		}
		return count;
	}
	
	
	
}
