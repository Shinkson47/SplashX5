package com.shinkson47.SplashX5.Game.Resources.Tiles;
import com.shinkson47.SplashX5.Client.Logger;
import com.shinkson47.SplashX5.Game.Enumerator.ETiles;
import com.shinkson47.SplashX5.Game.Enumerator.LogState;
import com.shinkson47.SplashX5.Game.Windows.Game;
import com.shinkson47.SplashX5.Game.World.Biomes;

public class Tiles {

	public static TileBase[] TileDictionary = new TileBase[25565];
	public static boolean init = false;
	public static int TileCount = 0;
	
	//init tiles
	public static void init() {
			TileCount = 0;
			Logger.log("Initialising tile dictionary", Tiles.class, LogState.Info);
			
			for (ETiles tile : ETiles.values()) {
				
				try {
					//See if a definition for this tile exists
					@SuppressWarnings("unused")
					Class<?> tileclass = Class.forName("com.shinkson47.SplashX5.Game.Resources.Tiles.tiles." + tile.toString()); 
				} catch (Exception e) {
					//if not Warn and skip
					Logger.log("Ignoring enum for " + tile.toString() + ", as there is no tile definition." , Tiles.class, LogState.Warn);
					continue;				
				}
				
				RegisterTile(new TileBase(-1,-1, tile, ""));
			}			
			Logger.log("Initalised " + (TileCount - 1) + " tiles into the tile dictionary.", Tiles.class, LogState.Info);
			init = true;
	}

	private static void RegisterTile(TileBase tileBase) {
		TileDictionary[TileCount] = tileBase;
		TileCount++;
	}

	//Get tile enum by string name
	public static ETiles parseTile(String string) {
		try {
		ETiles tile = ETiles.valueOf(string);
		
		if (tile == null) {
			return ETiles.mapnull;
		} else {
			return tile;
		}}
		catch (Exception e) {
			return ETiles.mapnull;
		}
	}
	
	//Biome Definitions
	public static TileBase GetTile(int x, int y) {
		ETiles tile = ETiles.Grass1;
		double eval = Game.CurrentMap.noise.eval(x * Game.CurrentMap.NScale, y * Game.CurrentMap.NScale);
			switch (Biomes.GetBiome(x,y)){
			case Desert:
				if (eval < -0.8){ tile = ETiles.Water; }
				if (eval < 0.5 && eval > -0.8){ tile = ETiles.Sand2; }
				if (eval > 0.5){ tile = ETiles.Sand1; }
				break;
			case Ocean:
				if (eval < 1.8){ tile = ETiles.Water; }
				if (eval > 1.8){ tile = ETiles.Sand1; }
				break;
			case Plains:
				if (eval < -0.7){ tile = ETiles.FreshWater; }
				if (eval < -0.6 && eval > -0.7){ tile = ETiles.Grass2; }
				if (eval < -0.4 && eval > -0.6){ tile = ETiles.Grass3; }
				if (eval < -0.2 && eval > -0.4){ tile = ETiles.Grass1; }
				if (eval > -0.2){ tile = ETiles.Grass4; }
				break;
			case FrozenPlains:
				if (eval < -0.5){ tile = ETiles.Ice; }
				if (eval > -0.5){ tile = ETiles.FrozenGrass; }				
				break;
			case FrozenOcean:
				tile = ETiles.Ice;				
				break;
			case RedVolcanic:
				if (eval < 0){ tile = ETiles.VolcanicRed; }
				if (eval > 0){ tile = ETiles.VolcanicOrange; }				
				break;
			case BlueVolcanic:
				if (eval < 0){ tile = ETiles.VolcanicBlue; }
				if (eval > 0){ tile = ETiles.VolcanicPurple; }
				break;
			default:
				break;
			}		
		return new TileBase(x,y,tile,"");
	}

	public static boolean IsTileInDictionary(ETiles tile) {
		for (TileBase Tile : TileDictionary) {
			try {
			if (Tile.tile == tile) {
				return true;
			}
		} catch (Exception e) {
			return false;
			//null tiles will prevent dictionary readout
		}
	}
		return false;	
}

	public static TileBase GetDictionaryTile(ETiles tile) {
		for (TileBase Tile : TileDictionary) {
			if (Tile.tile == tile) {
				return Tile;
			}
		}	
		return null;
	}
}
