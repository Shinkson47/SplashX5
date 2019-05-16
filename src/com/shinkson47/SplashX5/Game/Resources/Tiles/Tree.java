package com.shinkson47.SplashX5.Game.Resources.Tiles;

import java.awt.Image;

import com.shinkson47.SplashX5.Game.Resources.ResourceManager;
import com.shinkson47.SplashX5.Interfaces.ITile;

public class Tree implements ITile {
	public static Image Texture = ResourceManager.getTexture("Tiles/Tree");
	public static boolean Walkable = false, CausesDamage = false, IsFluid = false, IsHarvestable = true, CausesEvent = false, SupportsBaseTile = false, SupportsForeTile = true;
	public static int DamageMultiplyer = 1, SpeedReduction = 1, Hardness = 1;
	public static String TileData = "";	
}