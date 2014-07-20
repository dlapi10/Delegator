package freeuni.android.delegator.communicator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;

public class ServerClient {
	//Constants
	private static final String LOG_TAG = "Server-Client";
	// Socket port number to use
	public static final int SERVER_PORT = 2728;

	// Client external IP address
	private static String serverIP="10.0.3.2"; //Genymotion emulator IP, Needs To change dynamiclly 


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
		try {
			InetAddress serverAddr = InetAddress.getByName(serverIP);
			Log.e("serverAddr", serverAddr.toString());
			Log.e("TCP Client", "C: Connecting...");
			Socket socket = new Socket(serverAddr, SERVER_PORT);

			try{
				outputStream = new PrintWriter(socket.getOutputStream(), true);
				inputStream = new BufferedReader( new InputStreamReader(socket.getInputStream()));;

				//outputStream.println("insert into task_categories(category, color) values (\"android\",\"green\");");
				//outputStream.flush();

				//Second Try catch clauses
			}  catch (IOException e) {
				e.printStackTrace();
			}  finally{
				socket.close();
				outputStream.close();
				inputStream.close();

			}
			// First Try catch clauses
		}catch (UnknownHostException e) {
			e.printStackTrace();
			Log.i(LOG_TAG, "Error, შეცდომას აგდებს IP-ს გაგების დროს");
		}catch (IOException e) {
			e.printStackTrace();
			Log.i(LOG_TAG,"Error, შეცდომას აგდებს სოკეტის შექმნისას");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
