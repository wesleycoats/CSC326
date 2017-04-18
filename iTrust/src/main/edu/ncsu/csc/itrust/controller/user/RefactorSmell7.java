package edu.ncsu.csc.itrust.controller.user;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.DataBean;
import edu.ncsu.csc.itrust.model.ValidationFormat;
import edu.ncsu.csc.itrust.model.user.User;

public class RefactorSmell7 {
	
	public static <T> boolean smell7(String mid, DataBean<T> dataBean) throws DBException{
		User user = null;
		if( mid == null) return false;
		if(!(ValidationFormat.NPMID.getRegex().matcher(mid).matches())){
			//see if is non-patient user
			long id = -1;
			try{
				id = Long.parseLong(mid);
			}
			catch(NumberFormatException ne){
				return false;
			}
			user = (User) dataBean.getByID(id);
			if(!(user == null)){
					return true;
			}
			else{
				return false;
			}
		}
		long id = -1;
		try{
			id = Long.parseLong(mid);
		}
		catch(NumberFormatException ne){
			return false;
		}
		user = (User) dataBean.getByID(id);
		if(!(user == null)){
				return true;
		}
		else{
			return false;
		}

	}

}
