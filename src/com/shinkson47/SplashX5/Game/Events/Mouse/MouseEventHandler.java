package com.shinkson47.SplashX5.Game.Events.Mouse;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Game.Windows.CraftingBench;
import com.shinkson47.SplashX5.Game.Windows.Game;
import com.shinkson47.SplashX5.Game.Windows.TileDev;

public class MouseEventHandler implements MouseMotionListener, MouseListener, MouseWheelListener{
//TODO clean up this fucking mess of a handler
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (ClientWindow.GetWindow() == Windows.TileDev) {
			if (arg0.isControlDown()) {
				TileDev.Delete();	
			} else {
				TileDev.Print();
			}
		}
		
		if (ClientWindow.GetWindow() == Windows.Inventory) {
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (ClientWindow.GetWindow() == Windows.Game) { Game.Selector(arg0, true); } 
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (ClientWindow.GetWindow() == Windows.Game) { Game.Selector(arg0, false); }		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		if (ClientWindow.GetWindow() == Windows.TileDev) {
			TileDev.mouseMoved(arg0);
			
			if (ClientWindow.GetWindow() == Windows.TileDev) {
				if (arg0.isControlDown()) {
					TileDev.Delete();	
				} else {
					TileDev.Print();
				}
			}	
		}
		
		if (ClientWindow.GetWindow() == Windows.Game) { Game.Selector(arg0, true); } 
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		if (ClientWindow.GetWindow() == Windows.TileDev) {
			TileDev.mouseMoved(arg0);
		} else if (ClientWindow.GetWindow() == Windows.Game) {
			//Game.Mouse(arg0);
		}
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		if (ClientWindow.GetWindow() == Windows.Game) {
			Game.scroll(arg0);
			return;
		}
		
		if (ClientWindow.GetWindow() == Windows.CraftingBench) {
			CraftingBench.scroll(arg0);
		}
	}

}
