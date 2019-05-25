package com.shinkson47.SplashX5.Game.Resources.Tiles.tiles;

import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Interfaces.ITile;

public class CraftingBench implements ITile {
	public static String Texture = "Tiles/CraftingTable";
	public static boolean Walkable = false, CausesDamage = false, IsFluid = false, IsHarvestable = true, CausesEvent = true, SupportsForeTile = true, SupportsBaseTile = false;
	public static int DamageMultiplyer = 1, SpeedReduction = 0, Hardness = 1;
	public static String TileData = "", FriendlyName = "Crafting Bench", TileDrop = "CraftingTable";
	
	public static void event() {
		ClientWindow.SetWindow(Windows.CraftingBench);
	}
	
}
