package com.shinkson47.SplashX5.Interfaces;

import javax.swing.ImageIcon;

public interface ITile {
	static ImageIcon Texture = null;
	boolean Walkable = false, CausesDamage = false, IsFluid = false, IsHarvestable = false, CausesEvent = false;
	int DamageMultiplyer = 1, SpeedReduction = 1, Hardness = 1;
	String TileData = "";
	
	
}
