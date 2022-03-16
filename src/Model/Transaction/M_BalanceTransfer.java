package Model.Transaction;

public class M_BalanceTransfer extends M_Transaction implements M_IBalanceTransfer
{
	// additioanl attributes for M_BalanceTransfer
	private String transactionTargetBankAccID;

	public M_BalanceTransfer(boolean creatingNew)
	{
		super(creatingNew);
	}
	// getter and setter
	public String getTransactionTargetBankAccID(){
		return transactionTargetBankAccID;
	}
	public void setTransactionTargetBankAccID(String transactionSrcBankAccID){
		this.transactionTargetBankAccID = transactionSrcBankAccID;
		updated();
	}
}