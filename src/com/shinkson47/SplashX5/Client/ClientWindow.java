package com.shinkson47.SplashX5.Client;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.lang.reflect.Method;

import javax.swing.JFrame;

import com.shinkson47.SplashX5.Game.Enumerator.LogState;
import com.shinkson47.SplashX5.Game.Enumerator.WindowSizeState;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Game.Events.EventHandler;
import com.shinkson47.SplashX5.Game.Events.Mouse.MouseEventHandler;
import com.shinkson47.SplashX5.Game.Resources.ResourceManager;


public class ClientWindow {

	public static int height = 1037, width = 1920, x, y; //Initial size
	private static Windows CurrentWindow = Windows.PreInit; //current window, never directly set externally.
	public static ClientRenderer renderer; //Render handler
	public static JFrame window = new JFrame();
	public static WindowSizeState SizeState;
	
	public static void init() {
		//Initalise window only.
		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DefaultSize();
		window.setLocationRelativeTo(null);
		window.setFocusable(true);
		window.setResizable(false);
					
		window.addKeyListener(EventHandler.Listener);
		window.setTitle(Client.ClientName + Client.ClientVersion);

		MouseEventHandler mouseHandler = new MouseEventHandler();
		
		window.setPreferredSize(new Dimension(height,width));
		window.addMouseListener(mouseHandler);
		window.addMouseMotionListener(mouseHandler);
		window.addMouseWheelListener(mouseHandler);
		window.setUndecorated(true);
		window.setVisible(true);
		
		x = window.getLocation().x;
		y = window.getLocation().y;

		setFontSize(10f);
	}
	
	public static void setFontSize(float f) {
		window.setFont(ResourceManager.GetFont("xirod").deriveFont(f));		
	}

	public static Windows GetWindow() {
		return CurrentWindow;
	}

	public static void SetWindow(Windows window) {
		Windows tempWindow = window;
		CurrentWindow = window;
		Logger.log("Loading " + window, ClientHandler.class, LogState.Info);
		try {
			Class<?> Window = Class.forName("com.shinkson47.SplashX5.Game.Windows." + window.toString());
			Method init = Window.getMethod("init");
			init.invoke(null);
		} catch (Exception e) {
			Logger.log("Failed to load " + window.toString() + ". Is there a valid class with a static init method?", ClientWindow.class, LogState.Error);
			e.printStackTrace();
			Client.ParseException(e);
			CurrentWindow = tempWindow;
		}
		
	
		
	}

	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static boolean DoForceFocus = true, IgnoreHiddenWindow = false;
	public static void Update() {
		//window.setName(Client.ClientName + Client.ClientVersion + CurrentWindow); //Current window doesn't show, therefore this is useless.
		if (DoForceFocus) {window.requestFocusInWindow();}
		if (!window.isVisible() && !IgnoreHiddenWindow) {Client.HaltClient();}
		if (IgnoreHiddenWindow && window.isVisible()) { IgnoreHiddenWindow = !IgnoreHiddenWindow; }
}
		
	
	public static void DefaultSize() {
		if (SizeState == WindowSizeState.Default) {return;}
		
		if (screenSize.getWidth() < width) { fullscreen(); return; }
		if (screenSize.getHeight() < height) { fullscreen(); return; }
		
		window.setBounds(x,y,width,height);	
		SizeState = WindowSizeState.Default;
	}
	
	public static void fullscreen() {
		if (SizeState == WindowSizeState.Full) {return;} 
		int X = screenSize.width;
		int Y = screenSize.height;
		
		window.setBounds(0,0,X,Y);
		SizeState = WindowSizeState.Full;
	}
	

	
	
}
