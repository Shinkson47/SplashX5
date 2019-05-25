package com.shinkson47.SplashX5.Game.World;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

import com.shinkson47.SplashX5.Client.Client;
import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Game.Entities.Player.Player;
import com.shinkson47.SplashX5.Game.Entities.Player.PlayerBase;
import com.shinkson47.SplashX5.Game.Enumerator.Gamemode;
import com.shinkson47.SplashX5.Game.Enumerator.Realms;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Game.Resources.SoundManager;
import com.shinkson47.SplashX5.Game.Windows.Game;

public class WorldLoader {

	public static void Save(String name) {
		if (Game.CurrentMap == null) { return; }
		if (!Game.InGame) { return; }
		
		
		if (!Files.exists(Paths.get("./Worlds/"))) {
			try {
				Files.createDirectories(Paths.get("./Worlds/"));
			} catch (IOException e) {
				JOptionPane.showMessageDialog(ClientWindow.window, "Could not save : could not worlds folder");
				return;
			}
		}

		Game.CurrentMap.players = Player.players;
		JOptionPane.showMessageDialog(ClientWindow.window, "Saving, This may take a minute or two :)");
	    FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream("./Worlds/" + name);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
		    objectOut.writeObject(Game.CurrentMap);
		    objectOut.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(ClientWindow.window, "Could not save : could not save tileset");
		}
		JOptionPane.showMessageDialog(ClientWindow.window, "Done!");
	}
	
	public static void Load(String name) {
		if (Game.InGame) { return; } else {
			if (!Files.exists(Paths.get("./Worlds/"))) { 
				JOptionPane.showMessageDialog(ClientWindow.window, "There is no worlds folder!");
				return;
			}
			
			Game.InGame = false; //tells game to exit out of the game
			Game.Exit();			
			
			try {
			FileInputStream input = new FileInputStream("./Worlds/" + name);
			ObjectInputStream objin = new ObjectInputStream(input);	
			Game.CurrentMap = (MapBase) objin.readObject();
			
			objin.close();
			} catch (ClassNotFoundException | IOException e) {
				JOptionPane.showMessageDialog(ClientWindow.window, "Could not load : " + e.getMessage());
			}
			
			Game.InGame = true; //tells game not to initalise a new map, and act as if it were already loaded.
			ClientWindow.SetWindow(Windows.Game);
			
			//Emulate game start initalise
			SoundManager.PlayGame();
			ClientWindow.fullscreen();
			
			Player.reset();
			Player.players = Game.CurrentMap.players;
			//Player.Instantiate(new PlayerBase(Player.PlayerCount, Game.CurrentMap.CharStartX, Game.CurrentMap.CharStartY, Gamemode.SurviveAndThrive, Realms.Overworld));
			Client.PlayerID = 0;
			Player.UpdateScreenSpawn(Client.PlayerID);
			ClientWindow.setFontSize(20f);
			
			
		}
 	}
	
}
