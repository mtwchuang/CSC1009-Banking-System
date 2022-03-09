package Model.Transaction;

import java.util.UUID;

import ModelView.MV_Global;

public abstract class M_Transaction {
	private String createdBy;
	private long createdAt;
	private String updatedBy;
	private long updatedAt;
	private String executedBy;
	private long executedAt;

	private String transactionID;
	private short transactionType;
	private double transactionAmount;
	private String transactionDescription;
	private boolean transactionExecuted;
	private boolean transactionOverseas;
	
	private double transactionBankAccInitialAmount;
	private double transactionBankAccFinalAmount;
	private String transactionBankAccID;

	public M_Transaction(boolean creatingNew){
		if(creatingNew){
			this.createdBy = MV_Global.sessionUserAcc.getUserID();
			this.createdAt = System.currentTimeMillis();
			this.updatedBy = MV_Global.sessionUserAcc.getUserID();
			this.updatedAt = System.currentTimeMillis();
	
			this.transactionID = UUID.randomUUID().toString();
			this.transactionExecuted = false;
	
			this.transactionBankAccID = "";
		}
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
	public String getExecutedBy(){
		return executedBy;
	}
	public long getExecutedAt(){
		return executedAt;
	}
   
	public String getTransactionID(){
		return transactionID;
	}
	public short getTransactionType(){
		return transactionType;
	}
	public double getTransactionAmount(){
		return transactionAmount;
	}
	public String getTransactionDescription(){
		return transactionDescription;
	}

	public double getTransactionBankAccInitialAmount(){
		return transactionBankAccInitialAmount;
	}
	public double getTransactionBankAccFinalAmount(){
		return transactionBankAccFinalAmount;
	}
	public String getTransactionSrcBankAccID(){
		return transactionBankAccID;
	}

	public void setTransactionType(short transactionType){
		this.transactionType = transactionType;
	}
	public void setTransactionAmount(double transactionAmount){
		this.transactionAmount = transactionAmount;
	}
	public void setTransactionDescription(String transactionDescription){
		this.transactionDescription = transactionDescription;
	}
	public void settransactionOverseas(boolean transactionOverseas){
		this.transactionOverseas = transactionOverseas;
	}

	public void setTransactionBankAccInitialAmount(double transactionBankAccInitialAmount){
		this.transactionBankAccInitialAmount = transactionBankAccInitialAmount;
	}
	public void setTransactionBankAccFinalAmount(double transactionBankAccFinalAmount){
		this.transactionBankAccFinalAmount = transactionBankAccFinalAmount;
	}
	public void setTransactionSrcBankAccID(String transactionBankAccID){
		this.transactionBankAccID = transactionBankAccID;
	}

	public boolean isTransactionExecuted(){
		return transactionExecuted;
	}
	public boolean isTransactionOverseas(){
		return transactionOverseas;
	}
   
	public void executeTransaction(){
		this.transactionExecuted = true;
		this.executedBy = MV_Global.sessionUserAcc.getUserID();
		this.executedAt = System.currentTimeMillis();
	}

	public void updated(){
		this.updatedBy = MV_Global.sessionUserAcc.getUserID();
		this.updatedAt = System.currentTimeMillis();
	}
}