package com.shinkson47.SplashX5.Game.Resources.Tiles.tiles;

import java.awt.Image;

import com.shinkson47.SplashX5.Game.Resources.ResourceManager;
import com.shinkson47.SplashX5.Interfaces.ITile;

public class FreshWater implements ITile {
	public static Image Texture = ResourceManager.getTexture("Tiles/FreshWater");
	public static boolean Walkable = false, CausesDamage = false, IsFluid = true, IsHarvestable = false, CausesEvent = false, SupportsForeTile = false, SupportsBaseTile = true;;
	public static int DamageMultiplyer = 1, SpeedReduction = 1, Hardness = 1;
	public static String TileData = "";	
}
