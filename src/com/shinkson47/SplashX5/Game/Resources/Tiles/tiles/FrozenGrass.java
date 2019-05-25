package com.shinkson47.SplashX5.Game.Resources.Tiles.tiles;

import com.shinkson47.SplashX5.Interfaces.ITile;

public class FrozenGrass implements ITile {
	public static String Texture = "Tiles/FrozenGrass";
	public static boolean Walkable = true, CausesDamage = false, IsFluid = false, IsHarvestable = true, CausesEvent = false, SupportsForeTile = false, SupportsBaseTile = true;
	public static int DamageMultiplyer = 1, SpeedReduction = 3, Hardness = 1;
	public static String TileData = "", FriendlyName = "Grass", TileDrop = "Grass";	
}
