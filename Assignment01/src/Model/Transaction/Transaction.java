package Model.Transaction;

import java.util.UUID;

import ModelView.Global;

public abstract class Transaction {
    private UUID createdBy;
    private long createdAt;
    private UUID updatedBy;
    private long updatedAt;
    private UUID executedBy;
    private long executedAt;

    private UUID transactionID;
    private short transactionType;
    private double transactionAmount;
    private String transactionDescription;
    private boolean transactionExecuted;
    private boolean transactionOverseas;

    private UUID transactionSrcBankAccID;
    private short transactionSrcBankAccBranch;

    public Transaction(UUID transactionSrcBankAccID, short transactionSrcBankAccBranch){
        this.createdBy = Global.sessionUserID;
        this.createdAt = System.currentTimeMillis();
        this.updatedBy = Global.sessionUserID;
        this.updatedAt = System.currentTimeMillis();

        this.transactionID = UUID.randomUUID();
        this.transactionExecuted = false;

        this.transactionSrcBankAccID = transactionSrcBankAccID;
        this.transactionSrcBankAccBranch = transactionSrcBankAccBranch;
    }

    public UUID getCreatedBy(){
        return createdBy;
    }
    public long getCreatedAt(){
        return createdAt;
    }
    public UUID getUpdatedBy(){
        return updatedBy;
    }
    public long getUpdatedAt(){
        return updatedAt;
    }
    public UUID getExecutedBy(){
        return executedBy;
    }
    public long getExecutedAt(){
        return executedAt;
    }
   
    public UUID getTransactionID(){
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

    public UUID getTransactionSrcBankAccID(){
        return transactionSrcBankAccID;
    }
    public short getTransactionSrcBankAccBranch(){
        return transactionSrcBankAccBranch;
    }

    public void setTransactionID(UUID transactionID){
        this.transactionID = transactionID;
        updated();
    }
    public void setTransactionType(short transactionType){
        this.transactionType = transactionType;
        updated();
    }
    public void setTransactionAmount(double transactionAmount){
        this.transactionAmount = transactionAmount;
        updated();
    }
    public void setTransactionDescription(String transactionDescription){
        this.transactionDescription = transactionDescription;
    }
    public void settransactionOverseas(boolean transactionOverseas){
        this.transactionOverseas = transactionOverseas;
    }

    public boolean isTransactionExecuted(){
        return transactionExecuted;
    }
    public boolean isTransactionOverseas(){
        return transactionOverseas;
    }
   
    public void executeTransaction(){
        this.transactionExecuted = true;
        this.executedBy = Global.sessionUserID;
        this.executedAt = System.currentTimeMillis();
        updated();
    }

    protected void updated(){
        this.updatedBy = Global.sessionUserID;
        this.updatedAt = System.currentTimeMillis();
    }
}