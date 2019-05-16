package com.shinkson47.SplashX5.Game.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.shinkson47.SplashX5.Client.Client;
import com.shinkson47.SplashX5.Game.World.Maps;

public class Server implements Runnable{
	public static Thread ServerThread;
	private static int DefaultPort = 25565;
	
	@SuppressWarnings("resource")
	public Server(){
       // declare a "general" socket and a server socket
        Socket connection;
        ServerSocket listenSocket;
        //declare low level and high level objects for input
        InputStream inStream;
        DataInputStream inDataStream;

        // declare low level and high level objects for output
        OutputStream outStream;
        DataOutputStream outDataStream;
        
        // declare other variables
        @SuppressWarnings("unused")
		Client client[];
        
        Maps.GenerateNew();

        while(true){
            try{
                // create a server socket
                listenSocket= new ServerSocket(DefaultPort);
                // log textWindow.append("Listening on port "+ port +"\n");

                //listen for a connection from the client 
                connection = listenSocket.accept();

                // create an input stream from the client
                inStream = connection.getInputStream();
                inDataStream = new DataInputStream(inStream);

                // create an output stream from the client
                outStream = connection.getOutputStream();
                outDataStream = new DataOutputStream(outStream);

                //client = inDataStream.readUTF();
                
                while(true){
                	inDataStream.read();
                	outDataStream.writeChars("Pong");
                }
                
             //listenSocket.close();
            }catch(IOException e){}
            }
}

	public static void StartServer() {
		ServerThread = new Thread(new Server());
		ServerThread.run();
	}
	
	@Override
	public void run() {		
	}
}