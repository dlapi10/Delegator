package freeuni.android.delegator.communicator;

/**
 * Listener interface. Invoked when server message is received.
 * @author Admin
 *
 */
public interface OnServerMessageReceived {
	public void messageReceived(String header, String message);
}
