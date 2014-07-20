package freeuni.android.delegator.communicator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import android.util.Log;

public class ServerClient {
	//Constants
	private static final String LOG_TAG = "Server-Client";
	// Socket port number to use
	public static final int SERVER_PORT = 2728;

	// Client external IP address
	private static String serverIP="10.0.3.2"; //Genymotion emulator IP, Needs To change dynamiclly 

	private boolean isRunning;
	private String receivedServerMessage;

	//Listeners
	private ArrayList<OnServerMessageReceived> messageReceivedListeners = null;

	//IO variables
	private BufferedReader inputStream;
	private PrintWriter outputStream;

	/**
	 * Constructor
	 */
	public ServerClient(){
		messageReceivedListeners = new ArrayList<OnServerMessageReceived>();
	}

	/**
	 * Adding Listeners
	 * @param listener
	 */
	public void addListeners(OnServerMessageReceived listener){
		if(!messageReceivedListeners.contains(listener))
			messageReceivedListeners.add(listener);
	}

	/**
	 * Deleting Listener
	 * @param listener
	 */
	public void deleteListener(OnServerMessageReceived listener){
		if(messageReceivedListeners.contains(listener))
			messageReceivedListeners.remove(listener);
	}

	/**
	 * Sends message to the server
	 * @param message
	 */
	public synchronized void sendMessage(String header, String message) {
		if (outputStream != null && !outputStream.checkError()) {
			outputStream.println(header);
			outputStream.println(message);
			outputStream.flush();
		}
	}

	/**
	 * Stop running client
	 */
	public void stopClient(){
		isRunning = false;
	}

	/**
	 * Startup run
	 */
	public void runClient(){
		isRunning = true;
		//Socket to connect to the server
		try {
			InetAddress serverAddr = InetAddress.getByName(serverIP);
			Log.e("serverAddr", serverAddr.toString());
			Log.e("TCP Client", "C: Connecting...");
			Socket socket = new Socket(serverAddr, SERVER_PORT);

			try{
				outputStream = new PrintWriter(socket.getOutputStream(), true);
				inputStream = new BufferedReader( new InputStreamReader(socket.getInputStream()));;

				while(isRunning){
					receivedServerMessage = inputStream.readLine();

					if (receivedServerMessage != null && messageReceivedListeners != null) {
						// call the method messageReceived from MyActivity class
						for(int i=0;i<messageReceivedListeners.size();i++){
							messageReceivedListeners.get(i).messageReceived(receivedServerMessage);
						}
					}
					receivedServerMessage = null;
				}

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
