package DataAccess.Transaction;
// import packages from model transactions
import java.io.BufferedReader;
import Model.Transaction.*;
public class DA_Transaction 
{
    // fields "merchantID", "executedBy", "executedAt" omitted
    /* fields within M_Transaction.java
    fields for audit tracking 
	 00: createdBy - String
	 01: createdAt - long
	 02: updatedBy - String
	 03: updatedAt - long
    fields for general transaction records
     04: transactionID - String
     05: transactionType - short
     06: transactionAmount - double
     07: transactionDescription - String
     08: transactionExecuted - boolean
     09: transactionOverseas - boolean
    extra fields for M_BalanceTransfer.java
     10: transactionTargetBankAccID - String
    extra fields for M_BalanceChange.java
     11: executedOnAtm - boolean
     12: AtmID - String
     13: executedOnPurchase - boolean */
    // private function to return one balance transfer
    // private function to return multiple balance transfer
    // private function to return one balance change
    // private functin to return multiple balance change
    // public function to return transactions by transactionID
    // public function to return transactions by bankAcct
    // observing the method in DA_Bankaccount, why is your return type M_IBankAccount, an interface? Not a class type?
    //


    /* public function to return transaction by corporateID */

}