package com.shinkson47.SplashX5.Game.Events.Keys;

import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Game.Enumerator.InventoryAreas;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Game.Events.EventHandler;
import com.shinkson47.SplashX5.Game.Windows.CraftingBench;

public class CraftingBenchKeyHandler {

	public static void process() {
		switch (EventHandler.key.getKeyChar()) {
		case 'e': 
			ClientWindow.SetWindow(Windows.Game);
			break;

		case 'w':
			if (CraftingBench.area == InventoryAreas.HotBar) {return;}
			if (CraftingBench.area == InventoryAreas.Inventory) { CraftingBench.area = InventoryAreas.HotBar;}
			if (CraftingBench.area == InventoryAreas.CraftingGrid) { 
				if (CraftingBench.selectorx <= 0) { CraftingBench.area = InventoryAreas.HotBar; }	
			}
			
			
			break;
			
		case 'a':
			break;
		case 's':
			break;
		case 'd':
			break;
			
		}
		
	}
}

