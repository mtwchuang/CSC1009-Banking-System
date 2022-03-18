package Model.Transaction;
public interface M_IBalanceChange extends M_ITransaction
{
    // getter and setter abstract methods for M_BalanceChange
    public boolean isExecutedOnAtm();
    public String getAtmID();
    public boolean isExecutedOnPurchase();
    // public void setExecutedOnAtm(boolean executedOnAtm); (should be non-modifiable)
    // public void setExecutedOnPurchase(boolean executedOnPurchase); (should be non-modifiable)
    // public void setAtmID(String atmID); (should be non-modifable)
}
