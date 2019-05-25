package com.shinkson47.SplashX5.Game.Events;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Method;

import com.shinkson47.SplashX5.Client.Client;
import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Client.Logger;
import com.shinkson47.SplashX5.Client.ClientRenderer;
import com.shinkson47.SplashX5.Game.Entities.Player.Player;
import com.shinkson47.SplashX5.Game.Enumerator.LogState;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Game.Events.Keys.DebugKeyHandler;
import com.shinkson47.SplashX5.Game.Resources.SoundManager;

public class EventHandler {
	public static boolean DoKeyEvent = true, FocusPaused = false;
		
	public static void init() {
		//Called when the client initalises.
		//Use only to init the event handler, not to use the event handler to init other stuff.
		//To init other stuff, use the initHandler.
	}
	
	public static void MiscEvents() {
		if (Client.CauseCrash) {
			@SuppressWarnings("unused")
			int i = 100 / 0;
		}
	}
	
	public static void OnFrameEvent() {
		//This is called AFTER every frame render.
		Client.KeyPressedInFrame = false;
	}

	public static void Pause() {
		if (!FocusPaused) {
			//Trigger once per focus loss 'OnPause' here.
			//ClientWindow.window = Window.Paused;
			
			//if (ClientWindow.GetWindow() == Windows.Game) {	Player.HaltMovement(Client.PlayerID); }
		} else {
			//Trigger pause ticks here
		}
	}
	
	public static void Halt() {
		//Client is halting.		
	}
	
	public static void RenderNextFrame() {
		ClientRenderer.Update();
		OnFrameEvent();
	}
	
	public static void Update() {
		//Update is only caused when update no longer paused, so if this is called then we are not longer frame paused.
		if (FocusPaused) { 
			FocusPaused = !FocusPaused;
		}
	
		//Cause global update ticks
		ClientWindow.Update(); 
		SoundManager.Update();
		ControllerHandler.Update();
		MiscEvents();
		
		//Cause update in current window
		try {
			Class<?> Window = Class.forName("com.shinkson47.SplashX5.Game.Windows." + ClientWindow.GetWindow().toString());
			Method init = Window.getMethod("Update");
			init.invoke(null);
		} catch (Exception e) {
			Logger.log("Failed to tick" + ClientWindow.GetWindow().toString() + ". Is there a valid class with a static Update method?", ClientWindow.class, LogState.Error);
			Client.ParseException(e);
		}	
		
		
		
	}

	public static KeyEvent key, Rkey;
	public static KeyListener Listener = new KeyListener() {
		
		@Override
		public void keyPressed(KeyEvent arg0) {
			key = arg0;
			DebugKeyHandler.process();
		try {
			Class<?> Window = Class.forName("com.shinkson47.SplashX5.Game.Events.Keys." + ClientWindow.GetWindow().toString() + "KeyHandler");
			Method init = Window.getMethod("process");
			init.invoke(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			Rkey = arg0;
			try {
				Class<?> Window = Class.forName("com.shinkson47.SplashX5.Game.Events.Keys." + ClientWindow.GetWindow().toString() + "KeyHandler");
				Method init = Window.getMethod("released");
				init.invoke(null);
			} catch (Exception e) {
			}
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			DebugKeyHandler.process();
		}};




}
