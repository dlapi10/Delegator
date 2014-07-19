package freeuni.android.delegator.communicator;

import freeuni.android.delegator.app.App;
import freeuni.android.delegator.db.DBManager;
import freeuni.android.delegator.model.User;


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
		DatabaseCommunicator db= App.getDb();
		User user = db.getUser(name);
		if(user!=null){
			String truePass = user.getPassword();
			if(truePass!=null && pass.equals(truePass))
				return true;
		}
		return false;
	}
	
}
