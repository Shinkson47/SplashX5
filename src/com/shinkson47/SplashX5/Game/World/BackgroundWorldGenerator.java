package com.shinkson47.SplashX5.Game.World;

import com.shinkson47.SplashX5.Game.Windows.Game;

public class BackgroundWorldGenerator implements Runnable {

	@Override
	public void run() {
		for (int width = 0; width < Game.CurrentMap.WorldBorder / Game.ChunkSize; width++) {
			for (int height = 0; height < Game.CurrentMap.WorldBorder / Game.ChunkSize; height++) {
				Maps.GenerateChunk(width * Game.ChunkSize, height * Game.ChunkSize, Game.CurrentMap.TileSet);
			}	
		}
	}

}
