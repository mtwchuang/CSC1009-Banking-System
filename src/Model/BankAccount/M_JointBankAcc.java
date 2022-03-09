package Model.BankAccount;

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