import ModelView.Global.MV_Global;
import View.Global.V_ViewManager;

public class Main {
	public static void main(String[] args){
		//Initialize ATM config
		MV_Global.initailizeAtmConfig();

		//Start UI
		V_ViewManager.redirect("login");
	}
}