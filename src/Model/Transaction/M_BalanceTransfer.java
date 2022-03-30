package Model.Transaction;

/* Model:
M_Transaction [Abstract Parent]
	00: createdBy - String
	01: createdAt - long
	02: updatedBy - String
	03: updatedAt - long
	04: transactionID - String
		Case 00: Withdrawals
		Case 01: Deposits
		Case 02: Charges/Purchases
		Case 03: Transfer Sending
		Case 04: Transfer Receiving
	05: transactionType - short
	06: transactionAmount - double
	07: transactionDescription - String
	08: transactionOverseas - boolean
	09: transactionBankAccID - String
	10: transactionBankAccInitialAmount - double
	11: transactionBankAccFinalAmount - double
M_BalanceTransfer [Extends Parent]
	15: transactionTargetBankAccID - String 
*/

public class M_BalanceTransfer extends M_Transaction implements M_IBalanceTransfer
{
	// additioanl attributes for M_BalanceTransfer
	private String transactionTargetBankAccID;

	public M_BalanceTransfer(boolean creatingNew)
	{
		super(creatingNew);
	}
	// getter and setter
	public String getTransactionTargetBankAccID(){
		return transactionTargetBankAccID;
	}
	public void setTransactionTargetBankAccID(String transactionSrcBankAccID){
		this.transactionTargetBankAccID = transactionSrcBankAccID;
		updated();
	}
}