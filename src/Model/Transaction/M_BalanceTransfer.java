package Model.Transaction;

public class M_BalanceTransfer extends M_Transaction implements M_IBalanceTransfer{
	private String transactionTargetBankAccID;

	public M_BalanceTransfer(boolean creatingNew){
		super(creatingNew);
	}

	public String getTransactionTargetBankAccID(){
		return transactionTargetBankAccID;
	}

	public void setTransactionTargetBankAccID(String transactionSrcBankAccID){
		this.transactionTargetBankAccID = transactionSrcBankAccID;
		updated();
	}
}