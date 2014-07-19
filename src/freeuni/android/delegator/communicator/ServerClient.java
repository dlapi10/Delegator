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
	private String clientExternalIP="10.0.3.2"; //Genymotion emulator IP, need to change
	private static String serverIP; // computer's IP Adress; It shouldn't be variable;
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
		//clientExternalIP = Processing.getLocalIpAddress(); //TODO
		//Socket to connect to the server
		Socket socket = null; 
		try {
			InetAddress serverAddr = InetAddress.getByName(serverIP);
			socket = new Socket(serverAddr.toString(), serverPort);
			
			outputStream = new PrintWriter(socket.getOutputStream(), true);
			inputStream = new BufferedReader( new InputStreamReader(socket.getInputStream()));;
			
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			Log.i(LOG_TAG, "Error, შეცდომას აგდებს IP-ს გაგების დროს");
		} catch (IOException e) {
			e.printStackTrace();
			Log.i(LOG_TAG,"Error, შეცდომას აგდებს სოკეტის შექმნისას");
		} finally{
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
				Log.i(LOG_TAG,"Error, სოკეტის დახურვისას");
			}
		}
	}

}
