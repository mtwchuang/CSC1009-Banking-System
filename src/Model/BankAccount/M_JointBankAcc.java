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
M_JointBankAcc [Extends Parent]
	12: bankAccSubHolderIDs - String[]
*/

public class M_JointBankAcc extends M_BankAccount implements M_IJointBankAcc{
	private String bankAccSubHolderID;

	public M_JointBankAcc(){
	}
	public M_JointBankAcc(String bankAccHolderID){
		super(bankAccHolderID);
	}

	public String getBankAccSubHolderID(){
		return bankAccSubHolderID;
	}

	public void setBankAccSubHolderID(String bankAccSubHolderID){
		this.bankAccSubHolderID = bankAccSubHolderID;
		updated();
	}
}