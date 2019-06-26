<<<<<<< HEAD
package com.shinkson47.SplashX5.Game.Events.Keys;

import com.shinkson47.SplashX5.Client.Client;
import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Game.Entities.Player.Player;
import com.shinkson47.SplashX5.Game.Enumerator.InventoryAreas;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Game.Events.EventHandler;
import com.shinkson47.SplashX5.Game.Windows.CraftingBench;
import com.shinkson47.SplashX5.Game.Windows.Inventory;

public class CraftingBenchKeyHandler {

	public static void process() {
		switch (EventHandler.key.getKeyChar()) {
		case 'e':
			ClientWindow.SetWindow(Windows.Game);
			break;

		case 'w':
			if (CraftingBench.area == InventoryAreas.HotBar)
				return;

			if (CraftingBench.area == InventoryAreas.Inventory) {
				CraftingBench.area = InventoryAreas.HotBar;
				return;
			}

			if (CraftingBench.area == InventoryAreas.CraftingGrid) {
				if (CraftingBench.selectory > 0) {
					CraftingBench.selectory--;
					return;
				}

				CraftingBench.area = InventoryAreas.Inventory;
				return;
			}
			break;

		case 'a':
			if (CraftingBench.selectorx > 0)
				CraftingBench.selectorx--;
			break;
		case 's':
			if (CraftingBench.area == InventoryAreas.HotBar) {
				CraftingBench.area = InventoryAreas.Inventory;
				return;
			}

			if (CraftingBench.area == InventoryAreas.Inventory) {
				CraftingBench.area = InventoryAreas.CraftingGrid;
				if (CraftingBench.selectorx > CraftingBench.CraftingMatrix[0].length - 1)
					CraftingBench.selectorx = CraftingBench.CraftingMatrix[0].length - 1;
				return;
			}

			if (CraftingBench.area == InventoryAreas.CraftingGrid)
				if (CraftingBench.selectory < CraftingBench.CraftingMatrix[0].length - 1) {
					CraftingBench.selectory++;
					return;
				}
			break;
		case 'd':
			if (CraftingBench.area == InventoryAreas.HotBar | CraftingBench.area == InventoryAreas.Inventory)
				if (CraftingBench.selectorx < Player.players[Client.PlayerID].inventory.HotBar.length) {
					CraftingBench.selectorx++;
					return;
				} else {
					CraftingBench.selectorx = Player.players[Client.PlayerID].inventory.HotBar.length;
					return;
				}

			if (CraftingBench.area == InventoryAreas.CraftingGrid)
				if (CraftingBench.selectorx < CraftingBench.CraftingMatrix.length - 1) {
					CraftingBench.selectorx++;
					return;
				} else {
					CraftingBench.selectorx = CraftingBench.CraftingMatrix.length - 1;
					return;
				}

			break;

		case ' ':
			if (CraftingBench.IsPicked)
				CraftingBench.drop();
			else
				CraftingBench.lift();
			break;
		case 'c':
			CraftingBench.Single(Inventory.x, Inventory.y);
			break;
		}

		if (EventHandler.key.getKeyCode() == 27)
			ClientWindow.SetWindow(Windows.Game);
	}
}
=======
package com.shinkson47.SplashX5.Game.Events.Keys;

import com.shinkson47.SplashX5.Client.Client;
import com.shinkson47.SplashX5.Client.ClientWindow;
<<<<<<< HEAD
=======
import com.shinkson47.SplashX5.Game.Entities.Player.Player;
>>>>>>> 23fe9242ccc11a110962801e7e828d362097108e
import com.shinkson47.SplashX5.Game.Enumerator.InventoryAreas;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Game.Events.EventHandler;
import com.shinkson47.SplashX5.Game.Windows.CraftingBench;

public class CraftingBenchKeyHandler {

	public static void process() {
		switch (EventHandler.key.getKeyChar()) {
<<<<<<< HEAD
		case 'e': 
=======
		case 'e':
>>>>>>> 23fe9242ccc11a110962801e7e828d362097108e
			ClientWindow.SetWindow(Windows.Game);
			break;

		case 'w':
<<<<<<< HEAD
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
		
=======
			if (CraftingBench.area == InventoryAreas.HotBar)
				return;

			if (CraftingBench.area == InventoryAreas.Inventory) {
				CraftingBench.area = InventoryAreas.HotBar;
				return;
			}

			if (CraftingBench.area == InventoryAreas.CraftingGrid) {
				if (CraftingBench.selectory > 0) {
					CraftingBench.selectory--;
					return;
				}

				CraftingBench.area = InventoryAreas.Inventory;
				return;
			}
			break;

		case 'a':
			if (CraftingBench.selectorx > 0)
				CraftingBench.selectorx--;
			break;
		case 's':
			if (CraftingBench.area == InventoryAreas.HotBar) {
				CraftingBench.area = InventoryAreas.Inventory;
				return;
			}

			if (CraftingBench.area == InventoryAreas.Inventory) {
				CraftingBench.area = InventoryAreas.CraftingGrid;
				if (CraftingBench.selectorx > CraftingBench.CraftingMatrix[0].length - 1)
					CraftingBench.selectorx = CraftingBench.CraftingMatrix[0].length - 1;
				return;
			}

			if (CraftingBench.area == InventoryAreas.CraftingGrid)
				if (CraftingBench.selectory < CraftingBench.CraftingMatrix[0].length - 1) {
					CraftingBench.selectory++;
					return;
				}
			break;
		case 'd':
			if (CraftingBench.area == InventoryAreas.HotBar | CraftingBench.area == InventoryAreas.Inventory)
				if (CraftingBench.selectorx < Player.players[Client.PlayerID].inventory.HotBar.length) {
					CraftingBench.selectorx++;
					return;
				} else {
					CraftingBench.selectorx = Player.players[Client.PlayerID].inventory.HotBar.length;
					return;
				}

			if (CraftingBench.area == InventoryAreas.CraftingGrid)
				if (CraftingBench.selectorx < CraftingBench.CraftingMatrix.length - 1) {
					CraftingBench.selectorx++;
					return;
				} else {
					CraftingBench.selectorx = CraftingBench.CraftingMatrix.length - 1;
					return;
				}

			break;

		case ' ':
			if (CraftingBench.IsPicked)
				CraftingBench.drop();
			else
				CraftingBench.lift();
			break;
		}
>>>>>>> 23fe9242ccc11a110962801e7e828d362097108e
	}
}

>>>>>>> branch 'master' of https://github.com/Shinkson47/SplashX5
