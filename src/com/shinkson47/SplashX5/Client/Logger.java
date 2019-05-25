package com.shinkson47.SplashX5.Client;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import com.shinkson47.SplashX5.Game.Enumerator.LogState;

public class Logger {


	public static boolean DoLogs = true;
	protected static String Logs[] = new String[65525];
	private static int logCount = 0;
	
	public static void log(String string, Class<?> sender, LogState state) {
		if (!DoLogs) {return;}
		String log = "[" + sender.getName() + ", " + state.toString().toUpperCase() + " ]: " + string;
		
		switch(state) {
		case Info:
			
			System.out.println(log);
			break;
			
		case Warn:
			System.err.println(log);
			break;
		
		case Error:
			System.err.println(log);
			break;
		}
		
		Logs[logCount] = log;
		logCount++;
	}

	public static void writeOut() {
		File file = new File("./LatestCrash.log");
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			for (String log : Logs) {
				if (log == null || log.equals("null")) {continue;}
				writer.append(System.lineSeparator() + log);
			}
			writer.append(System.lineSeparator() + System.lineSeparator());
			writer.close();
			
			PrintStream ps = new PrintStream(file);
			ClientHandler.CrashException.printStackTrace(ps);
			ps.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
