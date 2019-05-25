package com.shinkson47.SplashX5.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.shinkson47.SplashX5.Game.Enumerator.ClientState;
import com.shinkson47.SplashX5.Game.Enumerator.LogState;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Game.Events.EventHandler;

public class Client implements Runnable {
	public static ClientState state = ClientState.PreInit;
	protected static boolean doClientRun = true;
	public final static String ClientVersion = "V0.7.5-A", ClientName = "Splash X5 ";
	public static boolean CauseCrash = false, KeyPressedInFrame = false, CauseRestart = false;
	public static int LoadPercent = 0, ClientRestartCount = 0, PlayerID;
	public static long ClientStartTime = 0L;
	public static Thread commandthread = new Thread(new ClientCommandline());
	
	@Override
	public void run() {
		try {
		if (state == ClientState.PreInit) {
			//By this point, the window is open, BUT
			//Client has not yet loaded.
			state = ClientState.Init;
			ClientWindow.SetWindow(Windows.Init);
			
			//The client is now loaded, run post init script
			state = ClientState.PostInit;
			ClientWindow.SetWindow(Windows.PostInit);
		}
		
		commandthread.start();
		
		Logger.log("Client is ready!", Client.class, LogState.Info);
		long lastTime = System.nanoTime(); //Used to keep updates well timed
		double nanoSecondConversion = 1000000000.0 / 60; //TODO Frame limit 
		double changeInSeconds = 0;
		
		while(true) {	//Client loop
			//prepare for next client loop
			if (CauseRestart) {RestartClient();}
			long now = System.nanoTime();
			changeInSeconds += (now - lastTime) / nanoSecondConversion;
			
			//DoClientLoop
			if (ClientWindow.window.hasFocus()) {
			while(changeInSeconds >= 1) {
				changeInSeconds--;

				//===========EVERY TICK=========//
				//Don't put code here, use the event handler.
				EventHandler.Update(); 
			}} else {
				//Cause pause tick
				EventHandler.Pause();
			}

			//===========EVERY FRAME=========//
			//Don't put code here, use the event handler 'OnFrameEvent'
			EventHandler.RenderNextFrame();
			 
			lastTime = now; // Prep for next client loop
			}
		
		} catch (Exception e) {
			//Catch exceptions thrown from within the client loop
			//This doesn't catch exceptions from events caused by the mouse or keyboard.
			ParseException(e);
		}
	}

	public static void ParseException(Exception e) {
		EventHandler.Halt(); //Attempt to safely shutdown the client
		
		if (ClientHandler.CrashException == null) //If there is currently no crash, start one - otherwise ignore it.
			{
			ClientHandler.CrashException = e; //parse exception to crash handler to use.
			}
		
		if (ClientWindow.window.isVisible()) { ClientWindow.window.setVisible(false); } //Hide client, else the thread will terminate with the window still open, but unresponsive.

		@SuppressWarnings("unused")
		int i = 1 / 0; // Cause a divide by 0 exception to crash thread and parse to ClientHandler's Handle crash
	}

	public static void RestartClient() {
		ClientHandler.ClientThread.setName("restart"); //Indicate that the client closing is deliberate, and cause a restart in Client Handler
		Logger.log("Preparing to restart.", Client.class, LogState.Warn);
		HaltClient();
	}
	
	/*
	 * Close codes:
	 * 1: Generic exception in the client loop.
	 * 2: Client closed unexpectedly with no exception 
	 * 3: Client ran out of memory
	 * 100: Client called for halt, typically indicates an intended shutdown.
	 */
	public static void HaltClient() {
	state = ClientState.Halted;
	Logger.log("Halting Client.", Client.class, LogState.Warn);
	EventHandler.Halt();
	java.lang.Runtime.getRuntime().halt(100);
	}
	
	
	
	
	 // declare low level and high level objects for input
    private static InputStream inStream;
    private static DataInputStream inDataStream;

    // declare low level and high level objects for output
    private static OutputStream outStream;
    private static DataOutputStream outDataStream;

    // declare socket
    private static Socket connection;

    // declare attribute to told details of remote machine and port
    private static String remoteMachine;
    private static int port;

	public static void Connect() {
        try{
            // attempt to create a connection to the server
            connection = new Socket(remoteMachine,port);
            
            // create an input stream from the server
            inStream = connection.getInputStream();
            inDataStream = new DataInputStream(inStream);

            //create output stream to the server
            outStream = connection.getOutputStream();
            outDataStream = new DataOutputStream(outStream);

            //send the host IP to the server
            //outDataStream.writeUTF(connection.getLocalAddress().getHostAddress());
            
            outDataStream.writeChars("ping");
            Logger.log(inDataStream.readUTF(), Client.class, LogState.Info);
            
        }catch (UnknownHostException e){
            //msg.setText("Unknow host");
        }
        catch (IOException except){
            //msg.setText("Network Exception");
        }
	}

	@SuppressWarnings("deprecation")
	public static void RestartCommandThread() {
		if (Client.commandthread.isAlive()) {
		try {
			Client.commandthread.getClass().getMethod("Halt").invoke(null);
			commandthread.stop();
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {}}
		
		commandthread = new Thread(new ClientCommandline());
		commandthread.start();
	}
	
}
