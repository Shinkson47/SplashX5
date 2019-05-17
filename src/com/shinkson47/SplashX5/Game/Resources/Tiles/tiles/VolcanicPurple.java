package com.shinkson47.SplashX5.Game.Resources.Tiles.tiles;

import java.awt.Image;

import com.shinkson47.SplashX5.Game.Resources.ResourceManager;
import com.shinkson47.SplashX5.Interfaces.ITile;

public class VolcanicPurple implements ITile {
	public static Image Texture = ResourceManager.getTexture("Tiles/VolcanicPurple");
	public static boolean Walkable = true, CausesDamage = true, IsFluid = false, IsHarvestable = true, CausesEvent = false;
	public static int DamageMultiplyer = 5, SpeedReduction = 5, Hardness = 10;
	public static String TileData = "";	
}
