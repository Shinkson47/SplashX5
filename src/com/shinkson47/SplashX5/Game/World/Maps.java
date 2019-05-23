
package com.shinkson47.SplashX5.Game.World;

import java.util.Random;

import javax.swing.JOptionPane;

import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Client.Logger;
import com.shinkson47.SplashX5.Game.Entities.Player.Player;
import com.shinkson47.SplashX5.Game.Enumerator.LogState;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Game.Resources.Tiles.TileBase;
import com.shinkson47.SplashX5.Game.Resources.Tiles.Tiles;
import com.shinkson47.SplashX5.Game.Windows.Game;

public class Maps implements Runnable {


	public static void init() {

	}
	
	public static void GenerateNew() {
			TileBase TileSet[][] = new TileBase[CurrentMap.WorldBorder + 1][CurrentMap.WorldBorder + 1	];
			CurrentMap.TileSet = TileSet;
			if (CurrentMap.Seed == 0) {
				CurrentMap.Seed = System.nanoTime();
			}
			CurrentMap.random = new Random(CurrentMap.Seed);
			CurrentMap.noise = new OpenSimplexNoise(CurrentMap.Seed);
			Structures.rnd = new Random(CurrentMap.Seed);
			CurrentMap.ChunkExists = new boolean[CurrentMap.WorldBorder / Game.ChunkSize][CurrentMap.WorldBorder / Game.ChunkSize];
			CurrentMap.CharStartX = -1;
			CurrentMap.CharStartY = -1;
			InitChunkCount = 0;
			
			Biomes.generate();
			
			BackgroundWorldGenerator worker = new BackgroundWorldGenerator();
			Thread thread = new Thread(worker);
			thread.run();
			
			for (int width = 0; width < CurrentMap.WorldBorder / Game.ChunkSize; width++) {
				for (int height = 0; height < CurrentMap.WorldBorder / Game.ChunkSize; height++) {
					if (Player.BoundCheck(width * Game.ChunkSize,height * Game.ChunkSize)) { CurrentMap.CharStartX = width; CurrentMap.CharStartY = height; }			
				}	
			}		

			
			if ((CurrentMap.CharStartX == -1 && CurrentMap.CharStartY == -1)) {
				Logger.log("Initial chunks are not habitable, Generating more.", Maps.class, LogState.Info);
				int x = Game.InitialChunkGenCount, y = Game.InitialChunkGenCount;
				while(CurrentMap.CharStartX == -1 && CurrentMap.CharStartY == -1) {
					x++;
					try {
					GenerateChunk(x * Game.ChunkSize, y * Game.ChunkSize, TileSet);
					if (Player.BoundCheck(x * Game.ChunkSize,y* Game.ChunkSize)) { CurrentMap.CharStartX = x* Game.ChunkSize; CurrentMap.CharStartY = y* Game.ChunkSize; }
					} catch (Exception e) {
						x = Game.InitialChunkGenCount;
						y++;
						if (x == Game.InitialChunkGenCount && y == Game.InitialChunkGenCount) {
							ClientWindow.SetWindow(Windows.MapSelector);
							Logger.log("The generated world was not hospitable.", Maps.class, LogState.Warn);
							JOptionPane.showMessageDialog(ClientWindow.window, "The generated world was not hospitable.");
						}
					}
					
				}
			}
			Logger.log("Generated a new world with " + InitChunkCount + " chunks initially." , Maps.class, LogState.Info	);
			CurrentMap.TileSet = TileSet;
}
	
	private static Thread thread;
	private static int InitChunkCount;
	private static int X, Y;
	private static TileBase Tileset[][];
	public static void GenerateChunk(int x, int y, TileBase tileset[][]) {
		X = x;
		Y = y;
		Maps maps = new Maps();
		thread = new Thread(maps);
		InitChunkCount++;
			for (int X = x; X <= x + Game.ChunkSize; X++) {
				for (int Y = y; Y <= y + Game.ChunkSize; Y++) {
					tileset[X][Y] = Tiles.GetTile(X,Y);
					tileset[X][Y].ForeTile = Structures.getStructure(X,Y);
					CurrentMap.ChunkExists[x / Game.ChunkSize][y / Game.ChunkSize] = true;
				}	
			}
			CurrentMap.TileSet = tileset;
	}
	
	public static boolean ChunkExists(int x, int y) {
		return CurrentMap.ChunkExists[getChunkStart(x) / Game.ChunkSize][getChunkStart(y) / Game.ChunkSize];
	}
	
	public static int getChunkStart(int x) {
		return  (int) (Math.floor(x / Game.ChunkSize) * Game.ChunkSize);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		InitChunkCount++;
		for (int x = X; x <= x + Game.ChunkSize; x++) {
			for (int y = Y; y <= y + Game.ChunkSize; y++) {
				Tileset[x][y] = Tiles.GetTile(x,y);
				Tileset[x][y].ForeTile = Structures.getStructure(x,y);
				CurrentMap.ChunkExists[x / Game.ChunkSize][y / Game.ChunkSize] = true;
			}	
		}
		CurrentMap.TileSet = Tileset;
		thread.stop();
	}
}
		
		
