package View.Index;

import ModelView.MV_Global;

public class V_UserAccIndex 
{
	public void run() throws Exception{
		//Authorization
		if(MV_Global.sessionUser == null) throw new Exception("No user logged in");

		
	}
}