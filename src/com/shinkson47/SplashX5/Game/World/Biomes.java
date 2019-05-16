package com.shinkson47.SplashX5.Game.World;

import com.shinkson47.SplashX5.Game.Enumerator.Biome;

public class Biomes {

	public static void generate() {
		Voroni.Generate(CurrentMap.WorldBorder, CurrentMap.Seed);		
	}
	
	public static Biome GetBiome(int x, int y) {
		return Voroni.eval(x, y);
	}
}
