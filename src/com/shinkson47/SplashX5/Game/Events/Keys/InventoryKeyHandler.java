package com.shinkson47.SplashX5.Game.Events.Keys;

import com.shinkson47.SplashX5.Client.Client;
import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Game.Entities.Player.Player;
import com.shinkson47.SplashX5.Game.Enumerator.InventoryAreas;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Game.Events.EventHandler;
import com.shinkson47.SplashX5.Game.Windows.Inventory;
import com.shinkson47.SplashX5.Interfaces.IKeyHandler;

public class InventoryKeyHandler implements IKeyHandler{

	
	
	public static void process() {
		switch (EventHandler.key.getKeyChar()) {
		case 'w':
			if (Inventory.area == InventoryAreas.HotBar) {return;}
			if (Inventory.y == 0) {Inventory.area = InventoryAreas.HotBar; return;}			
			
			if (Inventory.y > 0) {
				Inventory.y--;
			}
			
			break;		
		case 'a':
			if (Inventory.x > 0) {
				Inventory.x--;
			}
			break;		
		case 's':
			if (Inventory.area == InventoryAreas.HotBar) {Inventory.area = InventoryAreas.Inventory;
			if (Inventory.x > Player.players[Client.PlayerID].inventory.Inventory.length - 1) {
				Inventory.x = Player.players[Client.PlayerID].inventory.Inventory.length - 1;
			}
			
			return;}
						
			if (Inventory.y < Player.players[Client.PlayerID].inventory.Inventory[0].length - 1) {
				Inventory.y++;
			}
			
			break;		
		case 'd':	
			if (Inventory.area == InventoryAreas.HotBar) {
				if (Inventory.x < Player.players[Client.PlayerID].inventory.HotBar.length) {
					Inventory.x++;
				}
			} else {
				if (Inventory.x < Player.players[Client.PlayerID].inventory.Inventory.length - 1) {
					Inventory.x++;
				}
			}
			
			
			break;
		case 'e':
				ClientWindow.SetWindow(Windows.Game);
			break;
			
		case ' ':
			if (EventHandler.key.isShiftDown()) {
				Player.players[Client.PlayerID].inventory.cut(Inventory.area, Inventory.x, Inventory.y);	
			} else {
				if (!Player.players[Client.PlayerID].inventory.IsPicked) {
					Player.players[Client.PlayerID].inventory.lift(Inventory.area, Inventory.x, Inventory.y);
				} else {
					Player.players[Client.PlayerID].inventory.drop(Inventory.area, Inventory.x, Inventory.y);
				}
			}
			break;		
		default:
			break;		
	}
		
		if (EventHandler.key.getKeyCode() == 27) { ClientWindow.SetWindow(Windows.Game); } 
	}
}
