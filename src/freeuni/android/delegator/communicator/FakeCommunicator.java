package freeuni.android.delegator.communicator;


/*
 * 
 */
public class FakeCommunicator implements NetworkCommunicator{
	
	/**
	 * Checking credentials of user. Returns true if given information matches
	 * @param name user name
	 * @param pass password
	 * @return false if there is not such user with password, true otherwise
	 */
	public boolean checkCredentials(String name, String pass){
		return true;
	}
	
}
