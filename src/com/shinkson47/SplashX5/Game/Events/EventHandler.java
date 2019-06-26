package com.shinkson47.SplashX5.Game.Events;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Method;

import com.shinkson47.SplashX5.Client.Client;
import com.shinkson47.SplashX5.Client.ClientRenderer;
import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Client.Logger;
import com.shinkson47.SplashX5.Game.Enumerator.LogState;
import com.shinkson47.SplashX5.Game.Events.Keys.DebugKeyHandler;
import com.shinkson47.SplashX5.Game.Resources.SoundManager;

public class EventHandler {
	public static boolean DoKeyEvent = true, FocusPaused = false;
	private static int i;

	public static void init() {
		// Called when the client initalises.
		// Use only to init the event handler, not to use the event handler to init
		// other stuff.
		// To init other stuff, use the initHandler.
	}

	@SuppressWarnings("unused")
	public static void MiscEvents() {
		if (Client.CauseCrash) {
			int i;
			i = 100 / 0; // I'm sorry. My code cleaner removes the {} if there's one statement, then
							// there's build errors caused by creating an int in this way.
		}
	}

	public static void OnFrameEvent() {
		// This is called AFTER every frame render.
		Client.KeyPressedInFrame = false;
	}

	public static void Pause() {
		if (!FocusPaused) {
			// Trigger once per focus loss 'OnPause' here.
			// ClientWindow.window = Window.Paused;

			// if (ClientWindow.GetWindow() == Windows.Game) {
			// Player.HaltMovement(Client.PlayerID); }
		} else {
			// Trigger pause ticks here
		}
	}

	public static void Halt() {
		// Client is halting.
	}

	public static void RenderNextFrame() {
		ClientRenderer.Update();
		OnFrameEvent();
	}

	public static void Update() {
		// Update is only caused when update no longer paused, so if this is called then
		// we are not longer frame paused.
		if (FocusPaused)
			FocusPaused = !FocusPaused;

		// Cause global update ticks
		ClientWindow.Update();
		SoundManager.Update();
		ControllerHandler.Update();
		MiscEvents();

		// Cause update in current window
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

			KeyEvent parsed = ParseAlternates(key);
			if (parsed != null) {
				keyPressed(parsed);
				return;
			}
			DebugKeyHandler.process();
			try {
				Class<?> Window = Class.forName("com.shinkson47.SplashX5.Game.Events.Keys." + ClientWindow.GetWindow().toString() + "KeyHandler");
				Method init = Window.getMethod("process");
				init.invoke(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * Used to declare key presses which should be ran alongside other key presses.
		 *
		 * (i.e; 'UpArrow' causes 'w' to be parsed through the event handler. the parent
		 * key is ignored.)
		 *
		 * @param toparse
		 * @return
		 */
		public KeyEvent ParseAlternates(KeyEvent toparse) {
			int i = toparse.getKeyCode();

			if (toparse.getKeyCode() == 40)
				return new KeyEvent(ClientWindow.window, 0, System.nanoTime(), 0, 83, 's');
			if (toparse.getKeyCode() == 38)
				return new KeyEvent(ClientWindow.window, 0, System.nanoTime(), 0, 87, 'w');
			if (toparse.getKeyCode() == 37)
				return new KeyEvent(ClientWindow.window, 0, System.nanoTime(), 0, 65, 'a');
			if (toparse.getKeyCode() == 39)
				return new KeyEvent(ClientWindow.window, 0, System.nanoTime(), 0, 68, 'd');
			if (toparse.getKeyCode() == 10)
				return new KeyEvent(ClientWindow.window, 0, System.nanoTime(), 0, 32, ' ');
			return null;
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			Rkey = arg0;

			KeyEvent parsed = ParseAlternates(Rkey);
			if (parsed != null) {
				keyReleased(parsed);
				return;
			}

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
		}
	};

}
