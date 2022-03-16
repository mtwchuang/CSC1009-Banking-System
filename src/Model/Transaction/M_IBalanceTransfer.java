package Model.Transaction;
public interface M_IBalanceTransfer extends M_ITransaction
{
    // getter and setters abstract methods for M_BalanceTransfer
    public String getTransactionTargetBankAccID();
    // public void setTransactionTargetBankAccID(String transactionSrcBankAccID); (should be non-modifiable)
}
