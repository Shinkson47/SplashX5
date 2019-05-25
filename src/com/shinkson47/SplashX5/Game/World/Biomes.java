package com.shinkson47.SplashX5.Game.World;

import com.shinkson47.SplashX5.Game.Enumerator.Biome;
import com.shinkson47.SplashX5.Game.Windows.Game;

public class Biomes {

	public static void generate() {
		Voroni.Generate(Game.CurrentMap.WorldBorder, Game.CurrentMap.Seed);		
	}
	
	public static Biome GetBiome(int x, int y) {
		return Voroni.eval(x, y);
	}
}
