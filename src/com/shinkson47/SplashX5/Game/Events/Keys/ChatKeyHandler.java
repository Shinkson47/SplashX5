package com.shinkson47.SplashX5.Game.Events.Keys;

import javax.swing.JOptionPane;

import com.shinkson47.SplashX5.Client.Client;
import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Game.Events.EventHandler;
import com.shinkson47.SplashX5.Game.Server.chat.Chat;

public class ChatKeyHandler {

	public static void process() {
		if (EventHandler.key.getKeyChar() == 't') { Chat.newMessage(JOptionPane.showInputDialog(ClientWindow.window, null, "Chat", 0), Client.PlayerID);; }
		if (EventHandler.key.getKeyCode() == 27) { ClientWindow.SetWindow(Windows.Game); }
	}
	
}
