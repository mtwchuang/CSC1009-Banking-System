package Model.Transaction;
// Interface for M_Transaction
// Parent interface of M_IBalanceChange and M_IBalanceTransfer
public interface M_ITransaction 
{
    // getters for private variables
	public String getCreatedBy();
	public long getCreatedAt();
	public String getUpdatedBy();
	public long getUpdatedAt();
	// public String getExecutedBy();
	// public long getExecutedAt();
	public String getTransactionID();
	public short getTransactionType();
	public double getTransactionAmount();
	public String getTransactionDescription();
	public double getTransactionBankAccInitialAmount();
	public double getTransactionBankAccFinalAmount();
	public String getTransactionSrcBankAccID();
    // setters for private variables
	public void setTransactionType(short transactionType);
	public void setTransactionAmount(double transactionAmount);
	public void setTransactionDescription(String transactionDescription);
	public void settransactionOverseas(boolean transactionOverseas);
	public void setTransactionBankAccInitialAmount(double transactionBankAccInitialAmount);
	public void setTransactionBankAccFinalAmount(double transactionBankAccFinalAmount);
	public void setTransactionSrcBankAccID(String transactionBankAccID);
	public boolean isTransactionExecuted();
	public boolean isTransactionOverseas();
    // other methods
	// public void executeTransaction();
	public void updated();
}
