package com.shinkson47.SplashX5.Game.Resources.Tiles.tiles;

import com.shinkson47.SplashX5.Interfaces.ITile;

public class Tree implements ITile {
	public static String Texture = "Tiles/Tree";
	public static boolean Walkable = false, CausesDamage = false, IsFluid = false, IsHarvestable = true, CausesEvent = false, SupportsBaseTile = false, SupportsForeTile = true;
	public static int DamageMultiplyer = 1, SpeedReduction = 1, Hardness = 1;
	public static String TileData = "";	
}