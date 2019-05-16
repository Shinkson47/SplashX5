package com.shinkson47.SplashX5.Game.Events.Keys;

import com.shinkson47.SplashX5.Game.Events.EventHandler;
import com.shinkson47.SplashX5.Game.Windows.TileDev;
import com.shinkson47.SplashX5.Interfaces.IKeyHandler;

public class TileDevKeyHandler implements IKeyHandler{

	public static void process() {
		switch (EventHandler.key.getKeyChar()) {
		case 'w':
			if (TileDev.CursorY > 0) { TileDev.CursorY--;}
			break;
			
		case 'a':
			if (TileDev.CursorX > 0) { TileDev.CursorX--; }
			break;
			
		case 's':
			if (TileDev.CursorY < 63) { TileDev.CursorY++; }
			break;
			
		case 'd':
			if (TileDev.CursorX < 63) { TileDev.CursorX++; }
			break;
		
		case '+':
			if (EventHandler.key.isControlDown()) {
				TileDev.Zoom(1);
			} else {
				if (TileDev.CursorMultiplyer < 64) { TileDev.CursorMultiplyer++; }
			}
			break;
		
		case '-':
			if (EventHandler.key.isControlDown()) {
				TileDev.Zoom(-1);
			} else {
				if (TileDev.CursorMultiplyer > 1) { TileDev.CursorMultiplyer--; }
			}
			break;
		case ' ':
				if (EventHandler.key.isControlDown()) {
					TileDev.Delete();	
				} else {
					TileDev.Print();
				}
			break;
		case 'e':
			TileDev.Export();
			break;
		
		default:
			try {
				int i = Integer.parseInt(String.valueOf((char) EventHandler.key.getKeyCode()));

				if (EventHandler.key.isControlDown()) {
					TileDev.NewColour(i);
				} else {
					TileDev.SetColour(i);
				}
			} catch (Exception e1) {
				
			}
			break;		
	}
	}
	
}
