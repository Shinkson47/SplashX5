package com.shinkson47.SplashX5.Client;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.shinkson47.SplashX5.Game.Enumerator.LogState;

/**
 * Static logger for any item within the Splash runtime to use.
 * 
 * @author gordie
 *
 */
public class Logger {
	
	/**
	 * Declares wether or not the logger will handle logs.
	 * 
	 * If false, the logger will ignore all log requests.
	 */
	public static boolean DoLogs = true;
	

	/**
	 * Storage for all logs handled since the vm started.
	 */
	private static List<String> Logs = new ArrayList<String>();


	/**
	 * Processes logs from anywhere within the Splash runtime.
	 * 
	 * Ignores all requests if DoLogs = false.
	 * 
	 * @param string : the log message
	 * @param sender : the class which the log is from
	 * @param state  : the log type (e.g error, info).
	 */
	public static void log(String string, Class<?> sender, LogState state) {							
		if (!DoLogs) {return;}																			//Ignore request if the DoLogs flag is not set.
		String log = "[" + sender.getName() + ", " + state.toString().toUpperCase() + " ]: " + string;	//Form the log line based upon the parameters given.
		
		switch(state) {																					//Switch to handle different log types.
		case Info:
			System.out.println(log);
			break;
			
		case Warn:
			System.err.println(log);																	//warns and errors are logged in the console as .err for effect.
			break;
		
		case Error:																						//TODO Error logging could be triggered here
			System.err.println(log);
			break;
		}
		Logs.add(log);
	}

	/**
	 * Caused on a crash to print the entire log history into a file just before Splash X5 terminates.
	 */
	public static void CrashWriteOut() {									
		File file = new File("./LatestCrash.log");							//Indicate the planned place to store the log.
		if (file.exists()) {												//If a log already exists, remove it.
			file.delete();
		}
		try {
			file.createNewFile();											//Create a new log file
			FileWriter writer = new FileWriter(file);						//Create a writer to write to the log file
			
			for (String log : Logs) {										//For every log,			
				if (log == null || log.equals("null")) {continue;}			//(Ignore null or empty logs)
				writer.append(System.lineSeparator() + log);				//append the log to the file on a new line.
			}
			
			writer.append(System.lineSeparator() + System.lineSeparator());	//WS on file's end to separate call stack
			writer.close();													//Close the writer resource
			
			if (ClientHandler.CrashException != null) {						//This check was added because an empty crash exception would leave the print stream open, and thus the log file would remain open and unusable.
				PrintStream ps = new PrintStream(file);						//Create a print stream for the exception class to use
				ClientHandler.CrashException.printStackTrace(ps);			//Have the exception write into the file.
				ps.close();													//Close the print stream resource.	
			}
		} catch (IOException e) { /* The client has already crashed, ignore this exception. */}
	}
}
