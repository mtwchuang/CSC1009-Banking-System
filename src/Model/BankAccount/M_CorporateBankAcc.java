package Model.BankAccount;

import java.util.List;

public class M_CorporateBankAcc extends M_BankAccount{
	private List<String> bankAccSubHolderIDs;
	private List<String> bankAccTransactOnlyIDs;

	public M_CorporateBankAcc(String bankAccHolderID){
		super(bankAccHolderID);
	}

	public List<String> getBankAccTransactOnlyIDs(){
		return bankAccTransactOnlyIDs;
	}
	public List<String> getBankAccSubHolderIDs(){
		return bankAccSubHolderIDs;
	}

	public void setBankAccSubHolderID(List<String> backAccSubHolderIDs){
		this.bankAccSubHolderIDs = backAccSubHolderIDs;
		updated();
	}
	public void setBankAccTransactOnlyIDs(List<String> bankAccTransactOnlyIDs){
		this.bankAccTransactOnlyIDs = bankAccTransactOnlyIDs;
		updated();
	}
}