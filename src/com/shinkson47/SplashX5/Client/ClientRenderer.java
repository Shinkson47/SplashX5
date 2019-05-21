package com.shinkson47.SplashX5.Client;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.lang.reflect.Method;
import java.util.Random;

import com.shinkson47.SplashX5.Game.Enumerator.LogState;
import com.shinkson47.SplashX5.Interfaces.Renderer;



public class ClientRenderer implements Renderer {
	
	
	public static boolean FrameUpdated = false;
	public static void init() {
		
	}
	
	public static Graphics graphics;
	public static void Update() {
		//GameHandler.Frames++;
		
		Image bdImage = ClientWindow.window.createImage(ClientWindow.window.getWidth(), ClientWindow.window.getHeight());
		graphics = bdImage.getGraphics();
		
		//Buffer, and create new frame
		
		setBackground(graphics);
		//if (!ClientWindow.window.hasFocus()) {return;} //Don't render on focus loss
		FrameUpdated = false;
		
		try {
			Class<?> Window = Class.forName("com.shinkson47.SplashX5.Game.Windows." + ClientWindow.GetWindow().toString());
			Method update = Window.getMethod("RenderFrame");
			update.invoke(null);
		} catch (Exception e) {
			//Logger.log("Could not call a renderer for window :" + ClientWindow.GetWindow().toString() + ". Is there a valid class with a static Update method?", ClientWindow.class, LogState.Error);
			//Client.ParseException(e);
		}		
		
		ClientWindow.window.getGraphics().drawImage(bdImage, 0, 0, null);
		
		if (FrameUpdated != true) {
			Logger.log("Frame was not indicated as being handled. Is there a valid renderer for the window: " + ClientWindow.GetWindow() +"?", ClientRenderer.class, LogState.Error);
		}
	}

	private static BufferedImage view = new BufferedImage(ClientWindow.width, ClientWindow.height, BufferedImage.TYPE_INT_RGB);
	private static int[] pixels;
	private static int colour;
	public static boolean CauseBackgroundUpdate = true;
	private static void setBackground(Graphics graphics) {
		view  = new BufferedImage(ClientWindow.window.getWidth(), ClientWindow.window.getHeight(), BufferedImage.TYPE_INT_RGB);
		if (CauseBackgroundUpdate) {
			pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
				if (Client.KeyPressedInFrame) {
					colour = 0x404080;
				} else { 
					colour = 0x303080;
				}
				
			for(int index = 0; index < pixels.length; index++) {pixels[index] = colour;} //set array to colour
			

		
		graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
		CauseBackgroundUpdate = !CauseBackgroundUpdate; //Update has been handled.
		}
	}

	
	
	
}
