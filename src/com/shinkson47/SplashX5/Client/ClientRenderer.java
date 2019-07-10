package com.shinkson47.SplashX5.Client;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.lang.reflect.Method;

import com.shinkson47.SplashX5.Game.Enumerator.LogState;
import com.shinkson47.SplashX5.Interfaces.Renderer;


/**
 * Manages frame rendering for the game client.
 * 
 * @author gordie
 *
 */
public class ClientRenderer implements Renderer {
	
	/**
	 * Used by window render methods to indicate weather they have rendered their frame, or not.
	 */
	public static boolean FrameUpdated = false;
	
	/**
	 * Used to store the Client JFrame's graphics.
	 */
	public static Graphics graphics;
	
	/**
	 * Main render method.
	 * 
	 * Determines what window the client is currently on, and gets that window to render it's frame, and then displays it.
	 */
	public static void Update() {	
		FrameUpdated = false;																							  	//Render variable preparation
		
		Image bdImage = new BufferedImage(ClientWindow.window.getWidth(), ClientWindow.window.getHeight(), BufferedImage.TYPE_INT_ARGB); //Create a buffer to draw to
		graphics = bdImage.getGraphics();																				  	//Get the graphics as a method of drawing to the buffer
																										 
		
		try {
			Class<?> Window = Class.forName("com.shinkson47.SplashX5.Game.Windows." + ClientWindow.GetWindow().toString()); //Get class of the current client window
			Method update = Window.getMethod("RenderFrame");																//Get the window's render method
			update.invoke(null);																							//Invoke the render method
		} catch (Exception e) { /* Render errors are no longer handled here. */ }		
		
		ClientWindow.window.getGraphics().drawImage(bdImage, 0, 0, null);													//Draw the buffer to the client window
		
		if (FrameUpdated != true) {																							//Has the frame been indicated as being handled by the render method?
			Logger.log("Frame was not indicated as being handled. Is there a valid renderer for the window: " + ClientWindow.GetWindow() +"?", ClientRenderer.class, LogState.Error); //Log
		}
	}
}
