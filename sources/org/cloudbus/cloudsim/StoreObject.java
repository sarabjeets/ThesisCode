package org.cloudbus.cloudsim;

import java.util.ArrayList;

public class StoreObject {
	
	private ArrayList<User> userArr;

    public  StoreObject() {
    	userArr = new ArrayList<User>();
    }

    public void addUser(int userid) {
    	userArr.add(new User(userid));
    }

    public User getSaleAtIndex(int index) {
        return userArr.get(index);
    }

    //or if you want the entire ArrayList:
   
}
