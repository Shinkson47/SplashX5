package com.shinkson47.SplashX5.Game.Resources.Tiles.tiles;

import com.shinkson47.SplashX5.Interfaces.ITile;

public class Water implements ITile {
	public static String Texture = "Tiles/Water";
	public static boolean Walkable = false, CausesDamage = false, IsFluid = true, IsHarvestable = false, CausesEvent = false, SupportsForeTile = false, SupportsBaseTile = true;
	public static int DamageMultiplyer = 1, SpeedReduction = 1, Hardness = 1;
	public static String TileData = "", FriendlyName = "Water", TileDrop = "Water";
}
