package Model.BankAccount;

public interface M_ICorporateBankAcc extends M_IBankAccount {
    public String[] getBankAccTransactOnlyIDs();
    public String[] getBankAccSubHolderIDs();
    public void setBankAccSubHolderIDs(String[] backAccSubHolderIDs);
	public void setBankAccTransactOnlyIDs(String[] bankAccTransactOnlyIDs);
}