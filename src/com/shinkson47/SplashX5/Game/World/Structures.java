package com.shinkson47.SplashX5.Game.World;

import java.util.Random;

import com.shinkson47.SplashX5.Game.Enumerator.ETiles;
import com.shinkson47.SplashX5.Game.Resources.Tiles.TileBase;
import com.shinkson47.SplashX5.Game.Windows.Game;

public class Structures {
	
	public static Random rnd;
	public static TileBase getStructure(int x, int y) {
		TileBase tile = null;
		double eval = Game.CurrentMap.noise.eval(x * Game.CurrentMap.NScale, y * Game.CurrentMap.NScale);
			switch (Biomes.GetBiome(x,y)){
			case Desert:
				//Abandoned ships?
				//Ruins
				break;
			case Ocean:
				//Lillies
				//Lanternss
				//Islands Treasure?
				//ships
				break;
			case Plains:
				//abandoned camps
				//populated camps
				//trees
				//Boulders
				if (eval > 0.5) {
					tile = new TileBase(x,y,ETiles.Tree,"");}
				break;
			default:
				break;
			}		
		return tile;
	}
	
}
