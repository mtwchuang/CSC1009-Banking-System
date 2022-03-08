package Model.BankAccount;

import java.util.List;

public interface M_ICorporateBankAcc extends M_IBankAccount {
	public List<String> getBankAccTransactOnlyIDs();
    public List<String> getBankAccSubHolderIDs();

}