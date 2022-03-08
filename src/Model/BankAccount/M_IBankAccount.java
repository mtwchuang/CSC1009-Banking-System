package Model.BankAccount;

public interface M_IBankAccount{
	public String getCreatedBy();
    public long getCreatedAt();
    public String getUpdatedBy();
    public long getUpdatedAt();

    public String getBankAccID();
    public String getBankAccHolderID();
    public short getBankAccType();
    public short getBankAccStatus();
    public double getBankAccBalance();
    public double getBankAccTransactionLimit();
    public double getBankAccMinBalance();
    public String[] getBankAccTransactions();

    public void setBankAccType(short bankAccType);
    public void setBankAccStatus(short bankAccStatus);
    public void setBankAccTransactionLimit(double bankAccTransactionLimit);
    public void setBankAccTransactions(String[] bankAccTransactions);
    public void updated();
}