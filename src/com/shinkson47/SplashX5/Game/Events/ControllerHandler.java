package com.shinkson47.SplashX5.Game.Events;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Game.Events.Mouse.MouseEventHandler;
import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;

public class ControllerHandler {
	private static ControllerManager manager;
	private static MouseEventHandler mouseEventHandler = new MouseEventHandler();
	private static boolean LSDown, LSUp, LSRight, LSLeft, DLeft, DRight, DUp, DDown;
	public static void init(){
		manager = new ControllerManager();
		manager.initSDLGamepad();
	}

	public static void Update() {
		
		ControllerState state = manager.getState(0);
		manager.update();
	
		//ABXY
		if (state.xJustPressed) {
			mouseEventHandler.mouseClicked(new MouseEvent(ClientWindow.window, 0, System.currentTimeMillis(), MouseEvent.SHIFT_DOWN_MASK, 0, 0, 1, false, MouseEvent.BUTTON1));
		} //shift + lmb
		
		if (state.yJustPressed) {
			EventHandler.Listener.keyPressed(new KeyEvent(ClientWindow.window, 0, System.currentTimeMillis(), KeyEvent.KEY_PRESSED, (int) 'e', 'e'));
		} //e
		if (state.aJustPressed) {
			EventHandler.Listener.keyPressed(new KeyEvent(ClientWindow.window, 0, System.currentTimeMillis(), KeyEvent.KEY_PRESSED, (int) ' ', ' '));	
		} //Space
	
		if (state.bJustPressed) {
			EventHandler.Listener.keyPressed(new KeyEvent(ClientWindow.window, 0, System.currentTimeMillis(), KeyEvent.KEY_PRESSED, (int) 'c', 'c'));
		} //Space
		
		if (state.startJustPressed) {
			EventHandler.Listener.keyPressed(new KeyEvent(ClientWindow.window, 0, System.currentTimeMillis(), KeyEvent.KEY_PRESSED, 27, '•'));	
		} //Esc
	
		
		
		//bumper
		if (state.lbJustPressed) {
			mouseEventHandler.mouseWheelMoved(new MouseWheelEvent(ClientWindow.window,0,System.currentTimeMillis(),0,0,0,0,0,0,false,MouseWheelEvent.WHEEL_UNIT_SCROLL, 1, -1, -1.0));
		} //scroll down
		
		if (state.rbJustPressed) {
			mouseEventHandler.mouseWheelMoved(new MouseWheelEvent(ClientWindow.window,0,System.currentTimeMillis(),0,0,0,0,0,0,false,MouseWheelEvent.WHEEL_UNIT_SCROLL, 1, 1, 1.0));
		} //scroll up
		
		
		//trigger
		if (state.leftTrigger < 0.4) {
			mouseEventHandler.mouseClicked(new MouseEvent(ClientWindow.window, 0, System.currentTimeMillis(), 0, 0, 0, 1, false, MouseEvent.BUTTON1));
		} //lmb
		
		if (state.rightTrigger < 0.4) {
			mouseEventHandler.mouseClicked(new MouseEvent(ClientWindow.window, 0, System.currentTimeMillis(), 0, 0, 0, 1, false, MouseEvent.BUTTON3));
		} //rmb

		
		
		//Left Stick
		//W
		if (state.leftStickY > 0.2f) {
			LSUp = true;
			EventHandler.Listener.keyPressed(new KeyEvent(ClientWindow.window, 0, System.currentTimeMillis(), KeyEvent.KEY_PRESSED, (int) 'w', 'w'));
		} else {
			if (LSUp) {
			EventHandler.Listener.keyReleased(new KeyEvent(ClientWindow.window, 0, System.currentTimeMillis(), KeyEvent.KEY_RELEASED, (int) 'w', 'w'));
			LSUp = false;
			}
		}
		
		//S
		if (state.leftStickY < -0.2f) {
			LSDown = true;
			EventHandler.Listener.keyPressed(new KeyEvent(ClientWindow.window, 0, System.currentTimeMillis(), KeyEvent.KEY_PRESSED, (int) 's', 's'));
		} else {
			if (LSDown) {
			EventHandler.Listener.keyReleased(new KeyEvent(ClientWindow.window, 0, System.currentTimeMillis(), KeyEvent.KEY_RELEASED, (int) 's', 's'));
			LSDown = false;
		}
		}
	
		//D
		if (state.leftStickX > 0.2f) {
			LSRight = true;
			EventHandler.Listener.keyPressed(new KeyEvent(ClientWindow.window, 0, System.currentTimeMillis(), KeyEvent.KEY_PRESSED, (int) 'd', 'd'));
		} else {
			if (LSRight) {
			EventHandler.Listener.keyReleased(new KeyEvent(ClientWindow.window, 0, System.currentTimeMillis(), KeyEvent.KEY_RELEASED, (int) 'd', 'd'));
			LSRight = false;
			}
		}
		
		//A
		if (state.leftStickX < -0.2f) {
			LSLeft = true;
			EventHandler.Listener.keyPressed(new KeyEvent(ClientWindow.window, 0, System.currentTimeMillis(), KeyEvent.KEY_PRESSED, (int) 'a', 'a'));
		} else {
			if (LSLeft) {
			EventHandler.Listener.keyReleased(new KeyEvent(ClientWindow.window, 0, System.currentTimeMillis(), KeyEvent.KEY_RELEASED, (int) 'a', 'a'));
			LSLeft = false;
			}	
		}
	
		//TODO Right stick
		
		//DPad
		
		if (state.dpadUpJustPressed) {
			DUp = true;
			EventHandler.Listener.keyPressed(new KeyEvent(ClientWindow.window, 0, System.currentTimeMillis(), KeyEvent.KEY_PRESSED, (int) 'w', 'w'));
		} else {
			if (DUp) {
				DUp = false;
				EventHandler.Listener.keyReleased(new KeyEvent(ClientWindow.window, 0, System.currentTimeMillis(), KeyEvent.KEY_RELEASED, (int) 'w', 'w'));	
			}
		}
	
	
		if (state.dpadDownJustPressed) {
			DDown = true;
			EventHandler.Listener.keyPressed(new KeyEvent(ClientWindow.window, 0, System.currentTimeMillis(), KeyEvent.KEY_PRESSED, (int) 's', 's'));
		} else {
			if (DDown) {
				DDown = false;
				EventHandler.Listener.keyReleased(new KeyEvent(ClientWindow.window, 0, System.currentTimeMillis(), KeyEvent.KEY_RELEASED, (int) 's', 's'));	
			}//S
		}
	
		if (state.dpadRightJustPressed) {
			DRight = true;
			EventHandler.Listener.keyPressed(new KeyEvent(ClientWindow.window, 0, System.currentTimeMillis(), KeyEvent.KEY_PRESSED, (int) 'd', 'd'));
		} else {
			if (DRight) {
				DRight = false;
				EventHandler.Listener.keyReleased(new KeyEvent(ClientWindow.window, 0, System.currentTimeMillis(), KeyEvent.KEY_RELEASED, (int) 'd', 'd'));	
			} //D
		}
		
		if (state.dpadLeftJustPressed) {
			DLeft = true;
			EventHandler.Listener.keyPressed(new KeyEvent(ClientWindow.window, 0, System.currentTimeMillis(), KeyEvent.KEY_PRESSED, (int) 'a', 'a'));
		} else {
			if (DLeft) {
				DLeft = false;
				EventHandler.Listener.keyReleased(new KeyEvent(ClientWindow.window, 0, System.currentTimeMillis(), KeyEvent.KEY_RELEASED, (int) 'a', 'a'));	
		}//A
		}
	}

	

}
