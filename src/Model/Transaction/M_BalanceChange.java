package Model.Transaction;

import ModelView.Global.MV_Global;

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
M_BalanceChange [Extends Parent]
	12: executedOnAtm - boolean
	13: AtmID - String
	14: executedOnPurchase - boolean
*/

public class M_BalanceChange extends M_Transaction implements M_IBalanceChange
{
	// additional attributes for M_BalanceChange
	private boolean executedOnAtm = false;
	private String AtmID;
	private boolean executedOnPurchase = false;

	public M_BalanceChange(boolean creatingNew)
	{
		super(creatingNew);
	}
	// balance check getters
	public boolean isExecutedOnAtm() {
		return executedOnAtm;
	}
	public String getAtmID() {
		return AtmID;
	}
	public boolean isExecutedOnPurchase() {
		return executedOnPurchase;
	}
	// balance check setters
	public void setExecutedOnAtm(boolean executedOnAtm) {
		this.executedOnAtm = executedOnAtm;
	}
	public void setExecutedOnPurchase(boolean executedOnPurchase) {
		this.executedOnPurchase = executedOnPurchase;
	}
	public void setAtmID(String atmID) {
		this.AtmID = atmID;
		this.executedOnAtm = true;
		updated();
	}

	public void executeOnAtm(){
		this.AtmID = MV_Global.getAtmID();
		this.executedOnAtm = true;
	}
}