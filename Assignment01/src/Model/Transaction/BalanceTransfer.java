package Model.Transaction;

import java.util.UUID;

public class BalanceTransfer extends Transaction{

    
    public BalanceTransfer(UUID transactionSrcBankAccID, short transactionSrcBankAccBranch){
        super(transactionSrcBankAccID, transactionSrcBankAccBranch);
    }
}