package Model.BankAccount;

import java.util.List;

public interface M_IBankAccount {
    public String getCreatedBy();
    public long getCreatedAt();
    public String getUpdatedBy();
    public long getUpdatedAt();
    public String getBankAccID();
    public short getBankAccType();
    public short getBankAccStatus();
    public String getBankAccBranch();
    public short getBankAccBalance();
    public short getBankAccTransactionLimit();
    public double getBankAccInterestRate();
    public double getBankAccMinBalance();
    public List<Long> getBankAccTransactions();

    public void setBankAccType(short bankAccType);
    public void setBankAccStatus(short bankAccStatus);
    public void setBankAccBranch(String bankAccBranch);
    public void setBankAccBalance(short bankAccBalance);
    public void setBankAccTransactionLimit(short bankAccTransactionLimit);
    public void setBankAccInterestRate(double bankAccInterestRate);
    public void setBankAccMinBalance(double bankAccMinBalance);
    public void setBankAccTransactions(List<Long> bankAccTransactions);
}
