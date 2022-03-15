package Model.Transaction;
public interface M_IBalanceChange extends M_ITransaction
{
    // getter and setter abstract methods for M_BalanceChange
    public boolean isExecutedOnAtm();
    public String getAtmID();
    public boolean isExecutedOnPurchase();
    public String getMerchantName();
    public void setAtmID(String atmID);
    public void setMerchantName(String merchantName);
}
