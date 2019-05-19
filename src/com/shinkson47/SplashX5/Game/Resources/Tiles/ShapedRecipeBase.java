package com.shinkson47.SplashX5.Game.Resources.Tiles;

import java.lang.reflect.Field;

import com.shinkson47.SplashX5.Client.Logger;
import com.shinkson47.SplashX5.Game.Enumerator.LogState;

public class ShapedRecipeBase {
	public TileStack[][] craftingMatrix;
	public TileStack output;
	
	
	ShapedRecipeBase(Class<?> rclass) {
		try {
			
			Object obj = rclass.newInstance();        				//get recipe
			Field matrixField = rclass.getField("craftingMatrix");	//Get matrix field from recipe
			Object[][] matrix = (Object[][])matrixField.get(obj);	//Import data as object array
			craftingMatrix = (TileStack[][])matrix;					//Store crafting matrix in this recipe
		
				output = (TileStack) rclass.getField("output").get(obj);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | InstantiationException e) {
				Logger.log("Nulling recipe for " + rclass.getSimpleName() + " as it is not valid!", ShapelessRecipeBase.class, LogState.Error);
			}
		
	}
	
	public boolean MatchesItem(TileStack[][] matrix) {
		boolean match = true;
		boolean absolutematch = false;
		for (int x = 0; x <= craftingMatrix.length - 1; x++) {
			for (int y = 0; y <= craftingMatrix[x].length - 1; y++) {
				//Get item from recipe
				TileStack test = craftingMatrix[x][y];
				
				//Skip if there is no item in that slot
				try {
					if (test == null) {continue;}	
				} catch (Exception e) {continue;}
				
				//If there is an item in the recipe, but not in the crafting matrix slot then it is not valid.
				try {
					if (matrix[y][x] == null) {match = false; continue;}	
				} catch (Exception e) {continue;}
			
				absolutematch = true;
				
			if (test.tile.tile == matrix[y][x].tile.tile && test.count <= matrix[y][x].count) {} else {match = false;}
		}}
		
		if (absolutematch) {
			return match;
		} else {
			return false;
		}
		
	}

	public int getRequiredCount(int x, int y) {
		int count = 0;
		
		try {if (craftingMatrix[x][y] == null) {return 0;}} catch (Exception e) {return 0;}
		
		count = craftingMatrix[x][y].count;
		
		return count;
	}
	
}
