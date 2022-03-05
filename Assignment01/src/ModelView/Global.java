package ModelView;

import java.util.UUID;

public class Global {
    public static String sessionUserID = UUID.randomUUID().toString();

    public static String dbUserAccount = "\\UserAccount\\UserAccounts.csv";
    public static String dbBankAccount = "\\BankAccount\\BankAccounts.csv";

    public static String dbBalanceChanges = "\\Transaction\\BalanceChanges.csv";
    public static String dbBalanceTranfers = "\\Transaction\\BalanceTransfers.csv";
}