package com.shinkson47.SplashX5.Game.World;

import java.util.Random;

import com.shinkson47.SplashX5.Game.Enumerator.Realms;
import com.shinkson47.SplashX5.Game.Resources.Tiles.TileBase;

public class CurrentMap {
	public static final Realms Diamention = Realms.Overworld;
	public static int CharStartX = -1, CharStartY = -1, MapY = 0, MapX = 0, LoadedMapIndex = 0, BScale = 10, WorldBorder = 1000; 
	public static TileBase TileSet[][];
	public static long Seed = 0;
	public static OpenSimplexNoise noise;
	public static Random random;
	public static boolean[][] ChunkExists;
	public static double NScale = -0.1;	
}