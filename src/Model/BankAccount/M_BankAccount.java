package Model.BankAccount;

import java.util.List;
import java.util.UUID;

import ModelView.Global;

public class M_BankAccount{
    private String createdBy;
    private long createdAt;
    private String updatedBy;
    private long updatedAt;

    private String bankAccID;
    private String bankAccHolderID;
    private short bankAccType;
    private short bankAccStatus;
    private int bankAccBranch;
    private double bankAccBalance;
    private double bankAccTransactionLimit;
    private double bankAccMinBalance;

    private List<Long> bankAccTransactions;

    public M_BankAccount(String bankAccHolderID){
        this.createdBy = Global.sessionUserID;
        this.createdAt = System.currentTimeMillis();
        this.updatedBy = Global.sessionUserID;
        this.updatedAt = System.currentTimeMillis();

        this.bankAccID = UUID.randomUUID().toString();
        this.bankAccHolderID = bankAccHolderID;
    }

    public String getCreatedBy() {
        return createdBy;
    }
    public long getCreatedAt() {
        return createdAt;
    }
    public String getUpdatedBy() {
        return updatedBy;
    }
    public long getUpdatedAt() {
        return updatedAt;
    }

    public String getBankAccID() {
        return bankAccID;
    }
    public String getBankAccHolderID() {
        return bankAccHolderID;
    }
    public short getBankAccType() {
        return bankAccType;
    }
    public short getBankAccStatus() {
        return bankAccStatus;
    }
    public int getBankAccBranch() {
        return bankAccBranch;
    }
    public double getBankAccBalance() {
        return bankAccBalance;
    }
    public double getBankAccTransactionLimit() {
        return bankAccTransactionLimit;
    }
    public double getBankAccMinBalance() {
        return bankAccMinBalance;
    }
    
    public List<Long> getBankAccTransactions() {
        return bankAccTransactions;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setBankAccID(String bankAccID) {
        this.bankAccID = bankAccID;
    }
    public void setBankAccHolderID(String bankAccHolderID) {
        this.bankAccHolderID = bankAccHolderID;
    }
    public void setBankAccType(short bankAccType) {
        this.bankAccType = bankAccType;
    }
    public void setBankAccStatus(short bankAccStatus) {
        this.bankAccStatus = bankAccStatus;
    }
    public void setBankAccBranch(int bankAccBranch) {
        this.bankAccBranch = bankAccBranch;
    }
    public void setBankAccBalance(double bankAccBalance) {
        this.bankAccBalance = bankAccBalance;
    }
    public void setBankAccTransactionLimit(double bankAccTransactionLimit) {
        this.bankAccTransactionLimit = bankAccTransactionLimit;
    }
    public void setBankAccMinBalance(double bankAccMinBalance) {
        this.bankAccMinBalance = bankAccMinBalance;
    }
    
    public void setBankAccTransactions(List<Long> bankAccTransactions) {
        this.bankAccTransactions = bankAccTransactions;
    }

    protected void updated(){
        this.updatedBy = Global.sessionUserID;
        this.updatedAt = System.currentTimeMillis();
    }
}