package com.shinkson47.SplashX5.Client;

import java.util.Scanner;

import com.shinkson47.SplashX5.Game.Server.chat.Chat;
import com.shinkson47.SplashX5.Game.Server.chat.MessageBase;

/**
 * Defines the client's command line thread.
 * 
 * this is intended to be used in a background thread, which operated in the terminal for user inputs.
 * 
 * for commands:
 * DO = A set a bool which other code uses to determin weather or not to run
 * CAUSE = run an event directly, or set a bool that causes it.
 * 
 * @author gordie
 *
 */
public class ClientCommandline implements Runnable {
	/**
	 * Scanner for recieveing data from the terminal
	 */
	private Scanner scanner = new Scanner(System.in);

	//TODO Commands: CauseThreadRestart, DoKeyPresses, PollTileData, PollOffsets
	/**
	 * Declares if the command line can run or execute commands.
	 */
	public boolean DoCommand = true;
	
	/**
	 * Used to specify if the execution loop may run.
	 */
	private boolean run = true;
	
	/**
	 * Main command line thread entry point.
	 */
	public void run() {
		System.out.println("[SPLASH X5 CMD STARTING]");										//Thread is not yet initialised. Log that it's starting.
		if (!DoCommand) {System.out.println("[SPLASH X5 CMD CAN'T START]"); return;}		//Thread is not permitted to start.
		run = true;																			//Prepare run loop.
		while(run) {																		//Command line loop
			try { 
				String command = "";														//Declare somewhere to store the input string
				while (command == "") {														//Used to ignore empty strings
					try {
						System.out.println("[SPLASH X5 CMD READY]");						//Indicate that we are ready for input
						command = scanner.nextLine();										//Wait for, and read, new input
					} catch (Exception e) {													//Scanner failed, initalise a new one.
						scanner = new Scanner(System.in);
					}
				}
				
				String[] arguments = command.split(" ");									//split the command into tokens.
				
				if (arguments[0].startsWith("/")) {											//commands must begin with /
					System.out.println("[SPLASH X5 CMD] " + Chat.ParseCommand(new MessageBase(command, Client.PlayerID))); //parse as chat command
				} else {
					System.out.println("[SPLASH X5 CMD] Commands must start with a '/'");	//command must start with /
				}
			
			} catch (Exception e) {} 														//catches and ignores general runtime exceptions.
		}
		
	}
	
	/**
	 * Is called when the thread is about to halt.
	 * 
	 * this method does not halt the thread.
	 */
	public void WillHalt() {
		try {
			scanner.close();		//Close the scanner
		} catch (Exception e) {}	//Catch 'Cannot close, already closed' exception.
		run = false;				//Stop the command line execution loop
	}

	
}
