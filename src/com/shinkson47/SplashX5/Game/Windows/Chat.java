package com.shinkson47.SplashX5.Game.Windows;

import java.awt.Color;
import java.awt.Graphics;

import com.shinkson47.SplashX5.Client.ClientRenderer;
import com.shinkson47.SplashX5.Client.ClientWindow;

public class Chat {
	public static void init() {
		//Ran when the user switches to this window
	}
	
	public static void Update() {
		//Ran on every tick
	}
	
	public static void RenderFrame() {
		ClientRenderer.FrameUpdated = true;
		
		Game.RenderFrame();
		
		Graphics g = ClientRenderer.graphics;
		g.setColor(Color.LIGHT_GRAY);
		g.fillRoundRect(10, ClientWindow.window.getHeight() - 10 - (30 * 4), ClientWindow.window.getWidth() - 30, (30 * 4), 31, 31);
		
		g.setColor(Color.black);
		try {
			g.drawString("[" + com.shinkson47.SplashX5.Game.Server.chat.Chat.log[com.shinkson47.SplashX5.Game.Server.chat.Chat.messagecount-1].PlayerID + "]" + com.shinkson47.SplashX5.Game.Server.chat.Chat.log[com.shinkson47.SplashX5.Game.Server.chat.Chat.messagecount - 1].Message, 10, ClientWindow.window.getHeight() - 20);
			g.drawString("[" + com.shinkson47.SplashX5.Game.Server.chat.Chat.log[com.shinkson47.SplashX5.Game.Server.chat.Chat.messagecount - 2].PlayerID + "]" + com.shinkson47.SplashX5.Game.Server.chat.Chat.log[com.shinkson47.SplashX5.Game.Server.chat.Chat.messagecount - 2].Message, 10, ClientWindow.window.getHeight() - 40);
			g.drawString("[" + com.shinkson47.SplashX5.Game.Server.chat.Chat.log[com.shinkson47.SplashX5.Game.Server.chat.Chat.messagecount - 3].PlayerID + "]" + com.shinkson47.SplashX5.Game.Server.chat.Chat.log[com.shinkson47.SplashX5.Game.Server.chat.Chat.messagecount - 3].Message, 10, ClientWindow.window.getHeight() - 60);
			g.drawString("[" + com.shinkson47.SplashX5.Game.Server.chat.Chat.log[com.shinkson47.SplashX5.Game.Server.chat.Chat.messagecount - 4].PlayerID + "]" + com.shinkson47.SplashX5.Game.Server.chat.Chat.log[com.shinkson47.SplashX5.Game.Server.chat.Chat.messagecount - 4].Message, 10, ClientWindow.window.getHeight() - 80);
			g.drawString("[" + com.shinkson47.SplashX5.Game.Server.chat.Chat.log[com.shinkson47.SplashX5.Game.Server.chat.Chat.messagecount - 5].PlayerID + "]" + com.shinkson47.SplashX5.Game.Server.chat.Chat.log[com.shinkson47.SplashX5.Game.Server.chat.Chat.messagecount - 5].Message, 10, ClientWindow.window.getHeight() - 100);
		} catch (Exception e) {}
	}
}
