package Model.BankAccount;

import java.util.List;
import java.util.UUID;

import ModelView.Global;

public class BankAccount implements IBankAccount{
    private String createdBy;
    private long createdAt;
    private String updatedBy;
    private long updatedAt;

    private String bankAccID;
    private String bankAccHolderID;
    private short bankAccType;
    private short bankAccStatus;
    private String bankAccBranch;
    private short bankAccBalance;
    private short bankAccTransactionLimit;
    private double bankAccInterestRate;
    private double bankAccMinBalance;

    private List<Long> bankAccTransactions;

    public BankAccount(String bankAccHolderID){
        this.createdBy = Global.sessionUserID;
        this.createdAt = System.currentTimeMillis();
        this.updatedBy = Global.sessionUserID;
        this.updatedAt = System.currentTimeMillis();

        this.bankAccID = UUID.randomUUID().toString();
        this.bankAccHolderID = bankAccHolderID;
    }

    public String getCreatedBy(){
        return createdBy;
    }
    public long getCreatedAt(){
        return createdAt;
    }
    public String getUpdatedBy(){
        return updatedBy;
    }
    public long getUpdatedAt(){
        return updatedAt;
    }

    public String getBankAccID(){
        return bankAccID;
    }
    public String getBankAccHolderID(){
        return bankAccHolderID;
    }
    public short getBankAccType(){
        return bankAccType;
    }
    public short getBankAccStatus(){
        return bankAccStatus;
    }
    public String getBankAccBranch(){
        return bankAccBranch;
    }
    public short getBankAccBalance(){
        return bankAccBalance;
    }
    public short getBankAccTransactionLimit(){
        return bankAccTransactionLimit;
    }
    public double getBankAccInterestRate(){
        return bankAccInterestRate;
    }
    public double getBankAccMinBalance(){
        return bankAccMinBalance;
    }
    public List<Long> getBankAccTransactions(){
        return bankAccTransactions;
    }

    public void setBankAccHolderID(String bankAccHolderID){
        this.bankAccHolderID = bankAccHolderID;
        updated();
    }
    public void setBankAccType(short bankAccType){
        this.bankAccType = bankAccType;
        updated();
    }
    public void setBankAccStatus(short bankAccStatus){
        this.bankAccStatus = bankAccStatus;
        updated();
    }
    public void setBankAccBranch(String bankAccBranch){
        this.bankAccBranch = bankAccBranch;
        updated();
    }
    public void setBankAccBalance(short bankAccBalance){
        this.bankAccBalance = bankAccBalance;
        updated();
    }
    public void setBankAccTransactionLimit(short bankAccTransactionLimit){
        this.bankAccTransactionLimit = bankAccTransactionLimit;
        updated();
    }
    public void setBankAccInterestRate(double bankAccInterestRate){
        this.bankAccInterestRate = bankAccInterestRate;
        updated();
    }
    public void setBankAccMinBalance(double bankAccMinBalance){
        this.bankAccMinBalance = bankAccMinBalance;
        updated();
    }
    public void setBankAccTransactions(List<Long> bankAccTransactions){
        this.bankAccTransactions = bankAccTransactions;
        updated();
    }

    protected void updated(){
        this.updatedBy = Global.sessionUserID;
        this.updatedAt = System.currentTimeMillis();
    }
}
