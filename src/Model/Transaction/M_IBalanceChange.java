package Model.Transaction;

public interface M_IBalanceChange {
    public boolean isExecutedOnAtm();
    public String getAtmID();
    public boolean isExecutedOnPurchase();
    public String getMerchantName();
    public void setAtmID(String atmID);
    public void setMerchantName(String merchantName);
}
