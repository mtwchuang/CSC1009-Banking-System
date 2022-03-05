package Model.Transaction;

public class M_BalanceTransfer extends M_Transaction{
    private String transactionTargetBankAccID;

    public M_BalanceTransfer(String transactionSrcBankAccID){
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