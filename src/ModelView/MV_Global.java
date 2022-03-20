package ModelView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import DataAccess.DA_Settings;
import Model.BankAccount.M_IBankAccount;
import Model.UserAccount.M_IUserAccount;

public class MV_Global {
    //ATM config
	//[ATM_ID]-[Country]db
	private static String atmID;
	private static int[][] availableNotes;
    public static String getAtmID(){
        return atmID;
    }
    public static int[][] getAvailableNotes(){
        return availableNotes;
    }

    //Bank config
    private static double overseasTransactionCharge;
    public static double getOverseasTransactionCharge(){
        return overseasTransactionCharge;
    }

	//Program-global scanner
	public static Scanner input = new Scanner(System.in);

	//Session storage
	public static M_IUserAccount sessionUserAcc;
	public static M_IBankAccount sessionBankAcc;

	//Database path config
	public static String dbUserAccounts = getDynamicDbPath() + "\\data\\UserAccount\\UserAccounts.txt";
	public static String dbUserAccountLogins = getDynamicDbPath() + "\\data\\UserAccount\\UserAccountLogins";
	public static String dbBankAccounts = getDynamicDbPath() + "\\data\\BankAccount\\BankAccounts.txt";
	public static String dbBalanceChanges = getDynamicDbPath() + "\\data\\Transaction\\BalanceChanges.txt";
	public static String dbBalanceTransfers = getDynamicDbPath() + "\\data\\Transaction\\BalanceTransfers.txt";
	public static String dbSettings = getDynamicDbPath() + "\\data\\Settings.txt";
	private static String getDynamicDbPath(){
		return new File("").getAbsolutePath();
	}
	
	//Program-global delay functions; for user experience and readability
	public static void wait(int milliseconds) throws Exception{
		TimeUnit.MILLISECONDS.sleep((long) milliseconds);
	}
	public static void waitSuccess() throws Exception{wait(1000);}
	public static void waitError() throws Exception{wait(1000);}

	//Clear session data
	public static void clearSession(){
		sessionUserAcc = null;
		sessionBankAcc = null;
	}

	//Load Atm config into MV_Global
    public static void initailizeAtmConfig(){
        String countryCode;
        int currentDenomination, currentDenominationCount;
        String[] notesDenominations = null, notesCount = null;
        List<int[]> availableNotesList = new ArrayList<int[]>();
		DA_Settings settingsDA = new DataAccess.DA_Settings();

        //Fetch settings from settings file
        try{
            MV_Global.atmID = settingsDA.dbSettings_GetByKey("AtmID")[0];
            countryCode = MV_Global.atmID.split("-")[1];

            MV_Global.overseasTransactionCharge = 
                Double.parseDouble(settingsDA.dbSettings_GetByKey("OverseasTransactionCharge")[0]);

            switch(countryCode){
                case "02":
                    notesDenominations = settingsDA.dbSettings_GetByKey("NotesDenominationJP");
                    break;
                case "03":
                    notesDenominations = settingsDA.dbSettings_GetByKey("NotesDenominationUS");
                    break;
                default:
                    notesDenominations = settingsDA.dbSettings_GetByKey("NotesDenominationSG");
                    break;
            }
            
            notesCount = settingsDA.dbSettings_GetByKey("AtmNotesQty");
        }
        //Invalid settings file
        catch(Exception e){
            System.out.println("Unable to initialize ATM configurations...\nProgram terminating");
            System.exit(0);
        }

        for(int i = 0; i < notesDenominations.length; i++){
            currentDenomination = Integer.parseInt(notesDenominations[i]);
            try{
                String temp = notesCount[i];
                currentDenominationCount = Integer.parseInt(temp);
            }
            catch(IndexOutOfBoundsException ioobe){
                currentDenominationCount = 0;
            }
            availableNotesList.add(new int[]{currentDenomination, currentDenominationCount});
        }

        int[][] temp = new int[availableNotesList.size()][2];
        for(int i = 0; i < availableNotesList.size(); i++){
            temp[i][0] = availableNotesList.get(i)[0];
            temp[i][1] = availableNotesList.get(i)[1];
        }
        availableNotes = temp;


    }
}