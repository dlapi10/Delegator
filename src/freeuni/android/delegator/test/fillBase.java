package freeuni.android.delegator.test;

import freeuni.android.delegator.app.App;
import freeuni.android.delegator.communicator.DatabaseCommunicator;
import freeuni.android.delegator.model.User;

public class FillBase {

	public static void fillBase(){
		DatabaseCommunicator db = App.getDb();
		User user = new User("data");
		user.setPassword("1234");
		user.setPhoneNumber("+995598171615");
		db.addUser(user);
		
		User user2 = new User("giorgi");
		user2.setPassword("1234");
		user2.setPhoneNumber("+995598171615");
		db.addUser(user2);
		
		User user3 = new User("nino");
		user3.setPassword("1234");
		db.addUser(user3);
		
		User user4 = new User("saba");
		user4.setPassword("1234");
		db.addUser(user4);
		
		User user5 = new User("ruska");
		user5.setPassword("+995598002720");
		db.addUser(user5);
		
		db.setSubortinate(user, user2);
		db.setSubortinate(user, user3);
		db.setSubortinate(user, user4);
		db.setSubortinate(user, user4);
	}

}
