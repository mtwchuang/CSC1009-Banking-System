package Model.BankAccount;

/* Model:
M_BankAccount [Parent]
	00: createdBy - String
	01: createdAt - long
	02: updatedBy - String
	03: updatedAt - long
	04: bankAccID - String
	05: bankAccHolderID - String
	06: bankAccType - short
		Case 00: Normal bank acc
		Case 01: Joint bank acc
		Case 02: Corperate bank acc
	07: bankAccDescription - String
	08: bankAccStatus - short
		Case 00: Normal
		Case 01: Closed
	09: bankAccBalance - double
	10: bankAccTransactionLimit - double
	11: bankAccMinBalance - double
M_CorperateBankAcc [Extends Parent]
	13: bankAccTransactOnlyIDs - String[]
*/

public class M_CorporateBankAcc extends M_BankAccount implements M_ICorporateBankAcc{
	private String[] bankAccSubHolderIDs;
	private String[] bankAccTransactOnlyIDs;

	public M_CorporateBankAcc(){
	}
	public M_CorporateBankAcc(String bankAccHolderID){
		super(bankAccHolderID);
	}

	public String[] getBankAccTransactOnlyIDs(){
		return bankAccTransactOnlyIDs;
	}
	public String[] getBankAccSubHolderIDs(){
		return bankAccSubHolderIDs;
	}

	public void setBankAccSubHolderIDs(String[] backAccSubHolderIDs){
		this.bankAccSubHolderIDs = backAccSubHolderIDs;
		updated();
	}
	public void setBankAccTransactOnlyIDs(String[] bankAccTransactOnlyIDs){
		this.bankAccTransactOnlyIDs = bankAccTransactOnlyIDs;
		updated();
	}
}