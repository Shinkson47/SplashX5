package com.shinkson47.SplashX5.Game.Resources.Tiles.tiles;

import java.awt.Image;

import com.shinkson47.SplashX5.Game.Resources.ResourceManager;
import com.shinkson47.SplashX5.Interfaces.ITile;

public class Grass3 implements ITile {
	public static Image Texture = ResourceManager.getTexture("Tiles/Grass3");
	public static boolean Walkable = true, CausesDamage = false, IsFluid = false, IsHarvestable = true, CausesEvent = false, SupportsForeTile = false, SupportsBaseTile = true;
	public static int DamageMultiplyer = 1, SpeedReduction = 0, Hardness = 1;
	public static String TileData = "", FriendlyName = "Grass", TileDrop = "Grass";
}
