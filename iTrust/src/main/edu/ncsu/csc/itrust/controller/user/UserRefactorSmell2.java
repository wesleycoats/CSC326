package edu.ncsu.csc.itrust.controller.user;

import edu.ncsu.csc.itrust.model.user.User;

public class UserRefactorSmell2 {
	
	public static long smell2(String mid){
		User user = null;
		if( mid == null) return -1;
		if(mid.isEmpty()) return -1;
		long id = -1;
		try{
			id = Long.parseLong(mid);
		}
		catch(NumberFormatException ne){
			return -1;
		}
		return id;

	}

}
