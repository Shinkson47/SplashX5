package com.shinkson47.SplashX5.Client;

import java.util.Scanner;

import com.shinkson47.SplashX5.Game.Server.chat.Chat;
import com.shinkson47.SplashX5.Game.Server.chat.MessageBase;

public class ClientCommandline implements Runnable {
	private Scanner scanner = new Scanner(System.in);
	public static void init() {
		
	}
	
	/*
	 * DO = A set a bool which other code uses to determin weather or not to run
	 * CAUSE = run an event directly, or set a bool that causes it.
	 */

	//TODO Commands: CauseThreadRestart, DoKeyPresses, PollTileData, PollOffsets
	public boolean DoCommand = true, run = true;
	public void run() {
		System.out.println("[SPLASH X5 CMD STARTING]");
		if (!DoCommand) {System.out.println("[SPLASH X5 CMD CAN'T START]"); return;}
		run = true;
		while(run) {
		try {
		System.out.println("[SPLASH X5 CMD READY]");
		
		String command = "";
		while (command == "") {
			try {
				command = scanner.nextLine();
			} catch (Exception e) {
				scanner.close();
				scanner = new Scanner(System.in);
			}
		}
		
		String[] arguments = command.split(" ");
		
		if (arguments[0].substring(0,1).equals("/")) {
			System.out.println("[SPLASH X5 CMD] " + Chat.ParseCommand(new MessageBase(command, Client.PlayerID)));
		} else {
			System.out.println("[SPLASH X5 CMD] Commands must start with a '/'");
		}
		
		} catch (Exception e) {}
		}
		
	}
	
	public void Halt() {
		try {
		scanner.close();
		} catch (Exception e) {
			
		}
		run = false;
	}

	
}
