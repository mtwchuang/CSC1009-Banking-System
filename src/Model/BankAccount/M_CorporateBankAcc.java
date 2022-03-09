package Model.BankAccount;

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