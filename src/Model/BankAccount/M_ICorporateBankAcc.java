package Model.BankAccount;

import java.util.List;

public interface M_ICorporateBankAcc extends M_IBankAccount {
	public List<String> getBankAccTransactOnlyIDs();
    public List<String> getBankAccSubHolderIDs();

    public void setBankAccSubHolderID(List<String> backAccSubHolderIDs);
    public void setBankAccTransactOnlyIDs(List<String> bankAccTransactOnlyIDs);
}