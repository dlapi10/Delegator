package freeuni.android.delegator.communicator;

import freeuni.android.delegator.app.App;
import freeuni.android.delegator.db.DBManager;


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
		DBManager db= App.getDb();
		String truePass = db.getUser(name).getPassword();
		if(truePass!=null && pass.equals(truePass))
			return true;
		return false;
	}
	
}
