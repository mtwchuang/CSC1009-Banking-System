package Model.Transaction;

public class BalanceTransfer extends Transaction{
    private String transactionTargetBankAccID;

    public BalanceTransfer(String transactionSrcBankAccID){
        super(transactionSrcBankAccID);
    }

    public String getTransactionTargetBankAccID(){
        return transactionTargetBankAccID;
    }

    public void setTransactionTargetBankAccID(String transactionSrcBankAccID){
        this.transactionTargetBankAccID = transactionSrcBankAccID;
        updated();
    }
}