package com.shinkson47.SplashX5.Client;

import java.lang.reflect.InvocationTargetException;

import com.shinkson47.SplashX5.Game.Enumerator.ClientState;
import com.shinkson47.SplashX5.Game.Enumerator.LogState;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Game.Events.EventHandler;

/**
 * Defines the main client of Splash X5
 * 
 * @author gordie
 *
 */
public class Client implements Runnable {
	
	/**
	 * Stores the current state of the client
	 */
	public static ClientState state = ClientState.PreInit;
	

	/**
	 * Determines if the client may run
	 */
	private static boolean doClientRun = true;

	/**
	 * Defines the client
	 */
	public static final String CLIENT_VERSION = "V0.7.5-A";
	public static final String CLIENT_NAME = "Splash X5 ";
	
	/**
	 * Event trigger booleans
	 * 
	 * These booleans are checked in the client loop
	 */
	public static boolean CauseCrash = false, CauseRestart = false;
	
	/**
	 * Runtime variables
	 */
	public static int LoadPercent = 0, ClientRestartCount = 0, PlayerID;
	
	/**
	 * Runtime variables
	 */
	public static boolean KeyPressedInFrame = false; 
	
	/**
	 * Indicates the nanosecond in which the client started
	 */
	public static long ClientStartTime = 0L;
	
	/**
	 * Thread which contains the command line
	 */
	public static Thread commandthread = new Thread(new ClientCommandline());
	
	private static final double NANO_SECOND_CONVERSION = 1000000000.0 / 60;
	
	/**
	 * Main client execution entry point.
	 * Is called when the client thread is started, and remains alive until the thread is closed.
	 */
	@Override
	public void run() {
		try {
		if (state == ClientState.PreInit) {			//Client in it's pre init phase.
			 										//By this point the window is open but Client has not yet loaded.
			commandthread.start();					//Start the command line thread
													//Start next client load phase, INIT.
			state = ClientState.Init;				//Indicate new phase
			ClientWindow.SetWindow(Windows.Init);	//Switch to the init window.
			
													//The client is now loaded. 
			state = ClientState.PostInit;			//indicate the client is now at post init stage
			ClientWindow.SetWindow(Windows.PostInit);//run post init script
		}
		
		Logger.log("Client is ready!", Client.class, LogState.Info);//Log that the client has loaded
		
		long lastTime = System.nanoTime(); 							//Variables used to keep updates and frames well timed
		double changeInSeconds = 0;
		
		while(doClientRun) {										//Client loop
																	//prepare for next client loop
			if (CauseRestart) {RestartClient();}					//If the client is waiting to restart, restart the client.
			if (state != ClientState.Running) {break;}				//If the client is not supposed to be running, exit the runtime loop.
			
			long now = System.nanoTime();							//Loop timing variables
			changeInSeconds += (now - lastTime) / NANO_SECOND_CONVERSION;
			
			while(changeInSeconds >= 1) {							//Wait until the next update tick should occur
				changeInSeconds--;
			}
			
			if (ClientWindow.window.hasFocus()) {					//If the client has focus
																	//Don't put code here, use the event handler.
				EventHandler.Update(); 								//Use the event handler to cause the update tick.
			} else {
																	//Don't put code here, use the event handler.
				EventHandler.Pause();								//Use the event handler to cause a pause tick.
			}
																	//Don't put code here, use the event handler 'OnFrameEvent'
			EventHandler.RenderNextFrame();							//Render and display the client's next frame.
			 
			lastTime = now; 										//prepare for next client loop.
		}
		
		Logger.log("Client execution loop has stopped!", Client.class, LogState.Warn); //Client loop has exited
		
		} catch (Exception e) {
									//Catch exceptions thrown from within the client loop
									//This doesn't catch exceptions from events caused by the mouse or keyboard.
			ParseException(e);		//Parse a client runtime exception.
		}
	}

	/**
	 * Causes crashes and manages runtime exceptions
	 * 
	 * @param e : The exception to handle.
	 */
	public static void ParseException(Exception e) {
		
		if (ClientHandler.client.state != ClientState.Crashed) {			//If the client is not currently in a crash state,
			EventHandler.Halt(); 											//Attempt to safely shutdown the client
			ClientHandler.CrashException = e; 								//parse exception to crash handler to use.
			ClientHandler.client.state = ClientState.Crashed;									//Indicate that the client has crashed.
		}
		
		if (ClientWindow.window.isVisible()) { ClientWindow.window.setVisible(false); } //Hide client, else the thread will terminate and the window will remain open, but unresponsive.

		@SuppressWarnings("unused")
		int i = 1 / 0;		 // Cause a divide by 0 exception to crash thread and parse the crash state up the call stack to the ClientHandler's Handle crash
	}

	/**
	 * Restarts the client thread.
	 */
	public static void RestartClient() {
		ClientHandler.ClientThread.setName("restart"); 						//Indicate to the Client Handler that the client closing is deliberate, and cause a restart in Client Handler
		Logger.log("Preparing to restart.", Client.class, LogState.Warn);   //Log event
		HaltClient(-1);														//Halt the client.
	}
	
	/**
	 * Halts the client.
	 * 
	 * Close codes:
	 * 1: Generic exception in the client loop.
	 * 2: Client closed unexpectedly with no exception 
	 * 3: Client ran out of memory
	 * 100: Client called for halt, typically indicates an intended shutdown.
	 * 
	 * -1: indicated no code specified. Causes code 100.
	 * 
	 * @param Code : the halt code.
	 */
	public static void HaltClient(int Code) {
		ClientHandler.client.state = ClientState.Halted;									//Indicate that the client is halting
		Logger.log("Halting Client.", Client.class, LogState.Warn); //Log event
		EventHandler.Halt();										//Cause halt events
		
		if (Code == -1) {											//If there is no code specified
			Runtime.getRuntime().halt(100);							//Use code 100
		} else {
			Runtime.getRuntime().halt(Code);						//else use specified halt code.
		}
	}

	/**
	 * Restarts the command line thread.
	 */
	@SuppressWarnings("deprecation")
	public static void RestartCommandThread() {
		if (Client.commandthread.isAlive()) {											//Is the thread alive?
			try {									
				Client.commandthread.getClass().getMethod("WillHalt").invoke(null);		//Cause it's halt events
				commandthread.stop();													//Halt the thread
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {} //Ignore, likely indicates a dead or empty thread.
		}
		commandthread = new Thread(new ClientCommandline());							//Create a new thread instance.
		commandthread.start();															//Start the new thread.
	}
	
}
