package Model.Transaction;

import java.util.UUID;

public class BalanceChange extends Transaction{
    private boolean executedOnAtm;
    private String AtmID;

    private boolean executedOnPurchase;
    private String merchantID;

    public BalanceChange(UUID transactionSrcBankAccID, short transactionSrcBankAccBranch){
        super(transactionSrcBankAccID, transactionSrcBankAccBranch);
    }

    public boolean isExecutedOnAtm() {
        return executedOnAtm;
    }
    public String getAtmID() {
        return AtmID;
    }

    public String getMerchantID() {
        return merchantID;
    }
    public boolean isExecutedOnPurchase() {
        return executedOnPurchase;
    }

    public void setAtmID(String atmID) {
        AtmID = atmID;
    }
    public void setExecutedOnAtm(boolean executedOnAtm) {
        this.executedOnAtm = executedOnAtm;
    }
    
    public void setExecutedOnPurchase(boolean executedOnPurchase) {
        this.executedOnPurchase = executedOnPurchase;
    }
    public void setMerchantID(String merchantID) {
        this.merchantID = merchantID;
    }
}