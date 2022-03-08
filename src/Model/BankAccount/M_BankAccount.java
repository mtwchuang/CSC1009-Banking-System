package Model.BankAccount;

import java.util.UUID;

import ModelView.MV_Global;

public class M_BankAccount{
	private String createdBy;
	private long createdAt;
	private String updatedBy;
	private long updatedAt;

	private String bankAccID;
	private String bankAccHolderID;
	private short bankAccType;
	private short bankAccStatus;
	private double bankAccBalance;
	private double bankAccTransactionLimit;
	private double bankAccMinBalance;

	private String[] bankAccTransactions;

	public M_BankAccount(){

	}
	public M_BankAccount(String bankAccHolderID){
		this.createdBy = MV_Global.sessionUser.getUserID();
		this.createdAt = System.currentTimeMillis();
		this.updatedBy = MV_Global.sessionUser.getUserID();
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
	public double getBankAccBalance() {
		return bankAccBalance;
	}
	public double getBankAccTransactionLimit() {
		return bankAccTransactionLimit;
	}
	public double getBankAccMinBalance() {
		return bankAccMinBalance;
	}
	
	public String[] getBankAccTransactions() {
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
	public void setBankAccBalance(double bankAccBalance) {
		this.bankAccBalance = bankAccBalance;
	}
	public void setBankAccTransactionLimit(double bankAccTransactionLimit) {
		this.bankAccTransactionLimit = bankAccTransactionLimit;
	}
	public void setBankAccMinBalance(double bankAccMinBalance) {
		this.bankAccMinBalance = bankAccMinBalance;
	}
	
	public void setBankAccTransactions(String[] bankAccTransactions) {
		this.bankAccTransactions = bankAccTransactions;
	}

	public void updated(){
		this.updatedBy = MV_Global.sessionUser.getUserID();
		this.updatedAt = System.currentTimeMillis();
	}
}