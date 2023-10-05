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
	public String getTransactionID();
	public short getTransactionType();
	public double getTransactionAmount();
	public String getTransactionDescription();
	public boolean isTransactionOverseas();
	public String getTransactionSrcBankAccID();

	public double getTransactionBankAccInitialAmount();
	public double getTransactionBankAccFinalAmount();

    // setters for private variables
	// public void setTransactionType(short transactionType); (should non-modifiable)
	// public void setTransactionAmount(double transactionAmount); (should non-modifiable)
	// public void setTransactionDescription(String transactionDescription); (should non-modifiable)
	public void setTransactionOverseas(boolean transactionOverseas);
	public void setTransactionBankAccInitialAmount(double transactionBankAccInitialAmount);
	public void setTransactionBankAccFinalAmount(double transactionBankAccFinalAmount);
	// public void setTransactionSrcBankAccID(String transactionBankAccID); (should non-modifiable)
    // other methods
	public void updated();
}
