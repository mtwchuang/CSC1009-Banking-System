package Model.Transaction;

public class BalanceChange extends Transaction{
    private boolean executedOnAtm = false;
    private String AtmID;

    private boolean executedOnPurchase = false;
    private String merchantName;

    public BalanceChange(String transactionSrcBankAccID){
        super(transactionSrcBankAccID);
    }

    public boolean isExecutedOnAtm() {
        return executedOnAtm;
    }
    public String getAtmID() {
        return AtmID;
    }

    public boolean isExecutedOnPurchase() {
        return executedOnPurchase;
    }
    public String getMerchantName() {
        return merchantName;
    }

    public void setAtmID(String atmID) {
        this.AtmID = atmID;
        this.executedOnAtm = true;
        updated();
    }
    
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
        this.executedOnPurchase = true;
        updated();
    }
}