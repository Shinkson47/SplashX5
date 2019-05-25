package com.shinkson47.SplashX5.Game.World;

import java.io.Serializable;
import java.util.Random;

import com.shinkson47.SplashX5.Game.Entities.Player.PlayerBase;
import com.shinkson47.SplashX5.Game.Enumerator.Realms;
import com.shinkson47.SplashX5.Game.Resources.Tiles.TileBase;

public class MapBase implements Serializable{
	private static final long serialVersionUID = 8831241248777503187L;
	public final Realms Diamention = Realms.Overworld;
	public int CharStartX = -1, CharStartY = -1, MapY = 0, MapX = 0, LoadedMapIndex = 0, BScale = 10, WorldBorder = 1000;
	public long Seed = 0;
	public OpenSimplexNoise noise;
	public Random random;
	public boolean[][] ChunkExists;
	public double NScale = -0.1;
	public PlayerBase players[];
	public TileBase TileSet[][];
	
}