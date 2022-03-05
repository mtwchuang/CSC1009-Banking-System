package ModelView;

import java.util.UUID;

public class Global {
    public static String sessionUserID = UUID.randomUUID().toString();

    public static String dbUserAccount = "\\data\\UserAccount\\UserAccounts.csv";
    public static String dbBankAccount = "\\data\\BankAccount\\BankAccounts.csv";

    public static String dbBalanceChanges = "\\data\\Transaction\\BalanceChanges.csv";
    public static String dbBalanceTranfers = "\\data\\Transaction\\BalanceTransfers.csv";
}