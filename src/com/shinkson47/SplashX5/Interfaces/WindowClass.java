package com.shinkson47.SplashX5.Interfaces;

import java.awt.Color;
import java.awt.Graphics;

import com.shinkson47.SplashX5.Client.ClientRenderer;

public interface WindowClass {
	public static void init() {
		//Ran when the user switches to this window
	}
	
	public static void Update() {
		//Ran on every tick
	}
	
	public static void RenderFrame() {
		//Ran on every frame in which this window is open
		
		//Tell the render handler that the frame has been handled.
		ClientRenderer.FrameUpdated = true;		
		
		//Get the frame to draw to
		Graphics g = ClientRenderer.graphics;
		
		//Draw to it however you wish.
		g.setColor(Color.yellow);
	}
	
}
