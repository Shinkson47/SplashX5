package com.shinkson47.SplashX5.Game.Resources.Tiles.tiles;

import java.awt.Image;

import com.shinkson47.SplashX5.Game.Resources.ResourceManager;
import com.shinkson47.SplashX5.Interfaces.ITile;

public class Logs implements ITile {
	public static Image Texture = ResourceManager.getTexture("Tiles/Logs");
	public static boolean Walkable = false, CausesDamage = false, IsFluid = false, IsHarvestable = true, CausesEvent = true, SupportsForeTile = false, SupportsBaseTile = false;
	public static int DamageMultiplyer = 1, SpeedReduction = 0, Hardness = 1;
	public static String TileData = "", FriendlyName = "Wooden Logs", TileDrop = "Logs";
}
