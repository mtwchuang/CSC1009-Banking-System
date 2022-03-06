package ModelView;
import java.io.File;
import java.util.UUID;

public class Global {
    private static String getDynamicDbPath(){
        File dynamicDir = new File("");
        String localDir = dynamicDir.getAbsolutePath();
        return localDir;
    }
    
    public static String sessionUserID = UUID.randomUUID().toString();

    public static String selectionOpt = 
        getDynamicDbPath() + "\\data\\SelectionOpt.txt";

    public static String dbUserAccounts = 
        getDynamicDbPath() + "\\data\\UserAccount\\UserAccounts.txt";
    public static String dbUserAccountLogins = 
        getDynamicDbPath() + "\\data\\UserAccount\\UserAccountLogins";

    public static String dbBankAccounts = 
        getDynamicDbPath() + "\\data\\BankAccount\\BankAccounts.txt";

    public static String dbBalanceChanges = 
        getDynamicDbPath() + "\\data\\Transaction\\BalanceChanges.txt";
    public static String dbBalanceTranfers = 
        getDynamicDbPath() + "\\data\\Transaction\\BalanceTransfers.txt";
}