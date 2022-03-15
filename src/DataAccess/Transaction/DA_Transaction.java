package DataAccess.Transaction;
// import packages from model transactions
import Model.Transaction.*;
public class DA_Transaction 
{
    /* fields within M_Transaction.java */
    // fields for audit tracking 
	//  00: createdBy - String
	//  01: createdAt - long
	//  02: updatedBy - String
	//  03: updatedAt - long
    //  04: executedBy - String
    //  05: executedAt - long
    // fields for general transaction records
    //  06: transactionID - String
    //  07: transactionType - short
    //  08: transactionAmount - double
    //  09: transactionDescription - String
    //  10: transactionExecuted - boolean
    //  11: transactionOverseas - boolean
    // extra fields for M_BalanceTransfer.java
    //  12: transactionSrcBankAccID - String (is this transactionTargetBankAccID?)
    // extra fields for M_BalanceChange.java
    //  13: executedOnAtm - boolean
    //  14: AtmID - String
    //  15: executedOnPurchase - boolean
}