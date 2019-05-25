package com.shinkson47.SplashX5.Game.Resources.Tiles.tiles;

import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Interfaces.ITile;

public class WoodenPlanks implements ITile {
	public static String Texture = "Tiles/WoodenPlanks";
	public static boolean Walkable = true, CausesDamage = false, IsFluid = false, IsHarvestable = true, CausesEvent = false, SupportsForeTile = true, SupportsBaseTile = true;
	public static int DamageMultiplyer = 0, SpeedReduction = 0, Hardness = 1;
	public static String TileData = "", FriendlyName = "Wooden Planks", TileDrop = "WoodenPlanks";
	
	public static void event() {
		ClientWindow.SetWindow(Windows.CraftingBench);
	}
	
}
