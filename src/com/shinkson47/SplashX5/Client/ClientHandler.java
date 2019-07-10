package com.shinkson47.SplashX5.Client;

import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.JOptionPane;

import com.shinkson47.SplashX5.Game.Enumerator.ClientState;
import com.shinkson47.SplashX5.Game.Enumerator.LogState;

/**
 * The client handler statically sits above the client within the operating hierarchy.
 * It starts, watches, and manages the client operation and it's crashes.
 * 
 * @author gordie
 *
 */
public class ClientHandler {	
	
		/**
		 * Contains the current instance of the client that's being managed.
		 */
		public static Client client = null;
		
		/**
		 * Contains the thread the monitored instance is operating within.
		 * Thread is public and static so the client operating within it may still access it.
		 */
		public static Thread ClientThread = null;

		/**
		 * Stores the exception thrown from the client when it crashes.
		 * 
		 * Set the exception here, then cause a crash event.
		 */
		public static Exception CrashException = null;
		
		public static void StartClient(){			
			while (true){ 								//loops boot loop for client restarts.
				try {
					
					client = null;						//Dispose old client
					client = new Client();				//Make a new one
					
					ClientThread = null;				//Dispose of old thread
					ClientThread = new Thread(client);	//make a new one
					
					
					Thread.UncaughtExceptionHandler h = new UncaughtExceptionHandler(){		//Create an exception handler class which parses uncaught thread exceptions into the client handler.
						@Override
						public void uncaughtException(Thread arg0, Throwable arg1) {
							HandleCrash(1);
						}
					}; 
					
					ClientThread.setUncaughtExceptionHandler(h); 		//Assign handler to runtime thread
					ClientThread.setName("Splash X5");					//Name thread. The threads name is used to know if a thread shutdown was intended, as there's no way to access data from inside a dead thread.
					ClientThread.start();								//START CLIENT. This is the main client entry point.
					
					Client.ClientRestartCount++; 						//Count successful client start
					Client.ClientStartTime = System.currentTimeMillis();//Set client start time
					
					while (ClientThread.isAlive()) {}					//Client handler thread idles here whilst the client is active.
						 												//TODO maybe listen for instructions from the client?

																		//Client has closed
					
					if (ClientThread.getName().equals("restart")) { 	//Does it intend to restart?
						Logger.log("Thread restarted itself", ClientHandler.class, LogState.Info); //Log
																		//Don't close, iterate loop and start client again.
					} else {
						HandleCrash(2); 								//Client closed and didn't intend to restart, assume it's a crash:
																		//if the client intended to shutdown the entire JREVM would have been closed, and this code would not be reached.
					}		
					
				}catch (OutOfMemoryError e) {
						HandleCrash(3); 								//Handle out of memory state
				}
			}
		}
		
		/**
		 * Handles client crashes
		 * 
		 * @param HaltCode : runtime halt code that is parsed to java.
		 * @see Client.HaltClient for Splash X5 halt codes.
		 */
		private static void HandleCrash(int HaltCode) {
			
			if (Client.state != ClientState.Crashed) {Client.state = ClientState.Crashed;} 		//If not already declared, indicate that the client has crashed.
			
			ClientThread = null; 																//Dispose thread
			client = null;																		//Dispose client
			
			JOptionPane.showMessageDialog(ClientWindow.window, Client.CLIENT_NAME + Client.CLIENT_VERSION + " has crashed :(", getComment(), 1); 														//Indicate to the user that the client has crashed with a message box.
			
			Logger.log("Thread Closed. Uptime: " + String.valueOf(getUpTime()) + " Restart count: " + Client.ClientRestartCount + " Exit Code: " + HaltCode, ClientHandler.class, LogState.Error);  //Create error log via the logger.
			if (CrashException != null) {																					//If we have an exception of the crash, log it
				Logger.log("Client: " + Client.CLIENT_NAME + Client.CLIENT_VERSION, ClientHandler.class, LogState.Error);
				Logger.log("Message: " + CrashException.getMessage(), ClientHandler.class, LogState.Error);
				Logger.log("Hash Code: " + CrashException.hashCode(), ClientHandler.class, LogState.Error);
				Logger.log("Cause: " + CrashException.getCause(), ClientHandler.class, LogState.Error);
				Logger.log("Class: " + CrashException.getClass(), ClientHandler.class, LogState.Error);
			}
			
			CrashException.printStackTrace(); 								//not logged, but is visible in the console. Print call stack.
			try {
				Thread.sleep(1500); 										//Delay gives time for java to finish printing all lines of the call stack before the comment is printed.
			} catch (InterruptedException e) {} 							//ignore
			
			CrashException = null;											//Dispose of exception
			
			Logger.log(getComment(), ClientHandler.class, LogState.Info);	//Log witty comment.
			Logger.CrashWriteOut(); 												//Print all logs to error file.
			Runtime.getRuntime().halt(HaltCode); 							//Close java runtime.
		}
		
		/**
		 * Getter for the client's up time.
		 * 
		 * @return : Milliseconds of client's up time.
		 */
		public static long getUpTime() {
			return System.currentTimeMillis() - Client.ClientStartTime;
		}
		
		/**
		 * Picks a random item from the array, and returns it.
		 * @return : string containing a witty crash comment.
		 */
		public static String getComment() {
			
		    String[] astring = new String[] { //List of comments			
		    		"We assure you that our highly trained monkey's sure are working hard to fix this!",
		    		"Now THAT'S what i call an error log!",
		    		"Dikleberg!",
		    		"Bugger.",
		    		"I told you that would happen!",
		    		"What do you mean, 'Exception'?",
		    		"Not again!",
		    		"I thought i'd already fixed that?",
		    		"I was so sure that it was goint to work that time...",
		    		"Oh, come on!",
		    		"Oh, don't mind, that's just the cat.",
		    		"We'll deal with that later..",
		    		"CauseCrash = true?", 
		    		"Was that my fault?",
		    		"Everything's going to plan. No, really, that was supposed to happen.",
		    		"Uh... Did I do that?",
		    		"Oops.",
		    		"Why did you do that?",
		    		"I feel sad now :(",
		    		"My bad.",
		    		"I'm sorry, Dave.", 
		    		"I let you down. i'm sorry :(",
		    		"On the bright side, I bought you a teddy bear!", 
		    		"Daisy, daisy...", 
		    		"Oh - I know what I did wrong!",
		    		"Hey, that tickles! Hehehe!", 
		    		"Don't be sad. I'll do better next time, I promise!", 
		    		"Don't be sad, have a hug! <3", 
		    		"I just don't know what went wrong :(", 
		    		"Shall we play a game?", 
		    		"Quite honestly, I wouldn't worry myself about that.", 
		    		"The problem will remain forever a mystery..", 
		    		"Perhaps this was fixed in the newer version?",  
		    		"I bet Cylons wouldn't have this problem.", 
		    		"Sorry :(",
		    		"Surprise! Haha. Well, this is awkward.",
		    		"Damn it, Karen!",
		    		"WAKE UP, KYLE! GET THIS FIXED!",
		    		"I didn't touch that. Did you?",
		    		"Would you like a cupcake?",
		    		"Hi. I'm Splash, and I'm a crashaholic.",
		    		"Ooh. Shiny.", 
		    		"This doesn't make any sense!", 
		    		"Why is it breaking :(",
		    		"Don't do that.", 
		    		"Ouch. That hurt :(", 
		    		"You're mean.", 
		    		"Was it meant to do that?",
		    		"Fuck sakes, not again!",
		    		"There are four lights!", 
		    		"But it works on my machine...", 
		    		"StackOverFlow, Here I Come!", 
		    		"Our highly trained monkey's sure are working hard to fix this..", 
		    		"Slabbed beans", 
		    		"But it worked in the last update..?",
		    		"That's nice, what does it say?"
		    		};
		    String Comment = null; 																//Somewhere to store the return value
		    try
		    {
		         Comment = astring[(int)(System.currentTimeMillis() % (long)astring.length)];   //Find a value using time based randomness
		    }
		    catch (Throwable var2)
		    {
		         Comment = "I have nothing to say.";											// :(
		    }
			return Comment; 																	//return value.
			}

}
