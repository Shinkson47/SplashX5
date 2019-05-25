package com.shinkson47.SplashX5.Client;

import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.JOptionPane;

import com.shinkson47.SplashX5.Game.Enumerator.ClientState;
import com.shinkson47.SplashX5.Game.Enumerator.LogState;

public class ClientHandler {	
		protected static int HaltCode = 0; //Code thrown to java when closing, default is 0.
		private static Client client = null; //The current instance of the client that is running.
		public static Thread ClientThread = null; //The thread the client is running in, and is being monitored
		//Thread is public, so the client can read it.
		
		public static Exception CrashException = null; //Exception from the client when it crashes.
		
		public static void StartClient(){			
			while (true){ //loop for restarts
				
				try {
					//Dispose old client, and make a new one
					client = null;
					client = new Client();
					
					//Dispose old thread, and make a new one
					ClientThread = null;
					ClientThread = new Thread(client);
					Thread.UncaughtExceptionHandler h = new UncaughtExceptionHandler(){
						@Override
						public void uncaughtException(Thread arg0, Throwable arg1) {
							HandleCrash(1);
						}
					}; //This handler catches the crashes within the client thread. Keyboard and mouse events are NOT in this thread, and won't be caught by this.
					
					ClientThread.setUncaughtExceptionHandler(h); //Set handler
					
					//Start thread
					ClientThread.setName("Splash X5");
					ClientThread.start();
					
					//Count start
					Client.ClientRestartCount++;
					
					Client.ClientStartTime = System.currentTimeMillis(); //Set start time
					
					while (ClientThread.isAlive()) {
						/*
						 * Wait here whilst thread is active.
						 * 
						 * TODO maybe listen to instructions from the client?
						 */
					}
					
					if (ClientThread.getName().equals("restart")) { //if client intends to restart.
						Logger.log("Thread restarted itself", ClientHandler.class, LogState.Info);
						//Don't close
					} else {
						HandleCrash(2); 
					}		
					
			}catch (OutOfMemoryError e) {
						HandleCrash(3); //Out of memory state
			}}}
		
		private static void HandleCrash(int HaltCode) { //handle a client crash
			
			Client.state = ClientState.Crashed;
			
			ClientThread = null; //Dispose thread and client
			client = null;
			
			JOptionPane.showMessageDialog(ClientWindow.window, Client.ClientName + Client.ClientVersion + " has crashed :(", getComment(), 1);
			//Create error log.
			Logger.log("Thread Closed. Uptime: " + String.valueOf(getUpTime()) + " Restart count: " + Client.ClientRestartCount + " Exit Code: " + HaltCode, ClientHandler.class, LogState.Error);
			if (CrashException != null) {
				Logger.log("Client: " + Client.ClientName + Client.ClientVersion, ClientHandler.class, LogState.Error);
				Logger.log("Message: " + CrashException.getMessage(), ClientHandler.class, LogState.Error);
				Logger.log("Hash Code: " + CrashException.hashCode(), ClientHandler.class, LogState.Error);
				Logger.log("Cause: " + CrashException.getCause(), ClientHandler.class, LogState.Error);
				Logger.log("Class: " + CrashException.getClass(), ClientHandler.class, LogState.Error);
			}
			
			try {
				Thread.sleep(1500); //Delay gives time for exception to finish printing all lines before the comment is called.
			} catch (InterruptedException e) {
			}
			
			
			
			CrashException.printStackTrace(); //not logged, but is visible in the console
			CrashException = null;
			Logger.log(getComment(), ClientHandler.class, LogState.Info);
			Logger.writeOut(); //Print all logs to error file.
			Runtime.getRuntime().halt(HaltCode); //Close java runtime.
		}
		
		public static long getUpTime() {
			return System.currentTimeMillis() - Client.ClientStartTime;
		}
		
		public static String getComment() {
		    String[] astring = new String[] {"We assure you that our highly trained monkey's sure are working hard to fix this!", "Now THAT'S what i call an error log!","Dikleberg!", "Bugger.", "I told you that would happen!", "What do you mean, 'Exception'?", "Not again!","I thought i'd already fixed that?", "I was so sure that it was goint to work that time...", "Oh, come on!", "Oh, don't mind, that's just the cat.", "We'll deal with that later..", "CauseCrash = true?", "Was that my fault?", "Everything's going to plan. No, really, that was supposed to happen.", "Uh... Did I do that?", "Oops.", "Why did you do that?", "I feel sad now :(", "My bad.", "I'm sorry, Dave.", "I let you down. i'm sorry :(", "On the bright side, I bought you a teddy bear!", "Daisy, daisy...", "Oh - I know what I did wrong!", "Hey, that tickles! Hehehe!", "Don't be sad. I'll do better next time, I promise!", "Don't be sad, have a hug! <3", "I just don't know what went wrong :(", "Shall we play a game?", "Quite honestly, I wouldn't worry myself about that.", "The problem will remain forever a mystery..", "Perhaps this was fixed in the newer version?",  "I bet Cylons wouldn't have this problem.", "Sorry :(", "Surprise! Haha. Well, this is awkward.", "Damn it, Karen!", "WAKE UP, KYLE! GET THIS FIXED!", "I didn't touch that. Did you?","Would you like a cupcake?", "Hi. I'm Splash, and I'm a crashaholic.", "Ooh. Shiny.", "This doesn't make any sense!", "Why is it breaking :(", "Don't do that.", "Ouch. That hurt :(", "You're mean.", "Was it meant to do that?", "Fuck sakes, not again!","There are four lights!", "But it works on my machine...", "StackOverFlow, Here I Come!", "Our highly trained monkey's sure are working hard to fix this..", "Slabbed beans", "But it worked in the last update..?"};
		    String Comment = null;
		    try
		    {
		         Comment = astring[(int)(System.currentTimeMillis() % (long)astring.length)];
		    }
		    catch (Throwable var2)
		    {
		         Comment = "I have nothing to say. I'm just sad. :(";
		    }
			return Comment;
			}

}
