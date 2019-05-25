
package com.shinkson47.SplashX5.Game.Resources.Tiles;

import java.io.Serializable;
import java.lang.reflect.Field;

import com.shinkson47.SplashX5.Client.Logger;
import com.shinkson47.SplashX5.Game.Enumerator.ETiles;
import com.shinkson47.SplashX5.Game.Enumerator.LogState;

public class TileBase implements Serializable{
	private static final long serialVersionUID = 2573927502989301191L;
	public ETiles tile;
	public String Texture = null;
	public boolean Walkable, CausesDamage, IsFluid, IsHarvestable, CausesEvent, SupportsForeTile, SupportsBaseTile;
	public int DamageMultiplyer = 1, SpeedReduction = 1, XPos, YPos, Hardness;
	public String TileData, FriendlyName, TileDrop; //Use to store misc data bout a tile, such as a sign's text.
	public TileBase ForeTile;

	public TileBase(int x, int y, ETiles Tile, String data) {
		XPos = x;
		YPos = y;
		tile = Tile;
		
		if (!Tiles.init) {
			//We are creating tile definitions - pull definition from class.
			try {
				Class<?> tileclass = Class.forName("com.shinkson47.SplashX5.Game.Resources.Tiles.tiles." + Tile.toString()); 
				Field walkable = tileclass.getField("Walkable");
				Walkable = walkable.getBoolean(walkable);
				
				Field causesDamage = tileclass.getField("CausesDamage");
				CausesDamage = causesDamage.getBoolean(causesDamage);
				
				Field isFluid = tileclass.getField("IsFluid");
				IsFluid = isFluid.getBoolean(isFluid);

				Field isHarvestable = tileclass.getField("IsHarvestable");
				IsHarvestable = isHarvestable.getBoolean(isHarvestable);
				
				Field causesEvent = tileclass.getField("CausesEvent");
				CausesEvent = causesEvent.getBoolean(causesEvent);
				
				Field damageMultiplyer = tileclass.getField("DamageMultiplyer");
				DamageMultiplyer = damageMultiplyer.getInt(damageMultiplyer);

				Field hardness = tileclass.getField("Hardness");
				Hardness = hardness.getInt(hardness);
				
				Field speedReduction = tileclass.getField("SpeedReduction");
				SpeedReduction = speedReduction.getInt(speedReduction);
				
				Field texture = tileclass.getField("Texture");
				Texture = (String) texture.get(texture);	
				
				if (Tile == ETiles.Tree) {
					Logger.DoLogs = true;
				}
				
				try {
					Field supportsForeTile = tileclass.getField("SupportsForeTile");
					SupportsForeTile = supportsForeTile.getBoolean(supportsForeTile);
				} catch (Exception e) {
					Logger.log("Tile " + Tile.toString() + " is missing the 'SupportsForeTile' field. It will be used, but this tile may cause issues.", Tiles.class, LogState.Warn);
				}	
				
				try {
					Field supportsBaseTile = tileclass.getField("SupportsBaseTile");
					SupportsBaseTile = supportsBaseTile.getBoolean(supportsBaseTile);
				} catch (Exception e) {
					Logger.log("Tile " + Tile.toString() + " is missing the 'SupportsBaseTile' field. It will be used, but this tile may cause issues.", Tiles.class, LogState.Warn);
				}	
					
				try {
					Field friendlyName = tileclass.getField("FriendlyName");
					FriendlyName = friendlyName.toGenericString();
				} catch (Exception e) {
					Logger.log("Tile " + Tile.toString() + " is missing the 'FriendlyName' field. It will be used, but this tile may cause issues.", Tiles.class, LogState.Warn);
				}	
					
				try {
					Field tileDrop = tileclass.getField("TileDrop");
					TileDrop = tileDrop.toGenericString();
				} catch (Exception e) {
					Logger.log("Tile " + Tile.toString() + " is missing the 'TileDrop' field. It will be used, but this tile may cause issues.", Tiles.class, LogState.Warn);
				}	
			} catch (Exception e){
				e.printStackTrace();
			}
			
		} else { //Use the tile definition
			
			//Is there a tile definition for this tile?
			if (Tiles.IsTileInDictionary(tile)) {
				TileBase newtile = Tiles.GetDictionaryTile(tile);
				
				try {
					if (tile == null) {return;} //This tile doesn't actually exist, skip it.
				} catch (Exception e) {
					if (tile == null) {return;} //This tile doesn't actually exist, skip it.
				} //Avoid null pointer, but one also means that there is no definition.
				
			//Create from premade definition.
			Walkable = newtile.Walkable;
			CausesDamage = newtile.CausesDamage;	
			IsFluid = newtile.IsFluid;
			IsHarvestable = newtile.IsHarvestable;
			CausesEvent = newtile.CausesEvent;
			DamageMultiplyer = newtile.DamageMultiplyer;
			SpeedReduction = newtile.SpeedReduction;
			Texture = newtile.Texture;
			SupportsForeTile = newtile.SupportsForeTile;
			SupportsBaseTile = newtile.SupportsBaseTile;
			FriendlyName = newtile.FriendlyName;
			TileDrop = newtile.TileDrop;
		} else {
			//This tile doesn't actually exist, skip it.
			return;
		}
		}
			
	
		
	}
}
