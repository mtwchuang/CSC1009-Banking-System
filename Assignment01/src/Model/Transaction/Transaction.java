package Model.Transaction;

import java.util.UUID;

import ModelView.Global;

public abstract class Transaction {
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

    public Transaction(String transactionBankAccID){
        this.createdBy = Global.sessionUserID;
        this.createdAt = System.currentTimeMillis();
        this.updatedBy = Global.sessionUserID;
        this.updatedAt = System.currentTimeMillis();

        this.transactionID = UUID.randomUUID().toString();
        this.transactionExecuted = false;

        this.transactionBankAccID = transactionBankAccID;
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

    public void setTransactionBankAccInitialAmount(double transactionBankAccInitialAmount){
        this.transactionBankAccInitialAmount = transactionBankAccInitialAmount;
        updated();
    }
    public void setTransactionBankAccFinalAmount(double transactionBankAccFinalAmount){
        this.transactionBankAccFinalAmount = transactionBankAccFinalAmount;
        updated();
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