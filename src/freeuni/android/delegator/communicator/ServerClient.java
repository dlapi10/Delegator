package freeuni.android.delegator.communicator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;

public class ServerClient {
	//Constants
	private static final String LOG_TAG = "Server-Client";

	// Client external IP address
	private String serverIP="10.0.3.2"; //Genymotion emulator IP, Needs To change dynamiclly 
	// Socket port number to use
	private static int serverPort = 2728;

	//IO variables
	private BufferedReader inputStream;
	private PrintWriter outputStream;

	/**
	 * Constructor
	 */
	public ServerClient(){
	}

	/**
	 * 
	 */
	public void runClient(){
		//Socket to connect to the server
		Socket socket = null; 
		try {
			InetAddress serverAddr = InetAddress.getByName(serverIP);
			Log.e("serverAddr", serverAddr.toString());
			Log.e("TCP Client", "C: Connecting...");
			socket = new Socket(serverAddr, serverPort);
			
			outputStream = new PrintWriter(socket.getOutputStream(), true);
			inputStream = new BufferedReader( new InputStreamReader(socket.getInputStream()));;
			
			outputStream.println("insert into task_categories(category, color) values (\"android\",\"green\");");
			outputStream.flush();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			Log.i(LOG_TAG, "Error, შეცდომას აგდებს IP-ს გაგების დროს");
		} catch (IOException e) {
			e.printStackTrace();
			Log.i(LOG_TAG,"Error, შეცდომას აგდებს სოკეტის შექმნისას");
		} finally{
			try {
				socket.close();
				outputStream.close();
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
				Log.i(LOG_TAG,"Error, სოკეტის დახურვისას");
			}
		}
	}

}
