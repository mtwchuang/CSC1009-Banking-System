import ModelView.MV_Global;
import View.V_ViewManager;

public class Main {
	public static void main(String[] args){
		//Initialize ATM config
		MV_Global.initailizeAtmConfig();

		//Start UI
		V_ViewManager.redirect("login");
	}
}