package View.AdminActions;

import ModelView.Global.MV_Ambiance;
import ModelView.Global.MV_Global;

public class V_Ambiance {
    public void run() throws Exception{
        //Page name
		System.out.println(">> Bank Account >> [Admin] Ambiance\n");

        //Call ModelView layer to start groovin
        new MV_Ambiance().run();

		System.out.println("Putting on that groove...\n");
        MV_Global.waitSuccess();
    }
}