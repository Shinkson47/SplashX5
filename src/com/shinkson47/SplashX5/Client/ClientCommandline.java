package com.shinkson47.SplashX5.Client;

import java.util.Scanner;

import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Game.Events.EventHandler;
import com.shinkson47.SplashX5.Game.Windows.Dog;

public class ClientCommandline {

	public static void init() {
		
	}
	
	/*
	 * DO = A set a bool which other code uses to determin weather or not to run
	 * CAUSE = run an event directly, or set a bool that causes it.
	 */

	//TODO Commands: CauseThreadRestart, DoKeyPresses, PollTileData, PollOffsets
	public static boolean DoCommand = true, run = true;
	public static void Run() {
		run = true;
		while(run) {
		if (!DoCommand) {return;}
		try {
		Scanner scanner = new Scanner(System.in);
		System.out.println("[SPLASH X5 CMD]");
		String command = scanner.nextLine();
		
		String[] arguments = command.split(" ");
		
		if (isCommand(arguments[0])) {
			ExecuteCommand(arguments);
		} else {
			System.out.println("That command is not valid!");
		}
		scanner.close();
		} catch (Exception e) {}
		}
		
	}
	
	private static boolean isCommand(String string) {
		for (String command : ValidCommands) {
			if (string.equals(command)) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean getBool(String string) {
		if (string.equals("true")) {return true;}
		return false;
	}
	
	protected static String[] ValidCommands = new String[]{"CauseDog","ClientCurrentWindow","DoKeyEvent","ClientVisible","ValidCommands", "DoCommand","DoForceFocus","DoLogs","CauseBackgroundUpdate","CauseExit","CauseCrash","CauseHalt"}; 
	public static void ExecuteCommand(String[] command) {
		if (command.length < 2) {//Reading a value
			switch (command[0]) {
			case "DoCommand":
				System.out.println(DoCommand);
				break;
				
			case "DoForceFocus":
				System.out.println(ClientWindow.DoForceFocus);
				break;
			
			case "DoLogs":
				System.out.println(Logger.DoLogs);
				break;
				
			case "DoKeyEvent":
				System.out.println(EventHandler.DoKeyEvent);
				break;
				
			case "ClientCurrentWindow":
				System.out.println(ClientWindow.GetWindow().toString());
				break;
				
			case "ValidCommands": 
					for (String cmds : ValidCommands) {
						System.out.println(cmds);
					}
					break;
			}
		}
		
		if (command[0].contains("Cause")) { //Trigger event
			System.out.println("Polling for command to cause event.");
		
		switch (command[0]) {
		case "CauseBackgroundUpdate":
			ClientRenderer.CauseBackgroundUpdate = true;
			break;
		case "CauseExit":
			run = false;
			break;
			
		case "CauseCrash":
			Client.CauseCrash = true;
			break;
			
		case "CauseHalt":
			Client.HaltClient();
			break;
		case "CauseDog":
			Dog.init();
			break;
			
		}
		
		}
	
		if (command.length < 2) {return;}
		
		if ((command[1].equals("true") || command[1].equals("false"))) { //Setting a boolean
			switch (command[0]) {
			case "DoCommand":
				DoCommand = getBool(command[1]);
				System.out.println(DoCommand);
				break;
				
			case "DoForceFocus":
				ClientWindow.DoForceFocus = getBool(command[1]);
				System.out.println(ClientWindow.DoForceFocus);
				break;
			
			case "DoLogs":
				Logger.DoLogs = getBool(command[1]);
				System.out.println(Logger.DoLogs);
				break;
				
			case "DoKeyEvent":
				EventHandler.DoKeyEvent = getBool(command[1]);
				System.out.println(EventHandler.DoKeyEvent);
				break;
			
			case "ClientCurrentWindow":
				try {
				ClientWindow.SetWindow(Windows.valueOf(command[2]));
				} catch (Exception e) {
					System.out.println(command[2] + " is not a valid window!");
				}
				System.out.println(ClientWindow.GetWindow().toString());
				
				break;
				
			case "ClientVisible":
				ClientWindow.window.setVisible(getBool(command[1]));
				break;
			}
			return;
		}
		
		
		
}
}
