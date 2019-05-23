package com.shinkson47.SplashX5.Game.Resources.Tiles.tiles;

import java.awt.Image;

import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Game.Resources.ResourceManager;
import com.shinkson47.SplashX5.Interfaces.ITile;

public class WoodenPlanks implements ITile {
	public static Image Texture = ResourceManager.getTexture("Tiles/WoodenPlanks");
	public static boolean Walkable = true, CausesDamage = false, IsFluid = false, IsHarvestable = true, CausesEvent = false, SupportsForeTile = true, SupportsBaseTile = true;
	public static int DamageMultiplyer = 0, SpeedReduction = 0, Hardness = 1;
	public static String TileData = "", FriendlyName = "Wooden Planks", TileDrop = "WoodenPlanks";
	
	public static void event() {
		ClientWindow.SetWindow(Windows.CraftingBench);
	}
	
}
