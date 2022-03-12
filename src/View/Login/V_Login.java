package View.Login;

import ModelView.MV_Global;
import ModelView.UserAccount.MV_UserAccount;

import View.V_ViewManager;

public class V_Login 
{
	public void run() throws Exception
	{
		boolean ahShit = false;

		boolean validationFlag;
		short authenticationStatus = 0;
		String inputUsername, inputPassword;

		//Infinite loop to simulate ATM's embedded OS's continuous runtime
		while(true){
			validationFlag = true;

			//Clear screen
			V_ViewManager.clearPage();

			//Print bank logo
			this.logo();
			
			//Intake username
			System.out.print("Username: ");
			if(ahShit) inputUsername = "admin";
			else inputUsername = MV_Global.input.nextLine();

			//Intake password
			if(ahShit) inputPassword = "000000";
			else inputPassword = new String(System.console().readPassword("Password: "));
			System.out.println("");

			System.out.print("Loading... "); 
			//Password validation; check if input is integer
			try
			{
				Integer.parseInt(inputPassword);
			}
			catch(Exception e)
			{
				validationFlag = false;
			}

			//Call out VL_checkAcct() from MV_UserAccount in layer ModelView to check against stored credentials
			if(validationFlag)
			{
				//Fetch session user data
				authenticationStatus = new MV_UserAccount().VLogin_checkAcc(inputUsername, inputPassword);

				//Check authenticationStatus
				switch(authenticationStatus)
				{
					//Authentication success
					case(0):
					{
						//Show user login success
						System.out.println("Login successful.");
						MV_Global.waitSuccess();

						//Page navigation after logging in
						V_ViewManager.redirect("useraccindex");

						//Redirect to logout page
						V_ViewManager.redirect("logout");

						break;
					}
					//Invalid login credentials
					default:
					{
						System.out.println("Invalid username or password.");
						MV_Global.waitError();
						break;
					}
				}
			}
			//Invalid login input
			else{
				System.out.print("Invalid username or password.");
				MV_Global.waitSuccess();
			}

			V_ViewManager.clearPage();
		}
	}

	//Print fancy bank logo
	private void logo(){
		System.out.println("");
		System.out.println("| | _  |  _  _ __  _    _|_ _ ");
		System.out.println("|^|(/_ | (_ (_)|||(/_    |_(_)");
		System.out.println("================================================================================");
		System.out.println("          oooo       oooooooooo.");
		System.out.println("          `888       `888'   `Y8b");
		System.out.println(".ooooo.    888        888     888  .oooo.   ooo. .oo.    .ooooo.   .ooooo.");
		System.out.println("d88' `88b  888        888oooo888' `P  )88b  `888P\"Y88b  d88' `\"Y8 d88' `88b");
		System.out.println("888ooo888  888        888    `88b  .oP\"888   888   888  888       888   888");
		System.out.println("888    .o  888        888    .88P d8(  888   888   888  888   .o8 888   888");
		System.out.println("`Y8bod8P' o888o      o888bood8P'  `Y888\"\"8o o888o o888o `Y8bod8P' `Y8bod8P'");
		System.out.println("================================================================================\n");

		System.out.println("Login to get started...\n");
	}
}