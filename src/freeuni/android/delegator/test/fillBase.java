package freeuni.android.delegator.test;

import freeuni.android.delegator.app.App;
import freeuni.android.delegator.db.DBManager;
import freeuni.android.delegator.model.Group;
import freeuni.android.delegator.model.User;

public class FillBase {

	public static void fillBase(){
		DBManager db= App.getDb();
		User user = new User("data");
		user.setPassword("1234");
		db.addUser(user);
		
		User user2 = new User("sub1");
		user2.setPassword("1234");
		db.addUser(user2);
		
		User user3 = new User("sub2");
		user3.setPassword("1234");
		db.addUser(user3);
		
		User user4 = new User("sub3");
		user4.setPassword("1234");
		db.addUser(user4);
		
		db.setSubortinate(user, user2);
		db.setSubortinate(user, user3);
		db.setSubortinate(user, user4);
		
		Group group1 = new Group(1);
		group1.setGroupName("Programmers");
		db.addGroup(group1, user);
		
		Group group2 = new Group(2);
		group2.setGroupName("Consultants");
		db.addGroup(group2, user);
	}

}
