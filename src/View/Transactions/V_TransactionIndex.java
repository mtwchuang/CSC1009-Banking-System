package View.Transactions;

import ModelView.MV_Global;
import ModelView.Transaction.MV_Transaction;
import View.V_ViewManager;

public class V_TransactionIndex{
    public void run() throws Exception{
        MV_Transaction txnMV = new MV_Transaction();
        String[] displayData;
        String userInput;
        int currentCacheInstance, currentPage = 1, userInputInt = 0;

        //Initialize current view cache instance
        currentCacheInstance = (int)Math.floor(Math.random()*(1000000 - 0 + 1) + 0);

        while(true){
            //Fetch display data
            try{
                displayData = txnMV.VTransactionIndex_getAllTxn(currentCacheInstance, currentPage);
            }
            catch(Exception e){
                System.out.println("Unable to fetch transaction data.\nReturning...");
                MV_Global.waitError();
                return;
            }
            
            V_ViewManager.clearPage();

            System.out.println(
				"User: " + MV_Global.sessionUserAcc.getUserLastName() + 
				" " + MV_Global.sessionUserAcc.getUserFirstName());
			System.out.println(
				"Bank Account: " + MV_Global.sessionBankAcc.getBankAccID());
			System.out.println("");

            System.out.println("Date\t\t\tType\t\t\tAmount\t\tDescription");
			System.out.println("------------------------------------------------------------------------------------------------------");
            for(int i = 0; i < displayData.length - 1; i++) System.out.println(displayData[i]);
			System.out.println("\n" + displayData[displayData.length - 1].split("\\|")[0]);
            currentPage = Integer.parseInt(displayData[displayData.length - 1].split("\\|")[1]);

            System.out.println("\nInput number to navigate to page. Input nothing to return.");
            System.out.print("Input: ");
            userInput = MV_Global.input.nextLine();

            try{
                if(userInput == null || userInput.isEmpty()){
                    System.out.println("\nReturning...");
                    MV_Global.waitSuccess();
                    return;
                }

                userInputInt = Integer.parseInt(userInput);
                if(userInputInt < 1) throw new Exception();
                else{
                    System.out.println("\nFetching data...");
                    currentPage = userInputInt;
                    MV_Global.waitSuccess();
                }
            }
            catch(Exception e){
                System.out.println("\nInvalid input...");
                MV_Global.waitError();
            }
        }
    }
}