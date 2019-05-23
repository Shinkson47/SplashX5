package com.shinkson47.SplashX5.Game.World;

import com.shinkson47.SplashX5.Game.Windows.Game;

public class BackgroundWorldGenerator implements Runnable {

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		for (int width = 0; width < CurrentMap.WorldBorder / Game.ChunkSize; width++) {
			for (int height = 0; height < CurrentMap.WorldBorder / Game.ChunkSize; height++) {
				Maps.GenerateChunk(width * Game.ChunkSize, height * Game.ChunkSize, CurrentMap.TileSet);
			}	
		}
	}

}
